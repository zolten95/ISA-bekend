package Isa.Isa.service;

import Isa.Isa.model.DTO.RegistracijaDTO;
import Isa.Isa.model.Korisnik;

import java.util.List;
import java.util.Optional;

public interface KorisnikService {

    Korisnik Registruj(RegistracijaDTO registracijaDTO);

    Korisnik GetByEmail(String email);

    Optional<Korisnik> findById(int id);

    List<Korisnik> getAll();

    Korisnik getTrenutniKorisnik(String email);

    boolean confirmMail(String email, String confirmToken);

    Korisnik smanjiPenale(Korisnik korisnik);

    List<Korisnik> getMedicinskoOsoblje();

}
