package app;


import config.AppConfig;
import controllers.ScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import servises.Analyser;
import servises.DBManager;
import servises.Tracer;

public class MainApp extends Application {
    public static final String TITLE = "Cableroutes v.0.9.9 by Zaiets A.Y.";

    @Override
    public void start(Stage stage) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        DBManager dbManager = context.getBean(DBManager.class);
        Tracer tracer = context.getBean(Tracer.class);
        Analyser analyser = context.getBean(Analyser.class);
        ScreenController screenController = context.getBean(ScreenController.class);
        screenController.setStage(stage);
        screenController.setDbManager(dbManager);
        screenController.setAnalyser(analyser);
        screenController.setTracer(tracer);
        Parent parent = FXMLLoader.load(getClass().getResource("/mainView.fxml"));
        Scene scene = new Scene(parent, 600, 480);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}