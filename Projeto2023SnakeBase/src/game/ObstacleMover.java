package game;

import environment.Cell;
import environment.LocalBoard;

public class ObstacleMover extends Thread {
	private Obstacle obstacle;
	private LocalBoard board;

	public ObstacleMover(Obstacle obstacle, LocalBoard board) {
		super();
		this.obstacle = obstacle;
		this.board = board;
	}

	@Override
	public void run() {

		

		while (this.obstacle.getRemainingMoves() > 0) {
			try {
			
				
				// aceder a lista de obstacles no board e
				this.obstacle.getBoard().getObstacles();
			
				// obter cell onde esta o obstaculo
				Cell previousCell = this.obstacle.getCell();
				// mover the position a cell em que o obstaculo esta
				// so mover parqa posicoes vazias de game elements
				this.obstacle.randomObstaclePosition();
				// game element .setgameElement(obstacle o )
				// na celula antiga set null o parametro game element
				previousCell.release();
				notifyAll();
				// decrementar o valor do obstaculo
				this.obstacle.decrementValue();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
