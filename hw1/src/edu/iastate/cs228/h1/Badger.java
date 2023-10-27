package edu.iastate.cs228.h1;

/**
 * 
 * @author /** A badger eats a rabbit and competes against a fox.
 */
public class Badger extends Animal {
	/**
	 * Constructor
	 * 
	 * @param p: plain
	 * @param r: row position
	 * @param c: column position
	 * @param a: age
	 */
	public Badger(Plain p, int r, int c, int a) {
		age = a;
		column = c;
		row = r;
		plain = p;
	}

	/**
	 * A badger occupies the square.
	 */
	public State who() {
		// TODO
		return State.BADGER;
	}

	/**
	 * A badger dies of old age or hunger, or from isolation and attack by a group
	 * of foxes.
	 * 
	 * @param pNew plain of the next cycle
	 * @return Living life form occupying the square in the next cycle.
	 */

//	A badger dies of old age or hunger, or from a group attack by foxes when it is alone. The life form on a Badger square in the next cycle will be...

//	Condition A: Empty if the Badger is currently at age 4;

//	Condition B: otherwise, Fox, if there is only one Badger but there are more than one Fox in the neighborhood;

//	Condition C: otherwise, Empty, if Badgers and Foxes together out number Rabbits in the neighborhood;

//	Condition D: otherwise, Badger (the badger will live on).

	public Living next(Plain pNew) {
		int[] population = new int[NUM_LIFE_FORMS]; // local int array of name "population" is equivalent to new

		census(population); // call census method to assist with the counting of the population in existence

		if (age == BADGER_MAX_AGE) { // if *this* age (age of constructed badger) is greater than or equal to the
										// BADGER_MAX_AGE = 4
			return new Empty(pNew, row, column); // Condition A
		} else if (population[BADGER] == 1 && population[FOX] > 1) { // if there is only ONE badger AND more than ONE
																		// fox exists within
			return new Fox(pNew, row, column, 0);// Condition B
		} else if (population[BADGER] + population[FOX] > population[RABBIT]) { // if the number of badgers and foxes
																				// combined together is greater than the
																				// number of rabbits
			return new Empty(pNew, row, column); // Condition C
		} else {
			return new Badger(pNew, row, column, age + 1);
		}

	}
}
