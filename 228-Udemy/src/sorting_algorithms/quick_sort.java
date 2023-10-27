package sorting_algorithms;

//Divide and conquer algorithm
//Recursive algorithm
//Uses a pivot element to partition the array into two parts
//Elements < pivot to its left, elements > pivot to its right
//Pivot will then be in its correct sorted position

//process is now repeated for the left array and right array
//eventually, every element has been the pivot, so every element will be in its correct sorted position
//As with merge sort, we'll end up partitioning the array into a series of 1-element arrays
//Does this in-place (unlike merge sort)

//in-place algorithm
//O(nlogn)-base 2. We are repeatedly partitioning the array into two halves
//Unstable

public class quick_sort {

	public static void main(String[] args) {

		int[] intArray = { 20, 35, -15, 7, 55, 1, -22 };

		quickSort(intArray, 0, intArray.length);

		for (int i = 0; i < intArray.length; i++) {
			System.out.println(intArray[i]);
		}
	}

	public static void quickSort(int[] input, int start, int end) {
		if (end - start < 2) {
			return;
		}
		int pivotIndex = partition(input, start, end);
		quickSort(input, start, pivotIndex);
		quickSort(input, pivotIndex + 1, end);

	}

	public static int partition(int[] input, int start, int end) {
		// This is using the first element as the pivot
		int pivot = input[start];
		int i = start;
		int j = end;

		while (i < j) {

			// empty loop body
			while (i < j && input[--j] >= pivot)
				;
			if (i < j) {
				input[i] = input[j];
			}
			// empty loop body
			while (i < j && input[++i] <= pivot)
				;
			if (i < j) {
				input[j] = input[i];
			}
		}
		input[j] = pivot;
		return j;
	}
}
