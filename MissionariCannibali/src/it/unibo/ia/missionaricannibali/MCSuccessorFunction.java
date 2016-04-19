/*
 * Missionari e Cannibali
 * Author: KappaEvin
 */
package it.unibo.ia.missionaricannibali;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class MCSuccessorFunction implements SuccessorFunction {
	
	@Override
	public List getSuccessors(Object state) {
		List result=new ArrayList();
		if(state instanceof MCState){
			MCState myState= (MCState) state;
			//numero missionari e cannibali sulla sponda della barca
			int numMissionari, numCannibali;
			
			if(myState.getPos_barca()==1){
				numMissionari=myState.getNum_missionari();
				numCannibali=myState.getNum_cannibali();
			}else{
				numMissionari=3-myState.getNum_missionari();
				numCannibali=3-myState.getNum_cannibali();
			}
			//se ho almeno un missionario sulla mia sponda posso applicare M
			if(numMissionari>0){
				MCState newState= myState.M();
				if(newState.isValid())
					result.add(new Successor("m", newState));
			}
			//se ho almeno un cannibale sulla mia sponda posso applicare C
			if(numCannibali>0){
				MCState newState= myState.C();
				if(newState.isValid())
					result.add(new Successor("c", newState));
			}
			//se ho almeno un cannibale e un missionario sulla mia sponda posso applicare MC
			if(numCannibali>0 && numMissionari>0){
				MCState newState= myState.MC();
				if(newState.isValid())
					result.add(new Successor("mc", newState));
			}
			//se ho almeno due missionari sulla mia sponda posso applicare MM
			if(numMissionari>1){
				MCState newState= myState.MM();
				if(newState.isValid())
					result.add(new Successor("mm", newState));
			}
			//se ho almeno due cannibali sulla mia sponda posso applicare CC
			if(numCannibali>1){
				MCState newState= myState.CC();
				if(newState.isValid())
					result.add(new Successor("cc", newState));
			}
		}
		return result;
	}
}
