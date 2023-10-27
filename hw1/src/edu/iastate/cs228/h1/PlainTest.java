package edu.iastate.cs228.h1;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author
 *
 */

class PlainTest {

	@Test
	void testA() {
		Plain plain = new Plain(2);

		assertEquals(2, plain.getWidth());

	}

}
