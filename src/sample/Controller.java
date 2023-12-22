package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class Controller {

    int[] xD = {10,10,20,50,80,90,90,80,80,20,20,30,50,70};
    int[] yD = {250,230,230,150,230,230,250,250,240,240,250,230,170,230};

    int[] p1D = {0, 1, 2, 3, 4, 5, 6, 7,8,9,10,0};
    int[] p2D = {11,12,13,11};

    int[] x4 = {150,110,110,150,150,160,160,150,150,120,150};
    int[] y4 = {150,200,210,210,250,250,170,170,200,200,160};

    int[] p4 = {0, 1, 2, 3, 4,5,6,7,8,9,10, 0};

    @FXML ColorPicker colorPicker;

    @FXML Canvas canvas;
    GraphicsContext gc;

    public void drawFigure(ActionEvent actionEvent) {
        gc = canvas.getGraphicsContext2D();
        Color color = colorPicker.getValue();

        drawLetterD(color);
        drawNumber4(color);
    }

    private void drawLetterD(Color color) {
        for (int i = 0; i < p1D.length - 1; i++) {
            line(xD[p1D[i]], yD[p1D[i]], xD[p1D[i + 1]], yD[p1D[i + 1]], color);
        }
        for (int i = 0; i < p2D.length - 1; i++) {
            line(xD[p2D[i]], yD[p2D[i]], xD[p2D[i + 1]], yD[p2D[i + 1]], color);
        }
    }

    private void drawNumber4(Color color) {
        for (int i = 0; i < p4.length - 1; i++) {
            line(x4[p4[i]], y4[p4[i]], x4[p4[i + 1]], y4[p4[i + 1]], color);
        }
    }

    private void line(int x1, int y1, int x2, int y2, Color color) {
        int xErr = 0, yErr = 0;
        int dx = x2 - x1;
        int dy = y2 - y1;
        int incX = x2 > x1 ? 1 : x2 < x1 ? -1 : 0;
        int incY = y2 > y1 ? 1 : y2 < y1 ? -1 : 0;
        dx = Math.abs(dx);
        dy = Math.abs(dy);
        int d = dx > dy ? dx : dy;
        int x = x1, y = y1;
        gc.setStroke(color);
        gc.strokeLine(x, y, x, y);
        for (int i = 0; i < d; i++) {
            xErr += dx;
            yErr += dy;
            if (xErr > d) {
                xErr -= d;
                x += incX;
            }
            if (yErr > d) {
                yErr -= d;
                y += incY;
            }
            gc.strokeLine(x, y, x, y);
        }
    }
    private Affine transformation = new Affine();

    public void moveFigure(ActionEvent actionEvent) {
        transformation.appendTranslation(10, 10);
        redrawFigure();
    }

    public void scaleFigure(ActionEvent actionEvent) {
        transformation.appendScale(1.1, 1.1);
        redrawFigure();
    }
    private double calculateCenterX(int[] xCoordinates) {
        double sum = 0;
        for (int x : xCoordinates) {
            sum += x;
        }
        return sum / xCoordinates.length;
    }

    private double calculateCenterY(int[] yCoordinates) {
        double sum = 0;
        for (int y : yCoordinates) {
            sum += y;
        }
        return sum / yCoordinates.length;
    }

    public void rotateFigure(ActionEvent actionEvent) {
        double centerX = calculateCenterX(xD);
        double centerY = calculateCenterY(yD);
        transformation.appendRotation(30, centerX, centerY);
        redrawFigure();
    }

    public void redrawFigure() {

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setTransform(transformation);
        drawFigure(null);
        gc.setTransform(new Affine());
    }
}
