package edu.iastate.cs228.hw1;

/**
 *  
 * @author Noah Nelson
 *
 */

/**
 * A rabbit eats grass and lives no more than three years.
 */
public class Rabbit extends Animal {

	/**
	 * Creates a Rabbit object.
	 * @param p: plain  
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Rabbit (Plain p, int r, int c, int a) {
		age = a;
		plain = p;
		row = r;
		column = c;
		plain.grid[row][column] = this;

	}

	/**
	 * Rabbit occupies the square.
	 * @return the State in the square
	 */
	public State who() {
		return State.RABBIT;
	}
	
	/**
	 * A rabbit dies of old age or hunger. It may also be eaten by a badger or a fox.  
	 * @param pNew     plain of the next cycle 
	 * @return Living  new life form occupying the same square
	 */
	public Living next(Plain pNew) {
		census();

		if (age == 3) {
			return new Empty(pNew, row , column);

		} else if (numGrasses == 0) {
			return new Empty(pNew, row , column);

		} else if (numRabbits + 1 <= numFoxes && numFoxes > numBadgers) {
			return new Fox(pNew, row, column, 0);

		} else if (numBadgers > numRabbits + 1) {
			return new Badger(pNew, row, column, 0);

		} else {
			age++;
			return new Rabbit(pNew, row, column, age);

		}
	}
}
