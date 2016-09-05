package com.gmail.mattdiamond98.problemsolver;

import java.util.LinkedList;
import java.util.List;

public abstract class Model implements Scenario {

	/**
	 * NOTE: Broken - doesn't account for repeats
	 * @param scenario the base scenario
	 * @return a list of ActionChains demonstrating all successful ends
	 */
	public List<Scenario> getSuccessfulResolutions() {
		List<Scenario> list = new LinkedList<>();
		
		for (Scenario s : getPossibleActions()) {
			if (s.isResolved()) {
				if (s.isSuccess()) {
					list.add(s);
				}
			} else {
				list.addAll(getSuccessfulResolutions());
			}
		}
		
		return list;
	}
	
	public Model getFirstSuccessfulResolution() {
		for (Scenario s : getPossibleActions()) {
			if (s.isResolved()) {
				if (s.isSuccess()) {
					return (Model) s;
				}
			} else {
				return ((Model)s).getFirstSuccessfulResolution();
			}
		}
		return null;
	}
	
	public abstract String toString();
	
}
