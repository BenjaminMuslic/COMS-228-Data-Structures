package sorting_algorithms;

//the following is a implementation of bubble sort. It will "bubble" values into new array in ascending order from the original array
//Big-O Time complexity O(n^2) quadratic
//not efficient
//stable sort algorithm (preserves the relative ordering of duplicate items)
public class bubble_sort {

	public static void main(String[] args) {

		int[] intArray = { 20, 35, -15, 7, 55, 1, -22 };

		for (int lastUnsortedIndex = intArray.length - 1; lastUnsortedIndex > 0; lastUnsortedIndex--) {
			for (int i = 0; i < lastUnsortedIndex; i++) {
				if (intArray[i] > intArray[i + 1]) {
					swap(intArray, i, i + 1);
				}
			}
		}
		for (int i = 0; i < intArray.length; i++) {
			System.out.println(intArray[i]);
		}
	}

	/**
	 * when this method is static it can be called from the main method method for
	 * swapping elements
	 */
	public static void swap(int[] array, int i, int j) {

		if (i == j) {
			return;
		}
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

}
