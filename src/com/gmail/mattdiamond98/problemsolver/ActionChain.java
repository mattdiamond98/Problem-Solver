package com.gmail.mattdiamond98.problemsolver;

import java.util.LinkedList;
import java.util.List;

public class ActionChain implements Scenario {
	
	public List<Scenario> actions = new LinkedList<>();
	
	public ActionChain() {}
	public ActionChain(List<Scenario> actions) {
		this.actions = actions;
	}
	public ActionChain(Scenario base) {
		actions.add(base);
	}
	
	public boolean isResolved() {
		return last().isResolved();
	}
	
	public boolean isSuccess() {
		return last().isSuccess();
	}
	
	public boolean symmetricallyEquals(ActionChain s) {
		return last().symmetricallyEquals(s.last());
	}
	public boolean symmetricallyEquals(Scenario s) {
		if (s instanceof ActionChain) return
				last().symmetricallyEquals(((ActionChain)s).last());
		return last().symmetricallyEquals(s);
	}
	
//	public ActionChain getFirstSuccessfulResolution() {
//		List<ActionChain> endScenarios = new LinkedList<>();
//		List<ActionChain> nextPossibilities = getNextPossibilities();
//		if (nextPossibilities.size() == 0) {
//			if (isSuccess()) {
//				endScenarios.add(this);
//			}
//		} else {
//			for (ActionChain possibility : nextPossibilities) 
//				endScenarios.addAll(possibility.getAllEndScenarios());
//		}
//		if (endScenarios.size() > 0)
//			return endScenarios.get(0);
//		return null;
//	}
	
	public List<ActionChain> getAllEndScenarios() {
		List<ActionChain> endScenarios = new LinkedList<>();
		List<ActionChain> nextPossibilities = getNextPossibilities();
		if (nextPossibilities.size() == 0) {
			if (isSuccess()) {
				endScenarios.add(this);
			}
		} else {
			for (ActionChain possibility : nextPossibilities) 
				endScenarios.addAll(possibility.getAllEndScenarios());
		}
		return endScenarios;
	}
	
	public List<ActionChain> getNextPossibilities() {
		List<ActionChain> list = new LinkedList<>();
		for (Scenario s : getPossibleActions()) {
			list.add(newBranch(s));
		}
		return list;
	}
	
	public ActionChain newBranch(ActionChain s) {
		try {
			ActionChain branch = this.getClass().newInstance();
			branch.actions = new LinkedList<>(actions);
			return branch.add(s.last());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ActionChain newBranch(Scenario s) {
		if (s instanceof ActionChain)
			return newBranch((ActionChain) s);
		try {
			ActionChain branch = this.getClass().newInstance();
			branch.actions = new LinkedList<>(actions);
			return branch.add(s);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Scenario> getPossibleActions() {
		List<Scenario> possibilities = new LinkedList<>();
		for (Scenario s : last().getPossibleActions()) {
				ActionChain ac;
				try {
					ac = this.getClass().newInstance();
					ac.actions = new LinkedList<>(this.actions);
					possibilities.add(ac.add(s));
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return possibilities;
	}
	
	public Scenario last() {
		return actions.get(actions.size() - 1);
	}
	
	public ActionChain clone() {
		return new ActionChain(new LinkedList<>(actions));
	}
	
	public ActionChain add(Scenario s) {
		actions.add(s);
		return this;
	}
		
	public String toString() {
		List<String[]> lines = new LinkedList<>();
		for (Scenario s : actions) {
			lines.add(s.toString().split("\n"));
		}
		String write = "";
		int i = 0;
		while (containsLines(lines, i)) {
			for (String[] s : lines) {
				try {
					write += s[i];
				} catch (ArrayIndexOutOfBoundsException e) {}
			}
			write += "\n";
			i++;
		}
		
		return write;
	}
	
	private boolean containsLines(List<String[]> lines, int line) {
		for (String[] s : lines) {
			try {
				if (s[line] != null) {
					return true;
				}
			} catch (ArrayIndexOutOfBoundsException e) {}
		}
		return false;
	}
	
	
}
