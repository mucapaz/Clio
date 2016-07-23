package cs190i.cs.ucsb.edu.pazspm.clio.connection;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import cs190i.cs.ucsb.edu.pazspm.clio.MainActivity;
import shared.Message;
import shared.MessageType;


public class MyAsyncTask extends AsyncTask<Void, Void, Void>
{
    private static ClientConnectionWrapper connection;

    private static String SERVER_IP = "169.231.91.127"; // Must be declared
    private static int SERVER_PORT = 4444;
    private static int LOCAL_PORT = 5000;
    private String password = "";
    private String id = "";
    private MainActivity activity;

    public MyAsyncTask(MainActivity activity, String id, String password){
        this.activity = activity;
        this.id = id;
        this.password = password;
        Random rand = new Random();
        int r = rand.nextInt(5000);
        if(r < 0) r *= -1;
        LOCAL_PORT = 4000 + r;
    }

    @Override
    protected void onPostExecute(Void result) {
        //Task you want to do on UIThread after completing Network operation
        //onPostExecute is called after doInBackground finishes its task.
    }

    @Override
    protected Void doInBackground(Void... params) {
        setConnection();
        return null;
    }
    public void bitmapToServer(Bitmap bitmap){
        connection.sendNewBitmap(bitmap);
    }

    public void setConnection(){
        Socket client;
        ObjectInputStream objectInputStream;
        ObjectOutputStream objectOutputStream;

        try {
            System.out.println(SERVER_IP + " " + SERVER_PORT + " " + ConnectionUtils.getCurrentIp() + " " + LOCAL_PORT );

            client = new Socket(SERVER_IP, SERVER_PORT, ConnectionUtils.getCurrentIp(), LOCAL_PORT +1); // connect to the server
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectInputStream = new ObjectInputStream(client.getInputStream());

            Message portrequest = new Message(MessageType.CONNECT, LOCAL_PORT, ConnectionUtils.getCurrentIp(), password);
            portrequest.setId(id);
            System.out.println(id + " laaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            objectOutputStream.writeObject(portrequest);
            objectOutputStream.flush();

            Message portreply = (Message) objectInputStream.readObject();

            objectOutputStream.close();
            objectInputStream.close();
            client.close();

            connection = new ClientConnectionWrapper( SERVER_IP , portreply.getPort(),
                    ConnectionUtils.getCurrentIp(), LOCAL_PORT, activity);
            connection.start();

            System.out.println("CONNECTION START");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ClientConnectionWrapper getConnection() {
        return connection;
    }

    public static void setConnection(ClientConnectionWrapper connection) {
        MyAsyncTask.connection = connection;
    }

    public void requestImageFromServer(){
        connection.sendImageRequest();
    }

    public void disconnect() {
        connection.disconnect();
    }
}