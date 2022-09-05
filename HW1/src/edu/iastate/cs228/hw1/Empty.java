package edu.iastate.cs228.hw1;

/**
 *  
 * @author Noah Nelson
 *
 */

/** 
 * Empty squares are competed by various forms of life.
 */
public class Empty extends Living {

	public Empty (Plain p, int r, int c) {
		plain = p;
		row = r;
		column = c;
		plain.grid[row][column] = this;
	}
	
	public State who() {
		return State.EMPTY;
	}
	
	/**
	 * An empty square will be occupied by a neighboring Badger, Fox, Rabbit, or Grass, or remain empty. 
	 * @param pNew     plain of the next life cycle.
	 * @return Living  life form in the next cycle.   
	 */
	public Living next(Plain pNew) {
		census();

		if (numRabbits > 1) {
			return new Rabbit(pNew, row, column, 0);

		} else if (numFoxes > 1) {
			return new Fox(pNew, row, column, 0);

		} else if (numBadgers > 1) {
			return new Badger(pNew, row, column, 0);

		} else if (numGrasses >= 1) {
			return new Grass(pNew, row, column);

		} else {
			return new Empty(pNew, row, column);

		}
	}
}
