// 318211463 Ronen Sivak
package models;

import biuoop.DrawSurface;
import objects.Rectangle;
import physics.Point;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The type Level model.
 */
public class LevelModel extends Models implements Sprite {
    /**
     * The Entity pos tuple.
     */
    private Tuple<Point, Integer> entityPosTuple;
    /**
     * The Entity pos list.
     */
    private java.util.List<Point> entityPosList;
    private int height;
    private int width;
    private int level;
    private DrawSurface d;

    /**
     * Instantiates a new Level model.
     *
     * @param level  the level
     * @param width  the width
     * @param height the height
     */
    public LevelModel(int level, int width, int height) {
        this.height = height;
        this.width = width;
        this.level = level;
    }

    @Override
    public void drawOn(DrawSurface d) {
        this.d = d;
        if (level == 1) {
            level1();
        } else if (level == 2) {
            level2();
        } else if (level == 3) {
            level3();
        } else if (level == 4) {
            level4();
        }
    }

    @Override
    public void timePassed() {

    }

    private void level1() {
        drawRetro();
        if (entityPosTuple == null) {
            createMatrix(50);
        }
        for (int i = 0; i < entityPosTuple.getPoints().size(); i++) {
            Point p = entityPosTuple.getPoints().get(i);
            if (p.getY() < height + entityPosTuple.getPoints().indexOf(p) * 15) {
                p.setY(p.getY() + entityPosTuple.getIntegers().get(i));
            } else {
                int x = 4 + randomInt(8);
                p.setX(randomInt(width));
                p.setY(-randomInt(height / 2));
                entityPosTuple.getIntegers().set(entityPosTuple.getPoints().indexOf(p), x);
            }
            d.setColor(new Color(238, 188, 255));
            for (int j = 0; j < entityPosTuple.getIntegers().get(i); j++) {
                d.drawText((int) p.getX(), (int) p.getY() - j * 15, "|", 10);
            }
        }
    }

    private void level2() {
        if (entityPosTuple == null) {
            createClouds(5, 0);
        }
        if (entityPosList == null) {
            createBirds(8);
        }

        d.setColor(new Color(21, 162, 211));
        d.fillRectangle(0, 0, width, height);

        d.setColor(new Color(253, 252, 252));
        d.drawCircle(100, 100, 55);
        d.drawCircle(100, 100, 53);
        d.fillCircle(100, 100, 45);
        d.setColor(new Color(250, 248, 235));
        d.fillCircle(100, 100, 40);
        d.setColor(new Color(253, 250, 225));
        d.fillCircle(100, 100, 35);


        for (int i = 0; i < Math.max(entityPosTuple.getPoints().size(), entityPosList.size()); i++) {
            if (i < entityPosTuple.getPoints().size()) {
                Point p = entityPosTuple.getPoints().get(i);
                if (p.getX() < width + entityPosTuple.getPoints().indexOf(p) * 10) {
                    p.setX(p.getX() + 1);
                } else {
                    int r = 5 + randomInt(15);
                    p.setX(-r * 10);
                    p.setY(randomInt(height - 200) + 100);
                    entityPosTuple.getIntegers().set(entityPosTuple.getPoints().indexOf(p), r);
                }
                drawClouds((int) p.getX(), (int) p.getY(),
                        entityPosTuple.getIntegers().get(entityPosTuple.getPoints().indexOf(p)),
                        new Color(254, 253, 250));
            }
            if (i < entityPosList.size()) {
                Point p = entityPosList.get(i);
                if (p.getX() < width + 15) {
                    p.setX(p.getX() + 2);
                } else {
                    p.setX(-15);
                    p.setY(randomInt(height - 200) + 100);
                }
                drawBird((int) p.getX(), (int) p.getY(), (int) p.getX());
            }
        }
        drawSuperMan(1, 3, 100, 300);
        drawTails(1, 3, 640, 100);
    }

    private void level3() {
        d.setColor(new Color(107, 236, 255));
        d.fillRectangle(0, 0, width, height);
        if (entityPosTuple == null) {
            createBubbles(15);
        }
        drawGreenFlower(2, 4, 580, 100);
        drawYellowFlower(2, 5, 300, 145);
        drawPinkFlower(2, 5, 200, 160);

        for (int i = 0; i < entityPosTuple.getPoints().size(); i++) {
            if (i < entityPosTuple.getPoints().size()) {
                Point p = entityPosTuple.getPoints().get(i);
                if (p.getY() > -entityPosTuple.getIntegers().get(i)) {
                    p.setY(p.getY() - (25 / entityPosTuple.getIntegers().get(i)));
                } else {
                    int r = 10 + randomInt(15);
                    p.setX(randomInt(width));
                    p.setY(height - randomInt(100));
                    entityPosTuple.getIntegers().set(entityPosTuple.getPoints().indexOf(p), r);
                }
                d.setColor(new Color(231, 254, 255));
                d.drawCircle((int) p.getX(), (int) (p.getY()), entityPosTuple.getIntegers().get(i));
                d.setColor(new Color(238, 188, 255));
                d.drawCircle((int) p.getX(), (int) (p.getY()), entityPosTuple.getIntegers().get(i) - 1);
                d.setColor(new Color(200, 188, 255));
                d.drawCircle((int) p.getX(), (int) (p.getY()), entityPosTuple.getIntegers().get(i) - 2);
                d.setColor(new Color(188, 247, 255));
                d.drawCircle((int) p.getX(), (int) (p.getY()), entityPosTuple.getIntegers().get(i) - 3);
                d.setColor(new Color(188, 255, 199));
                d.drawCircle((int) p.getX(), (int) (p.getY()), entityPosTuple.getIntegers().get(i) - 4);
            }
        }
        d.setColor(new Color(248, 223, 190));
        d.fillRectangle(0, 450, width, 150);
        d.setColor(new Color(93, 93, 93));
        d.fillRectangle(0, 550, width, 50);
        for (int i = 0; i < 5; i++) {
            d.fillRectangle(630 + 5 * i, 525 - 10 * i, 80 - 10 * i, 25);
            d.fillRectangle(90 + 5 * i, 525 - 10 * i, 80 - 10 * i, 25);
            if (i % 2 == 0) {
                d.fillRectangle(360 + 5 * i, 525 - 10 * i, 80 - 10 * i, 15);
            }
        }
        drawPineapple(2, 6, 600, 252);
        drawSquidRock(2, 5, 330, 250);
        drawPatrickRock(2, 6, 50, 375);
        drawSpongePatrick(1, 3, 500, 450);
    }

    private void level4() {
        d.setColor(new Color(135, 206, 250));
        d.fillRectangle(0, 0, width, height);
        d.setColor(new Color(231, 227, 216));
        d.fillCircle(550, 150, 60);
        d.setColor(new Color(236, 232, 208));
        d.fillCircle(550, 150, 50);
        if (entityPosList == null) {
            createNimbus();
        }
        if (entityPosTuple == null) {
            createRocks(50);
        }
        for (int i = 0; i < entityPosList.size(); i++) {
            Point p = entityPosList.get(i);
            if (p.getX() < width + 300) {
                p.setX(p.getX() + 2);
            } else {
                p.setX(-100);
                p.setY(randomInt(200) + 40);
            }
            d.setColor(new Color(255, 225, 27));
            d.fillRectangle((int) (-250 + p.getX()), (int) (p.getY() + 10), 300, 10);
            d.fillCircle((int) (-250 + p.getX()), (int) (p.getY() + 15), 5);
            drawNimbus(1, 2, (int) p.getX(), (int) p.getY());
        }
        drawDessert(4, 16, 0, 160);
        for (int i = 0; i < entityPosTuple.getPoints().size(); i++) {
            Point p = entityPosTuple.getPoints().get(i);
            if (p.getY() > 500) {
                p.setY(p.getY() - 4 / entityPosTuple.getIntegers().get(i));
            } else {
                int r = 2 + randomInt(2);
                if (p.getX() < 350) {
                    p.setX(200 + randomInt(100));
                } else {
                    p.setX(500 + randomInt(100));
                }
                p.setY(height + r);
                entityPosTuple.getIntegers().set(entityPosTuple.getPoints().indexOf(p), r);
            }
            d.setColor(new Color(154, 50, 50));
            d.fillRectangle((int) p.getX(), (int) p.getY(), entityPosTuple.getIntegers().get(i),
                    entityPosTuple.getIntegers().get(i));
            d.setColor(new Color(0, 0, 0));
            d.drawRectangle((int) p.getX(), (int) p.getY(), entityPosTuple.getIntegers().get(i),
                    entityPosTuple.getIntegers().get(i));
        }
        drawGoku(1, 2, 200, 510);
        drawVegeta(1, 2, 500, 510);
    }

    private void drawRetro() {
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File("src/sprites/retro.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        int blockWidth = 4;
        int blockHeight = 10;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Rectangle rect = new Rectangle(j * blockWidth, i * blockHeight,
                        blockWidth, blockHeight);
                if (c == '&') {
                    d.setColor(new Color(83, 1, 115));
                } else if (c == 'Y' || c == '*') {
                    d.setColor(new Color(245, 228, 41));
                } else if (c == 'U') {
                    d.setColor(new Color(252, 176, 62));
                } else if (c == 'O') {
                    d.setColor(new Color(220, 71, 231));
                } else if (c == '%') {
                    d.setColor(new Color(95, 0, 124));
                } else if (c == '#') {
                    d.setColor(new Color(118, 0, 124));
                } else if (c == '/') {
                    d.setColor(new Color(186, 17, 215));
                } else if (c == '@') {
                    d.setColor(new Color(0, 0, 0));
                } else if (c == '(') {
                    d.setColor(new Color(131, 2, 138));
                } else if (c == 'G') {
                    d.setColor(new Color(255, 117, 60));
                } else if (c == 'S') {
                    d.setColor(new Color(238, 188, 255));
                } else {
                    continue;
                }
                d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                        (int) rect.getWidth(), (int) rect.getHeight());
            }
        }
    }

    private void drawVegeta(int blockWidth, int blockHeight, int x, int y) {
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File("src/sprites/vegeta.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Rectangle rect = new Rectangle(x + j * blockWidth, y + i * blockHeight,
                        blockWidth, blockHeight);
                if (c == ' ') {
                    d.setColor(new Color(255, 255, 255));
                } else if (c == ',') {
                    d.setColor(new Color(243, 209, 81));
                } else if (c == '(') {
                    d.setColor(new Color(44, 98, 168));
                } else if (c == '.') {
                    d.setColor(new Color(241, 207, 201));
                } else if (c == '#') {
                    d.setColor(new Color(28, 119, 7));
                } else if (c == '@') {
                    d.setColor(new Color(0, 0, 0));
                } else {
                    continue;
                }
                d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                        (int) rect.getWidth(), (int) rect.getHeight());
            }
        }
    }

    private void drawGoku(int blockWidth, int blockHeight, int x, int y) {
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File("src/sprites/goku.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Rectangle rect = new Rectangle(x + j * blockWidth, y + i * blockHeight,
                        blockWidth, blockHeight);
                if (c == '.') {
                    d.setColor(new Color(243, 209, 81));
                } else if (c == '@') {
                    d.setColor(new Color(0, 0, 0));
                } else if (c == '0') {
                    d.setColor(new Color(241, 207, 201));
                } else if (c == 'W') {
                    d.setColor(new Color(225, 225, 225));
                } else if (c == 'B') {
                    d.setColor(new Color(3, 217, 243));
                } else if (c == 'R') {
                    d.setColor(new Color(145, 15, 15));
                } else if (c == ')') {
                    d.setColor(new Color(215, 39, 39));
                } else if (c == '/') {
                    d.setColor(new Color(218, 78, 36));
                } else if (c == '(') {
                    d.setColor(new Color(44, 98, 168));
                } else {
                    continue;
                }
                d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                        (int) rect.getWidth(), (int) rect.getHeight());
            }
        }
    }

    private void drawNimbus(int blockWidth, int blockHeight, int x, int y) {
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File("src/sprites/nimbus.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Rectangle rect = new Rectangle(x + j * blockWidth, y + i * blockHeight,
                        blockWidth, blockHeight);
                if (c == '%' || c == '#') {
                    d.setColor(new Color(197, 156, 22));
                } else if (c == '.') {
                    d.setColor(new Color(255, 225, 27));
                } else if (c == '*' || c == ',' || c == '(' || c == '/') {
                    d.setColor(new Color(255, 175, 11));
                } else {
                    continue;
                }
                d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                        (int) rect.getWidth(), (int) rect.getHeight());
            }
        }
    }

    private void drawDessert(int blockWidth, int blockHeight, int x, int y) {
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File("src/sprites/dessert.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Rectangle rect = new Rectangle(x + j * blockWidth, y + i * blockHeight,
                        blockWidth, blockHeight);
                if (c == '(') {
                    d.setColor(new Color(243, 143, 81));
                } else if (c == '0') {
                    d.setColor(new Color(178, 69, 12));
                } else if (c == ')') {
                    d.setColor(new Color(138, 84, 68));
                } else if (c == '#') {
                    d.setColor(new Color(154, 50, 50));
                } else if (c == '%') {
                    d.setColor(new Color(91, 1, 1));
                } else if (c == 'R') {
                    d.setColor(new Color(213, 110, 110));
                } else if (c == 'P') {
                    d.setColor(new Color(229, 93, 93));
                } else {
                    continue;
                }
                d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                        (int) rect.getWidth(), (int) rect.getHeight());
            }
        }
    }

    private void drawSuperMan(int blockWidth, int blockHeight, int x, int y) {
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File("src/sprites/superman.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Rectangle rect = new Rectangle(x + j * blockWidth, y + i * blockHeight,
                        blockWidth, blockHeight);
                if (c == '@') {
                    d.setColor(new Color(0, 0, 0));
                } else if (c == '/') {
                    d.setColor(new Color(20, 241, 234));
                } else if (c == '(') {
                    d.setColor(new Color(25, 135, 225));
                } else if (c == '#') {
                    d.setColor(new Color(145, 15, 15));
                } else if (c == ',') {
                    d.setColor(new Color(255, 218, 79));
                } else if (c == ')') {
                    d.setColor(new Color(215, 39, 39));
                } else if (c == '.') {
                    d.setColor(new Color(255, 219, 172));
                } else if (c == 'B') {
                    d.setColor(new Color(16, 35, 155));
                } else if (c == 'W') {
                    d.setColor(new Color(255, 255, 255));
                } else {
                    continue;
                }
                d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                        (int) rect.getWidth(), (int) rect.getHeight());
            }
        }
    }

    private void drawTails(int blockWidth, int blockHeight, int x, int y) {
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File("src/sprites/tails.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Rectangle rect = new Rectangle(x + j * blockWidth, y + i * blockHeight,
                        blockWidth, blockHeight);
                if (c == '@') {
                    d.setColor(new Color(0, 0, 0));
                } else if (c == 'W') {
                    d.setColor(new Color(255, 255, 255));
                } else if (c == 'B') {
                    d.setColor(new Color(0, 212, 255));
                } else if (c == '#') {
                    d.setColor(new Color(145, 15, 15));
                } else if (c == '*') {
                    d.setColor(new Color(255, 146, 79));
                } else if (c == '(' || c == '/') {
                    d.setColor(new Color(194, 104, 48));
                } else {
                    continue;
                }
                d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                        (int) rect.getWidth(), (int) rect.getHeight());
            }
        }
    }

    private void drawPineapple(int blockWidth, int blockHeight, int x, int y) {
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File("src/sprites/pineapple.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Rectangle rect = new Rectangle(x + j * blockWidth, y + i * blockHeight,
                        blockWidth, blockHeight);
                if (c == '@') {
                    d.setColor(new Color(0, 0, 0));
                } else if (c == 'G') {
                    d.setColor(new Color(104, 178, 84));
                } else if (c == '#') {
                    d.setColor(new Color(47, 117, 45));
                } else if (c == 'B') {
                    d.setColor(new Color(41, 154, 203));
                } else if (c == ',' || c == '*') {
                    d.setColor(new Color(77, 250, 255));
                } else if (c == '/') {
                    d.setColor(new Color(229, 150, 51));
                } else if (c == 'P') {
                    d.setColor(new Color(94, 20, 169));
                } else if (c == 'Y') {
                    d.setColor(new Color(255, 235, 28));
                } else {
                    continue;
                }
                d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                        (int) rect.getWidth(), (int) rect.getHeight());
            }
        }
    }

    /**
     * Draw squid rock.
     *
     * @param blockWidth  the block width
     * @param blockHeight the block height
     * @param x           the x
     * @param y           the y
     */
    public void drawSquidRock(int blockWidth, int blockHeight, int x, int y) {
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File("src/sprites/squidrock.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Rectangle rect = new Rectangle(x + j * blockWidth, y + i * blockHeight,
                        blockWidth, blockHeight);
                if (c == '@') {
                    d.setColor(new Color(0, 0, 0));
                } else if (c == '(') {
                    d.setColor(new Color(61, 52, 145));
                } else if (c == '#') {
                    d.setColor(new Color(47, 117, 45));
                } else if (c == '/') {
                    d.setColor(new Color(41, 154, 203));
                } else if (c == ',') {
                    d.setColor(new Color(129, 245, 250));
                } else if (c == '%') {
                    d.setColor(new Color(140, 61, 19));
                } else {
                    continue;
                }
                d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                        (int) rect.getWidth(), (int) rect.getHeight());
            }
        }
    }

    private void drawPatrickRock(int blockWidth, int blockHeight, int x, int y) {
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File("src/sprites/patrickrock.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Rectangle rect = new Rectangle(x + j * blockWidth, y + i * blockHeight,
                        blockWidth, blockHeight);
                if (c == '@') {
                    d.setColor(new Color(0, 0, 0));
                } else if (c == 'Y') {
                    d.setColor(new Color(255, 233, 92));
                } else if (c == '%') {
                    d.setColor(new Color(110, 47, 13));
                } else {
                    continue;
                }
                d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                        (int) rect.getWidth(), (int) rect.getHeight());
            }
        }
    }

    private void drawSpongePatrick(int blockWidth, int blockHeight, int x, int y) {
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File("src/sprites/spongepatrick.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Rectangle rect = new Rectangle(x + j * blockWidth, y + i * blockHeight,
                        blockWidth, blockHeight);
                if (c == '@') {
                    d.setColor(new Color(0, 0, 0));
                } else if (c == 'W') {
                    d.setColor(new Color(255, 255, 255));
                } else if (c == ',') {
                    d.setColor(new Color(247, 233, 72));
                } else if (c == 'G') {
                    d.setColor(new Color(114, 114, 114));
                } else if (c == 'R') {
                    d.setColor(new Color(154, 35, 35));
                } else if (c == '#') {
                    d.setColor(new Color(155, 84, 0));
                } else if (c == '*') {
                    d.setColor(new Color(255, 128, 139));
                } else if (c == '/') {
                    d.setColor(new Color(224, 118, 127));
                } else if (c == 'P') {
                    d.setColor(new Color(156, 23, 171));
                } else if (c == '(') {
                    d.setColor(new Color(49, 187, 15));
                } else {
                    continue;
                }
                d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                        (int) rect.getWidth(), (int) rect.getHeight());
            }
        }
    }

    private void drawPinkFlower(int blockWidth, int blockHeight, int x, int y) {
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File("src/sprites/pinkflower.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Rectangle rect = new Rectangle(x + j * blockWidth, y + i * blockHeight,
                        blockWidth, blockHeight);
                if (c == '*') {
                    d.setColor(new Color(119, 60, 159));
                } else {
                    continue;
                }
                d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                        (int) rect.getWidth(), (int) rect.getHeight());
            }
        }
    }

    private void drawYellowFlower(int blockWidth, int blockHeight, int x, int y) {
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File("src/sprites/yellowflower.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Rectangle rect = new Rectangle(x + j * blockWidth, y + i * blockHeight,
                        blockWidth, blockHeight);
                if (c == ',') {
                    d.setColor(new Color(250, 225, 79));
                } else {
                    continue;
                }
                d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                        (int) rect.getWidth(), (int) rect.getHeight());
            }
        }
    }

    private void drawGreenFlower(int blockWidth, int blockHeight, int x, int y) {
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File("src/sprites/greenflower.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Rectangle rect = new Rectangle(x + j * blockWidth, y + i * blockHeight,
                        blockWidth, blockHeight);
                if (c == '/') {
                    d.setColor(new Color(127, 250, 79));
                } else {
                    continue;
                }
                d.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                        (int) rect.getWidth(), (int) rect.getHeight());
            }
        }
    }

    private void createClouds(int num, int hight) {
        entityPosTuple = new Tuple<>();
        for (int i = 0; i < num; i++) {
            entityPosTuple.getIntegers().add(5 + randomInt(15));
            entityPosTuple.getPoints().add(new Point(randomInt(width),
                    hight == 0 ? randomInt(height - 200) + 100 : hight));
        }
    }

    private void createBirds(int num) {
        entityPosList = new java.util.ArrayList<>();
        for (int i = 0; i < num; i++) {
            entityPosList.add(new Point(randomInt(width), randomInt(height - 200) + 100));
        }
    }

    private void createMatrix(int num) {
        entityPosTuple = new Tuple<>();
        for (int i = 0; i < num; i++) {
            entityPosTuple.getIntegers().add(4 + randomInt(8));
            entityPosTuple.getPoints().add(new Point(randomInt(width), -randomInt(height / 2)));
        }
    }

    private void createBubbles(int num) {
        entityPosTuple = new Tuple<>();
        for (int i = 0; i < num; i++) {
            entityPosTuple.getIntegers().add(15 + randomInt(10));
            entityPosTuple.getPoints().add(new Point(randomInt(width), randomInt(height)));
        }
    }

    private void createNimbus() {
        entityPosList = new java.util.ArrayList<>();
        entityPosList.add(new Point(0, randomInt(200) + 40));
    }

    /**
     * Create rocks.
     *
     * @param num the num
     */
    public void createRocks(int num) {
        entityPosTuple = new Tuple<>();
        for (int i = 0; i < num; i++) {
            entityPosTuple.getIntegers().add(1 + randomInt(2));
            if (i < num / 2) {
                entityPosTuple.getPoints().add(new Point(200 + randomInt(100), height - randomInt(50)));
            } else {
                entityPosTuple.getPoints().add(new Point(500 + randomInt(100), height - randomInt(50)));
            }
        }
    }


    /**
     * Draw clouds.
     *
     * @param x     the x
     * @param y     the y
     * @param r     the r
     * @param color the color
     */
    public void drawClouds(int x, int y, int r, Color color) {
        d.setColor(Color.BLACK);
        d.drawOval(x, y, r * 10, (int) (r * 2.5));
        d.drawCircle(x + r * 2, y, (int) (1.5 * r));
        d.drawCircle(x + r * 4, y, (int) (2.5 * r));
        d.drawCircle((int) (x + r * 7.5), y - r / 3, (int) (2.5 * r));
        d.setColor(color);
        d.fillOval(x, y, r * 10, (int) (r * 2.5));
        d.fillCircle(x + r * 2, y, (int) (1.5 * r));
        d.fillCircle(x + r * 4, y, (int) (2.5 * r));
        d.fillCircle((int) (x + r * 7.5), y - r / 3, (int) (2.5 * r));
    }

    /**
     * Draw bird.
     *
     * @param x the x
     * @param y the y
     * @param k the k
     */
    public void drawBird(int x, int y, int k) {
        int startY = (k % 9 == 0 || k % 8 == 0 || k % 7 == 0 || k % 6 == 0 || k % 5 == 0) ? y + 4 : y;
        d.setColor(Color.BLACK);
        d.drawLine(x, startY, x + 4, y + 4);
        d.drawLine(x + 4, y + 4, x + 8, y);
        d.drawLine(x + 8, y, x + 12, y + 4);
        d.drawLine(x + 12, y + 4, x + 16, startY);
    }
}
