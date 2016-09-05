package com.gmail.mattdiamond98.problemsolver;

import java.util.LinkedList;
import java.util.List;

public class MarbleGame implements Scenario, Cloneable {
	
	int[][] board = {{-1,-1, 1, 1, 1,-1,-1},
					 {-1,-1, 1, 1, 1,-1,-1},
					 { 1, 1, 1, 1, 1, 1, 1},
					 { 1, 1, 1, 0, 1, 1, 1},
					 { 1, 1, 1, 1, 1, 1, 1},
					 {-1,-1, 1, 1, 1,-1,-1},
					 {-1,-1, 1, 1, 1,-1,-1}};
	
	public MarbleGame() {}
	public MarbleGame(int[][] board) {
		this.board = board;
	}
	
	public MarbleGame jump(int r, int c, int d) {
		if (canJump(r, c, d)) {
			board[r][c] = 0;
			setMarbleInDirectionTo(r, c, d, 1, 0);
			setMarbleInDirectionTo(r, c, d, 2, 1);
		} else throw new IllegalArgumentException();
		return this;
	}
	
	public boolean canJump(int r, int c, int d) {
		return hasMarble(r, c) &&
			   marbleInDirection(r, c, d, 1) == 1 &&
			   marbleInDirection(r, c, d, 2) == 0;
	}
	
	public int marbleInDirection(int r, int c, int d, int amt) {
		switch (d) {
			case 0: c += amt; break;
			case 1: r += amt; break;
			case 2: c -= amt; break;
			case 3: r -= amt; break;
		}
		if (!isInBounds(r,c)) return -1;
		return board[r][c];
	}
	
	public void setMarbleInDirectionTo(int r, int c, int d, int amt, int value) {
		if (marbleInDirection(r, c, d, amt) != -1 && (value == 0 || value == 1)) {
			switch (d) {
				case 0: c += amt; break;
				case 1: r += amt; break;
				case 2: c -= amt; break;
				case 3: r -= amt; break;
			}
			board[r][c] = value;
		} else throw new IllegalArgumentException();
	}
	
	public boolean isInBounds(int r, int c) {
		return r >= 0 && r < 7 && c >= 0 && c < 7;
	}
	
	public boolean hasMarble(int r, int c) {
		return isInBounds(r, c) && board[r][c] == 1;
	}
	
	@Override
	public List<Scenario> getPossibleActions() {
		List<Scenario> possibilities = new LinkedList<>();
		for (int r = 0; r < 7; r++) {
			for (int c = 0; c < 7; c++) {
				for (int d = 0; d < 4; d++) {
					if (canJump(r, c, d)) {
						MarbleGame p = clone();
						p.jump(r, c, d);
						possibilities.add(p);
					}
				}
			}
		}
		return possibilities;
	}

	@Override
	public boolean isResolved() {
		return getPossibleActions().size() == 0;
	}

	@Override
	public boolean isSuccess() {
		int marbles = 0;
		for (int[] row : board) {
			for (int i : row) {
				if (i == 1) marbles++;
			}
		}
		return marbles == 1;
	}
	
	public boolean symmetricallyEquals(Scenario s) {
		if (s instanceof MarbleGame) {
			return symmetricallyEquals((MarbleGame) s);
		}
		return false;
	}
	
	public boolean symmetricallyEquals(MarbleGame m) {
//		return equals(m) ||
//			   equalsCross(m.board) ||
//			   equalsVertical(m.board) ||
//			   equalsRot90(m.board) ||
//			   equalsRot180(m.board) ||
//			   equalsRot270(m.board);
		//as soon as not equal, return false
		boolean equals = true, cross = true, vertical = true, rot90 = true, rot180 = true, rot270 = true;
		for (int r = 0; r < board.length; r++) {
			if (equals || cross || vertical || rot90 || rot180 || rot270) {
				for (int c = 0; c < board.length; c++) {
					if (board[r][c] != m.board[r][c]) equals = false;
					if (board[r][c] != m.board[r][6-c]) cross = false;
					if (board[r][c] != m.board[6-r][c]) vertical = false;
					if (board[r][c] != m.board[c][6-r]) rot90 = false;
					if (board[r][c] != m.board[6-r][6-c]) rot180 = false;
					if (board[r][c] != m.board[6-c][r]) rot270 = false;
				}
			} else return false;
		} return true;
	}
	
	public boolean equals(Object o) {
		if (o instanceof MarbleGame) {
			for (int r = 0; r < board.length; r++) {
				for (int c = 0; c < board[r].length; c++) {
					if (board[r][c] != ((MarbleGame)o).board[r][c]) {
						return false;
					}
				}
			}
			return true;
		} else return false;
	}
	
	private boolean equalsCross(int[][] b2) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				if (board[r][c] != b2[r][6-c])
					return false;
			}
		}
		return true;
	}
	
	private boolean equalsVertical(int[][] b2) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				if (board[r][c] != b2[6-r][c])
					return false;
			}
		}
		return true;
	}
	
	private boolean equalsRot90(int[][] b2) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				if (board[r][c] != b2[c][6-r])
					return false;
			}
		}
		return true;
	}
	
	private boolean equalsRot180(int[][] b2) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				if (board[r][c] != b2[6-r][6-c])
					return false;
			}
		}
		return true;
	}
	
	private boolean equalsRot270(int[][] b2) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				if (board[r][c] != b2[6-c][r])
					return false;
			}
		}
		return true;
	}
	public MarbleGame clone() {
		MarbleGame clone = new MarbleGame();
		for (int r = 0; r < 7; r++) {
			for (int c = 0; c < 7; c++) {
				clone.board[r][c] = this.board[r][c];
			}
		}
		return clone;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int[] row : board) {
			for (int i : row) {
				if (i == -1) {
					sb.append("  ");
				} else sb.append(i + " ");
			}
			sb.append(" \n");
		}
		return sb.toString();
	}

}
