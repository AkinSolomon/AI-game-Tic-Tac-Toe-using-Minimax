

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * A state of the Tic-tac-toe game is characterized by a board containing
 * symbols X and O, the next player to move, and an utility information.
 * 
 * @author Akinlawon Solomon
 * 
 */
public class TicTacToeState implements Cloneable {
	public static final String O = "O";
	public static final String X = "X";
	public static final String EMPTY = "-";
	
	//The board configuration
	public String[] board = new String[] { EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
			EMPTY, EMPTY, EMPTY, EMPTY };

	private String playerToMove = X;
	private double utility = -2; // 1: win for X, 0: win for O, 0.5: draw

	
	public String getPlayerToMove() {
		return playerToMove;
	}
	
	public boolean isEmpty(int row, int col) {
		return board[getAbsPosition(row,col)] == EMPTY;
	}

	public String getValue(int row, int col) {
		return board[getAbsPosition(row, col)];
	}

	public double getUtility() {
		return utility;
	}

	public void mark(XYLocation action) {
		mark(action.getXCoOrdinate(), action.getYCoOrdinate());
	}

	
	public void mark(int row, int col) {
		if (utility == -2 && getValue(row, col) == EMPTY) {
			board[getAbsPosition(row, col)] = playerToMove;
			analyzeUtility();
			playerToMove = (playerToMove == X ? O : X);  //Toggles the next player to move from the current one using a conditional.
		}
	}

	/*
	 * Reports the utility depending on the current player.
	 */
	private void analyzeUtility() {
		if (lineThroughBoard()) {
			utility = (playerToMove == X ? 1 : -1);
		} else if (getNumberOfMarkedPositions() == 9) {
			utility = 0;
		}
	}

	/*
	 * Checks if any of the winning conditions have been satisfied by checking if any of the
	 * rows, diagonals and columns have at exactly three X's or O's.
	 */
	public boolean lineThroughBoard() {
		return (isAnyRowComplete() || isAnyColumnComplete() || isAnyDiagonalComplete());
	}
	
	private boolean isAnyRowComplete() {
		for (int row = 0; row < 3; row++) {
			String val = getValue(row,0);
			if (val != EMPTY && val == getValue(row,1) && val == getValue(row,2)) {
				return true;
			}
		}
		return false;
	}

	private boolean isAnyColumnComplete() {
		for (int col = 0; col < 3; col++) {
			String val = getValue(0,col);
			if (val != EMPTY && val == getValue(1,col) && val == getValue(2,col)) {
				return true;
			}
		}
		return false;
	}

	private boolean isAnyDiagonalComplete() {
		boolean retVal = false;
		String val = getValue(0, 0);
		if (val != EMPTY && val == getValue(1, 1) && val == getValue(2, 2)) {
			return true;
		}
		val = getValue(0, 2);
		if (val != EMPTY && val == getValue(1, 1) && val == getValue(2, 0)) {
			return true;
		}
		return retVal;
	}

	public int getNumberOfMarkedPositions() {
		int retVal = 0;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (!(isEmpty(row,col))) {
					retVal++;
				}
			}
		}
		return retVal;
	}

	public List<XYLocation> getUnMarkedPositions() {
		List<XYLocation> result = new ArrayList<XYLocation>();
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (isEmpty(row,col)) {
					result.add(new XYLocation(row,col));
				}
			}
		}
		return result;
	}

	@Override
	/*
	 * Returns a copy of the current state provided the TicTacToestate implements
	 * the cloneable interface. 
	 */
	public TicTacToeState clone() {
		TicTacToeState copy = null;
		try {
			copy = (TicTacToeState) super.clone();
			copy.board = Arrays.copyOf(board, board.length);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace(); // should never happen...
		}
		return copy;
	}

	@Override
	/*
	 * Allows a comparison of two TicTacToeStates by comparing all the positions on
	 * their respective boards.
	 */
	public boolean equals(Object anObj) {
		if (anObj != null && anObj.getClass() == getClass()) {
			TicTacToeState anotherState = (TicTacToeState) anObj;
			for (int i = 0; i < 9; i++) {
				if (board[i] != anotherState.board[i]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		// Need to ensure equal objects have equivalent hashcodes (Issue 77).
		return toString().hashCode();
	}

	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				strBuilder.append(getValue(row,col) + " ");
			}
			strBuilder.append("\n");
		}
		return strBuilder.toString();
	}

	//
	// PRIVATE METHODS
	//

	private int getAbsPosition(int row, int col) {
		return row * 3 + col;
	}
}
