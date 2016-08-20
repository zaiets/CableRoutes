package app.service.functionality;

import app.dto.models.*;

import java.io.File;
import java.util.List;

/**
 * Main app functions description
 */
public interface IFunctionalityService {
    /**
     * for initial data parsing (excel files)
     */
    List<JournalDto> parseNewJournalFiles(List<File> filesList);
    List<EquipmentDto> parseNewEquipmentDataFile(File file);
    List<JoinPointDto> parseNewJoinPointDataFile(File file);
    List<RouteDto> parseNewRouteFile(File file);

    /**
     * Main app functions like:
     * tracing of cables, calculating the cables lengths for estimate calculations ect.
     */
    List<CableDto> traceCablesAndDefineLengths(List<CableDto> cables);
    List<CableDto> traceJournalsAndDefineLengths(List<JournalDto> journals);

    /**
     * find closest joinpoints in equipments
     */
    List<EquipmentDto> defineEquipmentsClosestPoints(List<EquipmentDto> equipments);

    /**
     * find all new equipments in journals
     */
    List<EquipmentDto> defineNewEquipmentsInJournals(List<JournalDto> journals);

    /**
     * generate excel files from DB journals(to be able to transfer them to users)
     */
    List<File> generateJournalInExcelFormatTraced(List<String> journalNames);

    /**
     * generate excel files from DB journals (to be able to transfer them to users)
     */
    List<File> generateJournalInExcelFormatCalculated(List<String> journalNames);

}
