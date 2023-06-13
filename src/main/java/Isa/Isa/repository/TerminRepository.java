package Isa.Isa.repository;

import Isa.Isa.model.Korisnik;
import Isa.Isa.model.MedicinskiCentar;
import Isa.Isa.model.Termin;
import Isa.Isa.model.enums.StatusTermina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TerminRepository extends JpaRepository<Termin, Integer> {
    List<Termin> findAllByMedicinskiCentarAndStatusTermina(MedicinskiCentar medicalCenter, StatusTermina isFree);

    List<Termin> findAllByPacijent(Korisnik pacijent);

    List<Termin> findAllByPacijentAndMedicinskiCentar(Korisnik user, MedicinskiCentar medicalCenter);

    Optional<Termin> findById(int termId);

    List<Termin> findAllByPacijentAndDatumTerminaBefore(Korisnik pacijent, Date date);
}
