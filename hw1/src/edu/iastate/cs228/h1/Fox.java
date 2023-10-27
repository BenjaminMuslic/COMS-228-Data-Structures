package edu.iastate.cs228.h1;

/**
 *  
 * @author 
 *
 */

/**
 * A fox eats rabbits and competes against a badger.
 */
public class Fox extends Animal {
	/**
	 * Constructor
	 * 
	 * @param p: plain
	 * @param r: row position
	 * @param c: column position
	 * @param a: age
	 */
	public Fox(Plain p, int r, int c, int a) {
		age = a;
		column = c;
		row = r;
		plain = p;
	}

	/**
	 * A fox occupies the square.
	 */
	public State who() {
		// TODO
		return State.FOX;
	}

	/**
	 * A fox dies of old age or hunger, or from attack by numerically superior
	 * badgers.
	 * 
	 * @param pNew plain of the next cycle
	 * @return Living life form occupying the square in the next cycle.
	 */
//	A fox dies of old age, hunger, or an attack by more numerous badgers. The life form on a Fox square in the next cycle will be...

//	Condition A: Empty if the Fox is currently at age 6;

//	Condition B: otherwise, Badger, if there are more Badgers than Foxes in the neighborhood;

//	Condition C: otherwise, Empty, if Badgers and Foxes together out number Rabbits in the neighborhood;

//	Condition D: otherwise, Fox (the fox will live on).

	public Living next(Plain pNew) {

		int[] population = new int[NUM_LIFE_FORMS];
		census(population);

		if (age == FOX_MAX_AGE) { // FOX_MAX_AGE = 6
			return new Empty(pNew, row, column); // Condition A
		} else if (population[BADGER] > population[FOX]) {
			return new Badger(pNew, row, column, 0); // Condition B
		} else if (population[BADGER] + population[FOX] > population[RABBIT]) {
			return new Empty(pNew, row, column); // Condition C
		} else {
			return new Fox(pNew, row, column, age + 1);
		}
	}
}
