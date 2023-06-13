package Isa.Isa.service;

import Isa.Isa.model.DTO.UpitnikDTO;
import Isa.Isa.model.Korisnik;
import Isa.Isa.model.Upitnik;
import Isa.Isa.repository.KorisnikRepository;
import Isa.Isa.repository.UpitnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UpitnikServiceImpl implements UpitnikService{
    @Autowired
    private UpitnikRepository upitnikRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Override
    public Upitnik dodaj(UpitnikDTO upitnikDTO){


        Date danas = new Date();

        Optional<Korisnik> korisnik = korisnikRepository.findById(upitnikDTO.pacijentID);

        if(korisnik.isEmpty()) { return null; }

        Upitnik stari = upitnikRepository.findOneByPacijentAndObrisan(korisnik.get(), false);

        if(stari != null) {
            stari.setObrisan(true);
            upitnikRepository.save(stari);
        }

        Upitnik upitnik = UpitnikDTO.konvertujUModel(upitnikDTO);

        upitnik.setDatumPopunjavanja(danas);
        upitnik.pacijent = korisnik.get();

        Upitnik sacuvan = upitnikRepository.save(upitnik);

        return sacuvan;
    }

    @Override
    public Upitnik getPoslednjiZaKorisnika(int pacijentID){
        Optional<Korisnik> korisnik = korisnikRepository.findById(pacijentID);

        if(korisnik.isEmpty()) { return null; }

        Upitnik upitnik = upitnikRepository.findOneByPacijentAndObrisan(korisnik.get(), false);

        if(upitnik == null) { return null; }

        return upitnik;
    }

}
