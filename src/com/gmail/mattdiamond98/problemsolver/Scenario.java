package com.gmail.mattdiamond98.problemsolver;

import java.util.List;

public interface Scenario {
	
	public List<Scenario> getPossibleActions();
	
	public boolean isResolved();
	public boolean isSuccess();
	
	public boolean symmetricallyEquals(Scenario s);
	
}
