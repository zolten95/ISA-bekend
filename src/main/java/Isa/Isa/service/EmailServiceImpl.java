package Isa.Isa.service;

import Isa.Isa.model.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;


@Service
public class EmailServiceImpl implements  EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private KorisnikService korisnikService;
    @Value("vracarmilan3@gmail.com")
    private String sender;

    public String sendConfirmationMail(EmailDetails details)
    {
        try {

            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);

            return "Mail Sent Successfully...";
        }

        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    public boolean confirmMail(String email, String confirmToken){
        return korisnikService.confirmMail(email, confirmToken);
    }
}
