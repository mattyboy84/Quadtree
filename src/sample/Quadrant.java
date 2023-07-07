package sample;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class Quadrant {


    Vecc2f position;
    Vecc2f center;

    Quadrant topLeft;
    Quadrant bottomLeft;
    Quadrant bottomRight;
    Quadrant topRight;

    Line top;
    Line bottom;
    Line left;
    Line right;
    //
    ArrayList<Point> points = new ArrayList<>();
    int capacity;
    boolean divided;
    int width, height;
    int scale = 1;

    public Quadrant(int positionX, int positionY, int capacity, int width, int height) {
        this.position = new Vecc2f(positionX, positionY);
        this.capacity = capacity;
        this.divided = false;
        this.width = width;
        this.height = height;
        this.center = new Vecc2f(this.position.x + (width / (2)), this.position.y + (height / (2)));
    }


    public Quadrant(int width, int height, int positionX, int positionY, Group group, int scale, int capacity) {

        this.divided = false;
        this.capacity = capacity;
        this.position = new Vecc2f(positionX, positionY);
        this.width = (width / (scale));
        this.height = (height / (scale));
        this.center = new Vecc2f(this.position.x + this.width, this.position.y + this.height);
        //
        this.left = new Line(positionX, positionY, positionX, (positionY + (height / scale)));
        this.top = new Line(positionX, positionY, positionX + (height / scale), (positionY));
        this.right = new Line(positionX + (width / scale), positionY, positionX + (width / scale), positionY + (height / scale));
        this.bottom = new Line(positionX, positionY + (height / scale), positionX + (width / scale), positionY + (height / scale));
        //
        group.getChildren().addAll(this.left, this.top, this.right, this.bottom);
    }

    public ArrayList<Point> find(Rectangle rectangle) {

        ArrayList<Point> found = new ArrayList<>();

        if (!rectangle.intersects(this.position.x, this.position.y, width, height)) {
            return new ArrayList<>();
        } else {
            for (Point point : points) {
                if (rectangle.contains(point.x,point.y)) {
                    found.add(point);
                }
            }
            if (this.divided) {
                found.addAll(this.topLeft.find(rectangle));
                found.addAll(this.bottomLeft.find(rectangle));
                found.addAll(this.bottomRight.find(rectangle));
                found.addAll(this.topRight.find(rectangle));
            }
        }
        return found;
    }

    public boolean insert(Point p, Group group) {
        if (!contains(p)) {
            return false;
        }
        if (this.points.size() < this.capacity) {
            this.points.add(p);
            return true;
        } else {
            if (!this.divided) {
                this.subdivide(group);
            }
            //
            if (this.topLeft.insert(p, group)) {
                return true;
            } else if (this.bottomLeft.insert(p, group)) {
                return true;
            } else if (this.bottomRight.insert(p, group)) {
                return true;
            } else return this.topRight.insert(p, group);
        }
    }

    private void subdivide(Group group) {
        this.scale = this.scale * 2;
        //
        this.topLeft = new Quadrant(this.width, this.height, (int) (this.position.x), (int) (this.position.y), group, this.scale, this.capacity);
        this.bottomLeft = new Quadrant(this.width, this.height, (int) (this.position.x), (int) (this.position.y + (this.height / this.scale)), group, this.scale, this.capacity);
        this.bottomRight = new Quadrant(this.width, this.height, (int) (this.position.x + (this.width / this.scale)), (int) (this.position.y + (this.height / this.scale)), group, this.scale, this.capacity);
        this.topRight = new Quadrant(this.width, this.height, (int) (this.position.x + (this.width / this.scale)), (int) (this.position.y), group, this.scale, this.capacity);
        this.divided = true;
        //
        //
        //
    }

    public boolean contains(Point p) {
        return (p.x >= this.position.x && p.x <= this.position.x + width && p.y >= this.position.y && p.y <= this.position.y + height);
    }
}