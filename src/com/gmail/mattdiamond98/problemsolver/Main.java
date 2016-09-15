package com.gmail.mattdiamond98.problemsolver;

import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		PegGame game = new PegGame();
//		game.jump(3, 1, 0);
//		game.jump(1, 2, 1);
		ActionChain chain = new ActionChain(game);
		System.out.println(chain);
		
//		System.out.println(chain.getFirstSuccessfulResolution());
		
		
		List<ActionChain> paths = new LinkedList<>();
		paths.add(chain);
		
		long startTime = System.currentTimeMillis();
		
		for (int i = 0; i < 10; i++) {
			

			List<ActionChain> currentPaths = new LinkedList<>(paths);
			paths.clear();
			for (ActionChain step : currentPaths) {
				paths.addAll(step.getNextPossibilities());
			}
			//removeSymmetrical --> some algorythm, loops and compares each step to all previous steps ... dont use for each
			if (paths.size() < 1000)
			for (int j = 0; j < paths.size(); j++) {
				for (int k = 0; k < j; k++) {
					if (paths.get(j).symmetricallyEquals(paths.get(k))) {
						paths.remove(j);
						j--;
						break;
					}
				}
			}
			long endTime = System.currentTimeMillis();

			System.out.println("Current Paths: " + paths.size());
			System.out.println("Time Taken: " + (endTime - startTime) + "ms");
			System.out.println();
		}
		
		List<ActionChain> uniqueEnds = new LinkedList<>();
		
		for (ActionChain end : chain.getAllEndScenarios()) {
			boolean unique = true;
			for (ActionChain u : uniqueEnds) {
				if (end.last().equals(u.last())) {
					unique = false;
				}
			}
			if (unique)
				uniqueEnds.add(end);
		}
		long endTime = System.currentTimeMillis();
		
		System.out.println(uniqueEnds.size());
		System.out.println();
		for (ActionChain end : uniqueEnds) {
			System.out.println(end);
		}
		System.out.println();
		System.out.println("Total time: " + (endTime - startTime) + "ms");
		
	}
	
}
