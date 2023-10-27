package edu.iastate.cs228.h1;

/**
 * 
 * @author /** Grass remains if more than rabbits in the neighborhood;
 *         otherwise, it is eaten.
 *
 */
public class Grass extends Living {
	public Grass(Plain p, int r, int c) {
		column = c;
		row = r;
		plain = p;
	}

	public State who() {
		// TODO
		return State.GRASS;
	}

	/**
	 * Grass can be eaten out by too many rabbits. Rabbits may also multiply fast
	 * enough to take over Grass.
	 */
//	Grass may be eaten out by overcrowded rabbits. Rabbits may also multiply fast enough to take over the Grass square. In the next cycle, the life form on a Grass square will...
	// be

//	Condition A: Empty if at least three times as many Rabbits as Grasses in the neighborhood; 

//	Condition B: otherwise, Rabbit if there are at least three Rabbits in the neighborhood;

//	Condition C: otherwise,Grass.

	public Living next(Plain pNew) {

		int[] population = new int[NUM_LIFE_FORMS];
		census(population);

		if (population[RABBIT] >= population[GRASS] * 3) {
			return new Empty(pNew, row, column); // Condition A
		} else if (population[RABBIT] >= 3) {
			return new Rabbit(pNew, row, column, 0); // Condition B
		} else {
			return new Grass(pNew, row, column);
		}
	}

}
