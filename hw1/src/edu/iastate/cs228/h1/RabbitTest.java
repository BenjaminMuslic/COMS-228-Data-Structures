package edu.iastate.cs228.h1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author
 *
 */

class RabbitTest {

	@Test
//	Condition A: Empty if the Rabbit's current age is 3;
	void conditionA() {

		Plain plainTest = new Plain(2);

		plainTest.grid[0][0] = new Grass(plainTest, 0, 0);
		plainTest.grid[0][1] = new Grass(plainTest, 0, 1);
		plainTest.grid[1][0] = new Grass(plainTest, 1, 0);
		plainTest.grid[1][1] = new Rabbit(plainTest, 1, 1, 3); // at age 3

		assertEquals(State.EMPTY, plainTest.grid[1][1].next(plainTest).who());

	}

	@Test
//	Condition B: otherwise, Empty if there is no Grass in the neighborhood (the rabbit needs food);
	void conditionB() {
		Plain plainTest = new Plain(2);

		plainTest.grid[0][0] = new Fox(plainTest, 0, 0, 0);
		plainTest.grid[0][1] = new Fox(plainTest, 0, 1, 0);
		plainTest.grid[1][0] = new Rabbit(plainTest, 1, 0, 0);
		plainTest.grid[1][1] = new Badger(plainTest, 1, 1, 0);

		assertEquals(State.EMPTY, plainTest.grid[0][0].next(plainTest).who());

	}
}
