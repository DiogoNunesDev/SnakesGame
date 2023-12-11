package remote;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import environment.Board;
import gui.SnakeGui;


/** Remore client, only for part II
 * 
 * @author luismota
 *
 */

public class Client {

	
	private Socket socket;
	private String address;
	private RemoteBoard board;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean playerInitializationSent = true;
	
	public static void main(String[] args) throws IOException {
		Client client = new Client("localhost", 1234);
		client.run();
		
	}
	
	public Client(String address, int port) throws IOException {
		socket = new Socket(address, port);
		makeConnections();
		board = new RemoteBoard();
		new Thread(this::startGame).start();
	}
	
	private void startGame() {
		SnakeGui game = new SnakeGui(board,600,0);
		game.init();
	}
	
	private void makeConnections() throws IOException {
		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(socket.getInputStream());
	}
	
	
	public void run() {
		new Thread(this::receiveGameState).start(); // Thread for receiving
		new Thread(this::sendToServer).start();     // Thread for sending
	}
	
	private void sendToServer() {
		
		new Thread(()->{
	        try {
	        	Thread.sleep(board.REMOTE_REFRESH_INTERVAL);
				if(playerInitializationSent) {
		           	sendPlayerInitialization(board);
		           	playerInitializationSent=false;
		        }
				while (true) {
					Thread.sleep(board.REMOTE_REFRESH_INTERVAL);
					sendPlayerCommand(board.getCommand());
				}
			} catch (IOException e) {
				Thread.currentThread().interrupt();
				System.out.println("Broadcast was interrupted...");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	
	private void receiveGameState() {
		try {	
			while(true) {
				Board receivedBoard = (Board) in.readObject();
				board.update(receivedBoard);
			}
		} catch (ClassNotFoundException | IOException e) {
	        e.printStackTrace();
	        // Handle exceptions
	    }
	}
	
	 private synchronized void sendPlayerInitialization(RemoteBoard board) throws IOException {
		out.writeObject(board);
		out.flush();
		out.reset();
	 }
	 
	 private synchronized void sendPlayerCommand(String command) throws IOException {
		 out.writeObject(command);
		 out.flush();
		 out.reset();
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
