package Isa.Isa.service;

import Isa.Isa.model.DTO.RegistracijaDTO;
import Isa.Isa.model.EmailDetails;
import Isa.Isa.model.Korisnik;
import Isa.Isa.model.enums.TipKorisnika;
import Isa.Isa.repository.KorisnikRepository;
import Isa.Isa.repository.MedicinskiCentarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class KorisnikServiceImpl implements KorisnikService {

    private static final String TOKEN_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private MedicinskiCentarRepository medicinskiCentarRepository;

    @Autowired
    private EmailService emailService;

    public Korisnik Registruj(RegistracijaDTO registracijaDTO){
        if (ZauzetEmail(registracijaDTO.getEmail())){
            return null;
        }

        EmailDetails emailDetails = new EmailDetails();
        String uniqueToken = generateToken();
        emailDetails.setRecipient(registracijaDTO.getEmail());
        emailDetails.setSubject("Confirmation email");
        String tokenForUser = "http://localhost:8080/api/email/potvrdiNalog/" + emailDetails.getRecipient() + "/" + uniqueToken;
        emailDetails.setMsgBody(tokenForUser);
        emailService.sendConfirmationMail(emailDetails);

        Korisnik korisnik = RegistracijaDTO.Konvertuj(registracijaDTO);
        korisnik.setTipKorisnika(TipKorisnika.RegistrovaniKorisnik);
        korisnik.setTokenZaPotvrdu(uniqueToken);
        korisnik = korisnikRepository.save(korisnik);

        return korisnik;

    }

    public static String generateToken() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 32; i++) {
            sb.append(TOKEN_CHARS.charAt(random.nextInt(TOKEN_CHARS.length())));
        }
        return sb.toString();
    }


    @Override
    public List<Korisnik> getAll(){
        return korisnikRepository.findAll();
    }

    @Override
    public Korisnik getTrenutniKorisnik(String email){
        Korisnik user = GetByEmail(email);
        return user;
    }


    @Override
    public Optional<Korisnik> findById(int id){
        return korisnikRepository.findById(id);
    }

    @Override
    public Korisnik GetByEmail(String email){
        return korisnikRepository.findOneByEmail(email).get();
    }




    public boolean ZauzetEmail(String email){
        Optional<Korisnik> korisnik = korisnikRepository.findOneByEmail(email);

        if (korisnik.isPresent()){
            return true;
        }

        return false;
    }

    @Override
    public boolean confirmMail(String email, String confirmToken){
        Korisnik korisnik = GetByEmail(email);

        if(korisnik.getTokenZaPotvrdu().equals(confirmToken)){
            korisnik.setPotvrdjen(true);
            korisnik.setTokenZaPotvrdu(null);
            korisnikRepository.save(korisnik);

            return true;
        }

        return false;
    }

    @Override
    public Korisnik smanjiPenale(Korisnik korisnik){
        Optional<Korisnik> k = korisnikRepository.findById(korisnik.getId());

        if(k.isEmpty()){
            return  null;
        }

        Korisnik korisnik1 = k.get();
        korisnik1.setPenali(0);

        Korisnik sacuvan = korisnikRepository.save(korisnik1);

        return sacuvan;
    }

    //dodato
    @Override
    public List<Korisnik> getMedicinskoOsoblje(){
        return korisnikRepository.findByTipKorisnika(TipKorisnika.MedicinskiRadnik);
    }

}
