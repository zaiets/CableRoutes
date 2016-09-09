package app.controller.data;

import app.dto.models.RouteTypeDto;
import app.service.entities.IRouteTypeService;
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
@RequestMapping(value = "/routeType")
public class RouteTypeController {
    @Autowired
    IRouteTypeService service;

    //CREATE ONE ROUTETYPE
    @RequestMapping(value = "/", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid RouteTypeDto entityDto) {
        boolean isCreated = service.create(entityDto);
        if (!isCreated) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //GET ROUTE
    @RequestMapping(value = "/{marker}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RouteTypeDto> read (@PathVariable("marker") String marker) {
        RouteTypeDto currentDto = service.read(marker);
        if (currentDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentDto, HttpStatus.OK);
    }

    //CREATE OR UPDATE ROUTETYPE
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateOrUpdate(@RequestBody @Valid RouteTypeDto entityDto) {
        if (service.createOrUpdate(entityDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //UPDATE ROUTETYPE
    @RequestMapping(value = "/{marker}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable("marker") String marker,
                                            @RequestBody @Valid RouteTypeDto entityDto) {
        if (service.update(marker, entityDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //DELETE ROUTETYPE
    @RequestMapping(value = "/{marker}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("marker") String marker) {
        if (service.delete(marker)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //GET ALL ROUTETYPES
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RouteTypeDto>> listAll() {
        List<RouteTypeDto> entityDtoList = service.getAll();
        if(entityDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entityDtoList, HttpStatus.OK);
    }

}
