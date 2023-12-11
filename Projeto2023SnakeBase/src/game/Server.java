package game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import environment.Board;
import environment.LocalBoard;
import gui.SnakeGui;
import remote.ClientHandler;

public class Server {
	
	public static final int PORT = 1234;
	private final ServerSocket serverSocket;
	private List<ClientHandler> clientHandlers = new CopyOnWriteArrayList<>();
	private Board board;
	private ExecutorService threadpool = Executors.newCachedThreadPool();
	
	public Server(Board board) throws IOException {
		this.serverSocket = new ServerSocket(PORT);
		this.board = board;
		threadpool = Executors.newCachedThreadPool();
		System.out.println("Server has started...");
	}
	
	private void startGame() {
		SnakeGui game = new SnakeGui(board,600,0);
		game.init();
	}
	
	public void start(){
		new Thread(this::startGame).start();
		new Thread(this::startBroadcasting).start();
		try {
			while(true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Accepted to server...");
				ClientHandler clientHandler = new ClientHandler(clientSocket, this);
				addClientHandler(clientHandler);
				threadpool.execute(clientHandler);
			}	
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
	            threadpool.shutdown();
	            System.out.println("Shutting down server...");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
	}
	
	public void addClientHandler(ClientHandler clientHandler) {
        clientHandlers.add(clientHandler);
    }

    public void removeClientHandler(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public void broadcastBoard() throws IOException {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.sendBoard(this.getBoard());
        }
    }
	
	public synchronized void startBroadcasting() {
		
		new Thread(()->{
			while(true) {
	        	try {
					Thread.sleep(getBoard().REMOTE_REFRESH_INTERVAL);
					broadcastBoard();
				} catch (InterruptedException | IOException e) {
					Thread.currentThread().interrupt();
					System.out.println("Broadcast was interrupted...");
					break;
				}
	        }
		}).start();
        
    }
	
	
}
