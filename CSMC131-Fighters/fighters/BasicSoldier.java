package fighters;

import framework.BattleField;
import framework.Random131;

public class BasicSoldier {
	public final static int INITIAL_HEALTH = 35;
	public final static int ARMOR = 10;
	public final static int STRENGTH = 30; 
	public final static int SKILL = 25;
	
	public final static int UP = 0;
	public final static int RIGHT = 1;
	public final static int DOWN = 2;
	public final static int LEFT = 3;
	public final static int UP_AND_RIGHT = 4;
	public final static int DOWN_AND_RIGHT = 5;
	public final static int DOWN_AND_LEFT = 6;
	public final static int UP_AND_LEFT = 7;
	public final static int NEUTRAL = -1;

	public final BattleField grid;  
	public int row, col; 
	public int health; 
	public final int team; 

	//set up and initialize basic soldier constructor
	public BasicSoldier(BattleField gridIn, int teamIn, int rowIn, int colIn) {
		this.grid = gridIn;
		this.team = teamIn;
		this.row = rowIn;
		this.col = colIn;
		this.health = INITIAL_HEALTH;
	}
	
	/*
	 * This method returns a boolean value telling whether or not the player
	 * can move up, down, left, or right. There are no parameters.
	 */
	public boolean canMove() {
		int empty = BattleField.EMPTY;
		//find what is in the square in the corresponding direction
		int aboveSquare = grid.get(row - 1, col);
		int belowSquare = grid.get(row + 1, col);
		int leftSquare = grid.get(row, col - 1);
		int rightSquare = grid.get(row, col + 1);
		//checks each direction and checks if its empty
		boolean canMoveUp;
		boolean canMoveDown;
		boolean canMoveLeft;
		boolean canMoveRight;
		if (aboveSquare == empty) {
			canMoveUp = true;
		}else {
			canMoveUp = false;
		}
		if (belowSquare == empty) {
			canMoveDown = true;
		}else {
			canMoveDown = false;
		}
		if (leftSquare == empty) {
			canMoveLeft = true;
		}else {
			canMoveLeft = false;
		}
		if (rightSquare == empty) {
			canMoveRight = true;
		}else {
			canMoveRight = false;
		}
		/*
		 * if ALL FOUR spaces to move to are full the player cannot move, 
		 * otherwise he may
		 */
		if (canMoveUp == false && canMoveDown == false && 
				canMoveLeft == false && canMoveRight == false) {
			return false;
		}else {
			return true;
		}
	}
	
	/*
	 * This method returns an integer value of the number of enemies 
	 * remaining on the battlefield. There are no parameters.
	 */
	public int numberOfEnemiesRemaining() {
		int enemyTeam;
		if (team == BattleField.RED_TEAM) {
			enemyTeam = BattleField.BLUE_TEAM;
		}else {
			enemyTeam = BattleField.RED_TEAM;
		}
		int numEnemies = 0;
		/*loop through rows and columns and add 1 to numEnemies every 
		time an enemy is found*/
		for (int i = 0; i < grid.getRows(); i++) {
			for (int j = 0; j < grid.getCols(); j++) {
				if (grid.get(i, j) == enemyTeam) {
					numEnemies++;
				}
			}
		}
		return numEnemies;
	}
	
	/*
	 * This method returns an integer value of the total amount of moves it
	 * takes to reach a certain position on the BattleField. It will begin at 
	 * the current row and column and either add or subtract from the row
	 * until it meets the destination row. It will do the same with columns.
	 * Then it will return the total amount of moves, vertical and horizontal.
	 */
	public int getDistance(int destinationRow, int destinationCol) {
		int currentRow = this.row;
		int currentCol = this.col;
		int moves = 0;
		//if row is less than destination keep adding 1 to row and moves
		if (currentRow < destinationRow) {
			while (currentRow < destinationRow) {
				currentRow++;
				moves++;
			}
		/*
		 * if row is greater than destination keep subtracting 
		 * 1 from row and add 1 to moves
		 */
		}else if (currentRow > destinationRow) {
			while(currentRow > destinationRow) {
				currentRow--;
				moves++;
			}
		}
		//if col is less than destination keep adding 1 to col and moves
		if (currentCol < destinationCol) {
			while (currentCol < destinationCol) {
				currentCol++;
				moves++;
			}
		/*
		 * if col is greater than destination keep subtracting 
		 * 1 from col and add 1 to moves
		 */
		}else if (currentCol > destinationCol) {
			while(currentCol > destinationCol) {
				currentCol--;
				moves++;
			}
		}
		return moves;
	}
	
	
	/*
	 * This method returns an integer value that correlates to a direction
	 * defined above in the named constants. It uses a similar format to 
	 * the getDistance method but is simply determining which way the 
	 * soldier needs to move; it does not matter how far. It then combines
	 * row and column direction to assign it to a named constant and then 
	 * returns a named constant value.
	 */
	public int getDirection(int destinationRow, int destinationCol) {
		int currentRow = this.row;
		int currentCol = this.col;
		
		/* combines row and column to assign to a named constant */
		if (currentRow > destinationRow && currentCol < destinationCol) {
			return UP_AND_RIGHT;
		}else if(currentRow > destinationRow && currentCol > destinationCol) {
			return UP_AND_LEFT;
		}else if(currentRow < destinationRow && currentCol < destinationCol) {
			return DOWN_AND_RIGHT;
		}else if(currentRow < destinationRow && currentCol > destinationCol) {
			return DOWN_AND_LEFT;
		}else if(currentRow > destinationRow && currentCol == destinationCol) {
			return UP;
		}else if(currentRow < destinationRow && currentCol == destinationCol) {
			return DOWN;
		}else if(currentRow == destinationRow && currentCol > destinationCol) {
			return LEFT;
		}else if(currentRow == destinationRow && currentCol < destinationCol) {
			return RIGHT;
		}else {
			return NEUTRAL;
		}
	}
	
	/*
	 * this method returns an integer value that tells how many friends are 
	 * within a certain radius. it first determines what team is friendly
	 * then runs through the grid adding 1 to numFriends every time it 
	 * finds a friend
	 */
	public int countNearbyFriends(int radius) {
		int friends;
		if (team == BattleField.RED_TEAM) {
			friends = BattleField.RED_TEAM;
		}else {
			friends = BattleField.BLUE_TEAM;
		}
		int numFriends = 0;
		
		for (int i = 0; i < radius; i++) {
			for (int j = 0; j < radius; j++) {
				if (grid.get(i, j) == friends) {
					numFriends++;
				}
			}
		}
		return numFriends;
	}
	
	/*
	 * this method decides what the soldier is going to do. it prioritizes 
	 * attacking over moving. so, if it can attack it will, but if it 
	 * cannot it will move.
	 */
	public void performMyTurn() {
		int enemyTeam;
		if (team == BattleField.RED_TEAM) {
			enemyTeam = BattleField.BLUE_TEAM;
		}else {
			enemyTeam = BattleField.RED_TEAM;
		}
		int aboveSquare = grid.get(row - 1, col);
		int belowSquare = grid.get(row + 1, col);
		int leftSquare = grid.get(row, col - 1);
		int rightSquare = grid.get(row, col + 1);
		int empty = BattleField.EMPTY;
		
		//if square is enemy, attack
		if (aboveSquare == enemyTeam) {
			grid.attack(row - 1, col);
		}else if(belowSquare == enemyTeam) {
			grid.attack(row + 1, col);
		}else if(leftSquare == enemyTeam) {
			grid.attack(row, col - 1);
		}else if(rightSquare == enemyTeam) {
			grid.attack(row, col + 1);
		}else if(this.canMove() == true) {
			//if square is empty and did not attack, move
			if (aboveSquare == empty) {
				this.row = this.row - 1;
			}else if(belowSquare == empty) {
				this.row = this.row + 1;
			}else if(leftSquare == empty) {
				this.col = this.col - 1;
			}else if(rightSquare == empty) {
				this.col = this.col + 1;
			}
		}
	}
}
