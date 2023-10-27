package sorting_algorithms;

//divide and conquer algorithm
//recursive algorithm
//Two phases: splitting and merging
//Splitting phase leads to a faster sorting during the merging phase
//Splitting is logical. We don't create new arrays

//splitting phase:
//start with an unsorted array
//Divide the array into two arrays, which are unsorted. The first array is the left array, and the second array is
//the right array
//Split the left and right arrays into two arrays each
//Keep splitting until all the arrays have only one element each - these arrays are sorted

//merging phase:
//Merge every left/right pair of sibling arrays into a sorted array
//After the first merge, we'll have a bunch of 2-element sorted arrays
//Then merge those sorted arrays (left/right siblings) to end up with a bunch of 4-element sorted arrays
//Repeat until you have a single sorted array
//Not in-place. Uses temporary arrays

public class merge_sort {

	public static void main(String[] args) {

		int[] intArray = { 20, 35, -15, 7, 55, 1, -22 };

		mergeSort(intArray, 0, intArray.length);

		for (int i = 0; i < intArray.length; i++) {
			System.out.println(intArray[i]);
		}

	}

	public static void mergeSort(int[] input, int start, int end) {
		if (end - start < 2) {
			return;
		}

		int mid = (start + end) / 2;
		mergeSort(input, start, mid);
		mergeSort(input, mid, end);
		merge(input, start, mid, end);
	}

	public static void merge(int[] input, int start, int mid, int end) {
		if (input[mid - 1] <= input[mid]) {
			return;
		}
		int i = start;
		int j = mid;
		int tempIndex = 0;

		int[] temp = new int[end - start];

		while (i < mid && j < end) {
			temp[tempIndex++] = input[i] <= input[j] ? input[i++] : input[j++];
		}
		System.arraycopy(input, i, input, start + tempIndex, mid - i);
		System.arraycopy(temp, 0, input, start, tempIndex);
	}
}
