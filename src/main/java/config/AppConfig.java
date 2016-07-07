package config;

import controllers.ScreenController;
import excel.IOExcelForAnalyser;
import excel.IOExcelForCalculator;
import excel.IOExcelForTracer;
import excel.ExcelDBService;
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
import servises.utils.CommonUtil;


@Configuration
@ComponentScan(basePackages = "java.*")
@Import(PropertiesHolder.class)
public class AppConfig {

	@Bean
	PropertiesHolder propertiesHolder() {
		return new PropertiesHolder();
	}

	@Bean
	ExcelDBService excelDBService() {
		return new ExcelDBService();
	}

	@Bean
	InMemoryDB inMemoryDB() {
		return InMemoryDB.INSTANCE;
	}

	@Bean
	IDao<Cable> cableDao() {
		return new CableDao();
	}

	@Bean
	IDao<Equipment> equipmentDao() {
		return new EquipmentDao();
	}

	@Bean
	IDao<JoinPoint> joinPointDao() {
		return new JoinPointDao();
	}

	@Bean
	IDao<Journal> journalDao() {
		return new JournalDao();
	}

	@Bean
	IDao<Route> routeDao() {
		return new RouteDao();
	}

	@Bean
	Tracer tracer() {
		return new Tracer();
	}

	@Bean
	Analyser analyser() {
		return new Analyser();
	}

	@Bean
	Calculator calculator() {
		return new Calculator();
	}

	@Bean
	Manager dBManager() {
		return new Manager();
	}

	@Bean
	CommonUtil tracingHelper() {
		return new CommonUtil();
	}

	@Bean
	IOExcelForTracer iOExcelForTracer() {
		return new IOExcelForTracer();
	}

	@Bean
	IOExcelForAnalyser iOExcelForAnalyser() {
		return new IOExcelForAnalyser();
	}
	@Bean
	IOExcelForCalculator iOExcelForCalculator() {
		return new IOExcelForCalculator();
	}

	@Bean
	ScreenController screenController() {
		return new ScreenController();
	}
}

