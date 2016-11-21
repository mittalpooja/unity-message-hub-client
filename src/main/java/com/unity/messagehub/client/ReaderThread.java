package com.unity.messagehub.client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import com.unity.messagehub.library.MessageHubProtocol;

/*
 * This thread reads the response messages that this client receives from the server
 * 
 * We are printing the responses to stdout for now.
 */
public class ReaderThread implements Runnable {
	
	private Socket sock;
	DataInputStream in = null;

	public ReaderThread(Socket sock) throws IOException {
		this.sock = sock;
		this.in = new DataInputStream(new BufferedInputStream(this.sock.getInputStream()));
	}

	public void run() {		
		byte msg_type; 

		// Read the Input messages from the socket stream (server) to this client
		try {
			while ((msg_type = in.readByte())!=-1) {
				//System.out.println("msg_type:"+msg_type);
					
				switch(msg_type) {
					case MessageHubProtocol.GET_ID_RESPONSE:
						long id = in.readLong();
						System.out.println(id);
						break;
					case MessageHubProtocol.GET_LIST_RESPONSE:
						byte size = in.readByte();
						
						for (int i=0; i<size; i++) {
							long id1 = in.readLong();
							System.out.print(id1);
							if (i!=(size-1)) System.out.print(",");
						}
						System.out.println();
						break;
					case MessageHubProtocol.RELAY_RESPONSE:
						int messageSize = in.readInt();
						byte[] message = new byte[messageSize];
						in.read(message);
						System.out.println(new String(message));
						break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
