package app;


import config.AppConfig;
import controllers.ScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import servises.Analyser;
import servises.Calculator;
import servises.Manager;
import servises.Tracer;

@Configuration
@Import(AppConfig.class)
public class MainApp extends Application {
    public static final String TITLE = "Cableroutes v.0.9.9 by Zaiets A.Y.";

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/mainView.fxml"));
        Scene scene = new Scene(parent, 600, 480);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.setResizable(false);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ScreenController screenController = context.getBean(ScreenController.class);
        screenController.setdBManager(context.getBean(Manager.class));
        screenController.setTracer(context.getBean(Tracer.class));
        screenController.setAnalyser(context.getBean(Analyser.class));
        screenController.setCalculator(context.getBean(Calculator.class));
        screenController.setStage(stage);
        screenController.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}