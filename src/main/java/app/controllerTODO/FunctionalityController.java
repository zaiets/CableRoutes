package app.controllerTODO;

import app.dto.models.*;
import app.service.entities.ICableService;
import app.service.functionality.IFunctionalityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/functionality")
public class FunctionalityController {
    static final Logger logger = LoggerFactory.getLogger(FunctionalityController.class);

    @Autowired
    IFunctionalityService functionalityService;
    @Autowired
    ICableService cableService;


    @RequestMapping(value = "/parse/journals", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<JournalDto>> parseNewJournalFiles(@RequestBody List<File> filesList) {
        logger.info("Requested to parse journal files");
        List<JournalDto> journalDtoList = functionalityService.parseNewJournalFiles(filesList);
        if (journalDtoList == null) {
            logger.warn("Unable to parse journal files");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(journalDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/parse/equipments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentDto>> parseNewEquipmentDataFile(@RequestBody File file) {
        logger.info("Requested to parse equipment files");
        List<EquipmentDto> equipmentDtoList = functionalityService.parseNewEquipmentDataFile(file);
        if (equipmentDtoList == null) {
            logger.warn("Unable to parse equipment files");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(equipmentDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/parse/joinpoints", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<JoinPointDto>> parseNewJoinPointDataFile(@RequestBody File file) {
        logger.info("Requested to parse joinpoint files");
        List<JoinPointDto> joinPointDtoList = functionalityService.parseNewJoinPointDataFile(file);
        if (joinPointDtoList == null) {
            logger.warn("Unable to parse joinpoint files");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(joinPointDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/parse/equipments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RouteDto>> parseNewRouteFile(@RequestBody File file) {
        logger.info("Requested to parse route files");
        List<RouteDto> routeDtoList = functionalityService.parseNewRouteFile(file);
        if (routeDtoList == null) {
            logger.warn("Unable to parse route files");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(routeDtoList, HttpStatus.OK);
    }


    @RequestMapping(value = "/trace/cables", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CableDto>> traceCablesAndDefineLengths(@RequestBody @NotNull List<String> cablesKksList) {
        logger.info("Requested to trace cables: {}", cablesKksList);
        List<CableDto> cableDtoList = new ArrayList<>();
        cablesKksList.forEach(kks -> {
            CableDto cable = cableService.read(kks);
            if (cable == null) {
                logger.warn("Unable to find cable in DB: {}", kks);
            } else {
                cableDtoList.add(cable);
            }
        });
        if (cableDtoList.isEmpty()) {
            logger.warn("Unable to trace all received cables");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<CableDto> result = functionalityService.traceCablesAndDefineLengths(cableDtoList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/trace/cables", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CableDto>> traceJournalsAndDefineLengths(@RequestBody @NotNull List<String> journalsKksList) {
        logger.info("Requested to trace journals: {}", journalsKksList);
        List<CableDto> cableDtoList = new ArrayList<>();
        journalsKksList.forEach(kks -> {
            List<CableDto> currentList = cableService.readAllByJournal(kks);
            if (currentList == null || currentList.isEmpty()) {
                logger.warn("Unable to find cable in DB: {}", kks);
            } else {
                cableDtoList.addAll(currentList);
            }
        });
        if (cableDtoList.isEmpty()) {
            logger.warn("Unable to trace all received journals");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<CableDto> result = functionalityService.traceCablesAndDefineLengths(cableDtoList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    //TODO else

}
