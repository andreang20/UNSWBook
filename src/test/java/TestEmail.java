import dao.Request;
import email.MyEmail;
import org.apache.commons.mail.EmailException;

public class TestEmail {
    public static void main(String[] args) {
        try {
            MyEmail myEmail = new MyEmail();
            String content = "hello";
            myEmail.send("UNSWbook: Friend request", content, "http://localhost:8080","clintonfeng@gmail.com");
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
