package yte.intern.spring.security.service;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yte.intern.spring.security.dto.ParticipantDetailsDTO;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class MailService {


    private static final String QR_CODE_IMAGE_PATH = "C:\\Users\\berka\\OneDrive\\Masaüstü\\Event-Management-System-Frontend\\src\\qr-code-images";

    public static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static void emailWithImage(ParticipantDetailsDTO details, String type) {

        try {

            final String from = "odevdeneme10@gmail.com";
            final String password = "deneme123";

            String toAddress = details.getUserEmail();

            // JavaMail session object
            Session session;

            // The JavaMail message object
            Message mesg;

            //SMTP server properties
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", 587);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            // authenticate sender username and password
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            };


            String cid = "qr";
            generateQRCodeImage("Sayin " + details.getNameSurname() + ", " + details.getUsername()
                    + " kullanici adi ile " + details.getEventName()
                    + " etkinligine katildiniz.", 350, 350, QR_CODE_IMAGE_PATH
                    + "\\" + "qr.png");

            if(type.equals("qr")){
                return;
            }
            // initialize session object
            session = Session.getInstance(properties, auth);
            session.setDebug(false);

            // initialize message object
            mesg = new MimeMessage(session);

            // from Address
            mesg.setFrom(new InternetAddress(from));

            // Email Addresses
            InternetAddress toAdd = new InternetAddress(toAddress);


            mesg.addRecipient(Message.RecipientType.TO, toAdd);

            // email Subject
            mesg.setSubject("Etkinlik kayıt");

            // message body.
            Multipart mp = new MimeMultipart("related");



            MimeBodyPart pixPart = new MimeBodyPart();
            pixPart.attachFile("qrcode.png");
            pixPart.setContentID("<" + cid + ">");
            pixPart.setDisposition(MimeBodyPart.INLINE);

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("<html>" + "Merhaba " + details.getUsername() + ", <br> "
                    + "Etkinlik girisinizi asagidaki QR code ile yapabilirsiniz. <br> "
                    + "<div><img src=\"cid:" + cid
                    + "\" /></div></html>" + "Tesekkurler, <br> "
                    + "Etkinlik Yonetim Sistemi</html>", "US-ASCII", "html");

            // Attach text and image to message body
            mp.addBodyPart(textPart);
            mp.addBodyPart(pixPart);

            // Setting message content
            mesg.setContent(mp);

            // Send mail
            Transport.send(mesg);

        } catch (MessagingException e) {
            System.err.println(e);
            e.printStackTrace(System.err);
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


}
