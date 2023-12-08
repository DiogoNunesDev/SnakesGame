package remote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


/** Remore client, only for part II
 * 
 * @author luismota
 *
 */

public class Client {

	
	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;
	private Socket socket;
	
	
	public Client(int port) throws IOException {
		this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

}
