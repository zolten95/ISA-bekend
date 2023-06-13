package Isa.Isa.service;

import Isa.Isa.model.DonacijaKrvi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DonacijaKrviService {
    Page<DonacijaKrvi> GetAllForPacijent(int pacijentID, Pageable pageable);

    boolean DaLiMozeDaDonira(int korisnikID);
}
