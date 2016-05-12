package it.unibo.ai.didattica.mulino.test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import it.unibo.ai.didattica.mulino.customExceptions.InvalidPositionException;
import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;


/*
 *  		 "a7"-------------------"d7"-----------------"g7" 
		      |                      |                    |
		      |    					 |					  |
		 	  |		"b7"------------"d6"------------f6"   |
			  |      |               |              !     |
 			  |		 |		         |              |     |  
			  |		 |	  "c5"------"d5"-----"e5"   |     |
			  |		 |		|                  |    |     |
		      | 	 |		|                  |    |     |
			 "a4"---"b4"--"c4"               "e4"--"f4"--"g4"
			  |      |		|                  |    |     |
			  |		 |		|				   |    |     |
			  |		 |	  "c3"----- "d3"-----"e3"   |     |
			  |		 |				 |				|     |
			  |		 |	 			 |	 			|     |
			  |		"b2"------------"d2"-----------"f2"   |
			  |                      |                    |
			  |						 |					  |
			"a1"--------------------"d1"-----------------"g1"
 * 
 */
public class StateTestNew {

	public State normalState = new State();
	public State whiteVerticalTrisState = new State();
	public State whiteHorizontalTrisState = new State();
	
	
	@Before
	public void setUp(){

		
		normalState.getBoard().put("e4", Checker.BLACK);
		normalState.getBoard().put("f4", Checker.BLACK);
		normalState.getBoard().put("g4", Checker.BLACK);
		
		normalState.getBoard().put("a1", Checker.BLACK);

				
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

//	@Test
//	public void testFindMorrisInOppCheck1(){ 
//		System.out.println(normalState.toString());
//		assertEquals("Tris trovato nella colonna d1d2d3",true,normalState.isOppCheckerInTris(Checker.WHITE, "d7"));
//		assertEquals("Tris trovato nella colonna d1d2d3",true,normalState.isOppCheckerInTris(Checker.WHITE, "d6"));
//		assertEquals("Tris trovato nella colonna d1d2d3",true,normalState.isOppCheckerInTris(Checker.WHITE, "d5"));
//		assertEquals("Tris trovato nella colonna d1d2d3",true,normalState.isOppCheckerInTris(Checker.BLACK, "d3"));
//		assertEquals("Tris trovato nella colonna d1d2d3",true,normalState.isOppCheckerInTris(Checker.BLACK, "d2"));
//		assertEquals("Tris trovato nella colonna d1d2d3",true,normalState.isOppCheckerInTris(Checker.BLACK, "d1"));
//		assertEquals("Tris trovato nella colonna d1d2d3",true,normalState.isOppCheckerInTris(Checker.BLACK, "e4"));
//		assertEquals("Tris trovato nella colonna d1d2d3",true,normalState.isOppCheckerInTris(Checker.BLACK, "f4"));
//		assertEquals("Tris trovato nella colonna d1d2d3",true,normalState.isOppCheckerInTris(Checker.BLACK, "g4"));
//		assertEquals("Tris trovato nella colonna d1d2d3",false,normalState.isOppCheckerInTris(Checker.WHITE, "b6"));
//		assertEquals("Tris trovato nella colonna d1d2d3",false,normalState.isOppCheckerInTris(Checker.BLACK, "c5"));
//		assertEquals("Tris trovato nella colonna d1d2d3",false,normalState.isOppCheckerInTris(Checker.BLACK, "b4"));
//		assertEquals("Tris trovato nella colonna d1d2d3",false,normalState.isOppCheckerInTris(Checker.WHITE, "c4"));
//		assertEquals("Tris trovato nella colonna d1d2d3",false,normalState.isOppCheckerInTris(Checker.WHITE, "b2"));
//		assertEquals("Tris trovato nella colonna d1d2d3",false,normalState.isOppCheckerInTris(Checker.WHITE, "a1"));
//		assertEquals("Tris trovato nella colonna d1d2d3",false,normalState.isOppCheckerInTris(Checker.WHITE, "f2"));
//		assertEquals("Tris trovato nella colonna d1d2d3",false,normalState.isOppCheckerInTris(Checker.WHITE, "g1"));
//		
//	}

	@Test
	public void testIsDeletable() {
		System.out.println(normalState.toString());
		
		assertEquals("Stocastico",true,normalState.isDeletableChecker(Checker.BLACK, "a1"));
	}

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