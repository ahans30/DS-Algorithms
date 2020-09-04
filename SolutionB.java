import java.util.Scanner;

public class SolutionB {

  public static void main(String[] args) {
    /**
     * Problem Statement: You are given an array of integers that represents the IDs of the users that have registered in “UpgradBook.”
     *
     * The array of integers have the following properties:
     * > The array is sorted is an ascending order
     * > The array holds distinct integers (i.e. there are no repeating numbers)
     * > The array is 1-indexed, i.e. the first element is stored in A[1] (not A[0])
     * > However the length of the array, N, is unknown (i.e. you don’t know how long the array is and arrayName.length is
     * not available to you). In short, you can not use the length of the array to apply binary search
     */

    Scanner sc = new Scanner(System.in);
    System.out.println("Please enter the number of users: ");
    int n = sc.nextInt();
    System.out.println("\nPlease enter the key to be searched: ");
    int key = sc.nextInt();
    int[] upgradBookId = new int[n + 1];
    upgradBookId[0] = -1;
    System.out.println("\nPlease enter users IDs: ");
    for (int i = 1; i <= n; i++) {
      upgradBookId[i] = sc.nextInt();
    }
    if (isSortedAndDistinct(upgradBookId)) {
      getAnswer(upgradBookId, key);
    }
  }

  public static void getAnswer(int[] A, int key) {
    getAnswer(A, key, 1, 2);
  }

  private static void getAnswer(int[] A, int key, int lo, int hi) {
    /**
     * This method consists 3 parts:
     *
     * 1> Finding an upper bound on input array size N - tightest upper bound in powers of 2 - since we double
     *    the 'hi' in each recursive step in try block
     *
     *    Time Complexity: O(lg N) since we double the of 'hi' - we find upper bound to N in lg N (base 2) + 1 steps
     *
     * 2> Finding tight bound on N from 'hi' - we zero in on N by recursively cutting down difference to half
     *    each time in catch block where we move 'mid' (middle of lo/hi) until we reach acceptable N
     *
     *    Time Complexity: O(lg N) - since we divide the distance of 'lo' and 'hi' in two halves in each steps.
     *
     * 3> Binary Search of 'key': Once we find acceptable values of 'lo' and 'hi' - we call binary search to find 'key'
     *    in given array
     *
     *    Time Complexity: O(lg N)
     *
     * Since, all three of above steps are called in specified order i.e. sequentially - we can add complexities to
     * give the complexity of overall method/algorithm
     *
     * Algorithm Time Complexity: O(lg N + lg N + lg N) = O(lg N)
     */
    try {
      if (lo == hi) { //Base Condition check for recursion: i.e. lo == hi == N (end of list/array)
        if (A[lo] == key) {
          System.out.print(lo);
        } else {
          System.out.print("NOT_FOUND");
        }
        return;
      }

      if (key <= A[hi]) {
        //We can conclusively say if 'key' is in array (sorted/distinct) we can find it using binary search from '0' to 'hi'
        //For optimization, as we know 'key' >= A[lo] - we run binary search from index 'lo' to 'hi'.
        int ans = binarySearch(A, key, lo, hi);
        if (ans == -1) {
          System.out.print("NOT_FOUND");
        } else {
          System.out.print(ans);
        }
      } else if (key > A[hi]) {
        /* If key is greater than A[hi] i.e. it is not present in indices less than 'hi' - we recursively
         *  search/check for our answer in indices 'hi' to 2*'hi' */
        getAnswer(A, key, hi, 2 * hi);
      }

    } catch (Exception e) {
      /* If and when we get an ArrayIndexOutOfBoundsException exception thrown from the try block - we reach here
      *  This will happen when our 'hi' > N (=length of our 1-INDEXED array)
      *  So as a result, this gives us info that we have over-calculated upper bound 'hi' and therefore we will
      reduce the upper bound to middle of 'hi' and 'lo' while keeping the 'lo' static

      *  We intend to hit the base condition of recursive call so as to find value of N */
      int mid = lo + (hi - lo) / 2;
      getAnswer(A, key, lo, mid);
    }
  }

  private static int binarySearch(int[] arr, int key, int lo, int hi) {
    if (lo > hi) {
      return -1;
    }
    int mid = lo + (hi - lo) / 2;
    if (arr[mid] > key) {
      return binarySearch(arr, key, lo, mid - 1); //first of array
    } else if (arr[mid] < key) {
      return binarySearch(arr, key, mid + 1, hi); //second half of array
    } else {
      return mid;
    }
  }

  public static boolean isSortedAndDistinct(int[] A) {
    //Uses private helper methods viz. isSorted() and hasDistinctElements().
    //Please refer to comment documentation of isSorted() and asDistinctElements().

    if (isSorted(A)) {
      return hasDistinctElements(A);
    } else {
      return false;
    }
  }

  private static boolean isSorted(int[] A) {
    //Returns true if array is sorted from index 1 to N (1-based array); false otherwise

    //Time Complexity: O(N): Goes through list/array element by element once
    //Space Complexity O(1): Does not have any auxiliary array

    for (int index = 2; index < A.length; ++index) {
      if (!(A[index - 1] <= A[index])) {
        System.out.println("\nThe input IDs are not sorted. Please try again.");
        return false;
      }
    }
    return true;
  }

  private static boolean hasDistinctElements(int[] A) {
    /*
     * 1> Returns true if all elements from index 1 to N (1-based array) are distinct; false otherwise.
     * 2> Please note, in a sorted array any two elements with equal keys (integer here) will be adjacent in the array.
     *    Therefore, this method relies on isSorted method to assert that all elements are distinct (or not).
     */

    //Time Complexity: O(N): Goes through list/array element by element once
    //Space Complexity O(1): Does not have any auxiliary array

    for (int index = 2; index < A.length; ++index) {
      if (A[index - 1] == A[index]) {
        System.out
            .println("\nThe input IDs are not unique/distinct. Please try again.");
        return false;
      }
    }
    return true;
  }
}
