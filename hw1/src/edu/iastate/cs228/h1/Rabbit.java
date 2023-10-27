package edu.iastate.cs228.h1;

/**
 * 
 * @author
 *
 */

/*
 * A rabbit eats grass and lives no more than three years.
 */
public class Rabbit extends Animal {
	/**
	 * Creates a Rabbit object.
	 * 
	 * @param p: plain
	 * @param r: row position
	 * @param c: column position
	 * @param a: age
	 */
	public Rabbit(Plain p, int r, int c, int a) {
		age = a;
		column = c;
		row = r;
		plain = p;
	}

	// Rabbit occupies the square.
	public State who() {
		// TODO
		return State.RABBIT;
	}

	/**
	 * A rabbit dies of old age or hunger. It may also be eaten by a badger or a
	 * fox.
	 * 
	 * @param pNew plain of the next cycle
	 * @return Living new life form occupying the same square
	 */
//	A rabbit dies of old age or hunger. It may also be eaten by a badger or a fox. More specifically, the life form on a Rabbit square in the next cycle will 
//be.

//	Condition A: Empty if the Rabbit's current age is 3;

//	Condition B: otherwise, Empty if there is no Grass in the neighborhood (the rabbit needs food);

//	Condition C: otherwise, Fox if in the neighborhood there are at least as many Foxes and Badgers
//	combined as Rabbits, and furthermore, if there are more Foxes than Badgers;

//	Condition D: otherwise, Badger if there are more Badgers than Rabbits in the neighborhood;

//	Condition E: otherwise, Rabbit (the rabbit will live on).

	public Living next(Plain pNew) {

		int[] population = new int[NUM_LIFE_FORMS];

		census(population);

		if (age == RABBIT_MAX_AGE) { // RABBIT_MAX_AGE = 3
			return new Empty(pNew, row, column); // Condition A
		} else if (population[GRASS] == 0) {
			return new Empty(pNew, row, column); // Condition B
		} else if (population[FOX] + population[BADGER] >= population[RABBIT] && population[BADGER] < population[FOX]) {
			return new Fox(pNew, row, column, 0); // Condition C
		} else if (population[BADGER] > population[RABBIT]) {
			return new Badger(pNew, row, column, 0); // Condition D
		} else {
			return new Rabbit(pNew, row, column, age + 1);
		}
	}
}
