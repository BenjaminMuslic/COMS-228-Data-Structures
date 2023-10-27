package edu.iastate.cs228.hw2;

/**
 * @author 
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class CompareSorters {

	/**
	 * Main method that compares the performance of four different sorting
	 * algorithms on randomly generated or read points from a file.
	 *
	 * @param args command line arguments
	 */

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Performance of Sorting Algorithms in Finding Points\n");
		System.out.println("Keys: 1 (random integers) 2 (file input) 3 (exit)");

		int trial = 1;
		int option = 0;
		int amount = 0;

		while (true) {
			System.out.print("Trial " + trial + ": ");
			trial++;

			option = scanner.nextInt();

			// If the user entered 3, break out of the loop and exit the program
			if (option == 3) {
				System.out.println("Exited");
				break;
			}

			// If the user entered an invalid option, skip this trial and continue the loop
			if (option != 1 && option != 2 && option != 3) {
				System.err.println("Invalid choice. Please enter 1, 2, or 3.\n");
				continue;
			}

			// Generate or read points depending on user's choice
			Point[] points = null;
			if (option == 1) {
				System.out.print("Enter the number of random points to generate: ");
				amount = scanner.nextInt();
				Random random = new Random(5);
				points = generateRandomPoints(amount, random);
			} else {
				System.out.print("Enter the name of the file containing the points: ");
				String fileName = scanner.next();
				try {
					points = readPointsFromFile(fileName);
				} catch (FileNotFoundException e) {
					System.err.println("Error reading from file: " + e.getMessage());
				}
			}

//			a) Create an array of randomly generated integers, if needed.
//			b) Construct four PointScanner objects over the point array, each with a different
//			algorithm (i.e., a different value for the parameter algo of the constructor).
//			c) Have every created PointScanner objects call scan().
//			d) At the end of the round, output the statistics by having every PointScanner object call
//			the stats() method.
			// Initialize PointScanner objects with the different sorting algorithms
			PointScanner[] PointScanner = new PointScanner[4];
			PointScanner[0] = new PointScanner(points, Algorithm.SelectionSort);
			PointScanner[1] = new PointScanner(points, Algorithm.InsertionSort);
			PointScanner[2] = new PointScanner(points, Algorithm.MergeSort);
			PointScanner[3] = new PointScanner(points, Algorithm.QuickSort);

			// Print table header
			System.out.println("\nAlgorithm      Size   Time (ns)");
			System.out.println("---------------------------------");

			// Call scan() method for each PointScanner object and print statistics
			for (PointScanner pointScan : PointScanner) {
				pointScan.scan();
			}
			for (PointScanner pointScan : PointScanner) {
				System.out.println(pointScan.stats());
			}

			// Print table footer
			System.out.println("---------------------------------\n");
		}

		scanner.close();
	}

	private static Point[] readPointsFromFile(String fileName) throws FileNotFoundException {
		Scanner read_file = new Scanner(new File(fileName));
		int numPoints = read_file.nextInt();
		Point[] points = new Point[numPoints];
		for (int i = 0; i < numPoints; i++) {
			int x = read_file.nextInt();
			int y = read_file.nextInt();
			points[i] = new Point(x, y);
		}
		read_file.close();
		return points;
	}

	/**
	 * Generates an array of random points with the given number of points and
	 * random number generator.
	 *
	 * @param numPts the number of random points to generate
	 * @param rand   the random number generator
	 * @throws IllegalArgumentException if numPts is less than 1
	 */
//	To test your code, you may generate random points within the range [−50, 50] × [−50, 50].   
//	Such a point has its 𝑥- and 𝑦-coordinates generated separately as pseudo-random numbers 
//	within the range [−50, 50].  You already had experience with random number generation from 
//	Project 1.  Import the Java package java.util.Random.  Next, declare and initiate a 
//	Random object like below   
//	Random generator = new Random();  
//	Then, the expression  
//	generator.nextInt(101) – 50  
//	will generate a pseudo-random number between -50 and 50 every time it is executed. 
	private static Point[] generateRandomPoints(int numPts, Random rand) {
		try {
			if (numPts < 1) {
				throw new IllegalArgumentException("Number of points must be at least 1.");
			}
			Point[] points = new Point[numPts];
			for (int i = 0; i < points.length; i++) {
				int x = rand.nextInt(101) - 50;
				int y = rand.nextInt(101) - 50;
				points[i] = new Point(x, y);
			}
			return points;
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
}
