package Isa.Isa.repository;

import Isa.Isa.model.OdgovorZalba;
import Isa.Isa.model.Zalba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdgovorZalbaRepository extends JpaRepository<OdgovorZalba, Integer> {

    OdgovorZalba findFirstByZalba(Zalba zalba);
}
