package server;

import java.io.IOException;

public class ServerMain {

    public static final int SERVER_PORT = 4444;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Server server = new Server(SERVER_PORT);
        server.run();
    }
}
