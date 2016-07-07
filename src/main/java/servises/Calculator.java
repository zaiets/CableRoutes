package servises;

import excel.IOExcelForCalculator;
import model.db.IDao;
import model.entities.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import properties.PropertiesHolder;

import java.io.File;

import static excel.utils.ExcelUtils.buildFileName;

@Service
@Scope(value = "singleton")
public class Calculator {

    @Autowired
    private PropertiesHolder propertiesHolder;
    //excel writer
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
            String fileExtension = propertiesHolder.get("default.excelFileType");
            String journalPathName = propertiesHolder.get("output.path");
            File templateFile = new File(propertiesHolder.get("default.calc.journalTemplateFile"));
            for (Journal journal : journalDao.getAll()) {
                String targetFileName;
                if (targetPath == null || !targetPath.isDirectory()) {
                    targetFileName = buildFileName(journalPathName, null, journal.getKksName(), newMessage, fileExtension);
                } else {
                    targetFileName = buildFileName(targetPath.getAbsolutePath(), null, journal.getKksName(), newMessage, fileExtension);
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
