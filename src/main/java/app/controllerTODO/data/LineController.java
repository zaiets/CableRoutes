package app.controllerTODO.data;

import app.dto.models.LineDto;
import app.service.entities.ILineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/line")
public class LineController {
    @Autowired
    ILineService service;

    //CREATE ONE ROUTE
    @RequestMapping(value = "/", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody HttpHeaders headers, @Valid LineDto entityDto) {
        boolean isCreated = service.create(entityDto);
        if (!isCreated) {
            return new ResponseEntity<>(headers, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    //GET ROUTE
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LineDto> read (@RequestBody HttpHeaders headers, @PathVariable("id") int id) {
        LineDto currentDto = service.read(id);
        if (currentDto == null) {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentDto, headers, HttpStatus.OK);
    }

    //CREATE OR UPDATE ROUTE
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateOrUpdate(@RequestBody HttpHeaders headers,
                                                    @RequestBody @Valid LineDto entityDto) {
        if (service.createOrUpdate(entityDto)) {
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    //UPDATE ROUTE
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody HttpHeaders headers, @PathVariable("id") int id,
                                            @RequestBody @Valid LineDto entityDto) {
        if (service.update(id, entityDto)) {
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    //DELETE ROUTE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@RequestBody HttpHeaders headers, @PathVariable("id") int id) {
        if (service.delete(id)) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    //GET ALL ROUTES
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LineDto>> listAll(@RequestBody HttpHeaders headers) {
        List<LineDto> entityDtoList = service.getAll();
        if(entityDtoList.isEmpty()){
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entityDtoList, headers, HttpStatus.OK);
    }

    //else TODO?

}