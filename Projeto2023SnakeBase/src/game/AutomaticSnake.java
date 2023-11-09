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
		super(id,board);

	}
	

	@Override
	public void run() {
		doInitialPositioning();
		System.err.println("initial size:"+cells.size());
		//TODO: automatic movement
		while(this.getSize() < 10) {
			try {
				Thread.sleep(getBoard().PLAYER_PLAY_INTERVAL);
				BoardPosition goalPosition = getBoard().getGoalPosition();
				BoardPosition snakePosition = this.getCells().getLast().getPosition();
				Cell currenCell = getBoard().getCell(snakePosition);
				
				Double distanceToGoal = snakePosition.distanceTo(goalPosition);
				
				Cell nextCell = null;
				
				for(BoardPosition pos : getBoard().getNeighboringPositions(currenCell)) {
					if (distanceToGoal > pos.distanceTo(goalPosition)){
						nextCell = this.getBoard().getCell(pos);
					}
				}
				this.move(nextCell);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
	}
	
	
}
