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
		try {
			if(this.isOcupiedByGoal()) {
				this.getGoal().captureGoal(snake);
				this.gameElement=null;
			}
			while (isOcupied()) {
				isFree.await();
			}
			ocuppyingSnake = snake;
		} finally {
			lock.unlock();
		}
	}

	public void release() {
		lock.lock();
		try {
			ocuppyingSnake = null;
			isFree.signalAll();
		}
	    finally {
	        lock.unlock();
	    }
	}

	public boolean isOcupiedBySnake() {
		return ocuppyingSnake != null;
	}

	public synchronized void setGameElement(GameElement element) {
		gameElement = element;

	}

	public boolean isOcupied() {
		return isOcupiedBySnake() || (gameElement != null && gameElement instanceof Obstacle);
	}

	public Snake getOcuppyingSnake() {
		return ocuppyingSnake;
	}

	public Goal removeGoal() {
		if (gameElement instanceof Goal) {
			setGameElement(null);
		}
		Goal goal = (Goal) gameElement;
		return goal;
	}

	public void removeObstacle() {
		lock.lock();
		try {
			setGameElement(null);;
			isFree.signalAll();
		}
	    finally {
	        lock.unlock();
	    }
	}

	public Goal getGoal() {
		return (Goal) gameElement;
	}
	
	public boolean isOcupiedByGoal() {
		return (gameElement != null && gameElement instanceof Goal);
	}
	 
	 public void leaveCell() {
			lock.lock();
			try {
				ocuppyingSnake = null;
				lock.notifyAll();
			}
		    finally {
		        lock.unlock();
		    }
		}

}
