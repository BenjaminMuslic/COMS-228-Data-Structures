package edu.iastate.cs228.hw2;

/**
 *  
 * @author 
 * 
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.
 *
 */

public class MergeSorter extends AbstractSorter {
	// Other private instance variables if needed

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts input array of integers
	 */
	public MergeSorter(Point[] pts) {

		super(pts);
		algorithm = "mergesort";
	}

	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter.
	 * 
	 */
	@Override
	public void sort() {
		mergeSortRec(points);
	}

	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of
	 * points. One way is to make copies of the two halves of pts[], recursively
	 * call mergeSort on them, and merge the two sorted subarrays into pts[].
	 * 
	 * @param pts point array
	 */
	private void mergeSortRec(Point[] pts) {

		if (pts.length <= 1) {
			return;
		}

		Point[] left = new Point[pts.length / 2];
		Point[] right = new Point[pts.length - (pts.length / 2)];
		for (int index = 0; index < left.length; index++) {
			left[index] = pts[index];
		}

		for (int j = 0; j < right.length; j++) {
			right[j] = pts[left.length + j];
		}

		mergeSortRec(left);
		mergeSortRec(right);
		merge(pts, left, right);
	}

	private void merge(Point[] pts, Point[] left, Point[] right) {
		int mergeSize = pts.length;
		int mergePos = 0;
		int leftPosition = 0;
		int rightPosition = 0;
		Point[] mergedPoints = new Point[mergeSize];

		while (leftPosition < left.length && rightPosition < right.length) {
			if (left[leftPosition].compareTo(right[rightPosition]) <= 0) {
				mergedPoints[mergePos] = left[leftPosition];
				++leftPosition;
			} else {
				mergedPoints[mergePos] = right[rightPosition];
				++rightPosition;
			}
			++mergePos;
		}

		while (leftPosition < left.length) {
			mergedPoints[mergePos] = left[leftPosition];
			++leftPosition;
			++mergePos;
		}

		while (rightPosition < right.length) {
			mergedPoints[mergePos] = right[rightPosition];
			++rightPosition;
			++mergePos;
		}

		for (mergePos = 0; mergePos < mergeSize; mergePos++) {
			pts[mergePos] = mergedPoints[mergePos];
		}
	}

	// Other private methods if needed ...

}
