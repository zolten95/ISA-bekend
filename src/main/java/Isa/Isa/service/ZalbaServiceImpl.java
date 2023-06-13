package Isa.Isa.service;

import Isa.Isa.model.*;
import Isa.Isa.model.DTO.OdgvorenaZalbaDTO;
import Isa.Isa.model.DTO.ZalbaCentarDTO;
import Isa.Isa.model.DTO.ZalbaOsobljeDTO;
import Isa.Isa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ZalbaServiceImpl implements ZalbaService {

    @Autowired
    private ZalbaRepository zalbaRepository;

    @Autowired
    private TerminRepository terminRepository;

    @Autowired
    private TerminMedicinskiRadnikRepository terminMedicinskiRadnikRepository;

    @Autowired
    private DonacijKrviRepository donacijKrviRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private MedicinskiCentarRepository medicinskiCentarRepository;

    @Autowired OdgovorZalbaRepository odgovorZalbaRepository;

    private boolean ZalbaCentarProvera(Korisnik korisnik, MedicinskiCentar medicinskiCentar){
        DonacijaKrvi donacijaKrvi = donacijKrviRepository.findFirstByPacijentAndMedicinskiCentar(korisnik, medicinskiCentar);

        if( donacijaKrvi == null) {
            return false;
        }

        return true;
    }

    private boolean ZalbaOsobljeProvera(Korisnik pacijent, Korisnik medicinskoOsoblje) {
        List<Termin> termini = terminRepository.findAllByPacijentAndDatumTerminaBefore(pacijent, new Date());
        if(termini.isEmpty()){
            return false;
        }

        for(Termin t: termini){
            List<TerminMedicinskiRadnik> zaProveru = terminMedicinskiRadnikRepository.findAllByTermin(t);

            for(TerminMedicinskiRadnik temp : zaProveru){
                if(temp.getMedicinskiRadnik() == medicinskoOsoblje){
                    return true;
                }
            }
        }


        return false;
    }

    public Zalba addZalbaCentar(ZalbaCentarDTO zalbaCentarDTO){
        Optional<Korisnik> p = korisnikRepository.findById(zalbaCentarDTO.getPacijentID());
        if(p.isEmpty()){
            return null;
        }
        Korisnik pacijent = p.get();

        Optional<MedicinskiCentar> med = medicinskiCentarRepository.findById(zalbaCentarDTO.getMedCentarID());
        if(med.isEmpty()){
            return null;
        }
        MedicinskiCentar medicinskiCentar = med.get();

        if(!ZalbaCentarProvera(pacijent, medicinskiCentar)){
            return null;
        }

        Zalba zalba = ZalbaCentarDTO.convertToZalba(zalbaCentarDTO);

        zalba.setMedicinskiCentar(medicinskiCentar);
        zalba.setPacijent(pacijent);

        Zalba sacuvano = zalbaRepository.save(zalba);


        return sacuvano;
    }

    public Zalba addZalbaOsoblje(ZalbaOsobljeDTO zalbaOsobljeDTO){
        Optional<Korisnik> p = korisnikRepository.findById(zalbaOsobljeDTO.getPacijentID());
        if(p.isEmpty()){
            return null;
        }
        Korisnik pacijent = p.get();

        Optional<Korisnik> med = korisnikRepository.findById(zalbaOsobljeDTO.getOsobljeID());
        if(p.isEmpty() || !med.get().getRole().equals("MedicinskiRadnik")){
            return null;
        }
        Korisnik osoblje = med.get();

        if(!ZalbaOsobljeProvera(pacijent, osoblje)){
            return null;
        }

        Zalba zalba = ZalbaOsobljeDTO.convertToZalba(zalbaOsobljeDTO);

        zalba.setPacijent(pacijent);
        zalba.setMedicinskoOsoblje(osoblje);

        Zalba sacuvano = zalbaRepository.save(zalba);

        return sacuvano;
    }

    public List<Zalba> getAllNeodgovoreni(){
        List<Zalba> sveZalbe = zalbaRepository.findAllByObrisan(false);
        List<Zalba> neodgovorene = new ArrayList<>();

        for(Zalba z : sveZalbe){
            if(odgovorZalbaRepository.findFirstByZalba(z) == null){
                neodgovorene.add(z);
            }
        }

        return neodgovorene;
    }

    public List<OdgvorenaZalbaDTO> getAllOdgovoreni(){
        List<Zalba> sveZalbe = zalbaRepository.findAllByObrisan(false);
        List<OdgvorenaZalbaDTO> odgovorene = new ArrayList<>();

        for(Zalba z : sveZalbe){
            if(odgovorZalbaRepository.findFirstByZalba(z) != null){
                OdgvorenaZalbaDTO odgvorenaZalbaDTO = new OdgvorenaZalbaDTO();
                odgvorenaZalbaDTO.setZalba(z);
                odgvorenaZalbaDTO.setOdgovorZalba(odgovorZalbaRepository.findFirstByZalba(z));

                odgovorene.add(odgvorenaZalbaDTO);
            }
        }

        return odgovorene;
    }

    public List<Zalba> getAllNeodgovorenePacijent(int korisnikID){
        Optional<Korisnik> k = korisnikRepository.findById(korisnikID);
        List<Zalba> neodgovorene = new ArrayList<>();

        if (k.isEmpty()) {
            return new ArrayList<>();
        }

        Korisnik pacijent = k.get();

        List<Zalba> zalbe = zalbaRepository.findAllByPacijent(pacijent);

        for(Zalba z : zalbe){
            if(odgovorZalbaRepository.findFirstByZalba(z) == null){
                neodgovorene.add(z);
            }
        }

        return neodgovorene;
    }

    public List<OdgvorenaZalbaDTO> getAllOdgovorenePacijent(int korisnikID){
        Optional<Korisnik> k = korisnikRepository.findById(korisnikID);

        if (k.isEmpty()) {
            return new ArrayList<>();
        }

        Korisnik pacijent = k.get();

        List<Zalba> zalbe = zalbaRepository.findAllByPacijent(pacijent);
        List<OdgvorenaZalbaDTO> odgovorene = new ArrayList<>();

        for(Zalba z : zalbe){
            if(odgovorZalbaRepository.findFirstByZalba(z) != null){
                OdgvorenaZalbaDTO odgvorenaZalbaDTO = new OdgvorenaZalbaDTO();
                odgvorenaZalbaDTO.setZalba(z);
                odgvorenaZalbaDTO.setOdgovorZalba(odgovorZalbaRepository.findFirstByZalba(z));

                odgovorene.add(odgvorenaZalbaDTO);
            }
        }

        return  odgovorene;
    }


}
