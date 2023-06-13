package Isa.Isa.service;

import Isa.Isa.model.DTO.UpitnikDTO;
import Isa.Isa.model.Upitnik;

public interface UpitnikService {

    Upitnik dodaj(UpitnikDTO upitnikDTO);

    Upitnik getPoslednjiZaKorisnika(int korisnikID);
}
