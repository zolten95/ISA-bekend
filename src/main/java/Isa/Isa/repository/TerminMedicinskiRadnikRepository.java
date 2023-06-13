package Isa.Isa.repository;

import Isa.Isa.model.Korisnik;
import Isa.Isa.model.Termin;
import Isa.Isa.model.TerminMedicinskiRadnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TerminMedicinskiRadnikRepository extends JpaRepository<TerminMedicinskiRadnik, Integer> {
    TerminMedicinskiRadnik findByTerminAndMedicinskiRadnik(Termin termin, Korisnik medicinskiRadnik);

    List<TerminMedicinskiRadnik> findAllByTermin(Termin t);
}
