package server;

public class Profile {

    private String id, password;
    private int counter;
    private int likes;

    public Profile(String id, String password, int counter, int likes){
        this.id = id;
        this.password = password;
        this.counter = counter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void increaseCounter(){
        counter++;
    }
    
    public String toString(){
    	return id + " " + password + " " + counter;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
