package remote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import game.Server;

public class ClientHandler extends Thread {
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	
	public ClientHandler(Socket socket) throws IOException {
		this.socket = socket;
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
	
	
	//It's a thread that handles all input and output for one client.
	//It reads messages from the client and can send messages back.
	//It also can broadcast messages to other clients through the server.

	@Override
	public void run() {
		//keeps reading messages from client 
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

