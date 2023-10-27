package sorting_algorithms;

//Makes assumptions about the data
//Does not use comparisons
//Counts the number of occurrences of each value
//Only works with non-negative discrete values (can't work with floats, strings)
//Values must be within a specific range

//NOT an in-place algorithm
//O(n) - can achieve this because we're making assumptions about the data we're sorting
//If we want the sort to be stable, we have to do some extra steps

public class counting_sort {

	public static void main(String[] args) {

		int[] intArray = { 2, 5, 9, 8, 2, 8, 7, 10, 4, 3 };

		countingSort(intArray, 1, 10);

		for (int i = 0; i < intArray.length; i++) {
			System.out.println(intArray[i]);
		}

	}

	public static void countingSort(int[] input, int min, int max) {

		int[] countArray = new int[(max - min) + 1];

		for (int i = 0; i < input.length; i++) {
			countArray[input[i] - min]++;
		}
		int j = 0;
		for (int i = min; i <= max; i++) {
			while (countArray[i - min] > 0) {
				input[j++] = i;
				countArray[i - min]--;
			}
		}
	}

}
