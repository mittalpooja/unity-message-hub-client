package com.unity.messagehub.client;


import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
 * Main class for the message hub client
 */
public class Client 
{
	private static int NTHREADS = 2;
	public final static int SERVER_PORT = 8082;
	public final static String SERVER_HOST = "localhost";
	public final static Executor exec = Executors.newFixedThreadPool(NTHREADS);
	
	private Socket sock;

	public void run() throws Exception {

		sock = new Socket(SERVER_HOST, SERVER_PORT);
		
		ReaderThread reader = new ReaderThread(sock);
		WriterThread writer = new WriterThread(sock);
		exec.execute(reader);
		exec.execute(writer);
	}
	
	
    public static void main( String[] args ) throws Exception
    {
        Client client = new Client();
        client.run();
    }
}
