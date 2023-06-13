package Isa.Isa.service;

import Isa.Isa.model.DTO.OdgovorDTO;
import Isa.Isa.model.EmailDetails;
import Isa.Isa.model.OdgovorZalba;
import Isa.Isa.model.Zalba;
import Isa.Isa.repository.OdgovorZalbaRepository;
import Isa.Isa.repository.ZalbaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OdgovorServiceImpl implements OdgovorService{

    @Autowired
    private ZalbaRepository zalbaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OdgovorZalbaRepository odgovorZalbaRepository;

    @Override
    public OdgovorZalba add(OdgovorDTO dto, int zalbaID){
        if(dto == null){
            return null;
        }

        Zalba z = zalbaRepository.findById(zalbaID);
        if(z == null) { return null; }
        String pacijentEmail = z.getPacijent().getEmail();

        OdgovorZalba odgovorZalba = OdgovorDTO.ConvertToOdgovor(dto);

        odgovorZalba.setZalba(z);

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(pacijentEmail);
        if(z.getMedicinskiCentar() != null){
            emailDetails.setSubject("Odgovor na zalbu na medicinski centar" + ": " + z.getMedicinskiCentar().getImeCentra());
        } else {
            emailDetails.setSubject("Odgovor na zalbu na medicinsko osoblje" + ": " + z.getMedicinskoOsoblje().getIme() + " " +
                    z.getMedicinskoOsoblje().getPrezime());
        }
        emailDetails.setMsgBody(odgovorZalba.getText());
        emailService.sendConfirmationMail(emailDetails);

        OdgovorZalba odgovor = odgovorZalbaRepository.save(odgovorZalba);

        return odgovor;
    }


}
