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
public class Cell implements Serializable{
	private BoardPosition position;
	private Snake ocuppyingSnake = null;
	private GameElement gameElement = null;

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
		synchronized(this) {
			if(this.isOcupiedByGoal()) {
				this.getGoal().captureGoal(snake);
				this.gameElement=null;
			}
			while (isOcupied()) {
				wait();
			}
			ocuppyingSnake = snake;
		}
	}

	public void release() {
		
		synchronized (this) {
			ocuppyingSnake = null;
			notifyAll();
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

	public synchronized void removeObstacle() {
		
		if(gameElement instanceof Obstacle) {
			setGameElement(null);
			notifyAll();
		}
	}

	public Goal getGoal() {
		return (Goal) gameElement;
	}
	
	public boolean isOcupiedByGoal() {
		return (gameElement != null && gameElement instanceof Goal);
	}
	 
	 public synchronized void leaveCell() {
		 notifyAll();
	}
	 
	public boolean isOutOfBounds() {
		int x = this.getPosition().x;
		int y = this.getPosition().y;
		return x < 0 || x >= 30 || y < 0 || y >= 30; 
	}
	
}
