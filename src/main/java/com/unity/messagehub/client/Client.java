package com.unity.messagehub.client;


import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Client 
{
	public final static int SERVER_PORT = 8082;
	public final static String SERVER_HOST = "localhost";
	public final static Executor exec = Executors.newFixedThreadPool(5);
	
	private Socket sock;

	public void run() throws Exception {

		sock = new Socket(SERVER_HOST, SERVER_PORT);
		
		try {
			ReaderThread reader = new ReaderThread(sock);
			WriterThread writer = new WriterThread(sock);
			exec.execute(reader);
			exec.execute(writer);
		} finally {
			//sock.close();
		}
	}
	
	
    public static void main( String[] args ) throws Exception
    {
        Client client = new Client();
        client.run();
    }
}
