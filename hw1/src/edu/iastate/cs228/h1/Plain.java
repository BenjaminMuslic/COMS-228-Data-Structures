package edu.iastate.cs228.h1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * @author
 * 
 *         The plain is represented as a square grid of size width x width.
 *
 */
public class Plain {
	private int width; // grid size: width X width

	public Living[][] grid;

	/**
	 * Default constructor reads from a file
	 */
	public Plain(String inputFileName) throws FileNotFoundException {

		File file = new File(inputFileName);
		Scanner gridWidth = new Scanner(file); // scan file to extract grid width
		Scanner findLiving = new Scanner(file); // scan file to extract living objects
		String line = gridWidth.nextLine();
		this.width = line.length() / 3; // grid width
		grid = new Living[width][width];

		for (int plain_length = 0; plain_length < width; plain_length++) {
			for (int plain_width = 0; plain_width < width; plain_width++) {

				String animal = findLiving.next();

				if (animal.charAt(0) == 'B') { // check each animal
					int age = Character.getNumericValue(animal.charAt(1));
					grid[plain_length][plain_width] = new Badger(this, plain_length, plain_width, age);
				} else if (animal.charAt(0) == 'E') {
					grid[plain_length][plain_width] = new Empty(this, plain_length, plain_width);
				} else if (animal.charAt(0) == 'F') {
					int age = Character.getNumericValue(animal.charAt(1));
					grid[plain_length][plain_width] = new Fox(this, plain_length, plain_width, age);
				} else if (animal.charAt(0) == 'G') {
					grid[plain_length][plain_width] = new Grass(this, plain_length, plain_width);
				} else if (animal.charAt(0) == 'R') {
					int age = Character.getNumericValue(animal.charAt(1));
					grid[plain_length][plain_width] = new Rabbit(this, plain_length, plain_width, age);
				}
			}
		}
		gridWidth.close(); // close file
		findLiving.close(); // ---------
	}

	/**
	 * Constructor that builds a w x w grid without initializing it.
	 * 
	 * @param width the grid
	 */
	public Plain(int w) {
		width = w;
		grid = new Living[width][width]; // length & width of 2d array = [width][width]

	}

	public int getWidth() {

		return width; // to be modified
	}

	/**
	 * Initialize the plain by randomly assigning to every square of the grid one of
	 * BADGER, FOX, RABBIT, GRASS, or EMPTY.
	 * 
	 * Every animal starts at age 0.
	 */
	public void randomInit() {
		Random generator = new Random();

		for (int plain_length = 0; plain_length < width; plain_length++) { // set length variable and iterate until less
																			// than width of the grid.

			for (int plain_width = 0; plain_width < width; plain_width++) {

				int animal_assign = generator.nextInt(5); // will generate a random number between 0 and 4 that
															// corresponds to one of the five life forms.

				if (animal_assign == 0) {
					grid[plain_length][plain_width] = new Badger(this, plain_length, plain_width, 0);
				} else if (animal_assign == 1) {
					grid[plain_length][plain_width] = new Fox(this, plain_length, plain_width, 0);
				} else if (animal_assign == 2) {
					grid[plain_length][plain_width] = new Rabbit(this, plain_length, plain_width, 0);
				} else if (animal_assign == 3) {
					grid[plain_length][plain_width] = new Grass(this, plain_length, plain_width);
				} else if (animal_assign == 4) {
					grid[plain_length][plain_width] = new Empty(this, plain_length, plain_width);
				}
			}
		}
	}

	/**
	 * Output the plain grid. For each square, output the first letter of the living
	 * form occupying the square. If the living form is an animal, then output the
	 * age of the animal followed by a blank space; otherwise, output two blanks.
	 */
	public String toString() {

		StringBuilder plain_output = new StringBuilder(""); // use string builder for better manipulation of strings

		for (int plain_length = 0; plain_length < width; plain_length++) {// set length variable and iterate until less
																			// than width of the grid.

			for (int plain_width = 0; plain_width < width; plain_width++) {// set width variable and iterate until less
																			// than width of the grid. note since we
																			// only use width we can assume the [][] can
																			// be representative as both "width"

				// if at the particular location in the plain, call .who() to check what *animal
				// is in the space. Append to the plain_output with the corresponding letter of
				// the animal.
				// proceed to then cast the animal to the plain location and call my age along
				// with a segment of white space
				if (grid[plain_length][plain_width].who() == State.BADGER) {
					plain_output.append("B" + ((Badger) grid[plain_length][plain_width]).myAge() + " ");// *Badger
				}
				if (grid[plain_length][plain_width].who() == State.RABBIT) {
					plain_output.append("R" + ((Rabbit) grid[plain_length][plain_width]).myAge() + " ");// *Rabbit
				}
				if (grid[plain_length][plain_width].who() == State.FOX) {
					plain_output.append("F" + ((Fox) grid[plain_length][plain_width]).myAge() + " ");// *Fox
				}
				if (grid[plain_length][plain_width].who() == State.GRASS) {// not an animal (grass space)
					plain_output.append("G" + "  ");
				}
				if (grid[plain_length][plain_width].who() == State.EMPTY) {// not an animal (empty space)
					plain_output.append("E" + "  ");
				}
			}
			plain_output.append("\n");

		}
		return plain_output.toString(); // return the built string plain_output
	}

	/**
	 * Write the plain grid to an output file. Also useful for saving a randomly
	 * generated plain for debugging purpose.
	 * 
	 * @throws FileNotFoundException
	 */
	public void write(String outputFileName) throws FileNotFoundException {

		File file = new File(outputFileName); // open the file
		PrintWriter textFile = new PrintWriter(file); // write the file with built in Java class PrintWriter
		textFile.print(this.toString());// print the file
		textFile.close(); // close the file
	}
}
