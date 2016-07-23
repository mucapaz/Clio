package server;

/**
 * Created by Samuel on 3/11/2016.
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import shared.Message;
import shared.MessageType;


public class Server {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;

    private int nextPort;
	private int serverPort;

    private Repository repository;

    public Server(int serverPort){
        this.serverPort = serverPort;
        this.nextPort = serverPort + 1;
        this.repository = new Repository();
    }

    public void run() throws IOException, ClassNotFoundException {

        serverSocket = new ServerSocket(serverPort);

        while (true) {
            try {

                clientSocket = serverSocket.accept(); // accept the client connection
                objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

                Message postrequest = (Message) objectInputStream.readObject();
                Profile profile;

                if(repository.existProfile(postrequest.getId())){
                    profile = repository.findProfile(postrequest.getId());
                }else{
                    profile = repository.createProfile(postrequest.getId());
                }
                
                ServerConnectionWrapper serverConnectionWrapper = new
                        ServerConnectionWrapper(nextPort, profile, this);
                serverConnectionWrapper.start();
                Message postreply = new Message(MessageType.PORTREPLY, nextPort++);
                objectOutputStream.writeObject(postreply);
                objectOutputStream.flush();
                objectOutputStream.close();

                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

}
