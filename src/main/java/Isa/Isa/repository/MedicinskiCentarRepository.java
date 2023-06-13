package Isa.Isa.repository;

import Isa.Isa.model.MedicinskiCentar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicinskiCentarRepository extends JpaRepository<MedicinskiCentar, Integer> {

    Optional<MedicinskiCentar> findById(int medicinskiCentarID);

    List<MedicinskiCentar> findAllByObrisan(boolean obrisan);

    Page<MedicinskiCentar> findAll(Pageable pageable);

}
