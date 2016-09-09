package app.controller.data;

import app.dto.models.JournalDto;
import app.service.entities.IJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/journal")
public class JournalController {
    @Autowired
    IJournalService service;

    //CREATE ONE JOURNAL
    @RequestMapping(value = "/", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody @Valid JournalDto entityDto) {
        boolean isCreated = service.create(entityDto);
        if (!isCreated) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //GET JOURNAL
    @RequestMapping(value = "/{kks}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JournalDto> read (@PathVariable("kks") String kks) {
        JournalDto currentDto = service.read(kks);
        if (currentDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentDto, HttpStatus.OK);
    }

    //CREATE OR UPDATE JOURNAL
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createOrUpdate(@RequestBody @Valid JournalDto entityDto) {
        if (service.createOrUpdate(entityDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //UPDATE JOURNAL
    @RequestMapping(value = "/{kks}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable("kks") String kks,
                                            @RequestBody @Valid JournalDto entityDto) {
        if (service.update(kks, entityDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //DELETE JOURNAL
    @RequestMapping(value = "/{kks}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("kks") String kks) {
        if (service.delete(kks)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //GET ALL JOURNALS
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<JournalDto>> listAll() {
        List<JournalDto> entityDtoList = service.getAll();
        if(entityDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entityDtoList, HttpStatus.OK);
    }
}
