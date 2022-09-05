package edu.iastate.cs228.hw1;

/**
 *  
 * @author Noah Nelson
 *
 */

/**
 * A badger eats a rabbit and competes against a fox. 
 */
public class Badger extends Animal {

	/**
	 * Constructor 
	 * @param p: plain
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Badger (Plain p, int r, int c, int a) {
		age = a;
		plain = p;
		row = r;
		column = c;
		plain.grid[row][column] = this;
	}
	
	/**
	 * A badger occupies the square.
	 * @return the State in the square
	 */
	public State who() {
		return State.BADGER;
	}
	
	/**
	 * A badger dies of old age or hunger, or from isolation and attack by a group of foxes. 
	 * @param pNew     plain of the next cycle
	 * @return Living  life form occupying the square in the next cycle. 
	 */
	public Living next(Plain pNew) {
		census();
		if (age == 4) {
			return new Empty(pNew, row, column);

		} else if (numFoxes > numBadgers + 1) {
			return new Fox(pNew, row, column, 0);

		} else if (numFoxes + numBadgers + 1 > numRabbits) {
			return new Empty(pNew, row, column);

		} else {
			age++;
			return new Badger(pNew, row, column, age);

		}
	}
}
