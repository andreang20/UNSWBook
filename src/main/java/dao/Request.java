package dao;

public class Request {
    private String sender;
    private String receiver;
    private boolean accepted;

    public Request(String sender, String receiver, boolean accepted) {
        this.sender = sender;
        this.receiver = receiver;
        this.accepted = accepted;
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
