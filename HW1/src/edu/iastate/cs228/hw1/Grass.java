package edu.iastate.cs228.hw1;

/**
 *  
 * @author Noah Nelson
 *
 */

/**
 * Grass remains if more than rabbits in the neighborhood; otherwise, it is eaten. 
 *
 */
public class Grass extends Living {

	public Grass (Plain p, int r, int c) {
		plain = p;
		row = r;
		column = c;
		plain.grid[row][column] = this;
	}
	
	public State who() {
		return State.GRASS;
	}
	
	/**
	 * Grass can be eaten out by too many rabbits. Rabbits may also multiply fast enough to take over Grass.
	 */
	public Living next(Plain pNew) {
		census();

		if((numGrasses + 1) * 3 <= numRabbits) {
			return new Empty(pNew, row , column);

		} else if (numRabbits >= 3) {
			return new Rabbit(pNew, row, column, 0);

		} else {
			return new Grass(pNew, row, column);
		}
	}
}
