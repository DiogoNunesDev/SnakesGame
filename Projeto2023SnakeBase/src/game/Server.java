package game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import remote.Client;
import remote.ClientHandler;

public class Server {
	
	public static final int port = 1234;
	private final ServerSocket serverSocket;
	private ArrayList<ClientHandler> handlers;
	
	
	
	public Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		
	}
	
	private void broadcastMessage(ClientHandler sender, String message) {
		for(ClientHandler clientHandler : handlers) {
			System.out.println("IM HERE");
		}
	}
	
	
	//When a new client connects, the server creates a new ClientHandler thread to manage communications with that client.
	private void acceptClient() {
		System.out.println("Start server");
		handlers = new ArrayList<ClientHandler>();
		try {
			while(!serverSocket.isClosed()) {
				System.out.println("Waiting for clients");
				Socket socket = serverSocket.accept();
				System.out.println("Accepted");
				ClientHandler clientHandler = new ClientHandler(socket);
				System.out.println("Connection established");
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}
