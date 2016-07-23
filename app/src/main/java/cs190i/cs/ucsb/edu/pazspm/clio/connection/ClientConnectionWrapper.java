package cs190i.cs.ucsb.edu.pazspm.clio.connection;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import cs190i.cs.ucsb.edu.pazspm.clio.MainActivity;
import shared.Message;
import shared.MessageType;


public class ClientConnectionWrapper extends Thread{

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Socket socket;

    private MainActivity activity;

    public ClientConnectionWrapper(String address, int port, InetAddress currentIp, int localPort, MainActivity activity){
        try {

            socket= new Socket(address, port, currentIp, localPort);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            this.activity = activity;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){

        while(true){
            try {
                Message message = (Message) objectInputStream.readObject();
                if(message.getType() == MessageType.IMAGEREPLY){
                    Bitmap bitmap = JSONUtils.jsonToBitmap(message.getJson());
                    activity.bitmapFromServer(bitmap);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendImageRequest(){
        Message message = new Message(MessageType.IMAGEREQUEST);
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendNewBitmap(Bitmap bitmap){
        Message message = new Message(MessageType.NEWIMAGE, JSONUtils.bitmapToJson(bitmap));
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        stop();
        try {
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
