import java.io.*;
/*Daniel Frost
 * CSC501
 * Data Structures and Computer Systems
 * March, 2017
 *
 * The point of this program as assigned in Dr. Fox's CSC501 class is to analyze the effectiveness of various sorting
 * algorithms.  The algorithms being tested are Insertion Sort, Bubble Sort, Selection Sort, Merge Sort, and Quick Sort.
 * These will be tested by reading a set of values in from a file, and storing said values into different arrays
 * that correspond to the various sorting methods.  The first value in each file will be the size of the necessary array.
 * This represents the amount of ints in the .txt file, and allows specific implementation.  Then, each of the different
 * arrays will be sorted using their named sorting algorithm.  A variable called operationCount will be used to
 * determine how many operations were needed in the process of sorting.  Then, it will be easy to compare the validity
 * and efficiency of all tested algorithms.*/

/*This class is used as the driver class, and will handle all operation as per the program 3 assignment.  It includes
* member variables for the operationCount, a FileInputStream, a BufferedReader, and the various arrays for the different
* sorting algorithms.  Additionally, several other various variables are declared for specific reasons, which will be
* detailed in future comments.*/
public class Driver {
    private static FileInputStream in; //declaring global variables for a buffered reader and an input stream here
    private static BufferedReader reader;
    private static int operationCount;
    public static void main(String[] args) {
        try {
        int[] tempArray; //various arrays that will demonstrate sorting algorithms,
        int[] insertionArray; //with tempArray being the intermediate holding position
        int[] bubbleArray;
        int[] selectionArray;
        int[] mergeArray;
        int[] quickArray;

            in = new FileInputStream("C:\\Users\\Daniel\\IdeaProjects\\FrostProgram3\\src\\input4.txt"); //this name can be changed to
            reader = new BufferedReader(new InputStreamReader(in));                                             //to indicate different filepaths
            String x; int i = 0; //string x will be used for reading in new ints, and i is an incrementation variable later described
            tempArray = new int[Integer.parseInt(reader.readLine())]; //we read in the first value and instantiate the array to that size
            while ((x = reader.readLine()) != null) { //here we read until the BufferedReader reads in null
                tempArray[i] = Integer.parseInt(x); //and convert the string read in into an int, and add it to the array
                i++; //and increment the value of i, so that the next value we add to the array is at the next index
            }
            insertionArray = new int[tempArray.length]; //these lines instantiate each of the other arrays to
            bubbleArray = new int[tempArray.length]; //the size of tempArray (which depends on the txt file)
            selectionArray = new int[tempArray.length];
            mergeArray = new int[tempArray.length];
            quickArray = new int[tempArray.length];
            for (int j = 0; j < tempArray.length; j++) { //here we look through tempArray, and populate the other arrays based on
                insertionArray[j] = tempArray[j];        //the value present at this index in tempArray.
                bubbleArray[j] = tempArray[j];
                selectionArray[j] = tempArray[j];
                mergeArray[j] = tempArray[j];
                quickArray[j] = tempArray[j];
            }
            System.out.println("n = " + tempArray.length); //each of the following three sets of instructions include resetting the
            operationCount = 0;                            //the value of operationCount as well as running the specified sort on the corresponding array.
            insertionSort(insertionArray);                 //we then print out the results of operationCount for each algorithm.
            System.out.println("Insertion Sort: " + operationCount);
            operationCount = 0;
            bubbleSort(bubbleArray);
            System.out.println("Bubble Sort: " + operationCount);
            operationCount = 0;
            selectionSort(selectionArray);
            System.out.println("Selection Sort: " + operationCount);
            operationCount = 0;
            mergeSort(mergeArray);
            System.out.println("Merge Sort: " + operationCount);
            operationCount = 0;
            int low = 0;                        //the circumstance for quickSort required a low and high value.  we set the low value
            int high = quickArray.length - 1;       //to the beginning of the array, and the high value as the end of the array.
            quickSort(quickArray, low, high);       //these are then changed within the function recursively to perform the quickSort.
            System.out.println("Quick Sort: " + operationCount);
        }
        catch (IOException io) {}
        catch (Exception ex) {}

    }

    /*This is the quickSort function.  It has O(n) Big O notation.  It utilizes a partition algorithm (here
    * contained in the quickSort method).  It iterates from left to right through the array using
    * the low value, and from right to left using the high value, until they overlap.  */
    public static void quickSort(int array[], int low, int high) {
        int i = low, j = high; //use i and j for simplicity by equating these to low and high from params
        int temp;
        int pivot = array[(low + high) / 2]; //we calculate our first pivot point, being the mid of high and low.  changes
                                            //for each recursive call.
        /*This is the partition part of the algorithm*/
        while (i <= j) {  // while the two values haven't overlapped, we continue the algorithm for incrementation
            operationCount++;
            while (array[i] < pivot) { //and while the value of low moving right is less than our selected pivot (mid)
                operationCount++; //we count this comparison as an operation, and continue to increment low (i).
                i++;
            }
            while (array[j] > pivot) { //and while the value of high moving left is greater than the selected pivot (mid)
                operationCount++;   //this also gets counted as an operation, and we decrement high to move to the left
                j--;
            }
            if (i <= j) {
                temp = array[i];  //we use a swap algorithm to swap the values of low and high as long as they haven't overlapped
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }
        operationCount++;
        /*Recursively call*/
        if (low < j) {
            //we continue to do a recursive call, as long as the value of high doesnt reach the beginning of the array
            quickSort(array, low, j); //in our recursive call, we have to change the low and high values (high becomes the new
                                        //decremented value)
        }
        if (i < high) {
            //and we continue to call on the other half, as long as the value of low doesn't become equal to the last value
            quickSort(array, i, high); //in our recursive call, we change the low value to be the incremented value
        }
    }


    /*Merge sort is also a recursive algorithm in order to implement sorting.  This time, we have split up the algorithm
    * into a mergeSort, and an actual merge.  The algorithm works by splitting the array into two halves recursively and
    * doing so until the array is down to 1 value.  Then, they are combined using merge in order to creat
    * a final sorted array.*/
    public static void mergeSort(int[] array) {
        if (array.length > 1) {  //we make sure there actual is more than 1 value
            int mid = array.length / 2;  //and we calculate the position at which we will split the array into
            int[] left = new int[mid]; //two halves, and then we declare the left and right arrays based on the sizes (left goes to mid, right goes to end)
            int[] right = new int[array.length - mid];
            for (int i = 0; i < mid; i++) { //here we loop through the first half of the passed in array, and copy the values into the left half
                operationCount++;
                left[i] = array[i];
            }
            for (int i = mid; i < array.length; i++) { //and now we do the same for the second half and copy them into the right half.
                operationCount++;
                right[i - mid] = array[i];
            }
            mergeSort(left); //do the same and keep cutting the array in half, until array.length = 1
            mergeSort(right); //do the same for the right half
            merge(array, left, right); //and call merge once these are completed, to combine values into one array
        }
    }

    /*This is the merge portion of the mergeSort algorithm*/
    public static int[] merge(int[] array, int[] l, int[] r) {
        int index1 = 0, index2 = 0, index3 = 0;
        while ((index1 < l.length) && (index2 < r.length)) {
            if (l[index1] < r[index2]) {
                operationCount++;
                array[index3++] = l[index1++];
            }
            else {
                operationCount++;
                array[index3++] = r[index2++];
            }
        }
        while (index1 < l.length)  { //this is reached if we reach the end of the right half array, and then copy
            operationCount++;       //the left side at the end of the array
            array[index3++] = l[index1++];
        }
        while (index2 < r.length) {  //this is reached if we reach the end of the left half, and then we copy
            operationCount++;        //the right side at the end of the array
            array[index3++] = r[index2++];
        }
        return array;
    }

    /*This is the implementation for the selectionSort algorithm.  This algorithm
    * searches the array to find the smallest item between i and n - 1, and swap it with position
     * at i.  */
    public static void selectionSort(int[] array) {
        int n = array.length;
        int temp;
        int min;
        int minIndex;
        for (int i = 0; i < n - 1; i++) {
            temp = array[i]; //here we loop through, and copy the value of i into a temporary variable
            min = array[i]; //and set the minimum value as well as the minimum index.
            minIndex = i;
            for (int k = i+1; k < n; k++) {
                operationCount++; //we then loop through to compare this to the other values
                if (array[k] < min) { //once we find a value that is smaller than minimum
                    min = array[k]; //we set this value as the new minimum
                    minIndex = k; //and save its index
                }
            }
            array[i] = min; //after having the new minimum, we set the value at i to this value, and set the
            array[minIndex] = temp; //value at the new minIndex to that of temp (performing a swap)
        }
    }

    /*Insertion sort is the algorithm used by searching through the array, and copying the value at the current increment, so
     * when we find the correct location, we can insert it. */
    public static void insertionSort(int[] array) {
        int size = array.length;
        for (int j = 1; j < size; j++) { //here we loop through the array, and copy the value at that increment into our key variable
            int key = array[j]; //which we will use to compare, and find the spot for insertion.
            int i = j - 1; //i set to the end of the array, so we can compare our value in reverse order (backward)
            while ((i > -1) && (array[i] > key)) { //and we loop while the value at this index is still greater than our key value
                operationCount++;
                array[i+1] = array[i]; //once we find that value, we copy the next increment value
                i--; //continue to move backward
            }
            array[i+1] = key;
        }
    }

    /*This is the bubble sort, which is the easiest to implement. It compares each value to every other
     * value in the array.  It swaps the item on the left with the item on the right if the value on the left
      * is greater, and in this way moves all the way through the array.*/
    public static void bubbleSort(int[] array) {
        boolean sorted = false; //this is our flag to break out of the loop
        int length = array.length;
        int temp;
        while (!sorted) { //and while its not sorted, we will continue moving through the loop.  an alternate implementation is to use nested for loops
            sorted = true; //we will assume that the array is sorted, and if its not we will change the value in the if to continue the loop
            for (int i = 0; i < length - 1; i++) {
                operationCount++;
                if (array[i] > array[i+1]) { //as long as the value at the index is greater than the following value
                    temp = array[i]; //we use a swap algorithm to switch the value
                    array[i] = array[i+1];
                    array[i+1] = temp;
                    sorted = false; //and say that its not sorted, and we need to check again
                }
            }
            length--;
        }
    }
}
