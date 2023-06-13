package Isa.Isa.repository;

import Isa.Isa.model.Korisnik;
import Isa.Isa.model.enums.TipKorisnika;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {

    Optional<Korisnik> findOneByEmail(String email);

    //dodato
    List<Korisnik> findByTipKorisnika(TipKorisnika tipKorisnika);

}
