package Isa.Isa.service;

import Isa.Isa.model.EmailDetails;

public interface EmailService {
    String sendConfirmationMail(EmailDetails details);
    boolean confirmMail(String email, String confirmToken);
}
