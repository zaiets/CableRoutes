package app.service.functionalityTODO.service;

import app.service.functionalityTODO.excel.IOExcelForCalculator;
import app.service.functionalityTODO.properties.PropertiesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.repository.dao.business.IDao;
import app.repository.entities.business.Journal;

import java.io.File;

import static app.service.functionalityTODO.excel.utils.ExcelUtils.buildFileName;

@Service
public class Calculator {

    @Autowired
    private PropertiesManager propertiesManager;
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
            String newMessage = propertiesManager.get("output.suffix.calculatedJournals");
            String fileExtension = propertiesManager.get("default.excel.type");
            String journalPathName = propertiesManager.get("output.path");
            File templateFile = new File(propertiesManager.get("calc.journalTemplateFile"));
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
