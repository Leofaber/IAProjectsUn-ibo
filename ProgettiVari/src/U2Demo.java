import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.framework.TreeSearch;
import aima.search.uninformed.BreadthFirstSearch;

public class U2Demo {

	public static void main(String[] args) {
		U2State initState = new U2State();
		
		try {
			
			initState.getState();
			initState.getMembri();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			Problem problem = new Problem(initState,
					new U2SuccessorFunction(),
					initState);
			
			System.out.println("\nPress enter to execute Breadth First Search (Tree Search)...");
			br.readLine();
			System.out.println("\nBreadth First (Tree Search):");
			Search search = new BreadthFirstSearch(new TreeSearch());
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		/*	
			System.out.println("\nPress enter to execute Breadth First Search (Graph Search)...");
			br.readLine();
			System.out.println("\nBreadth First (Graph Search):");
			Search search = new BreadthFirstSearch(new TreeSearch());
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		*/	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printInstrumentation(Properties properties) {
		Iterator keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property.toString());
		}
	}

	private static void printActions(List actions) {
		for (int i = 0; i < actions.size(); i++) {
			String action = (String) actions.get(i);
			System.out.println(action);
		}
	}
}
