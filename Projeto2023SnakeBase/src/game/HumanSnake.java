package game;

import java.awt.event.KeyEvent;
import java.io.Serializable;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import remote.RemoteBoard;
 /** Class for a remote snake, controlled by a human 
  * 
  * @author luismota
  *
  */
public abstract class HumanSnake extends Snake implements Serializable{
	
	public HumanSnake(int id,RemoteBoard board) {
		super(id,board);
	}
	
	public BoardPosition getDirection(String command) {
		
		Cell cell = this.cells.getLast();
		BoardPosition currentPosition = cell.getPosition();
		
		switch (command) {
        case "UP":
            return currentPosition.getCellAbove();
        case "DOWN":
            return currentPosition.getCellBelow();
        case "LEFT":
            return currentPosition.getCellLeft();
        case "RIGHT":
            return currentPosition.getCellRight();
        default:
            return null;
		}
	}

	@Override
	protected void move(Cell cell) throws InterruptedException {
		super.move(cell);
	}

	
	

}