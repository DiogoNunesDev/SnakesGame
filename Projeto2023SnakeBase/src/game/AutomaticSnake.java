package game;

import java.util.LinkedList;
import java.util.List;

import javax.swing.text.Position;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Cell;
import environment.Board;
import environment.BoardPosition;

public class AutomaticSnake extends Snake {
	public AutomaticSnake(int id, LocalBoard board) {
		super(id, board);

	}

	@Override
	public void run() {
		doInitialPositioning();
		System.err.println("initial size:" + cells.size());
		while (true) {// this.size < 10) {
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

//				}else {
//					System.out.println("CHEGUEI AQUI!!!");
//					nextCell = newDirection();
//					//this.setIsWaiting(false);
//				}

				if (nextCell != null) {
					this.move(nextCell);
				}
			} catch (InterruptedException e1) {
				System.out.println("interrupted");

				BoardPosition nextPosition = newDirection();
				Cell new_nextCell = this.getBoard().getCell(nextPosition);
				if (nextPosition != null) {
					System.out.println("not null" + nextPosition);
				}
				System.out.println("end");
				if (new_nextCell != null) {
					try {
						this.move(new_nextCell);
						//nextCell.leaveCell();
						System.out.println("moved");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("end");
				}
			}

		}

	}
}
