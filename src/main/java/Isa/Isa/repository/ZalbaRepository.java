package Isa.Isa.repository;

import Isa.Isa.model.Korisnik;
import Isa.Isa.model.MedicinskiCentar;
import Isa.Isa.model.Zalba;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZalbaRepository extends JpaRepository<Zalba, Integer> {
    Zalba findById(int id);
    List<Zalba> findAllByMedicinskiCentar(MedicinskiCentar medicinskiCentar);
    List<Zalba> findAllByPacijent(Korisnik korisnik);
    List<Zalba> findAllByObrisan(boolean obrisan);

}
