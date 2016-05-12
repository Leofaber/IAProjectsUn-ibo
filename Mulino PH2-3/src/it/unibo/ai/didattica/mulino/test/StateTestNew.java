package it.unibo.ai.didattica.mulino.test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import it.unibo.ai.didattica.mulino.customExceptions.InvalidPositionException;
import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;


/*
 *  		 "a1"-------------------"a4"-----------------"a7" 
		      |                      |                    |
		      |    					 |					  |
		 	  |		"b2"------------"b4"------------b6"   |
			  |      |               |              !     |
 			  |		 |		         |              |     |  
			  |		 |	  "c3"------"c4"-----"c5"   |     |
			  |		 |		|                  |    |     |
		      | 	 |		|                  |    |     |
			 "d1"---"d2"--"d3"               "d5"--"d6"--"d7"
			  |      |		|                  |    |     |
			  |		 |		|				   |    |     |
			  |		 |	  "e3"----- "e4"-----"e5"   |     |
			  |		 |				 |				|     |
			  |		 |	 			 |	 			|     |
			  |		"f2"------------"f4"-----------"f6"   |
			  |                      |                    |
			  |						 |					  |
			"g1"--------------------"g4"-----------------"g7"
 * 
 */
public class StateTestNew {

	public State normalState = new State();
	public State whiteVerticalTrisState = new State();
	public State whiteHorizontalTrisState = new State();
	
	
	@Before
	public void setUp(){
		normalState.getBoard().put("d1", Checker.BLACK);
		normalState.getBoard().put("d2", Checker.BLACK);
		normalState.getBoard().put("d3", Checker.BLACK);
		
		normalState.getBoard().put("d5", Checker.WHITE);
		normalState.getBoard().put("d6", Checker.WHITE);
		normalState.getBoard().put("d7", Checker.WHITE);
		
		normalState.getBoard().put("e4", Checker.BLACK);
		normalState.getBoard().put("f4", Checker.BLACK);
		normalState.getBoard().put("g4", Checker.BLACK);
		
		normalState.getBoard().put("a1", Checker.WHITE);
		normalState.getBoard().put("g1", Checker.WHITE);
		normalState.getBoard().put("f2", Checker.WHITE);
		
		normalState.getBoard().put("b4", Checker.BLACK);
		normalState.getBoard().put("c3", Checker.BLACK);
		normalState.getBoard().put("c5", Checker.BLACK);
		
		normalState.getBoard().put("b6", Checker.WHITE);
		normalState.getBoard().put("b2", Checker.WHITE);
		normalState.getBoard().put("c4", Checker.WHITE);
		
		
		
		normalState.setArrayOfMorris();
	}
	
//	@Test
//	public void testMorrisOrizzontali() {
// 		whiteHorizontalTrisState.setArrayOfMorris();
// 		for(int i =0; i< whiteHorizontalTrisState.getMorrisArray("WHITE", "ROW").length;i++){
// 	 		System.out.println(whiteHorizontalTrisState.getMorrisArray("WHITE", "ROW")[i]);
//
// 		}
//	 	assertEquals("Tris orizzontale di d5-d6-d7", true, whiteHorizontalTrisState.rowMorrisWhite[4]==3);
//    }

//	@Test
//	public void testMorrisVerticali() {
//		whiteVerticalTrisState.setArrayOfMorris();
// 		assertEquals("LoL", true, whiteVerticalTrisState.columnMorrisWhite[0]==3);
//
//	}
	
//	@Test
//	public void testGetAdjacentPositions(){
//		try {			 
//			assertEquals("Posizione adiacenti a d5 : c5,d6,e5",true,Arrays.equals(normalState.getAdjacentPositions("d5"),new String[]{"c5","d6","e5"}));
//		} catch (InvalidPositionException e) {
//			e.printStackTrace();
//		}
//	}
	
//	@Test
//	public void testFindMorrisInOppCheck2(){ 
//		assertEquals("Tris trovato nella colonna d5d6d7",true,normalState.isOppCheckerInTris(Checker.WHITE, "d7"));
//	}

	@Test
	public void testFindMorrisInOppCheck1(){ 
		normalState.toString();
		for (String pos : normalState.positions) {
			System.out.print("YO ;) ");
			assertEquals("Tris trovato nella colonna d1d2d3",false,normalState.isOppCheckerInTris(Checker.BLACK, pos));
		}
	}
//	

//	
//	@Test
//	public void testFindMorrisInOppCheck3(){ 
//		assertEquals("Tris trovato nella riga e4f4g4",true,normalState.isOppCheckerInTris(Checker.BLACK, "g4"));
//	}
//	
//	@Test
//	public void testFindMorrisInOppCheck4(){ 
//		assertEquals("Tris trovato nella riga a7d7g7",true,normalState.isOppCheckerInTris(Checker.WHITE, "g7"));
//	}
	
}