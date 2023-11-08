package environment;

import java.io.Serializable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sound.midi.SysexMessage;

import game.GameElement;
import game.Goal;
import game.Obstacle;
import game.Snake;
import game.AutomaticSnake;

/**
 * Main class for game representation.
 * 
 * @author luismota
 *
 */
public class Cell {
	private BoardPosition position;
	private Snake ocuppyingSnake = null;
	private GameElement gameElement = null;
	private Lock lock = new ReentrantLock();
	private Condition isFree = lock.newCondition();

	public GameElement getGameElement() {
		return gameElement;
	}

	public Cell(BoardPosition position) {
		super();
		this.position = position;
	}

	public BoardPosition getPosition() {
		return position;
	}

	public void request(Snake snake) throws InterruptedException {
		lock.lock();
		// TODO coordination and mutual exclusion
		try {
			while (ocuppyingSnake != null) {
				isFree.await();
			}
			ocuppyingSnake = snake;
		} finally {
			lock.unlock();
		}
	}

	public void release() {
		ocuppyingSnake = null;
	}

	public boolean isOcupiedBySnake() {
		return ocuppyingSnake != null;
	}

	public void setGameElement(GameElement element) {
		// TODO coordination and mutual exclusion
		gameElement = element;

	}

	public boolean isOcupied() {
		return isOcupiedBySnake() || (gameElement != null && gameElement instanceof Obstacle);
	}

	public Snake getOcuppyingSnake() {
		return ocuppyingSnake;
	}

	public Goal removeGoal() {
		// TODO
		return null;
	}

	public void removeObstacle() {
		// TODO
	}

	public Goal getGoal() {
		return (Goal) gameElement;
	}

	public boolean isOcupiedByGoal() {
		return (gameElement != null && gameElement instanceof Goal);
	}

}
