package sample;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class Point {
    int x;
    int y;
    Rectangle rec;

    public Point(int x, int y, Group group) {
        this(x, y);
        group.getChildren().add(this.rec);
        this.rec.toFront();
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.rec = new Rectangle(x, y, 2, 2);
    }
}