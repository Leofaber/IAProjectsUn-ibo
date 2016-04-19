import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class U2SuccessorFunction implements SuccessorFunction {

	public U2SuccessorFunction() {
	}
	
	@Override
	public List getSuccessors(Object state) {
		
		List result = new ArrayList();
		if (state instanceof U2State) {
			U2State u2State = (U2State) state;
	//	System.out.println("Primo IF");
			if (!u2State.getTorcia()) {
				if (!u2State.getBono() && !u2State.getLarry() && u2State.getTempo() >= u2State.BL) {
					U2State newState = moveBL(u2State);
					result.add(new Successor( U2State.bl,newState));
				}
				
				if (!u2State.getBono() && !u2State.getEdge() && u2State.getTempo() >= u2State.BE) {
					U2State newState = moveBE(u2State);
					result.add(new Successor( U2State.be,newState));
				}
				
				if (!u2State.getBono() && !u2State.getAdam() && u2State.getTempo() >= u2State.BA) {
					U2State newState = moveBA(u2State);
					result.add(new Successor( U2State.ba,newState));
				}
				
				if (!u2State.getEdge() && !u2State.getAdam() && u2State.getTempo() >= u2State.EA) {
					U2State newState = moveEA(u2State);
					result.add(new Successor( U2State.ea,newState));
				}
				
				if (!u2State.getEdge() && !u2State.getLarry() && u2State.getTempo() >= u2State.EL) {
					U2State newState = moveEL(u2State);
					result.add(new Successor( U2State.el,newState));
				}
				
				if (!u2State.getAdam() && !u2State.getLarry() && u2State.getTempo() >= u2State.AL) {
					U2State newState = moveAL(u2State);
					result.add(new Successor( U2State.al,newState));
				}
			}
			else {
				if (u2State.getBono() && u2State.getTempo() >= u2State.B) {
					U2State newState = moveB(u2State);
					result.add(new Successor( U2State.b,newState));
				}
				
				if (u2State.getEdge() && u2State.getTempo() >= u2State.E) {
					U2State newState = moveE(u2State);
					result.add(new Successor( U2State.e,newState));
				}
				
				if (u2State.getAdam() && u2State.getTempo() >= u2State.A) {
					U2State newState = moveA(u2State);
					result.add(new Successor( U2State.a,newState));
				}
				
				if (u2State.getLarry() && u2State.getTempo() >= u2State.L) {
					U2State newState = moveL(u2State);
					result.add(new Successor( U2State.l,newState));
				}
			}
		}
		return result;
	}
	
	private U2State moveBL(U2State s) {
		return new U2State(!s.getBono(),s.getEdge(),s.getAdam(),!s.getLarry(),s.getMembriSx()+2,
							s.getMembriDx()-2, !s.getTorcia(), s.getTempo() - s.BL);
	}
	
	private U2State moveBE(U2State s) {
		return new U2State(!s.getBono(),!s.getEdge(),s.getAdam(),s.getLarry(),s.getMembriSx()+2,
							s.getMembriDx()-2, !s.getTorcia(), s.getTempo() - s.BE);
	}
	
	private U2State moveBA(U2State s) {
		return new U2State(!s.getBono(),s.getEdge(),!s.getAdam(),s.getLarry(),s.getMembriSx()+2,
							s.getMembriDx()-2, !s.getTorcia(), s.getTempo() - s.BA);
	}
	
	private U2State moveEA(U2State s) {
		return new U2State(s.getBono(),!s.getEdge(),!s.getAdam(),s.getLarry(),s.getMembriSx()+2,
							s.getMembriDx()-2, !s.getTorcia(), s.getTempo() - s.EA);
	}
	
	private U2State moveEL(U2State s) {
		return new U2State(s.getBono(),!s.getEdge(),s.getAdam(),!s.getLarry(),s.getMembriSx()+2,
							s.getMembriDx()-2, !s.getTorcia(), s.getTempo() - s.EL);
	}
	
	private U2State moveAL(U2State s) {
		return new U2State(s.getBono(),s.getEdge(),!s.getAdam(),!s.getLarry(),s.getMembriSx()+2,
							s.getMembriDx()-2, !s.getTorcia(), s.getTempo() - s.AL);
	}
	
	private U2State moveB(U2State s) {
		return new U2State(!s.getBono(),s.getEdge(),s.getAdam(),s.getLarry(),s.getMembriSx()-1,
							s.getMembriDx()+1, !s.getTorcia(), s.getTempo() - s.B);
	}
	
	private U2State moveE(U2State s) {
		return new U2State(s.getBono(),!s.getEdge(),s.getAdam(),s.getLarry(),s.getMembriSx()-1,
							s.getMembriDx()+1, !s.getTorcia(), s.getTempo() - s.E);
	}
	
	private U2State moveA(U2State s) {
		return new U2State(s.getBono(),s.getEdge(),!s.getAdam(),s.getLarry(),s.getMembriSx()-1,
							s.getMembriDx()+1, !s.getTorcia(), s.getTempo() - s.A);
	}
	
	private U2State moveL(U2State s) {
		return new U2State(s.getBono(),s.getEdge(),s.getAdam(),!s.getLarry(),s.getMembriSx()-1,
							s.getMembriDx()+1, !s.getTorcia(), s.getTempo() - s.L);
	}

}
