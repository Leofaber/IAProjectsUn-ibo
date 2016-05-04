package it.unibo.ai.didattica.mulino.client;

import java.util.Comparator;

import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;

public class ActionComparator implements Comparator<String>{
	private State currentState;
	private Checker player;
	
	public ActionComparator(State state, Checker player) {
		setCurrentState(state); setPlayer(player);
	}

	@Override
	public int compare(String action1, String action2) {			
		State s1=getCurrentState().clone();
		State s2=getCurrentState().clone();
		HeuristicEvaluator eval1;
		HeuristicEvaluator eval2;
		double val1=0.0 ,val2=0.0;
		
		if(action1.length()==2){
			val1 += MulinoGame.closedMill(s1,action1,getPlayer()) ? 18 : 0;
			s1.getBoard().put(action1, getPlayer());
			switch(getPlayer()){
			case WHITE:
				if(action1!=""){
					s1.setWhiteCheckers(s1.getWhiteCheckers()-1);			
					s1.setWhiteCheckersOnBoard(s1.getWhiteCheckersOnBoard()+1);
				}
				break;
			case BLACK:
				if(action1!=""){
					s1.setBlackCheckers(s1.getBlackCheckers()-1);
					s1.setBlackCheckersOnBoard(s1.getBlackCheckersOnBoard()+1);
				}
				break;
			}
			
		}else if(action1.length()==4){
			String sub1,sub2;
			sub1=action1.substring(0,2);
			sub2=action1.substring(2, 4);
			val1 += MulinoGame.closedMill(s1,sub1,getPlayer()) ? 18 : 0;
			s1.getBoard().put(sub1, getPlayer());
			s1.getBoard().put(sub2, Checker.EMPTY);
			switch(getPlayer()){
			case WHITE:
				if(action1!=""){
					s1.setWhiteCheckers(s1.getWhiteCheckers()-1);			
					s1.setWhiteCheckersOnBoard(s1.getWhiteCheckersOnBoard()+1);
					s1.setBlackCheckersOnBoard(s1.getBlackCheckersOnBoard()-1);
				}
				break;
			case BLACK:
				if(action1!=""){
					s1.setBlackCheckers(s1.getBlackCheckers()-1);
					s1.setBlackCheckersOnBoard(s1.getBlackCheckersOnBoard()+1);
					s1.setWhiteCheckersOnBoard(s1.getWhiteCheckersOnBoard()-1);
				}
				break;
			}
		}
		
		if(action2.length()==2){
			val2 += MulinoGame.closedMill(s2,action2,getPlayer()) ? 18 : 0;		
			s2.getBoard().put(action2, getPlayer());
			switch(getPlayer()){
			case WHITE:
				if(action2!=""){
					s2.setWhiteCheckers(s2.getWhiteCheckers()-1);
					s2.setWhiteCheckersOnBoard(s2.getWhiteCheckersOnBoard()+1);
				}
				break;
			case BLACK:
				if(action2!=""){
					s2.setBlackCheckers(s2.getBlackCheckers()-1);
					s2.setBlackCheckersOnBoard(s2.getBlackCheckersOnBoard()+1);
				}
				break;
			}
		}else if(action2.length()==4){
			String sub1,sub2;
			sub1=action2.substring(0,2);
			sub2=action2.substring(2, 4);
			val2 += MulinoGame.closedMill(s2,sub1,getPlayer()) ? 18 : 0;
			s2.getBoard().put(sub1, getPlayer());
			s2.getBoard().put(sub2, Checker.EMPTY);
			switch(getPlayer()){
			case WHITE:
				if(action2!=""){
					s2.setWhiteCheckers(s2.getWhiteCheckers()-1);			
					s2.setWhiteCheckersOnBoard(s2.getWhiteCheckersOnBoard()+1);
					s2.setBlackCheckersOnBoard(s2.getBlackCheckersOnBoard()-1);
				}
				break;
			case BLACK:
				if(action2!=""){
					s2.setBlackCheckers(s2.getBlackCheckers()-1);
					s2.setBlackCheckersOnBoard(s2.getBlackCheckersOnBoard()+1);
					s2.setWhiteCheckersOnBoard(s2.getWhiteCheckersOnBoard()-1);
				}
				break;
			}
		} 
		
		eval1=new HeuristicEvaluator(s1, getPlayer());
		eval2=new HeuristicEvaluator(s2, getPlayer());
		val1+=eval1.evaluate();
		val2+=eval2.evaluate();
		System.out.println(s1.toString());
		System.out.println("State1 VAL: "+val1);
		System.out.println(s2.toString());
		System.out.println("State2 VAL: "+val2);
		
		if (val1>val2)
			return 1;
		else if(val1==val2)
			return 0;
		else
			return -1;	
	}

	public Checker getPlayer() {
		return player;
	}

	public void setPlayer(Checker player) {
		this.player = player;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
	
}