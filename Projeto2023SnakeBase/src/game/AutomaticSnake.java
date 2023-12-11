package game;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.Position;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Cell;
import environment.Board;
import environment.BoardPosition;

public class AutomaticSnake extends Snake implements Serializable{
	public AutomaticSnake(int id, LocalBoard board) {
		super(id, board);

	}

	@Override
	public void run() {
		doInitialPositioning();
		System.err.println("initial size:" + cells.size());
		while (!getBoard().getIsFinished()) {
			Cell nextCell = null;
			try {
				Thread.sleep(getBoard().PLAYER_PLAY_INTERVAL);
				//Cell potCell = null;
				// if (getIsWaiting() == false) {

				BoardPosition goalPosition = getBoard().getGoalPosition();
				BoardPosition snakePosition = this.getCells().getLast().getPosition();
				Cell currentCell = getBoard().getCell(snakePosition);

				double min_distanceToGoal = Double.MAX_VALUE;

				for (BoardPosition pos : getBoard().getNeighboringPositions(currentCell)) {
					Cell potencialCell = this.getBoard().getCell(pos);
					double distanceToGoal = pos.distanceTo(goalPosition);

					if (!this.cells.contains(potencialCell) && distanceToGoal < min_distanceToGoal) {
						nextCell = potencialCell;
						//potCell = potencialCell;
						min_distanceToGoal = distanceToGoal;
					}
				}
				
				if (nextCell != null) {
					this.move(nextCell);
				}
			} catch (InterruptedException e1) {

				BoardPosition nextPosition = newDirection();
				Cell new_nextCell = this.getBoard().getCell(nextPosition);
				if (new_nextCell != null) {
					try {
						this.move(new_nextCell);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}

	}
}
