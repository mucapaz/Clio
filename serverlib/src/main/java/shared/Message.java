package shared;

import java.io.Serializable;
import java.net.InetAddress;

public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	private MessageType type;
    private String json;
    private int port;
    private InetAddress address;
    private String id;

    public Message(MessageType type){
        this.type = type;
    }

    public Message(MessageType type, String json){
        this.type = type;
        this.json = json;
    }

    public Message(MessageType type, int port, InetAddress address, String id){
    	this.type = type;
        this.port = port;
        this.address = address;
        this.id = id;
    }
    
    public Message(MessageType type, int port){
    	this.type = type;
        this.port = port;
    }

    public Message(MessageType type, int port, InetAddress address){
        this.type = type;
        this.port = port;
        this.address = address;
    }

    public Message(MessageType type, String json, int port){
        this.type = type;
        this.json = json;
        this.port = port;
    }


    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
