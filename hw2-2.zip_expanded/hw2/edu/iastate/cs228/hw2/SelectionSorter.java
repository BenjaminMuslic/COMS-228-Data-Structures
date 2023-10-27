package edu.iastate.cs228.hw2;

/**
 *  
 * @author 
 *
 */

/**
 * 
 * This class implements selection sort.
 *
 */

public class SelectionSorter extends AbstractSorter {
	// Other private instance variables if you need ...

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts
	 */
	public SelectionSorter(Point[] pts) {
		super(pts);
		algorithm = "selection sort";
	}

	/**
	 * Apply selection sort on the array points[] of the parent class
	 * AbstractSorter.
	 * 
	 */
	@Override
	public void sort() {
		for (int index = 0; index < points.length - 1; index++) {
			int small = index;
			for (int j = index + 1; j < points.length; j++) {
				if (pointComparator.compare(points[j], points[small]) < 0) {
					small = j;
				}
			}
			swap(index, small);
		}
	}
}
