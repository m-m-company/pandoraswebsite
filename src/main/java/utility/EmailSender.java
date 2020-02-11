package utility;

import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class EmailSender implements Runnable {

    private String text;
    private String receiver;
    private String subject;

    public EmailSender(String text, String subject, String receiver) {
        this.text = text;
        this.receiver = receiver;
        this.subject = subject;
    }

    @Override
    public void run() {
        try{
            Properties props = System.getProperties();
            props.put("mail.smtps.host", "smtp.gmail.com");
            props.put("mail.smtps.auth", "true");
            Session session = Session.getInstance(props, null);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("pandorasjar2019@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.receiver, false));
            msg.setSubject(subject);
            msg.setText(text); //TODO: make it bello
            msg.setHeader("X-Mailer", "");
            msg.setSentDate(new Date());
            SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
            t.connect("smtp.gmail.com", "pandorasjar2019@gmail.com", "ptqehcaqsmgrhjni");
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
        }
        catch (MessagingException e){
            e.printStackTrace();
        }
    }

}
