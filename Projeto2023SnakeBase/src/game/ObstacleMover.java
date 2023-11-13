package game;

import environment.BoardPosition;
import environment.Cell;
import environment.LocalBoard;
import java.lang.Runnable;
public class ObstacleMover implements Runnable {
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
				BoardPosition new_position = board.getNewRandomPosition();
				Cell new_cell = board.getCell(new_position);
				obstacle.move(new_cell);
				Thread.sleep(obstacle.getSleepTime());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
