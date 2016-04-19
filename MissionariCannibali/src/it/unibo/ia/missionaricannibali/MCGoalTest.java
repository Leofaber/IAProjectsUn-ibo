package it.unibo.ia.missionaricannibali;

import aima.search.framework.GoalTest;

public class MCGoalTest implements GoalTest {

	@Override
	public boolean isGoalState(Object state) {
		if(state instanceof MCState){
			MCState myState= (MCState) state;
			if(myState.getNum_cannibali()==0 && myState.getNum_missionari()==0 && myState.getPos_barca()==0)
				return true;
		}
		return false;
	}

}
