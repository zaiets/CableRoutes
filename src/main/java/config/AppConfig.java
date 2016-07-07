package config;

import controllers.ScreenController;
import excel.IOExcelForAnalyser;
import excel.IOExcelForTracer;
import model.db.ExcelDBService;
import model.db.IDao;
import model.db.InMemoryDB;
import model.db.implInMemory.*;
import model.entities.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import properties.PropertiesHolder;
import servises.Analyser;
import servises.Calculator;
import servises.Manager;
import servises.Tracer;
import servises.utils.HelperUtils;


@Configuration
@ComponentScan(basePackages = "java.*")
@Import(PropertiesHolder.class)
public class AppConfig {

	@Bean
	private PropertiesHolder propertiesHolder() {
		return new PropertiesHolder();
	}

	@Bean
	private ExcelDBService excelDBService() {
		return new ExcelDBService();
	}

	@Bean
	private InMemoryDB inMemoryDB() {
		return InMemoryDB.INSTANCE;
	}

	@Bean
	private IDao<Cable> cableDao() {
		return new CableDao();
	}

	@Bean
	private IDao<Equipment> equipmentDao() {
		return new EquipmentDao();
	}

	@Bean
	private IDao<JoinPoint> joinPointDao() {
		return new JoinPointDao();
	}

	@Bean
	private IDao<Journal> journalDao() {
		return new JournalDao();
	}

	@Bean
	private IDao<Route> routeDao() {
		return new RouteDao();
	}

	@Bean
	private Tracer tracer() {
		return new Tracer();
	}

	@Bean
	private Analyser analyser() {
		return new Analyser();
	}

	@Bean
	private Calculator calculator() {
		return new Calculator();
	}

	@Bean
	private Manager dBManager() {
		return new Manager();
	}

	@Bean
	private HelperUtils tracingHelper() {
		return new HelperUtils();
	}

	@Bean
	private IOExcelForTracer iOExcelForTracer() {
		return new IOExcelForTracer();
	}

	@Bean
	private IOExcelForAnalyser iOExcelForAnalyser() {
		return new IOExcelForAnalyser();
	}

	@Bean
	private ScreenController screenController() {
		return new ScreenController();
	}
}

