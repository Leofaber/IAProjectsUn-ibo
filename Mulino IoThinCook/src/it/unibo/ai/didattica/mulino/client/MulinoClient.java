package it.unibo.ai.didattica.mulino.client;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import it.unibo.ai.didattica.mulino.actions.Action;
import it.unibo.ai.didattica.mulino.actions.Phase1Action;
import it.unibo.ai.didattica.mulino.actions.Phase2Action;
import it.unibo.ai.didattica.mulino.actions.PhaseFinalAction;

import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import it.unibo.ai.didattica.mulino.domain.State.Phase;

import it.unibo.ai.didattica.mulino.engine.TCPMulino;
import it.unibo.ai.didattica.mulino.iothincook.MulinoGame;
import it.unibo.ai.didattica.mulino.iothincook.MulinoGamePhase2;
import it.unibo.ai.didattica.mulino.iothincook.MulinoGamePhase3;
import it.unibo.ai.didattica.mulino.iothincook.MulinoIterativeDeepeningAlphaBetaSearch;
import it.unibo.ai.didattica.mulino.iothincook.MulinoState;


public class MulinoClient {
	
	private State.Checker player;
	private Socket playerSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private static int time=50;
	private static boolean whiteIA = true;
	private static boolean blackIA = true;
	private static boolean debug = false;
	
	
	
	public MulinoClient(State.Checker player) throws UnknownHostException, IOException {
		this.player = player;
		int port = 0;
		switch (player) {
			case WHITE:
				port = TCPMulino.whiteSocket;
				break;
			case BLACK:
				port = TCPMulino.blackSocket;
				break;
			default:
				System.exit(5);
		}
		playerSocket = new Socket("localhost", port);
		out = new ObjectOutputStream(playerSocket.getOutputStream());
		in = new ObjectInputStream(new BufferedInputStream(playerSocket.getInputStream()));
	}
	
	
	public void write(Action action) throws IOException, ClassNotFoundException {
		out.writeObject(action);
	}
	
	public State read() throws ClassNotFoundException, IOException {
		return (State) in.readObject();
	}
	
	public State.Checker getPlayer() { return player; }
	public void setPlayer(State.Checker player) { this.player = player; }
	
	
	
	
	
	
	
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		State.Checker player=null;
		
		if (args.length == 0) {
            System.out.println("Usage:");
            System.out.println("-w --white     Set the player to White");
            System.out.println("-b --black     Set the player to Black");
            System.out.println("-h --human     Set the player as Human (default is IA)");
            System.out.println("-t <time>      Search w/ iterative deepening for <time> seconds");
            System.out.println("-d --debug     Print the debug output to standard output");
            System.exit(-1);
        }
			
		for (int i = 0; i < args.length; i++) {
			switch(args[i].toLowerCase()){
			case "-w":
            case "--white":
            	player= State.Checker.WHITE;
            	break;
            case "-b":
            case "--black":
            	player = State.Checker.BLACK;
            	break;
            case "-h":
            case "--human":
            	if(player == Checker.WHITE)
            		whiteIA=false;
            	else if(player == Checker.BLACK)
            		blackIA=false;
            	break;
            case "-t":
                time = Integer.parseInt(args[++i]);
                break;
            case "-d":
            case "--debug":
                debug = true;
                break;	
			}
		}
		
		System.out.println("You are player "+player+"!");
		if(player == Checker.WHITE){
			if(whiteIA)
				System.out.println("You are AI!");
			else 
				System.out.println("You are Human!");	
		}else{
			if(blackIA)
				System.out.println("You are AI!");
			else 
				System.out.println("You are Human!");	
		}
		
		String actionString = "";
		Phase1Action action;
		Phase2Action action2;
		PhaseFinalAction action3;
		State currentState = null;
		MulinoState mulinoCurrentState = null;
		
		if (player == State.Checker.WHITE) {
			MulinoClient client = new MulinoClient(State.Checker.WHITE);
//			System.out.println("You are player " + client.getPlayer().toString() + "!");
			System.out.println("Current state:");			
			currentState = client.read();
			System.out.println(currentState.toString());
			
			BufferedReader in = new BufferedReader( new InputStreamReader(System.in));
			
			while (currentState.getCurrentPhase()==Phase.FIRST) {
				System.out.println("Player " + client.getPlayer().toString() + ", do your move: ");

				if(whiteIA){
					try{
						mulinoCurrentState = MulinoState.convertStateToMulinoState(currentState);
						mulinoCurrentState.setCurrentPlayer(Checker.WHITE); 
						IterativeDeepeningAlphaBetaSearch<MulinoState, String, State.Checker> search=new MulinoIterativeDeepeningAlphaBetaSearch(new MulinoGame(mulinoCurrentState), -1100, 1100, time);
						search.setLogEnabled(debug);
						actionString=search.makeDecision(mulinoCurrentState.clone());
						System.out.println("decision: "+actionString);
					}catch(Exception e){
						System.out.println(e);
					}
				}else{
					actionString = in.readLine();					
				}
				

				action = new Phase1Action();
				action.setPutPosition(actionString.substring(0, 2));
				if (actionString.length() == 4)
					action.setRemoveOpponentChecker(actionString.substring(2,4));
				else
					action.setRemoveOpponentChecker(null);
				client.write(action);
				currentState = client.read();
				System.out.println("Effect of your move: ");
				System.out.println(currentState.toString());
				System.out.println("Waiting for your opponent move... ");
				currentState = client.read();
				System.out.println("Your Opponent did his move, and the result is: ");
				System.out.println(currentState.toString());
			}
			System.out.println("CLIENT W --> FASE 1 TERMINATA");
			while(currentState.getCurrentPhase()==Phase.SECOND) {
				System.out.println("Player " + client.getPlayer().toString() + ", do your move: ");
				if(whiteIA){	
					try{	
						mulinoCurrentState = MulinoState.convertStateToMulinoState(currentState);
						mulinoCurrentState.setCurrentPlayer(Checker.WHITE);
						IterativeDeepeningAlphaBetaSearch<MulinoState, String, State.Checker> search=new MulinoIterativeDeepeningAlphaBetaSearch(new MulinoGamePhase2(mulinoCurrentState), -1100, 1100, time);
						search.setLogEnabled(debug);
						actionString=search.makeDecision(mulinoCurrentState.clone());
						System.out.println("decision: "+actionString);
					}catch(Exception e){
						System.out.println(e);
					}
				}else{
					actionString = in.readLine();
				}

				action2=new Phase2Action();
				action2.setFrom(actionString.substring(0, 2));
				action2.setTo(actionString.substring(2, 4));
				if(actionString.length()==6)
					action2.setRemoveOpponentChecker(actionString.substring(4, 6));
				else
					action2.setRemoveOpponentChecker(null);				
				client.write(action2);
				currentState = client.read();
				System.out.println("Effect of your move: ");
				System.out.println(currentState.toString());
				System.out.println("Waiting for your opponent move... ");
				currentState = client.read();
				System.out.println("Your Opponent did his move, and the result is: ");
				System.out.println(currentState.toString());
			}
			System.out.println("CLIENT W --> FASE 2 TERMINATA");
			while(currentState.getCurrentPhase()==Phase.FINAL) {
				System.out.println("Player " + client.getPlayer().toString() + ", do your move: ");
				if(whiteIA){
					try{	
						mulinoCurrentState = MulinoState.convertStateToMulinoState(currentState);
						mulinoCurrentState.setCurrentPlayer(Checker.WHITE);
						IterativeDeepeningAlphaBetaSearch<MulinoState, String, State.Checker> search=new MulinoIterativeDeepeningAlphaBetaSearch(new MulinoGamePhase3(mulinoCurrentState), -1100, 1100, time);
						search.setLogEnabled(debug);
						actionString=search.makeDecision(mulinoCurrentState.clone());
						System.out.println("decision: "+actionString);
					}catch(Exception e){
						System.out.println(e);
					}
				}else{
					actionString = in.readLine();
				}
				action3=new PhaseFinalAction();
				action3.setFrom(actionString.substring(0, 2));
				action3.setTo(actionString.substring(2, 4));
				if(actionString.length()==6)
					action3.setRemoveOpponentChecker(actionString.substring(4, 6));
				else
					action3.setRemoveOpponentChecker(null);		
				client.write(action3);
				currentState = client.read();
				System.out.println("Effect of your move: ");
				System.out.println(currentState.toString());
				System.out.println("Waiting for your opponent move... ");
				currentState = client.read();
				System.out.println("Your Opponent did his move, and the result is: ");
				System.out.println(currentState.toString());
				if(currentState.getBlackCheckersOnBoard()<3){
					System.out.println("PLAYER W HAS WON!");
					break;
				}else if(currentState.getWhiteCheckersOnBoard()<3){
					System.out.println("PLAYER B HAS WON!");
					break;
				}		
			}
			System.out.println("MATCH IS FINISHED!");
			
		}
		else { 
			MulinoClient client = new MulinoClient(State.Checker.BLACK);
			BufferedReader in = new BufferedReader( new InputStreamReader(System.in));
			currentState = client.read();
//			System.out.println("You are player " + client.getPlayer().toString() + "!");
			System.out.println("Current state:");
			System.out.println(currentState.toString());
			while (currentState.getCurrentPhase()==Phase.FIRST) {
				System.out.println("Waiting for your opponent move...");
				currentState = client.read();
				System.out.println("Your Opponent did his move, and the result is: ");
				System.out.println(currentState.toString());
				System.out.println("Player " + client.getPlayer().toString() + ", do your move: ");

				if(blackIA){
					try{
						mulinoCurrentState = MulinoState.convertStateToMulinoState(currentState);
						mulinoCurrentState.setCurrentPlayer(Checker.BLACK);
						IterativeDeepeningAlphaBetaSearch<MulinoState, String, State.Checker> search=new MulinoIterativeDeepeningAlphaBetaSearch(new MulinoGame(mulinoCurrentState), -1100, 1100, time);
						search.setLogEnabled(debug);
						actionString=search.makeDecision(mulinoCurrentState.clone());
						System.out.println("decision: "+actionString);
					}catch(Exception e){
						System.out.println(e);
					}
				}else{
					actionString=in.readLine();
				}
				action = new Phase1Action();
				action.setPutPosition(actionString.substring(0, 2));
				if (actionString.length() == 4)
					action.setRemoveOpponentChecker(actionString.substring(2,4));
				else
					action.setRemoveOpponentChecker(null);
				client.write(action);
				currentState = client.read();
				System.out.println("Effect of your move: ");
				System.out.println(currentState.toString());
			}
			
			System.out.println("CLIENT B --> FASE 1 TERMINATA");
			System.out.println("Waiting for your opponent move...");
			currentState = client.read();
			System.out.println("Your Opponent did his move, and the result is: ");
			System.out.println(currentState.toString());
			
			while(currentState.getCurrentPhase()==Phase.SECOND){
				System.out.println("Player " + client.getPlayer().toString() + ", do your move: ");
				if(blackIA){
					try{
						mulinoCurrentState = MulinoState.convertStateToMulinoState(currentState);
						mulinoCurrentState.setCurrentPlayer(Checker.BLACK);
						IterativeDeepeningAlphaBetaSearch<MulinoState, String, State.Checker> search=new MulinoIterativeDeepeningAlphaBetaSearch(new MulinoGamePhase2(mulinoCurrentState), -1100, 1100, time);
						search.setLogEnabled(debug);
						actionString=search.makeDecision(mulinoCurrentState.clone());
						System.out.println("decision: "+actionString);
					}catch(Exception e){
						System.out.println(e);
					}
				}else{
					actionString=in.readLine();
				}
				
				action2=new Phase2Action();
				action2.setFrom(actionString.substring(0, 2));
				action2.setTo(actionString.substring(2, 4));
				if(actionString.length()==6)
					action2.setRemoveOpponentChecker(actionString.substring(4, 6));
				else
					action2.setRemoveOpponentChecker(null);	
				
				
				client.write(action2);
				currentState = client.read();
				System.out.println("Effect of your move: ");
				System.out.println(currentState.toString());
				
				System.out.println("Waiting for your opponent move...");
				currentState = client.read();
				System.out.println("Your Opponent did his move, and the result is: ");
				System.out.println(currentState.toString());	
			}
			System.out.println("CLIENT B --> FASE 2 TERMINATA");
			
			while(currentState.getCurrentPhase()==Phase.FINAL){
				System.out.println("Player " + client.getPlayer().toString() + ", do your move: ");
				if(blackIA){
					try{
						mulinoCurrentState = MulinoState.convertStateToMulinoState(currentState);
						mulinoCurrentState.setCurrentPlayer(Checker.BLACK);
						IterativeDeepeningAlphaBetaSearch<MulinoState, String, State.Checker> search=new MulinoIterativeDeepeningAlphaBetaSearch(new MulinoGamePhase3(mulinoCurrentState), -1100, 1100, time);
						search.setLogEnabled(debug);
						actionString=search.makeDecision(mulinoCurrentState.clone());
						System.out.println("decision: "+actionString);
					}catch(Exception e){
						System.out.println(e);
					}
				}else{
					actionString=in.readLine();
				}
				action3=new PhaseFinalAction();
				action3.setFrom(actionString.substring(0, 2));
				action3.setTo(actionString.substring(2, 4));
				if(actionString.length()==6)
					action3.setRemoveOpponentChecker(actionString.substring(4, 6));
				else
					action3.setRemoveOpponentChecker(null);	
				
				
				client.write(action3);
				currentState = client.read();
				System.out.println("Effect of your move: ");
				System.out.println(currentState.toString());
				
				System.out.println("Waiting for your opponent move...");
				currentState = client.read();
				System.out.println("Your Opponent did his move, and the result is: ");
				System.out.println(currentState.toString());
				if(currentState.getBlackCheckersOnBoard()<3){
					System.out.println("PLAYER W HAS WON!");
					break;
				}else if(currentState.getWhiteCheckersOnBoard()<3){
					System.out.println("PLAYER B HAS WON!");
					break;
				}
			}
			System.out.println("MATCH IS FINISHED!");
		}
		
	}

}
