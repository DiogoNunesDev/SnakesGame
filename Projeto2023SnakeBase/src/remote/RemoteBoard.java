package remote;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

import environment.LocalBoard;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import game.Goal;
import game.HumanSnake;
import game.Obstacle;
import game.Snake;

/** Remote representation of the game, no local threads involved.
 * Game state will be changed when updated info is received from Srver.
 * Only for part II of the project.
 * @author luismota
 *
 */
public class RemoteBoard extends Board{
	
	private HumanSnake snake;
	private String command;
	
	@Override
	public void handleKeyPress(int keyCode) {
		String command = convertKeyCodeToCommand(keyCode);
		setCommand(command);
	}

	@Override
	public void handleKeyRelease() {
		setCommand(null);
	}

	public HumanSnake getSnake() {
		return snake;
	}
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String next_command) {
		command = next_command;
	}

	@Override
	public void init() {
		setChanged();		
	}


	private String convertKeyCodeToCommand(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                return "UP";
            case KeyEvent.VK_DOWN:
                return "DOWN";
            case KeyEvent.VK_LEFT:
                return "LEFT";
            case KeyEvent.VK_RIGHT:
                return "RIGHT";
            default:
                return null;
        }
    }
	
	public synchronized void update(Board board) {
		cells = board.getCells();
        setGoalPosition(board.getGoalPosition()); 
        snakes = board.getSnakes();
        setObstacles(board.getObstacles());
        setFinished(board.getIsFinished());
		setChanged();
		if(snake==null) {
			int id = board.getSnakes().size() + 1;
			snake = new ClientSnake(id, this);
		}
		

	}

}
