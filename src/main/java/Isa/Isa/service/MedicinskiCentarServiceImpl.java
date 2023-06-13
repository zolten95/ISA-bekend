package Isa.Isa.service;

import Isa.Isa.model.DTO.MedicinskiCentarDTO;
import Isa.Isa.model.MedicinskiCentar;
import Isa.Isa.repository.MedicinskiCentarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicinskiCentarServiceImpl implements MedicinskiCentarService {

    @Autowired
    private MedicinskiCentarRepository medicinskiCentarRepository;

    @Override
    public MedicinskiCentar dodajCentar(MedicinskiCentarDTO medicinskiCentarDTO){
        MedicinskiCentar medicinskiCentar = MedicinskiCentarDTO.konvertuj(medicinskiCentarDTO);

        medicinskiCentar = medicinskiCentarRepository.save(medicinskiCentar);

        return medicinskiCentar;
    }

    @Override
    public List<MedicinskiCentar> getAll(){
        return medicinskiCentarRepository.findAllByObrisan(false);
    }

    @Override
    public Page<MedicinskiCentar> getAllSortirano(String field, int pageNo, int pageSize, String sortMode){
        Pageable paging;
        String s = "";

        if(sortMode.equals("Ascending")) {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, field));
        } else {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, field));
        }

        return medicinskiCentarRepository.findAll(paging);
    }

    @Override
    public Optional<MedicinskiCentar> findById(int id){
        return medicinskiCentarRepository.findById(id);
    }
}
