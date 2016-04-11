package com.aima.ftt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import aima.search.framework.*;
import aima.search.uninformed.*;
import aima.search.informed.*;
import aima.search.framework.SearchUtils;

public class Main { 

	public static boolean printPathFunc;
	
	public static void main(String[] args) throws Exception {
	//	WorldStateSuccessorFunction wsdf = new WorldStateSuccessorFunction();
 
//		WorldState ws = new WorldState(1,2);
//		ws.toString();
//		System.out.println("Stato ammissibile? "+WorldStateSuccessorFunction.isAllowed(ws));
//		System.out.println(ws.isGoalState(ws));
//		System.out.println("Successori:"+wsdf.getSuccessors(ws));
		
//		WorldState newState = WorldStateSuccessorFunction.moveNORTH(ws);
//		newState.toString();
//		System.out.println("Stato ammissibile? "+WorldStateSuccessorFunction.isAllowed(newState));
//		System.out.println(newState.isGoalState(newState));
//		
//		
//		WorldState new2State = WorldStateSuccessorFunction.moveNORTH(newState);
//		new2State.toString();
//		System.out.println("Stato ammissibile? "+WorldStateSuccessorFunction.isAllowed(new2State));
//		System.out.println(new2State.isGoalState(new2State));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		boolean random = false;
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter 1 for large size and random map: ");
		int n = reader.nextInt(); // Scans the next token of the input as an int.
		if(n==1){
			random = true;
		}
		
		printPathFunc = false;
		System.out.println("Enter 1 for print path functionality: ");
	    n = reader.nextInt(); // Scans the next token of the input as an int.
		if(n==1){
			printPathFunc = true;
		}
		
		
		
		WorldState initState = new WorldState(0,0,random);
	    initState.setFather(null);
		aima.search.framework.Problem problem = new aima.search.framework.Problem(initState,
				new WorldStateSuccessorFunction(),
				initState);
 		System.out.println("*********** TRESOURE FINDER *************");
 		
 		System.out.println("\n MAPPA DEL TESORO\n");
		System.out.println(initState.toString());
		System.out.println("\n\n @ Personaggio\n T Tesoro\n X Fossi\n * Accedibili\n Uniche azioni: MOVE SOUTH, MOVE EAST");

		
		System.out.println("- Press 1 to execute Breadth First Search (GraphSearch)");
		System.out.println("- Press 2 to execute Depth First Search (Graph Search)...");
		System.out.println("- Press 3 to execute Depth First Search Limited(Graph Search)...");
		System.out.println("- Press 4 to execute A* Search (Graph Search)...");

		int n1 = reader.nextInt(); // Scans the next token of the input as an int.
		switch(n1){
			case 1:
				System.out.println("\nBreadth First (Tree Search):");
				System.out.println(initState.toString());
				BreadthFirstSearch search = new BreadthFirstSearch(new GraphSearch());
				SearchAgent agent = new SearchAgent(problem, search);
				printActions(agent.getActions());
				printInstrumentation(agent.getInstrumentation());
				break;
			case 2:
 				System.out.println("\nDepth First (Graph Search):");
				DepthFirstSearch dsearch = new DepthFirstSearch(new GraphSearch());
				agent = new SearchAgent(problem, dsearch);
				printActions(agent.getActions());
				printInstrumentation(agent.getInstrumentation());
				break;
			case 3:
 				System.out.println("\nDepth First LIMITED (Graph Search):");
				System.out.println(initState.toString());
				DepthLimitedSearch searchDp = new DepthLimitedSearch(40);
				SearchAgent agent3 = new SearchAgent(problem, searchDp);
				printActions(agent3.getActions());
				printInstrumentation(agent3.getInstrumentation());
				
				break;
			case 4:
				problem = new Problem(initState, new WorldStateSuccessorFunction(), initState, initState, new WorldStateHeuristicFunction());
 				System.out.println(initState.toString());
			//	System.out.println("\nAStar (Tree Search):");
				AStarSearch search5 = new AStarSearch(new GraphSearch());
				SearchAgent agent1 = new SearchAgent(problem, search5);
				printActions(agent1.getActions());
				printInstrumentation(agent1.getInstrumentation());
				break;
				
				
		}
	
		
		
//		System.out.println("\nPress enter to execute Breadth First Search (Graph Search)...");
//		br.readLine();
//		System.out.println("\nBreadth First (Graph Search):");
//		System.out.println(initState.toString());
//		search = new BreadthFirstSearch(new GraphSearch());
//		agent = new SearchAgent(problem, search);
//		printActions(agent.getActions());
//		printInstrumentation(agent.getInstrumentation());
		
	
		
		
	
		
		
		
		
		
//		System.out.println("\nPress enter to execute Greedy Best First Search (Tree Search)...");
//		System.out.println(initState.toString());
//		br.readLine();
// 		GreedyBestFirstSearch search3 = new GreedyBestFirstSearch(new TreeSearch());
//		agent = new SearchAgent(problem, search3);
//		printActions(agent.getActions());
//		printInstrumentation(agent.getInstrumentation());
		
//		
//		System.out.println("\nPress enter to execute Hill Climbing Search...");
//		br.readLine();
//		System.out.println(initState.toString());
//		System.out.println("\nHill Climbing:");
//		HillClimbingSearch search4 = new HillClimbingSearch();
//		agent = new SearchAgent(problem, search4);
//		printActions(agent.getActions());
//		printInstrumentation(agent.getInstrumentation());
		
	 
		
		
		
		
		
		
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
