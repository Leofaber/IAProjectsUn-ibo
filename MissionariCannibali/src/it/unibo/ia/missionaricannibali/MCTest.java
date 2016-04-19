/*
 * Missionari e Cannibali
 * Author: KappaEvin
 */
package it.unibo.ia.missionaricannibali;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.framework.TreeSearch;
import aima.search.uninformed.BreadthFirstSearch;

public class MCTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MCState initState = new MCState();
		
		Problem problem = new Problem(
				initState,
				new MCSuccessorFunction(),
				new MCGoalTest());
		Search search =	new BreadthFirstSearch(new TreeSearch());
		try {
			SearchAgent agent =	new SearchAgent(problem, search);
			
			// semplici metodi di stampa dei risultati...
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
