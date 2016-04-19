package Quadrato;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.framework.TreeSearch;
import aima.search.uninformed.BreadthFirstSearch;
import aima.search.uninformed.DepthFirstSearch;

public class QuadratoDemo {
	public static void main(String[] args) {
		
	QState initState = new QState();
		
		try {
			
			initState.getState();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			Problem problem = new Problem(initState, new QSuccessorFunction(), initState);
	
			System.out.println("\nPress enter to execute Breadth First Search (Tree Search)...");
			br.readLine();
			System.out.println("\nBreadth First (Tree Search):");

//			Calendar cal = Calendar.getInstance();
//	        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//	        long millis = System.currentTimeMillis() % 1000;
//	        System.out.println("Tempo millisecondi: "+millis);
//	        System.out.println( sdf.format(cal.getTime()) );
	        
			Search search = new DepthFirstSearch(new TreeSearch());
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		
//			cal = Calendar.getInstance();
//	        sdf = new SimpleDateFormat("HH:mm:ss");
//	        millis = System.currentTimeMillis() % 1000;
//	        System.out.println("Tempo millisecondi: "+millis);
//	        System.out.println( sdf.format(cal.getTime()) );
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
