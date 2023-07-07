package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
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

    int capacity = 20;

    @Override
    public void start(Stage stage) throws Exception {
        Quadrant quadTree = new Quadrant(0, 0, capacity, width, height);
        //
        int searchwidth=400,searchheight=400;
        Rectangle searchArea=new Rectangle(random.nextInt(width-searchwidth),
                random.nextInt(height-searchheight),searchwidth,searchheight);
        searchArea.setFill(Color.GREEN);
        group.getChildren().add(searchArea);
        searchArea.toBack();
        //
        //
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.B) {
                Point p = new Point(random.nextInt(width), random.nextInt(height), group);
                quadTree.insert(p, group);
                getPoints(quadTree, searchArea);
            }
        });
        scene.setOnMouseDragged(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                Point p = new Point((int) (mouseEvent.getX()), (int) (mouseEvent.getY()), group);
                quadTree.insert(p, group);
                getPoints(quadTree, searchArea);
            }
        });
        scene.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                Point p = new Point((int) (mouseEvent.getX()), (int) (mouseEvent.getY()), group);
                quadTree.insert(p, group);
                getPoints(quadTree, searchArea);
            }
        });

        for (int i = 0; i < 100_00; i++) {
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