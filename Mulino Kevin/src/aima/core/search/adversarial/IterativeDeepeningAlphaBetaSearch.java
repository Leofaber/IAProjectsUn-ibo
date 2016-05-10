package aima.core.search.adversarial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aima.core.search.framework.Metrics;
import it.unibo.ai.didattica.mulino.client.ActionComparator;
import it.unibo.ai.didattica.mulino.client.MulinoGame;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import it.unibo.ai.didattica.mulino.test.ClosedMillTester;

/**
 * Implements an iterative deepening Minimax search with alpha-beta pruning and
 * action ordering. Maximal computation time is specified in seconds. The
 * algorithm is implemented as template method and can be configured and tuned
 * by subclassing.
 * 
 * @author Ruediger Lunde
 * 
 * @param <STATE>
 *            Type which is used for states in the game.
 * @param <ACTION>
 *            Type which is used for actions in the game.
 * @param <PLAYER>
 *            Type which is used for players in the game.
 */
public class IterativeDeepeningAlphaBetaSearch<STATE, ACTION, PLAYER>
		implements AdversarialSearch<STATE, ACTION> {

	public final static String METRICS_NODES_EXPANDED = "nodesExpanded";
	public final static String METRICS_MAX_DEPTH = "maxDepth";
	
	protected Game<STATE, ACTION, PLAYER> game;
	protected double utilMax;
	protected double utilMin;
	protected int currDepthLimit;
	private boolean maxDepthReached;
	private long maxTime;
	private boolean logEnabled;

	private Metrics metrics = new Metrics();

	/** Creates a new search object for a given game. */
	public static <STATE, ACTION, PLAYER> IterativeDeepeningAlphaBetaSearch<STATE, ACTION, PLAYER> createFor(
			Game<STATE, ACTION, PLAYER> game, double utilMin, double utilMax,
			int time) {
		return new IterativeDeepeningAlphaBetaSearch<STATE, ACTION, PLAYER>(
				game, utilMin, utilMax, time);
	}

	public IterativeDeepeningAlphaBetaSearch(Game<STATE, ACTION, PLAYER> game,
			double utilMin, double utilMax, int time) {
		this.game = game;
		this.utilMin = utilMin;
		this.utilMax = utilMax;
		this.maxTime = time * 1000; // internal: ms instead of s
	}

	public void setLogEnabled(boolean b) {
		logEnabled = b;
	}

	/**
	 * Template method controlling the search.
	 */
	@Override
	public ACTION makeDecision(STATE state) {
		metrics = new Metrics();
		List<ACTION> results = null;
		double resultValue = Double.NEGATIVE_INFINITY;
		PLAYER player = game.getPlayer(state);
		StringBuffer logText = null;
		currDepthLimit = 0;
		long startTime = System.currentTimeMillis();
		boolean exit = false;
		HashMap<ACTION, Double> table;
		do {
			table= new HashMap<>();
			incrementDepthLimit();
			maxDepthReached = false;
			List<ACTION> newResults = new ArrayList<ACTION>();
			double newResultValue = Double.NEGATIVE_INFINITY;
			double secondBestValue = Double.NEGATIVE_INFINITY;
			if (logEnabled)
				logText = new StringBuffer("depth " + currDepthLimit + ": ");
			for (ACTION action : orderActions(state, game.getActions(state), player,currDepthLimit)) {
				if (results != null
						&& System.currentTimeMillis() > startTime + maxTime) {
					exit = true;
					break;
				}
				double value = minValue(game.getResult(state, action), player,
						Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1);
				table.put(action, value);
				if (logEnabled)
					logText.append(action + "->" + value + " ");
				if (value >= newResultValue) {
					if (value > newResultValue) {
						secondBestValue = newResultValue;
						newResultValue = value;
						newResults.clear();
					}
					newResults.add(action);
				} else if (value > secondBestValue) {
					secondBestValue = value;
				}
			}
			if (logEnabled)
				System.out.println("\n"+logText);
				System.out.println("Tempo:"+(System.currentTimeMillis()-startTime));
			//	System.out.println("NEW RESULTS:\n"+newResults);
			if (!exit || isSignificantlyBetter(newResultValue, resultValue)) {
				results = newResults;
				resultValue = newResultValue;
			}
			if (!exit && results.size() == 1
					&& isSignificantlyBetter(resultValue, secondBestValue))
				break;
		} while (!exit && maxDepthReached && !hasSafeWinner(resultValue));
		Double max= -10000000.0;
		ACTION actionMax= null;
		for(Map.Entry<ACTION, Double> entry : table.entrySet()){
			if(entry.getValue()>max){
				max=entry.getValue();
				actionMax=entry.getKey();
			}
		}
//		return results.get(0);
		return actionMax;
	}

	public double maxValue(STATE state, PLAYER player, double alpha,
			double beta, int depth) { // returns an utility value
		
		updateMetrics(depth);
		if (game.isTerminal(state) || depth >= currDepthLimit) {
			
			/*
			 * 	DEBUG **********************************
			 * 
			 
			double evalDebug = eval(state, player);
			if(game.isTerminal(state)){
				System.out.println("\n[MAX] Questo stato è terminale:\n"+state.toString()+"\n ed ha valore: "+evalDebug);
			}
			if(depth >= currDepthLimit){
				System.out.println("\n[MAX] Profondità: "+depth+"    \n"+state.toString()+"\n ed ha valore: "+evalDebug);
							
			}
			return evalDebug;
			 fine debug 
			*/return eval(state, player);
		} else {
			double value = Double.NEGATIVE_INFINITY;
			for (ACTION action : orderActions(state, game.getActions(state),
					player, depth)) {
				value = Math.max(value, minValue(game.getResult(state, action), //
						player, alpha, beta, depth + 1));
				if (value >= beta)
					return value;
				alpha = Math.max(alpha, value);
			}
			return value;
		}
	}

	public double minValue(STATE state, PLAYER player, double alpha,
			double beta, int depth) { // returns an utility
		
		updateMetrics(depth);
		if (game.isTerminal(state) || depth >= currDepthLimit) {
			
			
			/*
			 * 	DEBUG **********************************
			 * 
			 
			
			
			double evalDebug = eval(state, player);
			if(game.isTerminal(state)){
				System.out.println("\n[MIN] Questo stato è terminale:\n"+state.toString()+"\n ed ha valore: "+evalDebug);
			}
			if(depth >= currDepthLimit){
				System.out.println("\n[MIN] Profondità: "+depth+"    \n"+state.toString()+"\n ed ha valore: "+evalDebug);
					
			}
			return evalDebug;
			fine debug 
			
			*/return eval(state, player);
		} else {
			double value = Double.POSITIVE_INFINITY;
			for (ACTION action : orderActions(state, game.getActions(state),
					player, depth)) {
				value = Math.min(value, maxValue(game.getResult(state, action), //
						player, alpha, beta, depth + 1));
				if (value <= alpha)
					return value;
				beta = Math.min(beta, value);
			}
			return value;
		}
	}

	private void updateMetrics(int depth) {
		metrics.incrementInt(METRICS_NODES_EXPANDED);
		metrics.set(METRICS_MAX_DEPTH, Math.max(metrics.getInt(METRICS_MAX_DEPTH), depth));
	}
	
	/** Returns some statistic data from the last search. */
	@Override
	public Metrics getMetrics() {
		return metrics;
	}

	/**
	 * Primitive operation which is called at the beginning of one depth limited
	 * search step. This implementation increments the current depth limit by
	 * one.
	 */
	protected void incrementDepthLimit() {
		currDepthLimit++;
	}

	/**
	 * Primitive operation which is used to stop iterative deepening search in
	 * situations where a clear best action exists. This implementation returns
	 * always false.
	 */
	protected boolean isSignificantlyBetter(double newUtility, double utility) {
		return false;
	}

	/**
	 * Primitive operation which is used to stop iterative deepening search in
	 * situations where a safe winner has been identified. This implementation
	 * returns true if the given value (for the currently preferred action
	 * result) is the highest or lowest utility value possible.
	 */
	protected boolean hasSafeWinner(double resultUtility) {
		return resultUtility <= utilMin || resultUtility >= utilMax;
	}

	/**
	 * Primitive operation, which estimates the value for (not necessarily
	 * terminal) states. This implementation returns the utility value for
	 * terminal states and <code>(utilMin + utilMax) / 2</code> for non-terminal
	 * states.
	 */
	protected double eval(STATE state, PLAYER player) {
		if (game.isTerminal(state)) {
//			System.out.println("TERMINAL STATE REACHED");
			return game.getUtility(state, player);
		} else {
			maxDepthReached = true;
			if(currDepthLimit>6){
				return game.getUtility(state, player);
			}
			return (utilMin + utilMax) / 2;
			
			//return (utilMin + utilMax) / 2;
		}
	}

	/**
	 * Primitive operation for action ordering. This implementation preserves
	 * the original order (provided by the game).
	 */
	public List<ACTION> orderActions(STATE state, List<ACTION> actions, PLAYER player, int depth) {
		
		return actions;
	}
}

