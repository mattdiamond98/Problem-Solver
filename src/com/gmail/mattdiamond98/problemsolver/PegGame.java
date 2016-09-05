package com.gmail.mattdiamond98.problemsolver;

import java.util.LinkedList;
import java.util.List;

public class PegGame implements Scenario, Cloneable {
	
	/**
	 * Represents the board itself
	 *  0 is an empty hole
	 *  1 is a hole occupied by a peg
	 * -1 is not part of the board
	 */
	int[][] board = {{0,-1,-1,-1,-1},
					 {1, 1,-1,-1,-1},
					 {1, 1, 1,-1,-1},
					 {1, 1, 1, 1,-1},
					 {1, 1, 1, 1, 1}};
	
	public PegGame() {}
	public PegGame(int[][] board) {
		this.board = board;
	}
	
	public PegGame jump(int r, int c, int direction) {
		if (canJump(r, c, direction)) {
			board[r][c] = 0;
			setPegInDirectionTo(r, c, direction, 1, 0);
			setPegInDirectionTo(r, c, direction, 2, 1);
		} else throw new IllegalArgumentException();
		return this;
	}
	
	public boolean canJump(int r, int c, int direction) {	
		return  /*isInBounds(r, c) &&*/
				hasPeg(r, c) &&
				pegInDirection(r, c, direction, 1) == 1 &&
				pegInDirection(r, c, direction, 2) == 0;
	}
	
	public int pegInDirection(int r, int c, int direction, int amt) {
		if (direction < 2) r-=amt;
		if (direction < 4 && direction > 1) c+=amt;
		if (direction < 5 && direction > 2) r+=amt;
		if (direction == 5 || direction == 0) c-=amt;
		if (!isInBounds(r,c)) return -1;
		return board[r][c];
	}
	
	public void setPegInDirectionTo(int r, int c, int direction, int amt, int value) {
		if (pegInDirection(r, c, direction, amt) != -1) {
			if (direction < 2) r-=amt;
			if (direction < 4 && direction > 1) c+=amt;
			if (direction < 5 && direction > 2) r+=amt;
			if (direction == 5 || direction == 0) c-=amt;
			board[r][c] = value;
		} else throw new IllegalArgumentException();
	}
	
	public boolean isInBounds(int r, int c) {
		return r >= 0 && c >= 0  && r < 5 && c < 5? board[r][c] != -1 : false;
	}
	
	public boolean hasPeg(int r, int c) {
		return isInBounds(r, c) && board[r][c] == 1;
	}
	
	public List<Scenario> getPossibleActions() {
		List<Scenario> possibilities = new LinkedList<>();
		for (int r = 0; r < 5; r++) {
			for (int c = 0; c <= r; c++) {
				for (int d = 0; d < 6; d++) {
					if (canJump(r, c, d)) {
						PegGame p = clone();
						p.jump(r, c, d);
						possibilities.add(p);
					}
				}
			}
		}
		return possibilities;
	}
	
	public boolean isResolved() {
		return getPossibleActions().size() == 0;
	}

	public boolean isSuccess() {
		int pegs = 0;
		for (int[] row : board) {
			for (int i : row) {
				if (i == 1) pegs++;
			}
		}
		return pegs == 1;
	}
	
	public PegGame clone() {
		PegGame clone = new PegGame();
		for (int r = 0; r < 5; r++) {
			for (int c = 0; c <= r; c++) {
				clone.board[r][c] = this.board[r][c];
			}
		}
		return clone;
	}
	
	public boolean equals(Object o) {
		if (o instanceof PegGame) {
			for (int r = 0; r < board.length; r++) {
				for (int c = 0; c < board[r].length; c++) {
					if (board[r][c] != ((PegGame)o).board[r][c]) {
						return false;
					}
				}
			}
			return true;
		} else return false;
	}
	
	//TODO: implement
	public boolean symmetricallyEquals(Scenario s) {
		return equals(s);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int[] row : board) {
			sb.append(rowToString(row));
		}
		return sb.toString();
	}
	
	private String rowToString(int[] row) {
		int valid = 5;
		for (int i : row) {
			if (i != -1) valid--;
		}
		String s = "";
		for (int i = 0; i < valid; i++) {
			s += " ";
		}
		valid = 5 - valid;
		for (int i = 0; i < valid; i++) {
			s += row[i] + " ";
		}
		valid = 5 - valid;
		for (int i = 0; i < valid; i++) {
			s += " ";
		}
		return s + ":\n";
	}

}
