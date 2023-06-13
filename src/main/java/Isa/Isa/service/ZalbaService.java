package Isa.Isa.service;

import Isa.Isa.model.DTO.OdgvorenaZalbaDTO;
import Isa.Isa.model.DTO.ZalbaCentarDTO;
import Isa.Isa.model.DTO.ZalbaOsobljeDTO;
import Isa.Isa.model.Zalba;

import java.util.List;

public interface ZalbaService {

    Zalba addZalbaCentar(ZalbaCentarDTO zalbaCentarDTO);
    Zalba addZalbaOsoblje(ZalbaOsobljeDTO zalbaOsobljeDTO);

    List<Zalba> getAllNeodgovoreni();

    List<OdgvorenaZalbaDTO> getAllOdgovoreni();

    List<Zalba> getAllNeodgovorenePacijent(int korisnikID);

    List<OdgvorenaZalbaDTO> getAllOdgovorenePacijent(int korisnikID);
}
