package edu.iastate.cs228.h1;

/**
 *  
 * @author 
 *
 */

/**
 * Empty squares are competed by various forms of life.
 */
public class Empty extends Living {
	public Empty(Plain p, int r, int c) {
		column = c;
		row = r;
		plain = p;
	}

	public State who() {
		// TODO
		return State.EMPTY;
	}

	/**
	 * An empty square will be occupied by a neighboring Badger, Fox, Rabbit, or
	 * Grass, or remain empty.
	 * 
	 * @param pNew plain of the next life cycle.
	 * @return Living life form in the next cycle.
	 */

	// Condition A: Rabbit, if more than one neighboring Rabbit;

	// Condition B: otherwise, Fox, if more than one neighboring Fox;

	// Condition C: otherwise, Badger, if more than one neighboring Badger;

	// Condition D: otherwise, Grass, if at least one neighboring Grass;

	// Condition E: otherwise, Empty.

	public Living next(Plain pNew) {
		// TODO
		//
		// See Living.java for an outline of the function.
		// See the project description for corresponding survival rules.

		int[] population = new int[NUM_LIFE_FORMS];
		census(population);

		if (population[RABBIT] > 1) {
			return new Rabbit(pNew, row, column, 0); // Condition A
		} else if (population[FOX] > 1) {
			return new Fox(pNew, row, column, 0); // Condition B
		} else if (population[BADGER] > 1) {
			return new Badger(pNew, row, column, 0); // Condition C
		} else if (population[GRASS] >= 1) {
			return new Grass(pNew, row, column); // Condition D
		} else {
			return new Empty(pNew, row, column);
		}
	}

}
