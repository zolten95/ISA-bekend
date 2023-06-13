package Isa.Isa.service;

import Isa.Isa.model.DTO.TerminDTO;
import Isa.Isa.model.Termin;

import java.util.List;
import java.util.Optional;

public interface TerminService {
    Termin add(TerminDTO termDTO);
    Optional<Termin> findById(int id);
    List<Termin> getAll();
    List<Termin >getAllForSelektovaniCentar(int medicinskiCentarId);
    Termin rezervisiTermin(int terminId, int korisnikId);
    Termin otkaziTermin(int terminId, int korisnikId);
    List<Termin> getRezervisaniZaKorisnika(int korisnikId);
}
