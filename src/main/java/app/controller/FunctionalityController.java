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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.validation.constraints.NotNull;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/functionality")
@MultipartConfig(fileSizeThreshold = 20971520)  //max file size
public class FunctionalityController {
    static final Logger logger = LoggerFactory.getLogger(FunctionalityController.class);

    @Autowired
    IFunctionalityService functionalityService;
    @Autowired
    ICableService cableService;
    @Autowired
    IJournalService journalService;


    @RequestMapping(value = "/parse/journals", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<JournalDto>> parseNewJournalFiles(@RequestParam("uploadedFilesList") List<MultipartFile> filesRef) {
        logger.info("Requested to read journal files");
        List<File> fileList = new ArrayList<>();
        filesRef.forEach(file -> fileList.add(readUploadedToTempFile(file)));
        List<JournalDto> journalDtoList = null;
        if (!fileList.isEmpty()) {
            try {
                logger.debug("Requested to parse journal files");
                journalDtoList = functionalityService.parseNewJournalFiles(fileList);
            } catch (Exception ex) {
                logger.warn("Exception while trying to parse journal files");
            }
        }
        if (journalDtoList == null || journalDtoList.isEmpty()) {
            logger.warn("Unable to parse journal files");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(journalDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/parse/equipments", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentDto>> parseNewEquipmentDataFile(@RequestParam("uploadedFile") MultipartFile fileRef) {
        logger.info("Requested to read Equipment file");
        File file = readUploadedToTempFile(fileRef);
        List<EquipmentDto> equipmentDtoList = null;
        if (file != null) {
            try {
                logger.debug("Requested to parse Equipment file");
                equipmentDtoList = functionalityService.parseNewEquipmentDataFile(file);
            } catch (Exception ex) {
                logger.warn("Exception while trying to parse Equipment file");
            }
        }
        if (equipmentDtoList == null) {
            logger.warn("Unable to parse Equipment files");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(equipmentDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/parse/joinPoints", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<JoinPointDto>> parseNewJoinPointDataFile(@RequestParam("uploadedFile") MultipartFile fileRef) {
        logger.info("Requested to read joinPoint file");
        File file = readUploadedToTempFile(fileRef);
        List<JoinPointDto> joinPointDtoList = null;
        if (file != null) {
            try {
                logger.debug("Requested to parse joinPoint file");
                joinPointDtoList = functionalityService.parseNewJoinPointDataFile(file);
            } catch (Exception ex) {
                logger.warn("Exception while trying to parse joinPoint file");
            }
        }
        if (joinPointDtoList == null) {
            logger.warn("Unable to parse joinPoint files");
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(joinPointDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/parse/route", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RouteDto>> parseNewRouteFile(@RequestParam("uploadedFile") MultipartFile fileRef) {
        logger.info("Requested to read Route file");
        File file = readUploadedToTempFile(fileRef);
        List<RouteDto> routeDtoList = null;
        if (file != null) {
            try {
                logger.debug("Requested to parse Route file");
                routeDtoList = functionalityService.parseNewRouteFile(file);
            } catch (Exception ex) {
                logger.warn("Exception while trying to parse Route file");
            }
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


    private File readUploadedToTempFile(MultipartFile fileRef) {
        String tempFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-uuuu - hh-mm-ss"));
        logger.debug("Trying to create temp file named '{}' for original file {}", tempFileName, fileRef.getOriginalFilename());
        File file = null;
        try {
            file = File.createTempFile(tempFileName, null);
            try (FileInputStream fileIS = (FileInputStream) fileRef.getInputStream();
                 BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
                logger.debug("Reading file to temp file: {}", tempFileName);
                int read;
                while ((read = fileIS.read()) != -1) {
                    bos.write(read);
                }
                bos.flush();
            } catch (Exception ex) {
                logger.warn("Exception while trying to write data into {}", tempFileName);
            }
        } catch (Exception ex) {
            logger.warn("Can't create file {}", tempFileName);
        }
        return file;
    }

}
