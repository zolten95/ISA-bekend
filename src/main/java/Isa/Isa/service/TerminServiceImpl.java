package Isa.Isa.service;

import Isa.Isa.model.DTO.TerminDTO;
import Isa.Isa.model.Korisnik;
import Isa.Isa.model.MedicinskiCentar;
import Isa.Isa.model.Termin;
import Isa.Isa.model.Upitnik;
import Isa.Isa.model.enums.StatusTermina;
import Isa.Isa.repository.KorisnikRepository;
import Isa.Isa.repository.MedicinskiCentarRepository;
import Isa.Isa.repository.TerminRepository;
import Isa.Isa.repository.UpitnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TerminServiceImpl implements TerminService{
    @Autowired
    private TerminRepository terminRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MedicinskiCentarRepository medicinskiCentarRepository;
    @Autowired
    private UpitnikRepository upitnikRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private DonacijaKrviService donacijaKrviService;

    @Override
    public Termin add(TerminDTO terminDTO){
        Termin termin = TerminDTO.convertBack(terminDTO);
        Optional<MedicinskiCentar> medicinskiCentar = medicinskiCentarRepository.findById(terminDTO.getMedicinskiCentarID());
        if(medicinskiCentar.isEmpty()){
            return null;
        }
        termin.setMedicinskiCentar(medicinskiCentar.get());
        termin = terminRepository.save(termin);
        return termin;
    }

    @Override
    public List<Termin> getRezervisaniZaKorisnika(int korisnikId){
        Date danas = new Date();
        List<Termin> terminiUBuducnosti = new ArrayList<>();
        List<Termin> termini;
        Optional<Korisnik> k = korisnikRepository.findById(korisnikId);
        if(k.isEmpty()){
            return null;
        }
        Korisnik korisnik = k.get();


        termini = terminRepository.findAllByPacijent(korisnik);

        for (Termin t : termini){
            if (t.getDatumTermina().after(danas)){
                terminiUBuducnosti.add(t);
            }
        }
        return terminiUBuducnosti;
    }

    @Override
    public Optional<Termin> findById(int id){
        Optional<Termin> termin = terminRepository.findById(id);
        return termin;
    }

    @Override
    public List<Termin> getAll(){
        return terminRepository.findAll();
    }

    @Override
    public Termin rezervisiTermin(int terminId, int korisnikId){
        Optional<Korisnik> k = korisnikRepository.findById(korisnikId);
        if (k.isEmpty()) {
            return null;
        }
        Korisnik korisnik = k.get();
        if(!donacijaKrviService.DaLiMozeDaDonira(korisnikId)){
            return null;
        }
        if(korisnik.getPenali() >= 3){
            return null;
        }

        Upitnik upitnik = upitnikRepository.findOneByPacijentAndObrisan(korisnik, false);

        if(upitnik == null){
            return null;
        }

        Optional<Termin> t = terminRepository.findById(terminId);
        if (t.isEmpty()) {
            return null;
        }
        Termin termin = t.get();


        MedicinskiCentar medicinskiCentar = termin.getMedicinskiCentar();

        if (medicinskiCentar == null) {
            return null;
        }

        Date danas = new Date();

        if(termin.getStatusTermina().equals(StatusTermina.Zauzet)){
            return null;
        }


        List<Termin> proveraDatum = terminRepository.findAllByPacijentAndMedicinskiCentar(korisnik, medicinskiCentar);

        for (Termin tr : proveraDatum) {
            if (tr != null && tr.getDatumTermina().after(danas)) {
                return null;
            }
        }



        termin.setPacijent(korisnik);
        termin.setStatusTermina(StatusTermina.Zauzet);

        Termin sacuvan = terminRepository.save(termin);
        return sacuvan;

    }

    @Override
    public List<Termin> getAllForSelektovaniCentar(int medicinskiCentarId){
        Optional<MedicinskiCentar> medicinskiCentar  = medicinskiCentarRepository.findById(medicinskiCentarId);
        List<Termin> terminiZaPrikaz = new ArrayList<>();
        List<Termin> sviTermini = terminRepository.findAll();
        Date danas = new Date();

        if (medicinskiCentar.isEmpty()){
            return null;
        }

        List<Termin> termini = terminRepository.findAllByMedicinskiCentarAndStatusTermina(medicinskiCentar.get(), StatusTermina.Slobodan);

        for(Termin t : termini){
            if(t.getDatumTermina().after(danas)){
                terminiZaPrikaz.add(t);
            }
        }

        termini.sort(Comparator.comparing(Termin::getDatumTermina));

        return terminiZaPrikaz;
    }

    @Override
    public Termin otkaziTermin(int terminId, int korisnikId){
        Date danas = new Date();
        long milisekundeUDanu = 24 * 60 * 60 * 1000;
        Optional<Termin> t = terminRepository.findById(terminId);

        if(t.isEmpty()){
            return null;
        }
        Termin termin = t.get();

        long milisekunde = termin.getDatumTermina().getTime();
        long newMillis = milisekunde - milisekundeUDanu;
        Date datumZaProveru = new Date(newMillis);

        if(datumZaProveru.before(danas)){
            return null;
        }

        termin.setStatusTermina(StatusTermina.Slobodan);
        termin.setPacijent(null);

        Termin sacuvan = terminRepository.save(termin);
        return sacuvan;
    }
}
