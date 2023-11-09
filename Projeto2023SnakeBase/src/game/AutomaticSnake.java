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
		System.err.println("CELL: " + cells.getLast().getPosition().x + " - " + cells.getLast().getPosition().y);
		//TODO: automatic movement
		while(this.getSize() < 10) {
			try {
				Thread.sleep(getBoard().PLAYER_PLAY_INTERVAL);
				BoardPosition position = cells.getLast().getPosition();
				BoardPosition newPosition = new BoardPosition(position.x+1, position.y);
				Cell nextCell = this.getBoard().getCell(newPosition);
				this.move(nextCell);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
	}
	

	
}
