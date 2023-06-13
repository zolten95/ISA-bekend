package Isa.Isa.repository;

import Isa.Isa.model.Korisnik;
import Isa.Isa.model.Upitnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UpitnikRepository extends JpaRepository<Upitnik, Integer> {
    Optional<Upitnik> findOneByIdAndObrisan(int id, boolean deleted);

    Upitnik findOneByPacijentAndObrisan(Korisnik korisnik, boolean obrisan);

}
