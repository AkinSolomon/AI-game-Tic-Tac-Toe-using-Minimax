import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;




/**
 *  This class implements the Minimax search algorithm in order to assign 
 *  values to outcomes depending on the utility for each player in a game.
 * 
 * 
 * @author Akinlawon Solomon
 * 
 */

public class MinimaxSearch  {



	public TicTacToe game;

	public MinimaxSearch(TicTacToe round){
		this.game=round;
	}


	/**
	 * Makes a decision based on the min values of the successors from the point of view of 
	 * X by finding the largest of their of min values.
	 * @param state
	 * @return
	 *    the best action based on the minimax values of the successors and
	 *    the current player.
	 */

	public XYLocation makeDecision(TicTacToeState state) {
		XYLocation result = null;
		double resultValue = Double.NEGATIVE_INFINITY;
		String player = game.getPlayer(state);
		for (XYLocation action : game.getActions(state)) {
			double value = minValue(game.getResult(state, action), player);
			if (value > resultValue) {
				result = action;
				resultValue = value;
			}
		}
		return result;
	}
	/**
	 * 
	 * @param state
	 * @return
	 *    A list of minimax values for each available position from X's point of 
	 *    view.
	 */
	public List<Double> MinimaxValues(TicTacToeState state){
		String player = game.getPlayer(state);
		List<Double> result = new ArrayList<Double>();
		for (XYLocation action : game.getActions(state)) {
			result.add(minValue(game.getResult(state, action), player));
		}
		return result;
	}

	/*
	 * Both functions recursively compute the minimax values for each state by continuing until terminal states
	 * and then backing up utility values depending on current player's turn at a given state.
	 */
	/**
	 * Computes the minimax value from X's point of view by computing the min values of current state's
	 * successors.
	 * @param state
	 * @param Player
	 * @return
	 */
	public double maxValue(TicTacToeState state, String Player) { // returns a utility
		// value
		if (game.isTerminal(state))
			return game.getUtility(state, Player );
		double value = Double.NEGATIVE_INFINITY;
		for (XYLocation action : game.getActions(state))
			value = Math.max(value,
					minValue(game.getResult(state, action), Player));
		return value;
	}

	/**
	 * Computes the minimax value from X's point of view by computing the max values 
	 * of current state's successors.
	 * @param state
	 * @param Player
	 * @return
	 */

	public double minValue(TicTacToeState state, String Player) { // returns a utility
		// value

		if (game.isTerminal(state))
			return game.getUtility(state, Player);
		double value = Double.POSITIVE_INFINITY;
		for (XYLocation action: game.getActions(state))
			value = Math.min(value,
					maxValue(game.getResult(state, action), Player));
		return value;
	}


}

