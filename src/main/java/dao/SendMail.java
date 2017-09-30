package dao;

import db.IDbManager;

import java.sql.*;

import java.util.Properties;
import java.util.TimerTask;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletRequest;

public class SendMail {
    private IDbManager dbm;
    private String host;
    private String username;
    private String password;
    private Session session;


    public SendMail(IDbManager dbManager){
        this.dbm = dbManager;
        this.host = "smtp.gmail.com";
        this.username = "Unswbookjusticeleague@gmail.com";
        this.password = "strongpassword";

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        session = Session.getDefaultInstance(props, auth);
        session.setDebug(true);
    }

    public boolean sendFriendMail(String sender, String receiver, HttpServletRequest req) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        String e_receiver ="";
        Statement stm = null;
        try {
            conn = dbm.establishConnection();
            Statement st = conn.createStatement();
            //Check if request exists
            String sql = "select * from unswbook.request where sender = '"+sender+"' and receiver = '"+receiver+"';";
            ResultSet rs1 = st.executeQuery(sql);
            //Add if not exists
            if (rs1.next() == false)
            {
                System.out.println("Add new request");
                stmt = conn.prepareStatement("insert into unswbook.request (sender, receiver, accepted) values (?,?,false);");
                stmt.setString(1,sender);
                stmt.setString(2,receiver);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to add request.");
        }


        //System.out.println("Done update");
        //get receiver email
        stm = conn.createStatement();
        String sql2 = "select email from unswbook.user_profile where username = '" +receiver+ "';";
        ResultSet rs = stm.executeQuery(sql2);
        while(rs.next())
        {
            e_receiver = rs.getString("email");
        }
        //System.out.println("email of receiver "+e_receiverreceiver);
        //close connection
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) { }
        }

        try {
            // Create a default MimeMessage object.
            //String url ="localhost:8080/accept_request/sender="+sender+"&&receiver="+receiver;
            Message message = new MimeMessage(session);
            String url = req.getRequestURL().toString();
            url = url.replace("/add", "/accept_request");
            url +="/sender="+sender+"&&receiver="+receiver;

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(this.username));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(e_receiver));

            // Set Subject: header field
            message.setSubject("Friend Request");

            // set the actual message
            //String link = "<a href = "http:l>Add Friend</a>";
            String msg = sender +" send invitation request to you\n" +
                    "To accept it please visit on the link bellow\n" +
                    url +
                    "\n\nThank You\n\n\n" +
                    "Admin";
            message.setText(msg);


            ;

            // Send message
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
            //return false;
        }
    }
}
