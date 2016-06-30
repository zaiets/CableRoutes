package config;

import model.db.InMemoryDB;
import model.db.impl.CableDao;
import model.db.impl.EquipmentDao;
import model.db.impl.JoinPointDao;
import model.db.impl.RouteDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import properties.PropertiesHolder;
import repository.IOExcelForAnalyser;
import repository.IOExcelForTracer;
import servises.Analyser;
import servises.DBInitManager;
import servises.Tracer;
import servises.tracerlogic.TracingHelper;


@Configuration
@ComponentScan(basePackages = "java.*")
public class AppConfig {
	@Bean
	PropertiesHolder propertiesHolder () {
		return new PropertiesHolder();
	}
	@Bean
	InMemoryDB getInMemoryDB() {
		return new InMemoryDB();
	}

	@Bean
	CableDao cableDao () {
		return new CableDao();
	}
	@Bean
	EquipmentDao equipmentDao() {
		return new EquipmentDao();
	}
	@Bean
	JoinPointDao joinPoint() {
		return new JoinPointDao();
	}
	@Bean
	RouteDao routeDao() {
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
	DBInitManager dbInitManager () {
		return new DBInitManager();
	}
	@Bean
	TracingHelper tracingHelper () {
		return new TracingHelper();
	}
	@Bean
	IOExcelForTracer ioExcelForTracer () {
		return new IOExcelForTracer();
	}
	@Bean
	IOExcelForAnalyser ioExcelForAnalyser () {
		return new IOExcelForAnalyser();
	}
}

