package edu.iastate.cs228.hw2;

/**
 * 
 * @author 
 *
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * This class sorts all the points in an array of 2D points to determine a
 * reference point whose x and y coordinates are respectively the medians of the
 * x and y coordinates of the original points.
 * 
 * It records the employed sorting algorithm as well as the sorting time for
 * comparison.
 *
 */
public class PointScanner {
	private Point[] points;

	private Point median_cord_point; // point whose x and y coordinates are respectively the medians of
										// the x coordinates and y coordinates of those points in the array
										// points[].
	private Algorithm sorting_algo;

	protected long scanning_time; // execution time in nanoseconds.

	/**
	 * This constructor accepts an array of points and one of the four sorting
	 * algorithms as input. Copy the points into the array points[].
	 * 
	 * @param pts input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException {

		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException();
		}

		points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++) {
			Point point = new Point(pts[i]);
			points[i] = point;
		}

		sorting_algo = algo;

	}

	/**
	 * This constructor reads points from a file.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException if the input file contains an odd number of
	 *                                integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException {
		int amount = 0;
		int input_number = 0;
		ArrayList<Integer> list = new ArrayList<Integer>();
		File file = new File(inputFileName);
		Scanner fileScanner = new Scanner(file);
		sorting_algo = algo;

		while (fileScanner.hasNext()) {
			if (fileScanner.hasNextInt()) {
				input_number = fileScanner.nextInt();
				list.add(input_number);
				amount++;
			}

			else {
				fileScanner.next();
			}
		}

		fileScanner.close();

		if (amount % 2 == 1) {
			throw new InputMismatchException();
		}

		int p = list.size() / 2;
		points = new Point[p];

		int i = 0;
		int j = 0;
		for (i = 0; i < list.size(); i += 2) {
			points[j++] = new Point(list.get(i), list.get(i + 1));
		}

	}

	/**
	 * Carry out two rounds of sorting using the algorithm designated by
	 * sortingAlgorithm as follows:
	 * 
	 * a) Sort points[] by the x-coordinate to get the median x-coordinate. b) Sort
	 * points[] again by the y-coordinate to get the median y-coordinate. c)
	 * Construct medianCoordinatePoint using the obtained median x- and
	 * y-coordinates.
	 * 
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter,
	 * InsertionSorter, MergeSorter, or QuickSorter to carry out sorting.
	 * 
	 * @param algo
	 * @return
	 */
	public void scan() {
		// TODO
		AbstractSorter abstractSorter = null;

		if (sorting_algo == Algorithm.SelectionSort) {
			abstractSorter = new SelectionSorter(points);
		} else if (sorting_algo == Algorithm.InsertionSort) {
			abstractSorter = new InsertionSorter(points);
		} else if (sorting_algo == Algorithm.MergeSort) {
			abstractSorter = new MergeSorter(points);
		} else if (sorting_algo == Algorithm.QuickSort) {
			abstractSorter = new QuickSorter(points);
		}

		abstractSorter.setComparator(0);
		long result = System.nanoTime();
		abstractSorter.sort();
		int mid_cord_x = abstractSorter.getMedian().getX();

		abstractSorter.setComparator(1);
		abstractSorter.sort();
		int mid_cord_y = abstractSorter.getMedian().getY();

		median_cord_point = new Point(mid_cord_x, mid_cord_y);

		result = System.nanoTime() - result;
		scanning_time = result;

	}

	/**
	 * Outputs performance statistics in the format:
	 * 
	 * <sorting algorithm> <size> <time>
	 * 
	 * For instance,
	 * 
	 * selection sort 1000 9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description.
	 */
	public String stats() {
		String algo_output = "   ";
		if (sorting_algo == Algorithm.MergeSort || sorting_algo == Algorithm.QuickSort) {
			algo_output = algo_output + "    ";
		}
		String stats = sorting_algo + algo_output + points.length + "     " + scanning_time;
		return stats;
	}

	/**
	 * Write MCP after a call to scan(), in the format "MCP: (x, y)" The x and y
	 * coordinates of the point are displayed on the same line with exactly one
	 * blank space in between.
	 */
	@Override
	public String toString() {
		int x = median_cord_point.getX();
		int y = median_cord_point.getY();

		String MCP = "MCP: (" + x + ", " + y + ")";
		return MCP;
	}

	/**
	 * 
	 * This method, called after scanning, writes point data into a file by
	 * outputFileName. The format of data in the file is the same as printed out
	 * from toString(). The file can help you verify the full correctness of a
	 * sorting result and debug the underlying algorithm.
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException {
		String string = sorting_algo + ".txt";
		File file = new File(string);

		PrintWriter printWriter = new PrintWriter(file);
		printWriter.println(toString());
		printWriter.close();
	}

}
