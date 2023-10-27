package sorting_algorithms;

//Makes assumptions about the date
//Data must have same radix and width
//Because of this, data must be integers or strings
//Sort based on each individual digit or letter position
//Start at the rightmost position
//Must use a stable sort algorithm at each state

//Counting sort is often used as the sort algorithm for radix sort
//-must be stable counting sort
//O(n)- can achieve this because we're making assumptions about the data we're sorting
//Even so, it often runs slower than O(nlogn) algorithms because of the overhead involved
//In-place depends on which sort algorithm you use
//Stable algorithm

public class radix_sort {

	public static void main(String[] args) {

		int[] radixArray = { 4725, 4586, 1330, 8792, 1594, 5729 };

		radixSort(radixArray, 10, 4);

		for (int i = 0; i < radixArray.length; i++) {
			System.out.println(radixArray[i]);
		}

	}

	public static void radixSort(int[] input, int radix, int width) {
		for (int i = 0; i < width; i++) {
			radixSingleSort(input, i, radix);
		}
	}

	public static void radixSingleSort(int[] input, int position, int radix) {

		int numItems = input.length;

		int[] countArray = new int[radix];

		for (int value : input) {
			countArray[getDigit(position, value, radix)]++;

		}
		// adjust the count array
		for (int j = 1; j < radix; j++) {
			countArray[j] += countArray[j - 1];
		}

		int[] temp = new int[numItems];
		for (int tempIndex = numItems - 1; tempIndex >= 0; tempIndex--) {
			temp[--countArray[getDigit(position, input[tempIndex], radix)]] = input[tempIndex];
		}

		for (int tempIndex = 0; tempIndex < numItems; tempIndex++) {
			input[tempIndex] = temp[tempIndex];
		}
	}

	public static int getDigit(int position, int value, int radix) {
		return value / (int) Math.pow(radix, position) % radix;
	}

}
