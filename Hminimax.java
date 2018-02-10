import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;




/**
 * Hminimax differs from minimax in that minimax values no longer depend solely on the utility
 * at a terminal state but states that are considered favorable i.e states a certain depth also possess
 * a value for a given player.
 * 
 * @author Akinlawon Solomon.
 * 
 */

public class Hminimax  {



	public TicTacToe game;
	public int maxdepth;
	public Hminimax(TicTacToe round, int depth){
		this.game=round;
		this.maxdepth=depth;
	}

	/*
	 * A change from minimax is that we can no longer consider values from X's point of view alone
	 * after each player searches to compute the minimax for a given state, since the backed-up values
	 * change on each search because the depth is incremented on each level.
	 */


	public XYLocation makeDecision(TicTacToeState state) {
		int depth=state.getNumberOfMarkedPositions();
		XYLocation result = null;
		double resultValue = Double.NEGATIVE_INFINITY;
		String player = game.getPlayer(state);
		if(player.equals("X")){
			for (XYLocation action : game.getActions(state)) {
				double value = minValue(game.getResult(state, action), player,depth+1);
				if (value > resultValue) {
					result = action;
					resultValue = value;
				}
			}
		}else if (player.equals("O")){
			resultValue=Double.POSITIVE_INFINITY;
			for (XYLocation action : game.getActions(state)) {
				double value = minValue(game.getResult(state, action), player,depth+1);
				if (value < resultValue) {
					result = action;
					resultValue = value;
				}

			}
		}
		return result;
	}

	public List<Double> MinimaxValues(TicTacToeState state,int depth){
		String player = game.getPlayer(state);
		List<Double> result = new ArrayList<Double>();
		for (XYLocation action : game.getActions(state)) {
			result.add(minValue(game.getResult(state, action), player,depth+1));
		}
		return result;
	}

	public double maxValue(TicTacToeState state, String Player, int depth) { // returns a utility
		// value
        /*
         * Now cutoff states are considered i.e states that occur at the current max depth
         * must return an evaluation function value.
         */
		if(depth==maxdepth){
			
			if (game.isCutoff(state, maxdepth)){
				return game.evalFunction(state, Player);
			}
		}
		if(game.isTerminal(state)){
			return game.evalFunction(state, Player);
		}

		double value = Double.NEGATIVE_INFINITY;
		for (XYLocation action : game.getActions(state))
			value = Math.max(value,
					minValue(game.getResult(state, action), Player,depth+1));
		return value;
	}

	public double minValue(TicTacToeState state, String Player,int depth) { // returns a utility
		// value
		/*
         * Now cutoff states are considered i.e states that occur at the current max depth
         * must return an evaluation function value.
         */
		if(depth==maxdepth){
	
			if (game.isCutoff(state, maxdepth)){
				return game.evalFunction(state, Player);
			}
		}
		if(game.isTerminal(state)){
			return game.evalFunction(state, Player);
		}
		double value = Double.POSITIVE_INFINITY;
		for (XYLocation action: game.getActions(state))
			value = Math.min(value,
					maxValue(game.getResult(state, action), Player,depth+1));
		return value;
	}


}

