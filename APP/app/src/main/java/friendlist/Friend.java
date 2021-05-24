package friendlist;

public class Friend {
    private String username;
    private int imageID;

    public Friend(String username, int imageID){
        this.username = username;
        this.imageID = imageID;
    }


public String getUsername(){
    return username;
}

public int getImageID(){
    return imageID;
}}