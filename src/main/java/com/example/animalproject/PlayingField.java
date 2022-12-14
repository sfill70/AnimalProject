package com.example.animalproject;

import com.example.animalproject.app.land.UtilAnimal;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.example.animalproject.app.land.IslandSingleton;
import com.example.animalproject.app.land.Cell;

public class PlayingField extends Application {
    /**
     * Здесь толко отрисовка поля и вывод статистики.
     * ни какой логики нет*/

    private static final String title = "Island";
    boolean isMultiThread = Setup.isIS_MULTI_THREAD();

    /**
     * ширина
     */
    private static final int sizeX = Setup.getX();
    /**
     * высота
     */
    private static final int sizeY = Setup.getY();
    private static final IslandSingleton ISLAND_SINGLETON = IslandSingleton.getInstance();
    public static int getSizeX() {
        return sizeX;
    }
    public static int getSizeY() {
        return sizeY;
    }
    public static IslandSingleton getIsland() {
        return ISLAND_SINGLETON;
    }

    public static void main(String[] args) {
        launch();
    }

    private ScrollPane buildGrid() {
        if (isMultiThread) {
            ISLAND_SINGLETON.initializationThread();
        } else {
            ISLAND_SINGLETON.initialization();
        }
        Group panel = new Group();
        for (int y = 0; y != sizeY; y++) {
            for (int x = 0; x != sizeX; x++) {
                Cell rect = this.buildRectangle(x, y, 15, ISLAND_SINGLETON.getArrayCell()[y][x]);
                panel.getChildren().add(rect);
                rect.setOnMouseClicked(this.buildMouseEvent(rect.viewResidents()));
            }
        }
        return new ScrollPane(panel);
    }

    private ScrollPane movement(String text) {
        int number = 0;
        try {
            number = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.INFORMATION);
            error.setTitle("Error");
            error.setHeaderText("wrong actions");
            error.setContentText("Enter number, one (1) clock will be executed");
            error.showAndWait();
            number = 1;
        }
        for (int i = 0; i < number; i++) {
            if (isMultiThread) {
                ISLAND_SINGLETON.movesThread();
                ISLAND_SINGLETON.clearAndReproduce();
            } else {
                ISLAND_SINGLETON.moves();
                ISLAND_SINGLETON.clearAndReproduceThread();
            }
        }
        return rendering();
    }

    private Cell buildRectangle(int x, int y, int size, Cell cell) {
        Cell rect = cell;
        rect.setX(x * size);
        rect.setY(y * size);
        rect.setHeight(size);
        rect.setWidth(size);
        rect.setFill(Color.CORAL);
        rect.setStroke(Color.BLACK);
        return rect;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private EventHandler<MouseEvent> buildMouseEvent(String st) {
        return event -> {
            Cell rect = (Cell) event.getTarget();
            rect.setFill(Color.AQUA);
            this.showAlert(st);
        };
    }

    private void statistic(String st) {
        this.showAlert(st);
    }


    private ScrollPane rendering() {
        Group panel = new Group();
        for (int y = 0; y < ISLAND_SINGLETON.getArrayCell().length; y++) {
            for (int x = 0; x < ISLAND_SINGLETON.getArrayCell()[0].length; x++) {
                Cell rect = ISLAND_SINGLETON.getArrayCell()[y][x];
                rect.setFill(Color.CORAL);
                rect.setStroke(Color.BLACK);
                panel.getChildren().add(rect);
                rect.setOnMouseClicked(this.buildMouseEvent(rect.viewResidents()));
            }
        }
        return new ScrollPane(panel);
    }

    private FlowPane getFlowPane(Node... nodes) {
        FlowPane pane = new FlowPane(nodes);
        pane.setPadding(new Insets(10));
        pane.setVgap(5);
        pane.setHgap(5);
        return pane;
    }

    @Override
    public void start(Stage stage) {
        BorderPane border = new BorderPane();

        TextField textField = new TextField();
        textField.setMaxWidth(60);

        Label labelTopFlowRight = new Label("Введите количество циклов");
        Label labelTopFlowCentre = new Label("Click to view statistics");
        Label labelTopFlowLeft = new Label("Click for restart");
        Label labelTopFlowBottom = new Label("Click to new moves");

        Button buttonRun = new Button("buttonRun");
        buttonRun.setOnMouseClicked(
                event -> border.setCenter(movement(textField.getText())));

        Button restart = new Button("Restart");
        restart.setOnMouseClicked(
                event -> border.setCenter(this.buildGrid())
        );
        Button statistics = new Button("Statistics");
        statistics.setOnMouseClicked(
                event -> statistic(ISLAND_SINGLETON.viewStatistic()));

        FlowPane generalFlowPane = new FlowPane();
        generalFlowPane.setPadding(new Insets(10));
        generalFlowPane.setVgap(5);
        generalFlowPane.setHgap(5);

        generalFlowPane.getChildren().addAll(getFlowPane(labelTopFlowRight, textField, buttonRun),
                getFlowPane(labelTopFlowCentre, statistics), getFlowPane(labelTopFlowLeft, restart));
        border.setTop(generalFlowPane);
        border.setCenter(this.buildGrid());


        stage.setScene(new Scene(border, 1200, 600));
        stage.setTitle(title);
        stage.setResizable(false);
        stage.show();
    }
}
