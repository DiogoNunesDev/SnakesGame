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
		try {
			ClientSnake.sleep(10000); // espera 10 segundos antes de entrar no jogo
		} catch (InterruptedException e) {
			e.printStackTrace();
		};
		doInitialPositioning();
	}

	public void makeMove(Cell cell) throws InterruptedException {
		if(cell != null) {
			this.move(cell);
		}
	}
	
	
	
	
	
}