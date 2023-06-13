package Isa.Isa.service;

import Isa.Isa.model.Korisnik;
import Isa.Isa.model.Termin;
import Isa.Isa.model.TerminMedicinskiRadnik;

import java.util.List;
import java.util.Optional;

public interface TerminMedicinskiRadnikService {
    TerminMedicinskiRadnik add(int terminId, int medRadnikId);
    TerminMedicinskiRadnik findById(int id);
    List<TerminMedicinskiRadnik> getAll();

    List<Korisnik> getOsobljeZaTermin(int terminId);
}
