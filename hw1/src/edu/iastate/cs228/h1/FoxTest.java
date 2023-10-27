package edu.iastate.cs228.h1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author
 *
 */

class FoxTest {

	@Test
//	Condition A: Empty if the Fox is currently at age 6;
	void conditionA() {

		Plain plainTest = new Plain(2);

		plainTest.grid[0][0] = new Grass(plainTest, 0, 0);
		plainTest.grid[0][1] = new Grass(plainTest, 0, 1);
		plainTest.grid[1][0] = new Grass(plainTest, 1, 0);
		plainTest.grid[1][1] = new Fox(plainTest, 1, 1, 6); // at age 6

		assertEquals(State.EMPTY, plainTest.grid[1][1].next(plainTest).who());

	}

	@Test
//	Condition B: otherwise, Badger, if there are more Badgers than Foxes in the neighborhood;
	void conditionB() {
		Plain plainTest = new Plain(2);

		plainTest.grid[0][0] = new Fox(plainTest, 0, 0, 0);
		plainTest.grid[0][1] = new Badger(plainTest, 0, 1, 0);
		plainTest.grid[1][0] = new Badger(plainTest, 1, 0, 0);
		plainTest.grid[1][1] = new Badger(plainTest, 1, 1, 0);

		assertEquals(State.BADGER, plainTest.grid[0][0].next(plainTest).who());

	}
}
