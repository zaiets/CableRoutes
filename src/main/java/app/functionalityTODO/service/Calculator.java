package app.functionalityTODO.service;

import app.functionalityTODO.excel.IOExcelForCalculator;
import app.functionalityTODO.properties.PropertiesHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.repository.dao.business.IDao;
import app.repository.entities.business.Journal;

import java.io.File;

import static app.functionalityTODO.excel.utils.ExcelUtils.buildFileName;

@Service
public class Calculator {

    @Autowired
    private PropertiesHolder propertiesHolder;
    //models.excel writer
    @Autowired
    IOExcelForCalculator ioExcelForCalculator;
    @Autowired
    private IDao<Journal> journalDao;

    public Calculator() {
    }

    public boolean calculateAllJournals(String projectName, File targetPath) {
        //List<RouteType> routeTypes = readAvaliableEstimateRouteTypes();
        try {
            String newMessage = propertiesHolder.get("output.suffix.calculatedJournals");
            String fileExtension = propertiesHolder.get("default.excel.type");
            String journalPathName = propertiesHolder.get("output.path");
            File templateFile = new File(propertiesHolder.get("calc.journalTemplateFile"));
            for (Journal journal : journalDao.getAll()) {
                String targetFileName;
                if (targetPath == null || !targetPath.isDirectory()) {
                    targetFileName = buildFileName(journalPathName, null, journal.getCommonKks(), newMessage, fileExtension);
                } else {
                    targetFileName = buildFileName(targetPath.getAbsolutePath(), null, journal.getCommonKks(), newMessage, fileExtension);
                }
                File targetFile = new File(targetFileName);
                ioExcelForCalculator.writeToFileEstimatedJournal(projectName, journal, targetFile, templateFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //no needed now
//	private List<RouteType> readAvaliableEstimateRouteTypes() {
//		File file = new File(propertiesHolder.get("default.calc.routeTypesFile"));
//		return ioExcelForCalculator.parseAvailableEstimateRouteTypes(file);
//	}
}
