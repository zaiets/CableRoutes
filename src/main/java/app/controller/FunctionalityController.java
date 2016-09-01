package app.controller;

import app.dto.models.*;
import app.service.entities.ICableService;
import app.service.entities.IJournalService;
import app.service.functionality.IFunctionalityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @Autowired
    IJournalService journalService;


    @RequestMapping(value = "/parse/journals", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<JournalDto>> parseNewJournalFiles(@RequestBody List<File> filesList) {

        //TODO filereader

        logger.info("Requested to parse journal files");
        List<JournalDto> journalDtoList = functionalityService.parseNewJournalFiles(filesList);
        if (journalDtoList == null) {
            logger.warn("Unable to parse journal files");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(journalDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/parse/equipment", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentDto>> parseNewEquipmentDataFile(@RequestParam("file") MultipartFile fileRef) {
        logger.info("Requested to parse Equipment file");
        List<EquipmentDto> equipmentDtoList = null;
        String tempFileName = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-uuuu - hh-mm-ss"));
        File file;
        try {
            file = File.createTempFile(tempFileName, null);
            try (FileInputStream fileIS = (FileInputStream) fileRef.getInputStream();
                 BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
                logger.info("Reading Equipment file to temp file: {}", file.getName());
                int read;
                while ((read = fileIS.read()) != -1) {
                    bos.write(read);
                }
                bos.flush();
                equipmentDtoList = functionalityService.parseNewEquipmentDataFile(file);
            } catch (Exception ex) {
                logger.warn("Exception while trying to parse Equipment file");
            }
        } catch (Exception ex) {
            logger.warn("Exception while trying to create temp Equipment file");
        }

        if (equipmentDtoList == null) {
            logger.warn("Unable to parse Route files");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(equipmentDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/parse/joinpoint", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<JoinPointDto>> parseNewJoinPointDataFile(@RequestParam("file") MultipartFile fileRef) {
        logger.info("Requested to parse joinpoint file");
        List<JoinPointDto> joinPointDtoList = null;
        String tempFileName = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-uuuu - hh-mm-ss"));
        File file;
        try {
            file = File.createTempFile(tempFileName, null);
            try (FileInputStream fileIS = (FileInputStream) fileRef.getInputStream();
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
                logger.info("Reading joinpoint file to temp file: {}", file.getName());
                int read;
                while ((read = fileIS.read()) != -1) {
                    bos.write(read);
                }
                bos.flush();
                joinPointDtoList = functionalityService.parseNewJoinPointDataFile(file);
            } catch (Exception ex) {
                logger.warn("Exception while trying to parse joinpoint file");
            }
        } catch (Exception ex) {
            logger.warn("Exception while trying to create temp joinpoint file");
        }

        if (joinPointDtoList == null) {
            logger.warn("Unable to parse joinpoint files");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(joinPointDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/parse/route", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RouteDto>> parseNewRouteFile(@RequestParam("file") MultipartFile fileRef) {
        logger.info("Requested to parse Route file");
        List<RouteDto> routeDtoList = null;
        String tempFileName = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-uuuu - hh-mm-ss"));
        File file;
        try {
            file = File.createTempFile(tempFileName, null);
            try (FileInputStream fileIS = (FileInputStream) fileRef.getInputStream();
                 BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
                logger.info("Reading Route file to temp file: {}", file.getName());
                int read;
                while ((read = fileIS.read()) != -1) {
                    bos.write(read);
                }
                bos.flush();
                routeDtoList = functionalityService.parseNewRouteFile(file);
            } catch (Exception ex) {
                logger.warn("Exception while trying to parse Route file");
            }
        } catch (Exception ex) {
            logger.warn("Exception while trying to create temp Route file");
        }

        if (routeDtoList == null) {
            logger.warn("Unable to parse Route files");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(routeDtoList, HttpStatus.OK);
    }


    @RequestMapping(value = "/trace/cable", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/trace/journal", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CableDto>> traceJournalsAndDefineLengths(@RequestBody @NotNull List<String> journalsKksList) {
        logger.info("Requested to trace journals: {}", journalsKksList);
        List<JournalDto> journalDtoList = new ArrayList<>();
        journalsKksList.forEach(kks -> {
            JournalDto journalDto = journalService.read(kks);
            if (journalDto == null) {
                logger.warn("Unable to find journal in DB: {}", kks);
            } else {
                journalDtoList.add(journalDto);
            }
        });
        if (journalDtoList.isEmpty()) {
            logger.warn("Unable to trace all received journals");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<CableDto> result = functionalityService.traceJournalsAndDefineLengths(journalDtoList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/define/pointsbyequips", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentDto>> defineEquipmentsClosestPoints(@RequestBody @NotNull List<EquipmentDto> equipments) {
        logger.info("Requested to define equipment closest points");
        List<EquipmentDto> equipmentDtoList = null;
        try {
            equipmentDtoList = functionalityService.defineEquipmentsClosestPoints(equipments);
        } catch (Exception ex) {
            logger.warn("Unable to define journalDtoList closest points, reason: {}", ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(equipmentDtoList, HttpStatus.OK);

    }

    @RequestMapping(value = "/define/equipsinjournal", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentDto>> defineNewEquipmentsInJournals(@RequestBody @NotNull List<JournalDto> journalDtoList) {
        logger.info("Requested to define equipment closest points");
        List<EquipmentDto> equipmentDtoList = null;
        try {
            equipmentDtoList = functionalityService.defineNewEquipmentsInJournals(journalDtoList);
        } catch (Exception ex) {
            logger.warn("Unable to define equipments in journals, reason: {}", ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(equipmentDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/generate/traced", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<File>> generateJournalInExcelFormatTraced(@RequestBody @NotNull List<String> journalNames) {
        logger.info("Requested to generate files (xlsx) of traced journals: {}", journalNames.toString());
        List<File> files;
        try {
            files = functionalityService.generateJournalInExcelFormatTraced(journalNames);
        } catch (Exception ex) {
            logger.warn("Unable to generate files (xlsx) of traced journals, reason: {}", ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        //TODO file converter and transfer??

        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @RequestMapping(value = "/generate/calculated", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<File>> generateJournalInExcelFormatCalculated(@RequestBody @NotNull List<String> journalNames) {
        logger.info("Requested to generate files (xlsx) of calculated journals: {}", journalNames.toString());
        List<File> files;
        try {
            files = functionalityService.generateJournalInExcelFormatCalculated(journalNames);
        } catch (Exception ex) {
            logger.warn("Unable to generate files (xlsx) of calculated journals, reason: {}", ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        //TODO file converter and transfer??

        return new ResponseEntity<>(files, HttpStatus.OK);
    }



}
