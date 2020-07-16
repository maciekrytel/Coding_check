package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;


public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) {

        startStageMethod(primaryStage);



    }

    private void startStageMethod(Stage primaryStage) {
        try {
            StackPane root = new StackPane();
            Scene scene = new Scene(root, Const.PRIMARY_STAGE_WIDTH, Const.PRIMARY_STAGE_HEIGHT);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            Label labelProgramTitle = labelProgramTitleFunction();

            Label labelInfo = new Label();
            labelInfo.getStylesheets().add(getClass().getResource("labelInfoProperties.css").toExternalForm());

            Label labelFilesView = new Label();
            labelFilesView.getStylesheets().add(getClass().getResource("labelFilesViewProperties.css").toExternalForm());

            Button buttonExit = exitButtonFunction();
            Button buttonOpen = openButtonFunction();
            Button buttonSearch = searchButtonFunction(labelInfo, labelFilesView, buttonOpen);

            root.getChildren().add(buttonExit);
            root.getChildren().add(buttonSearch);
            root.getChildren().add(labelProgramTitle);
            root.getChildren().add(labelFilesView);
            root.getChildren().add(labelInfo);
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private Label labelProgramTitleFunction() {
        Label labelProgramTitle = new Label();
        labelProgramTitle.setText(Const.PROGRAM_TITLE);
        labelProgramTitle.setWrapText(true);
        labelProgramTitle.setTextAlignment(TextAlignment.CENTER);
        labelProgramTitle.getStylesheets().add(getClass().getResource("labelProperties.css").toExternalForm());
        return labelProgramTitle;
    }

    private Button exitButtonFunction() {
        Button buttonExit = new Button();
        buttonExit.setText(Const.BUTTON_EXIT_TITLE);
        buttonExit.getStylesheets().add(getClass().getResource("buttonExitProperties.css").toExternalForm());
        buttonExit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                AppMethods.onButtonExitEvent();
            }
        });
        return buttonExit;
    }

    private Button openButtonFunction() {
        Button buttonOpen = new Button();
        buttonOpen.setText(Const.BUTTON_OPEN_TITLE);
        buttonOpen.getStylesheets().add(getClass().getResource("buttonOpenProperties.css").toExternalForm());
        buttonOpen.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                AppMethods.onButtonOpenEvent();
            }
        });
        return buttonOpen;
    }

    private Button searchButtonFunction(Label labelInfo, Label labelFilesView, Button buttonOpen) {
        Button buttonSearch = new Button();
        buttonSearch.setText(Const.BUTTON_SEARCH_TITLE);
        buttonSearch.getStylesheets().add(getClass().getResource("buttonSearchProperties.css").toExternalForm());
        buttonSearch.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                AppMethods.onButtonSearchEvent(labelFilesView, labelInfo);
                Pane pane = new Pane();
                Scene scene = new Scene(pane, Const.RAPORT_WINDOW_WIDTH, Const.RAPORT_WINDOW_HEIGHT);
                scene.getStylesheets().add(getClass().getResource("filesWindowProperties.css").toExternalForm());
                Stage stage = new Stage();
                stage.setTitle(Const.RAPORT_WINDOW_TITLE);
                Label labelRaportWindowTitle = new Label();
                if(AppMethods.getInfoIfNecessaryToCreateRaport() == true){
                    labelRaportWindowTitle.setText(Const.RAPORT_WINDOW_LOG);
                    Button buttonBack = new Button();
                    buttonBack.setText(Const.BUTTON_BACK_TITLE);
                    buttonBack.getStylesheets().add(getClass().getResource("buttonBackProperties.css").toExternalForm());
                    buttonBack.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            stage.close();
                        }
                    });
                    pane.getChildren().add(labelRaportWindowTitle);
                    pane.getChildren().add(buttonOpen);
                    pane.getChildren().add(buttonBack);
                    stage.setScene(scene);
                    stage.show();
                }

            }
        });
        return buttonSearch;
    }

    public static void main(String[] args) {
        launch(args);

    }

}
