package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import shared.Message;
import shared.MessageType;

public class ServerConnectionWrapper extends Thread{

	private Server server;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	private Profile profile;
	private Socket socket;
	private ServerSocket serverSocket;

	public ServerConnectionWrapper(int localPort,  Profile profile,  Server server){
		this.profile = profile;
		this.server = server;
		try {
			serverSocket = new ServerSocket(localPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run(){
		try{
			socket = serverSocket.accept();
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		}catch (IOException e) {
			e.printStackTrace();
		}

		while(true){
			try {
				Message message = (Message) objectInputStream.readObject();
				
				if(message.getType() == MessageType.NEWIMAGE){
					server.getRepository().createImage(message, profile.getId());
				}else if(message.getType() == MessageType.IMAGEREQUEST){
					Message replyMessage = new Message(MessageType.IMAGEREPLY, 
							server.getRepository().chooseRandomJsonImage());
					
					objectOutputStream.writeObject(replyMessage);
					System.out.println(replyMessage.toString() + " " + replyMessage.getJson());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();

				try {
					objectInputStream.close();
					objectOutputStream.close();

					serverSocket.close();
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				return;
			}
		}
	}



}
