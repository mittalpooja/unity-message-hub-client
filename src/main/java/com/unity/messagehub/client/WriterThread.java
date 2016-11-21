package com.unity.messagehub.client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.unity.messagehub.library.RelayRequestMessage;
import com.unity.messagehub.library.MessageHubProtocol;

/*
 * This thread used stdout to read the requests on the client
 * & then writes the requests to the socket stream
 * 
 * Note : For simplicity sake - this tread reads the following inputs
 * 1. getId (for getId request). It sends the message encoded into byte on the stream.
 * 2. getList (for getList request). Encodes the message to byte before sending it to the server
 * 3. relay (for relay request). It is followed by json in the next line.
 *    {"receivers":"2,3,4,7","message":"hello again"}
 *    This client takes the input in text/json form and converts it to more conpact form as described in the 
 *    MessageHubProtocol (unity-message-hub-library)
 */
public class WriterThread implements Runnable {
	
	Socket sock;
	DataOutputStream out; 
	
	public WriterThread(Socket sock) throws IOException {
		this.sock = sock;
		this.out = new DataOutputStream(new BufferedOutputStream(this.sock.getOutputStream()));
	}

	public void run() {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String s=null;
		
		while (true) {
			try {
				s = bufferedReader.readLine();
				byte msg_type = MessageHubProtocol.convertToByte(s);
				
				//System.out.println(s+":"+msg_type);
				
				switch(msg_type) {
					case MessageHubProtocol.GET_ID_REQUEST:
						out.writeByte(msg_type);
						out.flush();
						break;
					case MessageHubProtocol.GET_LIST_REQUEST:
						out.writeByte(msg_type);
						out.flush();
						break;
					case MessageHubProtocol.RELAY_REQUEST:
						s = bufferedReader.readLine();
						RelayRequestMessage msgToRelay = RelayRequestMessage.parseRelayMessage(s);
						if (msgToRelay!=null) {
							out.writeByte(msg_type);
							msgToRelay.send(out);
						} else {
							System.out.println("Unreadable Relay Message");
						}
						break;
					default:
						System.out.println("Incorrect Message Type");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}



