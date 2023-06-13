package Isa.Isa.service;


import Isa.Isa.model.DonacijaKrvi;
import Isa.Isa.model.Korisnik;
import Isa.Isa.repository.DonacijKrviRepository;
import Isa.Isa.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class DonacijaKrviServiceImpl implements DonacijaKrviService {

    @Autowired
    private DonacijKrviRepository donacijKrviRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Override
    public Page<DonacijaKrvi> GetAllForPacijent(int korisnikID, Pageable pageable){

        Optional<Korisnik> korisnik = korisnikRepository.findById(korisnikID);
        if(korisnik.isEmpty()){
            return null;
        }

        Page<DonacijaKrvi> donacijeKrvi = donacijKrviRepository.findAllByPacijent(korisnik.get(), pageable);
        return donacijeKrvi;
    }

    @Override
    public boolean DaLiMozeDaDonira(int korisnikID){
        Optional<Korisnik> korisnik = korisnikRepository.findById(korisnikID);
        DonacijaKrvi poslednjaDonacija = donacijKrviRepository.findFirstByPacijentOrderByDatumDesc(korisnik.get());
        if(poslednjaDonacija == null){
            return true;
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -6);
        Date sixMonthsAgo = cal.getTime();

        if(poslednjaDonacija.getDatum().after(sixMonthsAgo)){
            return false;
        }

        return true;
    }
}
