package email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class MyEmail {
    private HtmlEmail email;

    public MyEmail() throws EmailException {
        email = new HtmlEmail();
        email.setSmtpPort(587);
        email.setAuthenticator(new DefaultAuthenticator("unswbookjusticeleague@gmail.com",
                "strongpassword"));
        email.setDebug(false);
        email.setHostName("smtp.gmail.com");
        email.setFrom("unswbookjusticeleague@gmail.com");
        email.setTLS(true);

    }

    public void send(String subject, String content, String link, String to) throws EmailException {
        email.setSubject(subject);
        String content1 = "<html><body>"+content+" <a href="+link+">Link</a></body></html>";
        email.setHtmlMsg(content1);
        email.setTextMsg("Doesnt support html");
        email.addTo(to);
        email.send();
        System.out.println("Mail sent!");
    }
}
