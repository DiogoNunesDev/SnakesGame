package game;

import java.io.Serializable;
import java.util.LinkedList;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
/** Base class for representing Snakes.
 * Will be extended by HumanSnake and AutomaticSnake.
 * Common methods will be defined here.
 * @author luismota
 *
 */
public abstract class Snake extends Thread implements Serializable{
	private static final int DELTA_SIZE = 10;
	protected LinkedList<Cell> cells = new LinkedList<Cell>();
	protected int size = 5;
	private int id;
	private Board board;
	private boolean isWaiting = false;
	public Snake(int id,Board board) {
		this.id = id;
		this.board=board;
	}

	public int getSize() {
		return size;
	}

	public int getIdentification() {
		return id;
	}

	public int getLength() {
		return cells.size();
	}
	
	public LinkedList<Cell> getCells() {
		return cells;
	}
	
	public boolean getIsWaiting() {
		return this.isWaiting;
	}
	
	public void setIsWaiting(boolean state) {
		this.isWaiting = state;
	}
	protected void move(Cell cell) throws InterruptedException {
		cell = check_outOfBounds(cell);
		cell.request(this);
		cells.add(cell);
		if (getSize() < cells.size() && !cell.isOcupiedByGoal()) {
			//Se o tamanho da cobra for menor que o numero de cells que o seu corpo ocupa
			Cell tail = cells.removeFirst();
			tail.release();
		}
		getBoard().setChanged();
	}
	
	public LinkedList<BoardPosition> getPath() {
		LinkedList<BoardPosition> coordinates = new LinkedList<BoardPosition>();
		for (Cell cell : cells) {
			coordinates.add(cell.getPosition());
		}

		return coordinates;
	}	
	protected void doInitialPositioning() {
		// Random position on the first column. 
		// At startup, snake occupies a single cell
		int posX = 0;
		int posY = (int) (Math.random() * Board.NUM_ROWS);
		BoardPosition at = new BoardPosition(posX, posY);
		
		while (getBoard().getCell(at).isOcupied()) {
			posY = (int) (Math.random() * Board.NUM_ROWS);
			at = new BoardPosition(posX, posY);
		}
			
		try {
			board.getCell(at).request(this);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		cells.add(board.getCell(at));
	}
	
	public Board getBoard() {
		return board;
	}
	
	private Cell check_outOfBounds(Cell cell) {
		if (cell.getPosition().x==31) {
			BoardPosition nextPosition = new BoardPosition(0, cell.getPosition().y);
			cell = getBoard().getCell(nextPosition);
		}
		else if (cell.getPosition().x==-1) {
			BoardPosition nextPosition = new BoardPosition(30, cell.getPosition().y);
			cell = getBoard().getCell(nextPosition);
		}
		else if (cell.getPosition().y==31)	{
			BoardPosition nextPosition = new BoardPosition(cell.getPosition().x, 0);
			cell = getBoard().getCell(nextPosition);
		}
		else if (cell.getPosition().y==-1)	{
			BoardPosition nextPosition = new BoardPosition(cell.getPosition().x, 30);
			cell = getBoard().getCell(nextPosition);
		}
		return cell;
	
	
	}
	
	public BoardPosition newDirection() {
		
		BoardPosition goalPosition = getBoard().getGoalPosition();
		BoardPosition snakePosition = this.getCells().getLast().getPosition();
		Cell currentCell = getBoard().getCell(snakePosition);

		double min_distanceToGoal = Double.MAX_VALUE;
		BoardPosition nextDirection = null;

		for (BoardPosition pos : getBoard().getNeighboringPositions(currentCell)) {
			if (!this.getBoard().getCell(pos).isOcupied() && !this.getCells().contains(getBoard().getCell(pos))) {
				double distanceToGoal = pos.distanceTo(goalPosition);
				System.out.println(pos + "   ||||  next ||| " + this.getId());
				nextDirection = pos;
//				if (distanceToGoal < min_distanceToGoal) { 
//					nextDirection = pos;
//					min_distanceToGoal = distanceToGoal; 
//				}				 
			}
		}
		return nextDirection;

	}
	

	
	
	
	
	
	
	
	
	
	
	
}
