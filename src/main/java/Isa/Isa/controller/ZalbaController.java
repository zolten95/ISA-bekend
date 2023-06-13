package Isa.Isa.controller;

import Isa.Isa.model.DTO.*;
import Isa.Isa.model.Korisnik;
import Isa.Isa.model.OdgovorZalba;
import Isa.Isa.model.Zalba;
import Isa.Isa.service.OdgovorService;
import Isa.Isa.service.ZalbaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/zalba")
public class ZalbaController {

    @Autowired
    private ZalbaService zalbaService;

    @Autowired
    private OdgovorService odgovorService;

    @PostMapping(path = "/addZalbaCentar")
    public ResponseEntity<?> AddZalbaCentar(@RequestBody ZalbaCentarDTO zalbaCentarDTO) {
        if (zalbaCentarDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Zalba zalba = zalbaService.addZalbaCentar(zalbaCentarDTO);

        if (zalba == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(zalba, HttpStatus.OK);
    }

    @PostMapping(path = "/addZalbaOsoblje")
    public ResponseEntity<?> AddZalbaOsobolje(@RequestBody ZalbaOsobljeDTO zalbaOsobljeDTO) {
        if (zalbaOsobljeDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Zalba zalba = zalbaService.addZalbaOsoblje(zalbaOsobljeDTO);

        if (zalba == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(zalba, HttpStatus.OK);
    }

    @GetMapping(path = "/getNeodgovorene")
    public ResponseEntity<?> GetNeodgovorene() {
        List<Zalba> zalbe = zalbaService.getAllNeodgovoreni();

        return new ResponseEntity<>(zalbe, HttpStatus.OK);
    }

    @GetMapping(path = "/getOdgovorene")
    public ResponseEntity<?> GetOdgovorene() {
        List<OdgvorenaZalbaDTO> zalbe = zalbaService.getAllOdgovoreni();

        return new ResponseEntity<>(zalbe, HttpStatus.OK);
    }

    @GetMapping(path = "/getNeodgovoreneZalbeKorisnik/{korisnikID}")
    public ResponseEntity<?> GetOdgovoreneZalbeKorisnik(@PathVariable int korisnikID) {
        List<Zalba> zalbe = zalbaService.getAllNeodgovorenePacijent(korisnikID);

        return new ResponseEntity<>(zalbe, HttpStatus.OK);
    }

    @GetMapping(path = "/getOdgovoreneZalbeKorisnik/{korisnikID}")
    public ResponseEntity<?> GetNeodgovoreneZalbeKorisnik(@PathVariable int korisnikID) {
        List<OdgvorenaZalbaDTO> zalbe = zalbaService.getAllOdgovorenePacijent(korisnikID);

        return new ResponseEntity<>(zalbe, HttpStatus.OK);
    }

    @PostMapping(path = "/odgovori/{zalbaID}")
    public ResponseEntity<?> Odgovori(@PathVariable int zalbaID ,@RequestBody OdgovorDTO odgovorDTO){
        OdgovorZalba odgovorZalba = odgovorService.add(odgovorDTO, zalbaID);
        if(odgovorZalba == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<>(odgovorZalba, HttpStatus.OK);

    }
}
