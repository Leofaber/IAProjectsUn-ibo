package aima.core.search.adversarial;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import aima.core.search.framework.Metrics;

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
	private int maxDepth;
	private int initialDepth;

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
		
		HashMap<ACTION, Double> previousDepthTable = new HashMap<>();
		HashMap<ACTION, Double> currentDepthTable = new HashMap<>();
		//table old
		//table current
		do {
			previousDepthTable = currentDepthTable;
			currentDepthTable =new HashMap<>();
//			table old = table current
//			table current = new hashMap()
			
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
				currentDepthTable.put(action, value);
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
			if (logEnabled){
				System.out.println("\n"+logText);
				System.out.println("Tempo:"+(System.currentTimeMillis()-startTime));
			}
			if (!exit || isSignificantlyBetter(newResultValue, resultValue)) {
				results = newResults;
				resultValue = newResultValue;
			}
//			if (!exit && results.size() == 1 && isSignificantlyBetter(resultValue, secondBestValue))
//				break;
			if(currDepthLimit == maxDepth && (System.currentTimeMillis()-startTime)<(maxTime - 33000)){
				maxDepth++;
				if(logEnabled)
					System.out.println("Profondità massima aumentata a: "+maxDepth);					
			}
			
		} while (!exit && maxDepthReached && !hasSafeWinner(resultValue) && currDepthLimit<maxDepth);
		Double max= -10000000.0;
		ACTION actionMax= null;
		List<ACTION> listMaxAction = new ArrayList<ACTION>();
		List<ACTION> listPrecMaxAction = new ArrayList<ACTION>();
		
		for(Map.Entry<ACTION, Double> entry : table.entrySet()){
			if(entry.getValue()>max){
				max=entry.getValue();
				actionMax=entry.getKey();
			}
		}
		// cerca il massimo tra la old e la current
		ACTION previousActionMax = null;
		double previousMaxValue = Double.NEGATIVE_INFINITY;
		for(Map.Entry<ACTION, Double> entry : previousDepthTable.entrySet()){
			if(entry.getValue()>=previousMaxValue){
				previousMaxValue=entry.getValue();
				previousActionMax=entry.getKey();
			}
		}
		for(Map.Entry<ACTION, Double> entry : previousDepthTable.entrySet()){
			if(entry.getValue()==previousMaxValue){
				listPrecMaxAction.add(entry.getKey());
			}
		}
	
		ACTION currentActionMax = null;
		double currentMaxValue = Double.NEGATIVE_INFINITY;
		for(Map.Entry<ACTION, Double> entry : currentDepthTable.entrySet()){
			if(entry.getValue()>=currentMaxValue){
				currentMaxValue=entry.getValue();
				currentActionMax=entry.getKey();
			}
		}
		
		for(Map.Entry<ACTION, Double> entry : currentDepthTable.entrySet()){
			if(entry.getValue()==currentMaxValue){
				listMaxAction.add(entry.getKey());
			}
		}
		
		if(logEnabled){
			System.out.println("Miglior azione depth precedente: "+previousActionMax+" con valore: "+previousMaxValue);
			System.out.println("Miglior azione depth corrente: "+currentActionMax+" con valore: "+currentMaxValue);
		}
//		if(previousMaxValue > currentMaxValue && !currentDepthTable.containsKey(previousActionMax)){
		if(previousActionMax!=null && !currentDepthTable.containsKey(previousActionMax)){
			ACTION definitive;
			if(listPrecMaxAction.size()>0)
				definitive= listPrecMaxAction.get(new Random().nextInt(listPrecMaxAction.size()));
			else
				definitive=previousActionMax;
			if(logEnabled){
				System.out.println(listPrecMaxAction.toString());
				System.out.println("Azione migliore (prec): "+definitive+" con valore: "+previousMaxValue);
			}
			return definitive;
		}else{
			ACTION definitive;
			if(listMaxAction.size()>0)
				definitive= listMaxAction.get(new Random().nextInt(listMaxAction.size()));
			else 
				definitive=currentActionMax;
			if(logEnabled) {
				System.out.println(listMaxAction.toString());
				System.out.println("Azione migliore (curr): "+definitive+" con valore: "+currentMaxValue);
			}
			return definitive;
		}
		
		// SI PUò PENSARE DI RANDOMIZZARE LE MOSSE A PARI VALORE, TANTO PER RENDERE FRIZZANTE IL GIOCO
		//		return results.get(0);
		//return actionMax;
	}

	public double maxValue(STATE state, PLAYER player, double alpha,
			double beta, int depth) { // returns an utility value
		
		updateMetrics(depth);
		if (game.isTerminal(state) || depth >= currDepthLimit) {
			return eval(state, player);
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
			return eval(state, player);
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
			return game.getUtility(state, player);
		} else {
			maxDepthReached = true;
			if(currDepthLimit>initialDepth){
				return game.getUtility(state, player);
			}
			return (utilMin + utilMax) / 2;
		}
	}

	/**
	 * Primitive operation for action ordering. This implementation preserves
	 * the original order (provided by the game).
	 */
	public List<ACTION> orderActions(STATE state, List<ACTION> actions, PLAYER player, int depth) {
		
		return actions;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	public int getInitialDepth() {
		return initialDepth;
	}

	public void setInitialDepth(int initialDepth) {
		this.initialDepth = initialDepth;
	}
}

