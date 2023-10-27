package edu.iastate.cs228.h1;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  
 * @author 
 *
 */

/**
 * 
 * The Wildlife class performs a simulation of a grid plain with squares
 * inhabited by badgers, foxes, rabbits, grass, or none.
 *
 */
public class Wildlife {
	/**
	 * Update the new plain from the old plain in one cycle.
	 * 
	 * @param pOld old plain
	 * @param pNew new plain
	 */
	public static void updatePlain(Plain pOld, Plain pNew) {

		for (int plain_length = 0; plain_length < pOld.getWidth(); plain_length++) {
			for (int plain_width = 0; plain_width < pOld.getWidth(); plain_width++) {

				pNew.grid[plain_length][plain_width] = pOld.grid[plain_length][plain_width].next(pNew);

			}
		}

	}

	/**
	 * Repeatedly generates plains either randomly or from reading files. Over each
	 * plain, carries out an input number of cycles of evolution.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {

		Scanner userInput = new Scanner(System.in);
		Plain even = null; // plain after an even number of cycles
		Plain odd = null; // plain after an odd number of cycles
		int runs = 1; // number of trials completed
		int inputNumber = 0;
		int width = 0;
		int cycles = 0;
		boolean evenCycle = true; // tracks if the last completed cycle was even

		// Prompt the user for options and execute the chosen action
		System.out.println("Wildlife of the Plain simulation.");
		System.out.println("Select an option:");
		System.out.println("1. Generate a random plain");
		System.out.println("2. Load a plain from a file");
		System.out.println("3. Exit the program");

		while (inputNumber != 3) {
			System.out.print("Trial " + runs + ": ");
			inputNumber = userInput.nextInt();

			// Generate a random plain
			if (inputNumber == 1) {
				System.out.print("Enter the width of the plain: ");
				width = userInput.nextInt();

				// Ensure that the width is a positive integer
				while (width < 1) {
					try {
						System.err.print("Invalid: Enter a positive number: ");
						width = userInput.nextInt();

						if (width < 1) {
							throw new Exception("Input is not a positive number");
						}
					}

					catch (Exception e) {
						System.err.println("Invalid input. Please enter a positive number.");
					}
				}

				// Initialize the odd plain and generate a random state
				odd = new Plain(width);
				even = new Plain(odd.getWidth());
				odd.randomInit();
			}

			// Load a plain from a file
			if (inputNumber == 2) {
				System.out.print("Enter filename: ");
				String filename = userInput.next();

				try {
					odd = new Plain(filename);
					even = new Plain(odd.getWidth());
				} catch (FileNotFoundException e) {
					System.err.println("File not found: " + filename); // print error
				}
			}

			// Exit the program
			if (inputNumber == 3) {
				System.out.println("Exited");
				userInput.close();
				break;
			}

			// Ask the user for the number of cycles to run
			System.out.print("Enter the number of cycles to run: ");
			cycles = userInput.nextInt();

			// Ensure that the number of cycles is a positive integer
			while (cycles < 1) {
				try {
					System.err.print("Invalid: Enter a positive number: ");
					cycles = userInput.nextInt();

					if (cycles < 1) {
						throw new Exception("Input is not a positive number");
					}
				}

				catch (Exception e) {
					System.err.println("Invalid input. Please enter a positive number.");
				}
			}

			// Run the cycles and update the plains
			System.out.println("Initial plain: \n" + odd);
			for (int i = 0; i < cycles; i++) {
				if (i % 2 == 0) { // even cycle
					updatePlain(odd, even);
					evenCycle = true;
				} else { // odd cycle
					updatePlain(even, odd);
					evenCycle = false;
				}
			}

			// Print the final plain based on the last completed cycle
			if (evenCycle) {

				System.out.println("Final plain: \n" + even);

			} else {
				System.out.println("Final plain: \n" + odd);

			}

			// Increment the trial counter
			runs++;
		}

	}
}
