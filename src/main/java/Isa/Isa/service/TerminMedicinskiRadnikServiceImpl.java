package Isa.Isa.service;

import Isa.Isa.model.Korisnik;
import Isa.Isa.model.Termin;
import Isa.Isa.model.TerminMedicinskiRadnik;
import Isa.Isa.repository.KorisnikRepository;
import Isa.Isa.repository.TerminMedicinskiRadnikRepository;
import Isa.Isa.repository.TerminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TerminMedicinskiRadnikServiceImpl implements TerminMedicinskiRadnikService{
    @Autowired
    private TerminMedicinskiRadnikRepository terminMedicinskiRadnikRepository;

    @Autowired
    private TerminRepository terminRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Override
    public TerminMedicinskiRadnik add(int terminId, int medRadnikId){

        Optional<Termin> t = terminRepository.findById(terminId);
        if(t.isEmpty()){
            return null;
        }
        Termin termin = t.get();

        Optional<Korisnik> med = korisnikRepository.findById(medRadnikId);
        if(med.isEmpty()){
            return null;
        }
        Korisnik medRadnik = med.get();

        TerminMedicinskiRadnik terminMedicinskiRadnik = new TerminMedicinskiRadnik();
        terminMedicinskiRadnik.setTermin(termin);
        terminMedicinskiRadnik.setMedicinskiRadnik(medRadnik);

        terminMedicinskiRadnik = terminMedicinskiRadnikRepository.save(terminMedicinskiRadnik);

        return terminMedicinskiRadnik;
    }

    @Override
    public TerminMedicinskiRadnik findById(int id){

        Optional<TerminMedicinskiRadnik> t =  terminMedicinskiRadnikRepository.findById(id);
        if(t.isEmpty()) {
            return  null;
        }


        return t.get();
    }

    @Override
    public List<TerminMedicinskiRadnik> getAll(){
        return terminMedicinskiRadnikRepository.findAll();
    }

    @Override
    public List<Korisnik> getOsobljeZaTermin(int terminId){
        List<Korisnik> osoblje = new ArrayList<>();
        Optional<Termin> t = terminRepository.findById(terminId);
        if(t.isEmpty()) {
            return null;
        }

        Termin termin = t.get();

        List<TerminMedicinskiRadnik> temp = terminMedicinskiRadnikRepository.findAllByTermin(termin);

        for (TerminMedicinskiRadnik i: temp){
            osoblje.add(i.getMedicinskiRadnik());
        }

        return osoblje;
    }


}
