package game;

import java.io.Serializable;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import environment.LocalBoard;

public class Obstacle extends GameElement implements Serializable{
	
	
	private static final int NUM_MOVES=3;
	private static final int OBSTACLE_MOVE_INTERVAL = 400;
	private int remainingMoves=NUM_MOVES;
	private Board board;
	public Obstacle(Board board) {
		super();
		this.board = board;
	}
	
	public int getRemainingMoves() {
		return remainingMoves;
	}
	
	public int decrementValue() {
		return remainingMoves--;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public BoardPosition getPosition() {
		Cell cell = null;
		for (int i = 0; i < getBoard().getCells().length; i++) {
	        for (int j = 0; j < getBoard().getCells()[i].length; j++) {
	        	if(this == getBoard().getCells()[i][j].getGameElement()) {
	        		cell = getBoard().getCell(new BoardPosition(i, j));
	        	} 
	        }
		}
		
		BoardPosition position = cell.getPosition();
		return position;
	}
	
	public Cell getCell() {
		return getBoard().getCell(getPosition()) ;
	}
	
	protected void move(Cell cell) throws InterruptedException {
		getCell().removeObstacle();
		cell.setGameElement((GameElement)this);
		decrementValue();
		getBoard().setChanged();
	}
	 
	public int getSleepTime() {
		return OBSTACLE_MOVE_INTERVAL;
	}

}
