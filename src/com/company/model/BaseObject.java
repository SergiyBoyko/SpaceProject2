package com.company.model;

/**
 * Created by Serhii Boiko on 11.08.2017.
 */

import com.company.controller.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Базовый класс для всех объектов игры.
 */
public abstract class BaseObject {
    // массив кадров
    protected List<Integer> objectFrames = new ArrayList<>();
    // кадр
    protected int objectFrame;
    // индекс кадра
    protected double frameIterator = 0;
    //координаты
    protected double x;
    protected double y;
    //направление (-1 влево,+1 вправо,0 напротив)
    protected double direction = 0;
    //радиус объекта
    protected double radius;
    //очки жизни у объекта
    protected double health;
    //состояние объект - жив ли объект
    private boolean isAlive;

    public BaseObject(double x, double y, double radius, double health) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.health = health;
        this.isAlive = true;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getDirection() {
        return direction;
    }


    public void takeDamage(double damage) {
        if (this.health - damage <= 0) this.die();
        else this.health -= damage;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Двигаем себя на один ход.
     */
    public void move(Controller controller) {
        //do nothing
    }

    /**
     * Проверяем - не вперся ли (x,y) в ограду.
     */
    public boolean checkBorders(Field field) {
        char[][] stage = field.getStage();
        for (int i = 0; i < stage.length; i++) {
            for (int j = 0; j < stage[i].length; j++) {
                // TODO: 13.08.2017 char array needed. part-ready
                char[] barriers = field.getBarriers();
                for (int k = 0; k < barriers.length; k++) {

                    if (stage[i][j] == barriers[k]) {
                        if ((i == Math.round(y)) && (j >= (x) - 0.8 && j <= (x) + 0.8)) {
                            System.err.println("in Wall!!");
//                        System.err.println("v=" + j + ":" + i + " you=" + (x - 1) * 0.05 + ":" + y);
                            System.err.printf("v=%d:%d you=%.2f:%.2f(yRound=%d)\n", j, i, x, y, Math.round(y));
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Проверяем - не падает ли (x,y).
     */
    public boolean falling(Field field) {
        char[][] stage = field.getStage();
        if (stage.length > y + 1 && stage[0].length > x) {//(int) Math.round(x)
            //// TODO: 13.08.2017 check char array
            char[] barriers = field.getBarriers();
            for (int k = 0; k < barriers.length; k++) {
                if (stage[(int) y + 1][(int) Math.round(x + 0.3)] == barriers[k]
                        || stage[(int) y + 1][(int) Math.round(x - 0.3)] == barriers[k]) {
                    return false;
                }
            }
        }
        return true;
    }
//    public void checkBorders(double minx, double maxx, double miny, double maxy) {
//
//        if (x < minx) x = minx;
//        if (x > maxx) x = maxx;
//        if (y < miny) y = miny;
//        if (y > maxy) y = maxy;
//    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void die() {
        isAlive = false;
    }


    public void rise() {
        isAlive = true;
    }

    public void nextFrame() {
    }

    /**
     * Проверяем - пересекаются ли переданный(o) и наш(this) объекты.
     */
    public boolean isIntersect(BaseObject o) {
        double dx = x - o.x;
        double dy = y - o.y;
        double destination = Math.sqrt(dx * dx + dy * dy);
        double destination2 = Math.max(radius, o.radius);
        return destination <= destination2;
    }
}

