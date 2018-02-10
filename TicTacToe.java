
import components.simplewriter.SimpleWriter;



import components.simplewriter.SimpleWriter1L;
import components.simplereader.SimpleReader1L;












import java.util.ArrayList;
import java.util.Arrays;
/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 * 
 * @author P. Bucci
 */
import java.util.List;



/**
 * Provides an implementation of the Tic-tac-toe game which can be used for
 * experiments with the Minimax algorithm.
 * 
 * @author Akinlawon Solomon
 * 
 */

public class TicTacToe {

	TicTacToeState initialState = new TicTacToeState();




	public String[] getPlayers() {
		return new String[] { TicTacToeState.X, TicTacToeState.O };
	}

	public String getPlayer(TicTacToeState state) {
		return state.getPlayerToMove();
	}


	public List<XYLocation> getActions(TicTacToeState state) {
		return state.getUnMarkedPositions();
	}


	public TicTacToeState getResult(TicTacToeState state, XYLocation action) {
		TicTacToeState result=state.clone();
		result.mark(action);

		return result;
	}


	public boolean isTerminal(TicTacToeState state) {
		return state.getUtility() != -2;
	}



	public double getUtility(TicTacToeState state, String player) {
		double result = state.getUtility();
		if (result != -2) {
			if (player == TicTacToeState.O)
				result = -result;
		} else {
			throw new IllegalArgumentException("State is not terminal.");
		}
		return result;
	}

	public boolean isCutoff(TicTacToeState state, int maxdepth){
		return state.getNumberOfMarkedPositions()==maxdepth;
	}

	/**
	 * Calculates the evaluation function value which can either be the utility of a terminal state
	 * or a value from the equation eval=3*X2 + X1 -(3*O2 + O1) where Xn the number of rows,
	 * columns or diagonals that has exactly n X's.
	 * @param state
	 *      stores the current tic tac toe state
	 * @param player
	 *       the current player
	 * @return
	 *      An evaluation function value which changes depending on if the current state
	 *      is a cutoff state or a terminal state.
	 */
	public int evalFunction(TicTacToeState state,String player){
		int X1=0;
		int X2=0;
		int O1=0;
		int O2=0;
		TicTacToe currgame=new TicTacToe();
		
		if(currgame.isTerminal(state)){
			return (int) currgame.getUtility(state,player);
		} 
		
		
		//Keeps track of the number of rows with n X's or O's.
		for (int row=0; row<3; row++){
			int temp=0;
			int tempo=0;
			int temp3=0;
			int tempo2=0;
			int middle=0;
			for (int col=0; col<3; col++){
				String temp2=state.getValue(row,col);
				if(temp2.equals("X")){
					temp++;
				}else if (temp2.equals("O")){
					tempo++;
				}
			}
			
			if(tempo==0){
			if(temp==1){
				X1++;
			}else if(temp==2){
				X2++;
			}
			}
            
			if(temp==0){
			if(tempo==1){
				O1++;
			}else if(tempo==2){
				O2++;
			}
			}
			
			
		}
		//Keeps track of the number of columns with n X's or O's.
		for (int col=0; col<3; col++){
			int temp=0;
			int tempo=0;
			for (int row=0; row<3; row++){
				String temp2=state.getValue(row,col);
				if(temp2.equals("X")){
					temp++;
				}else if (temp2.equals("O")){
					tempo++;
				}
			}
			if(tempo==0){
			if(temp==1){
				X1++;
			}else if (temp==2){
				X2++;
			}
			}
            
			if(temp==0){
			if(tempo==1){
				O1++;
			}else if(tempo==2){
				O2++;
			}
			}
		}
		//Checks the two diagonals.
		int temp=0;
		int tempo=0;
		for (int col=0; col<3; col++){
			String temp2=state.getValue(col,col);
			if(temp2.equals("X")){
				temp++;
			}else if (temp2.equals("O")){
				tempo++;
			}
		}
		if(tempo==0){
		if(temp==1){
			X1++;
		}else if(temp==2){
			X2++;
		}
		}
        
		if(temp==0){
		if(tempo==1){
			O1++;
		}else if(tempo==2){
			O2++;
		}
		}
		
		List<String> diag=new ArrayList<>();
		diag.add(state.getValue(0, 2));
		diag.add(state.getValue(1, 1));
		diag.add(state.getValue(2, 0));
		int st=0;
		int st2=0;
		for(String checker:diag){
			if(checker.equals("X")){
				st++;
			}else if (checker.equals("O")){
				st2++;
			}
		}
		if(st2==0){
		if(st==1){
			X1++;
		}else if(st==2){
			X2++;
		}
		}

		if(st==0){
		if(st2==1){
			O1++;
		}else if(st2==2){
			O2++;
		}
		}
	
		
		
		
		int eval=3*X2 + X1 -(3*O2 + O1);
		return(eval);
	}


	public static void makeMove(TicTacToeState state, XYLocation action){
		state.mark(action);
	}

	public static void main(String[] args) {

		SimpleWriter out = new SimpleWriter1L();

		TicTacToeState initState = new TicTacToeState();
		TicTacToe currgame=new TicTacToe();
		MinimaxSearch search=new MinimaxSearch(currgame);
		Hminimax search2=new Hminimax(currgame,4);
		List<XYLocation> Actions= currgame.getActions(initState);

		TicTacToeState state=new TicTacToeState();
		TicTacToeState state2=new TicTacToeState();
		TicTacToeState state4=new TicTacToeState();
		List<Double> temp=search2.MinimaxValues(state,0);
		List<Double> temp3=search.MinimaxValues(state4);
		int i=0;
        
		for (XYLocation action2:Actions){
			double l=temp3.get(i);
			if(state.board[action2.getAbsPosition()].equals("-")){
				state.board[action2.getAbsPosition()]=(int)l + "";
				i++;
			}
		}
		out.println("The minimax values for X's first move are: ");
		out.println();
		out.println(state.toString());
		out.println("-------------------------------------------");
		
		i=0;
		for (XYLocation action2:Actions){
			double l=temp.get(i);
			if(state4.board[action2.getAbsPosition()].equals("-")){
				state4.board[action2.getAbsPosition()]=(int)l + "";
				i++;
			}
		}
		out.println("The h-minimax values for X's first move for a depth limit of 4: ");
		out.println();
		out.println(state4.toString());
		out.println("--------------------------------------------------------------");
		


       
		out.println("The optimal play for X using Minimax: ");
		while(!currgame.isTerminal(initState)){
			
			XYLocation action=search.makeDecision(initState);
			
			makeMove(initState,action);

            
			out.println(initState.toString());
			out.println("Current Player: "+ initState.getPlayerToMove());
			
		}
		
		out.println("----------------------------------------");
		
		
		
		out.println("The optimal play for X using H-minimax and a search limit of 4 plies: ");
		
		while (! currgame.isTerminal(state2)){
	      
			XYLocation action= search2.makeDecision(state2);
			
			makeMove(state2,action);
            search2.maxdepth++;
            
			out.println(state2.toString());
			out.println();
			out.println("Current Player: "+ state2.getPlayerToMove());
		}
		





		out.close();
	}

}
