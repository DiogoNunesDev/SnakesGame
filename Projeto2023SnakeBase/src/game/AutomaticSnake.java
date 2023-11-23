package game;

import java.io.PrintWriter;
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
		while(true){//this.size < 10) {
			
			try {
				Thread.sleep(getBoard().PLAYER_PLAY_INTERVAL);
				Cell nextCell = null;
				Cell potCell = null;
				if (getIsWaiting() == false) {
					
					BoardPosition goalPosition = getBoard().getGoalPosition();
					BoardPosition snakePosition = this.getCells().getLast().getPosition();
					Cell currentCell = getBoard().getCell(snakePosition);
					
					double min_distanceToGoal = Double.MAX_VALUE;
					
					for(BoardPosition pos : getBoard().getNeighboringPositions(currentCell)) {
						Cell potencialCell = this.getBoard().getCell(pos);
						double distanceToGoal = pos.distanceTo(goalPosition);
						
						if (!this.cells.contains(potencialCell) && distanceToGoal < min_distanceToGoal) {
							nextCell = potencialCell;
							potCell=potencialCell;
							min_distanceToGoal = distanceToGoal;
						}	
					}
					
				}else {
					System.out.println("CHEGUEI AQUI!!!");
					nextCell = newDirection();
					this.setIsWaiting(false);
				}
				
				if (nextCell != null) {
					this.move(nextCell);
					//potCell.leaveCell();
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
		
	}
	
	
}
