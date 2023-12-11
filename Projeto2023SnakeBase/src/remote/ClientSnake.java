package remote;

import java.awt.event.KeyEvent;
import java.io.Serializable;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import game.HumanSnake;

public class ClientSnake extends HumanSnake implements Serializable{

	public ClientSnake(int id, RemoteBoard board) {
		super(id, board);
		
	}
	
	@Override
	public void run() {
		doInitialPositioning();
	}

	public void makeMove(Cell cell) throws InterruptedException {
		this.move(cell);
	}
	
	
	
	
	
}