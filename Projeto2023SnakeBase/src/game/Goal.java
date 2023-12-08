package game;

import environment.Board;
import environment.BoardPosition;
import environment.LocalBoard;

public class Goal extends GameElement  {
	private int value=1;
	private Board board;
	public static final int MAX_VALUE=10;
	public Goal( Board board2) {
		this.board = board2;
	}
	
	public int getValue() {
		return value;
	}
	public void incrementValue() throws InterruptedException {
		this.value++;
	}

	public int captureGoal(Snake snake) throws InterruptedException {
		if (value < MAX_VALUE) {
			BoardPosition position = getBoard().getNewRandomPosition();
			getBoard().getCell(position).setGameElement((GameElement)this);
			getBoard().setGoalPosition(position);
			this.incrementValue();
			snake.size += getValue();
			getBoard().setChanged();
		}
		else {
			getBoard().setIsFinished(true);
		}
		return -1;
	}

	public Board getBoard() {
		return board;
	}
}
