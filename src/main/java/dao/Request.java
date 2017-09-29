package dao;

public class Request {
    public static final int INVALID = -1;

    private int id;
    private String sender;
    private String receiver;
    private boolean accepted;

    public Request(int id, String sender, String receiver, boolean accepted) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.accepted = accepted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
