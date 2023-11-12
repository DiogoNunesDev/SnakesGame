package game;

import environment.BoardPosition;
import environment.Cell;
import environment.LocalBoard;

public class ObstacleMover extends Thread {
	private Obstacle obstacle;
	private LocalBoard board;

	public ObstacleMover(Obstacle obstacle, LocalBoard board) {
		super();
		this.obstacle = obstacle ;
		this.board = board;
	}

	@Override
	public void run() {

		while (this.obstacle.getRemainingMoves() > 0) {
			try {
				
				BoardPosition new_position = this.obstacle.randomObstaclePosition();
				Cell new_cell = board.getCell(new_position);
				obstacle.move(new_cell);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
