package remote;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import game.HumanSnake;
import game.Server;
import game.Snake;

public class ClientHandler extends Thread {
	private Socket socket;
	private Server server;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ClientSnake snake;
	private boolean playerInitializationReceived = true;
	
	public ClientHandler(Socket socket, Server server) throws IOException {
		this.socket = socket;
		this.server = server;
		makeConnections();
	}
	
	
	//It's a thread that handles all input and output for one client.
	//It reads messages from the client and can send messages back.
	//It also can broadcast messages to other clients through the server.
	
	private void makeConnections() throws IOException {
		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(socket.getInputStream());
	}
	
	@Override
	public void run() {
		try {
			new Thread(this::receiveFromCliente).start(); // Thread for receiving
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unnable to continue session...");
		} finally {

		}
	}
	
	public synchronized void sendBoard(Board board) throws IOException{
		out.writeObject(board);
		out.flush();	
		out.reset();
	}
	
	private void receiveFromCliente() {
	    try {
	    	while(true) {
	    		
	    		if(playerInitializationReceived) {
			        RemoteBoard clientBoard = (RemoteBoard) in.readObject();
			        clientJoinGame(clientBoard.getSnake());
			        playerInitializationReceived=false;
	    		}
	    		String command = (String) in.readUnshared();
	    		moveHumanSnake(command);
	    	}
	    } catch (ClassNotFoundException | IOException e) {
	        e.printStackTrace();
	    }
	}

	
	public void clientJoinGame(HumanSnake humanSnake) {
		this.snake = (ClientSnake) humanSnake;
		server.getBoard().getSnakes().add((ClientSnake) humanSnake);
		server.getBoard().setChanged();
		humanSnake.start();
	
	}
	
	private void moveHumanSnake(String command) {
		if(command!=null) {
			BoardPosition nextPosition = snake.getDirection(command);
			Cell nextCell = server.getBoard().getCell(nextPosition);
			System.out.println(nextCell.getPosition());
			if(!nextCell.isOcupied() && !nextCell.isOutOfBounds()) {
				try {
					snake.makeMove(nextCell);
					server.getBoard().setChanged();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	
	private void closeConnection() {
		try {
			if(in != null)
				in.close();
			if(out != null)
				out.close();
			if(socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}



	
	
	
	
	
	
}

