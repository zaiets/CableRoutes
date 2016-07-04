package controllers;

import config.AppConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import servises.Analyser;
import servises.DBManager;
import servises.Tracer;

import javax.swing.*;
import java.io.File;
import java.util.List;

import static controllers.Messages.*;

@Service
@Import(AppConfig.class)

public class ScreenController {

    public static final int SMALL_FONT_SIZE = 9;
    public static final int MEDIUM_FONT_SIZE = 15;
    public static final int BIG_FONT_SIZE = 21;
    public static final double MAX_BAR_VALUE = 1.0;
    public static final double MIN_BAR_VALUE = 0.0;
    public static final int TEXTAREA_CHARS_COUNT_MARKER_VALUE = 80;
    @Autowired
    private static Analyser analyser;
    @Autowired
    private static Tracer tracer;
    @Autowired
    private static DBManager dBManager;

    private String projectName;
    private Stage stage;

    //Common elements
    @FXML
    private TextField projectNameArea;
    @FXML
    private Text textArea0, textAreaAnalyser0, textAreaTracer0, textAreaCalculator0;
    @FXML
    private ProgressBar progressBar, progressBarAnalyser, progressBarTracer, progressBarCalculator;
    @FXML
    private Button buttonActions, buttonActionsAnalyser, buttonActionsTracer, buttonActionsCalculator;
    //TODO realise input of path
    private static File targetPath = null;


    //DBinit page
    @FXML
    private Text textArea1, textArea2, textArea3, textArea4;
    @FXML
    private Button buttonChooseFile1, buttonChooseFile2, buttonChooseFile3, buttonChooseFiles4;
    @FXML
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    private static File fileEquipments, fileJoinPoints, fileRoutes = null;
    private static List<File> filesJournals = null;

    //Analyser page
    @FXML
    private Text textAreaAnalyser1, textAreaAnalyser2, textAreaAnalyser3;
    @FXML
    private Button buttonChooseFilesAnalyser1, buttonChooseFileAnalyser2;
    @FXML
    private RadioButton radioAnalyser1, radioAnalyser2, radioAnalyser3;
    @FXML
    private ToggleGroup group1;
    private static File fileEquipmentsForAnalyse = null;
    private static List<File> filesJournalsForAnalyse = null;

    //Tracer page
    @FXML
    private Text textAreaTracer1, textAreaTracer2;
    @FXML
    private Button buttonChooseFilesTracer1;
    @FXML
    private RadioButton radioTracer1, radioTracer2;
    @FXML
    private ToggleGroup group2;

    //Calculator page
    @FXML
    private Text textAreaCalculator1, textAreaCalculator2;
    @FXML
    private Button buttonChooseFilesCalculator1;
    @FXML
    private RadioButton radioCalculator1, radioCalculator2;
    @FXML
    private ToggleGroup group3;
    private static List<File> filesJournalsForCalculating = null;

    @FXML
    protected void setNewProjectName(ActionEvent event) {
        projectName = projectNameArea.getText();
        textArea0.setText(projectName);
        textAreaAnalyser0.setText(projectName);
        textAreaTracer0.setText(projectName);
        textAreaCalculator0.setText(projectName);
    }

    @FXML
    protected void press_ButtonActions(ActionEvent event) {
        progressBar.setProgress(MAX_BAR_VALUE * 0.2);
        try {
            if (checkBox1.isSelected()) {
                if (dBManager.initJoinPoints(projectName, fileJoinPoints)) {
                    textArea1.setText(WORK_DONE_OK.getMessage());
                } else {
                    textArea1.setText(JP_INIT_ERROR.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            textArea1.setText(JP_INIT_ERROR.getMessage());
        }
        try {
            progressBar.setProgress(MAX_BAR_VALUE * 0.4);
            if (checkBox2.isSelected()) {
                if (dBManager.initEquipments(projectName, fileEquipments)) {
                    textArea2.setText(WORK_DONE_OK.getMessage());
                } else {
                    textArea2.setText(EQUIP_INIT_ERROR.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            textArea2.setText(EQUIP_INIT_ERROR.getMessage());
        }

        try {
            progressBar.setProgress(MAX_BAR_VALUE * 0.6);
            if (checkBox3.isSelected()) {
                if (dBManager.initRoutes(projectName, fileRoutes)) {
                    textArea3.setText(WORK_DONE_OK.getMessage());
                } else {
                    textArea3.setText(ROUTES_INIT_ERROR.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            textArea3.setText(ROUTES_INIT_ERROR.getMessage());
        }
        try {
            progressBar.setProgress(MAX_BAR_VALUE * 0.8);
            if (checkBox4.isSelected()) {
                if (dBManager.initJournals(projectName, filesJournals)) {
                    textArea4.setText(WORK_DONE_OK.getMessage());
                } else {
                    textArea4.setText(JOURNALS_INIT_ERROR.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            textArea4.setText(JOURNALS_INIT_ERROR.getMessage());
        }
        progressBar.setProgress(MAX_BAR_VALUE);
        checkBox1.setSelected(false);
        checkBox2.setSelected(false);
        checkBox3.setSelected(false);
        checkBox4.setSelected(false);
    }

    @FXML
    protected void setDefaults() {
        projectName = projectNameArea.getText();
        progressBar.setProgress(MIN_BAR_VALUE);
        progressBarAnalyser.setProgress(MIN_BAR_VALUE);
        progressBarCalculator.setProgress(MIN_BAR_VALUE);
        progressBarTracer.setProgress(MIN_BAR_VALUE);
    }

    @FXML
    protected void setCheckBox1Defaults() {
        if (fileJoinPoints == null) textArea1.setText(CHOOSE_YOUR_FILE.getMessage());
    }

    @FXML
    protected void setCheckBox2Defaults() {
        if (fileEquipments == null) textArea2.setText(CHOOSE_YOUR_FILE.getMessage());
    }

    @FXML
    protected void setCheckBox3Defaults() {
        if (fileRoutes == null) textArea3.setText(CHOOSE_YOUR_FILE.getMessage());
    }

    @FXML
    protected void setCheckBox4Defaults() {
        if (filesJournals == null) {
            textArea4.setFont(Font.font(MEDIUM_FONT_SIZE));
            textArea4.setText(CHOOSE_YOUR_FILE.getMessage());
        }
    }

    @FXML
    protected void chooseJoinPointsFile(ActionEvent event) {
        fileJoinPoints = inputFile(JP_INPUT.getMessage());
        textArea1.setText(defineFileMessage(fileJoinPoints));
    }

    @FXML
    protected void chooseEquipmentsFile(ActionEvent event) {
        fileEquipments = inputFile(EQUIP_INPUT.getMessage());
        textArea2.setText(defineFileMessage(fileEquipments));
    }

    @FXML
    protected void chooseRoutesFile(ActionEvent event) {
        fileRoutes = inputFile(ROUTES_INPUT.getMessage());
        textArea3.setText(defineFileMessage(fileRoutes));
    }

    @FXML
    protected void chooseJournalsFiles(ActionEvent event) {
        filesJournals = inputFiles(JOURNALS_INPUT.getMessage());
        if (filesJournals != null) {
            StringBuilder sb = new StringBuilder();
            filesJournals.forEach(o -> sb.append(o.getAbsolutePath()).append("\n"));
            if (sb.toString().length() > TEXTAREA_CHARS_COUNT_MARKER_VALUE)
                textArea4.setFont(Font.font(SMALL_FONT_SIZE));
            textArea4.setText(sb.toString());
        } else {
            textArea4.setFont(Font.font(MEDIUM_FONT_SIZE));
            textArea4.setText(CHOSEN_DEFAULT_FILE.getMessage());
        }
    }

    //ANALYSER
    @FXML
    protected void analyserActions(ActionEvent event) {
        boolean result = false;
        progressBarAnalyser.setProgress(MIN_BAR_VALUE);
        if (radioAnalyser1.isSelected()) {
            //TODO implement this
            textAreaAnalyser1.setText("NOT IMPLEMENTED FOR NOW!!");
            result = true;
        } else if (radioAnalyser2.isSelected()) {
            if (filesJournalsForAnalyse != null) {
                result = analyser.findNewEquipmentsInJournals(projectName, filesJournalsForAnalyse, targetPath);
            }
        } else if (radioAnalyser3.isSelected()) {
            if (fileEquipmentsForAnalyse != null) {
                result = analyser.defineEquipmentsClosestPoints(projectName, fileEquipmentsForAnalyse, targetPath);
            }
        }
        if (!result) textAreaAnalyser1.setText(ANY_INPUT.getMessage());
        progressBarAnalyser.setProgress(MAX_BAR_VALUE);
        textAreaAnalyser0.setText(WORK_DONE_OK.getMessage());
    }

    @FXML
    protected void setFilesJournalsForAnalyseEquipments(ActionEvent event) {
        filesJournalsForAnalyse = inputFiles(JOURNALS_INPUT.getMessage());
        if (filesJournalsForAnalyse != null) {
            StringBuilder sb = new StringBuilder();
            filesJournalsForAnalyse.forEach(o -> sb.append(o.getAbsolutePath()).append("\n"));
            if (sb.toString().length() > TEXTAREA_CHARS_COUNT_MARKER_VALUE)
                textAreaAnalyser2.setFont(Font.font(SMALL_FONT_SIZE));
            textAreaAnalyser2.setText(sb.toString());
        } else {
            textAreaAnalyser2.setFont(Font.font(MEDIUM_FONT_SIZE));
            textAreaAnalyser2.setText(CHOSEN_DEFAULT_FILE.getMessage());
        }
    }

    @FXML
    protected void setFileEquipmentsForAnalysePoints(ActionEvent event) {
        fileEquipmentsForAnalyse = inputFile(EQUIP_INPUT.getMessage());
        textAreaAnalyser3.setText(defineFileMessage(fileEquipmentsForAnalyse));
    }

    //TRACER
    @FXML
    protected void tracerActions(ActionEvent event) {
        boolean result = false;
        progressBarTracer.setProgress(MIN_BAR_VALUE);
        if (radioTracer1.isSelected()) {
            result = tracer.testModelIsReadyForTracing();
        } else if (radioTracer2.isSelected()) {
            if (targetPath != null) {
                result = tracer.traceJournals(projectName, targetPath);
            }
        }
        if (!result) textAreaAnalyser1.setText(TRACING_ERRORS.getMessage());
        progressBarAnalyser.setProgress(MAX_BAR_VALUE);
        textAreaAnalyser0.setText(WORK_DONE_OK.getMessage());
    }


    @FXML
    protected void setPathForTracer(ActionEvent event) {
        targetPath = inputPath(PATH_INPUT.getMessage());
        if (targetPath != null) {
            textAreaTracer2.setText(defineFileMessage(targetPath));
        } else {
            textAreaTracer2.setText(CHOSEN_DEFAULT_FILE.getMessage());
        }
    }


    //private helpers
    private String defineFileMessage(File file) {
        return file == null ? CHOSEN_DEFAULT_FILE.getMessage() : file.getAbsolutePath();
    }

    private File inputFile(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файлы excel (*.xlsx)", "*.xlsx"));
        fileChooser.setTitle(title);
        return fileChooser.showOpenDialog(stage);
    }

    //TODO rewrite?
    private File inputPath (String title) {
        JFileChooser pathChooser = new JFileChooser();
        pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        pathChooser.setAcceptAllFileFilterUsed(false);
        pathChooser.setDialogTitle(title);
        pathChooser.showOpenDialog(null);
        return pathChooser.getSelectedFile();
    }

    private List<File> inputFiles(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файлы excel (*.xlsx)", "*.xlsx"));
        fileChooser.setTitle(title);
        return fileChooser.showOpenMultipleDialog(stage);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        stage.show();
    }

    public void setdBManager(DBManager dBManager) {
        ScreenController.dBManager = dBManager;
    }
}