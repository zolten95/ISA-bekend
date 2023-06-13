package Isa.Isa.service;

import Isa.Isa.model.DTO.MedicinskiCentarDTO;
import Isa.Isa.model.MedicinskiCentar;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface MedicinskiCentarService {

    MedicinskiCentar dodajCentar(MedicinskiCentarDTO medicinskiCentarDTO);

    List<MedicinskiCentar> getAll();
    Page<MedicinskiCentar> getAllSortirano(String field, int pageNo, int pageSize, String sortMode);

    Optional<MedicinskiCentar> findById(int id);

}
