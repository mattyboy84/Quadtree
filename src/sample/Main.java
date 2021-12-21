package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    static Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

    int width = 1080;
    int height = 1080;
    //
    //int width = (int) screenBounds.getHeight();
    //int height = (int) screenBounds.getHeight();
    Group group = new Group();
    Scene scene = new Scene(group, width, height);

    Random random = new Random();

    int capacity = 4;

    @Override
    public void start(Stage stage) throws Exception {
        Quadrant quadTree = new Quadrant(0, 0, capacity, width, height);
        //
        Rectangle searchArea=new Rectangle(random.nextInt(width),random.nextInt(height),400,400);
        searchArea.setFill(Color.GREEN);
        group.getChildren().add(searchArea);
        searchArea.toBack();
        //
        //
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case B:
                    Point p = new Point(random.nextInt(width), random.nextInt(height), group);
                    quadTree.insert(p, group);
                    getPoints(quadTree,searchArea);
                    break;
            }
        });
        scene.setOnMouseDragged(mouseEvent -> {
            switch (mouseEvent.getButton()) {
                case PRIMARY:
                    Point p = new Point((int) (mouseEvent.getX()), (int) (mouseEvent.getY()), group);
                    quadTree.insert(p, group);
                    getPoints(quadTree,searchArea);
                    break;
            }
        });
        scene.setOnMouseClicked(mouseEvent -> {
            switch (mouseEvent.getButton()) {
                case PRIMARY:
                    Point p = new Point((int) (mouseEvent.getX()), (int) (mouseEvent.getY()), group);
                    quadTree.insert(p, group);
                    getPoints(quadTree,searchArea);
                    break;
            }
        });


        for (int i = 0; i < 200; i++) {
            Point p = new Point(random.nextInt(width), random.nextInt(height), group);
            quadTree.insert(p, group);
        }
        getPoints(quadTree,searchArea);

        stage.setScene(scene);
        stage.show();
    }

    private void getPoints(Quadrant quadTree, Rectangle searchArea) {
        ArrayList<Point> found =  quadTree.find(searchArea);

        for (Point point : found) {
            point.rec.setFill(Color.RED);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}