package Isa.Isa.service;


import Isa.Isa.model.DTO.OdgovorDTO;
import Isa.Isa.model.OdgovorZalba;

public interface OdgovorService {
    OdgovorZalba add(OdgovorDTO dto, int zalbaID);
}
