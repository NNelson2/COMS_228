package edu.iastate.cs228.hw1;

/**
 *  
 * @author Noah Nelson
 *
 */

/**
 * A fox eats rabbits and competes against a badger. 
 */
public class Fox extends Animal {

	/**
	 * Constructor 
	 * @param p: plain
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Fox (Plain p, int r, int c, int a) {
		age = a;
		plain = p;
		row = r;
		column = c;
		plain.grid[row][column] = this;
	}
		
	/**
	 * A fox occupies the square. 	 
	 */
	public State who() {
		return State.FOX;
	}
	
	/**
	 * A fox dies of old age or hunger, or from attack by numerically superior badgers. 
	 * @param pNew     plain of the next cycle
	 * @return Living  life form occupying the square in the next cycle. 
	 */
	public Living next(Plain pNew) {
		census();

		if (age == 6) {
			return new Empty(pNew, row, column);

		} else if (numBadgers > 1 && numBadgers + 1 == 1) {
			return new Badger(pNew, row, column, 0);

		} else if (numBadgers + numFoxes + 1 > numRabbits) {
			return new Empty(pNew, row, column);

		} else {
			age++;
			return new Fox(pNew, row, column, age);

		}
	}
}
