package Isa.Isa.repository;

import Isa.Isa.model.DonacijaKrvi;
import Isa.Isa.model.Korisnik;
import Isa.Isa.model.MedicinskiCentar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonacijKrviRepository extends JpaRepository<DonacijaKrvi, Integer> {

    Page<DonacijaKrvi> findAllByPacijent(Korisnik pacijent, Pageable pageable);
    DonacijaKrvi findFirstByPacijentOrderByDatumDesc(Korisnik pacijent);

    DonacijaKrvi findFirstByPacijentAndMedicinskiCentar(Korisnik pacijent, MedicinskiCentar medicinskiCentar);

}
