package com.company.model;

import com.company.controller.Controller;

/**
 * Created by Serhii Boiko on 11.08.2017.
 */
public class SpaceWarrior extends BaseObject {
    //вектор движения (-1 влево,+1 вправо,0 стоп)
    private double dx = 0;
    //вектор движения (-1 влево,+1 вправо,0 стоп)
    private double dy = 0;
    //направление (-1 влево,+1 вправо,0 напротив)
    private double direction = 0;
    // на ногах или в воздухе
    private boolean jumped;
    private boolean onFloor;

    private double beforeJumpY;

    public boolean isJumped() {
        return jumped;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    /**
     * Возвращает вектор движения
     */
    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    /**
     * Устанавливаем вектор движения стоп
     */
    public void stopMove() {
        dx = 0;
//        direction = 0;
    }

    /**
     * Устанавливаем вектор движения влево
     */
    public void jump() {
        if (jumped || !onFloor) return;
        this.beforeJumpY = y;
        dy = -0.25;
        jumped = true;
        onFloor = false;
    }

    /**
     * Устанавливаем вектор движения влево
     */
    public void moveLeft() {
        dx = -0.2;
        direction = -1;
    }

    /**
     * Устанавливаем вектор движения вправо
     */
    public void moveRight() {
        dx = 0.2;
        direction = 1;
    }

    @Override
    public void move(Controller controller) {
        onFloor = !falling(controller.getField());
        if (jumped) {
//            System.out.println("jump");
            if (beforeJumpY - 2 < y && !jumpInterrupted(controller.getField())) {
                y += dy;
                x += dx;
            } else jumped = false;
        } else if (!onFloor) {
//            System.out.println("fall");
            y += 0.25;
            x += dx / 2;
        } else {
//            System.out.println("moving");
            x = x + dx;
        }
//        System.out.println("dx=" + dx);
        if (!checkBorders(controller.getField())) {
            x = x - dx;
        }
    }

    private boolean falling(Field field) {
        char[][] stage = field.getStage();
        if (stage.length > y + 1 && stage[0].length > x) {//(int) Math.round(x)
            //// TODO: 13.08.2017 check char array 
            if (stage[(int) y + 1][(int) Math.round(x + 0.3)] == 'l' || stage[(int) y + 1][(int) Math.round(x - 0.3)] == 'l'
                    || stage[(int) y + 1][(int) Math.round(x + 0.3)] == 'v' || stage[(int) y + 1][(int) Math.round(x - 0.3)] == 'v') {
                    return false;
            }
        }
        return true;
    }

    private boolean jumpInterrupted(Field field) {
        char[][] stage = field.getStage();
//        System.out.printf("falling: x=%d, y=%.2f field: x=%d, y=%d",
//          (int) Math.round(x * 0.05), y, stage[0].length, stage.length);
        if (stage.length > y - 1 && stage[0].length > (int) Math.round(x)) {
//            System.out.println("\n under you: " + stage[(int) y + 1][(int) Math.round(x)]);
            if (stage[(int) y - 1][(int) Math.round(x)] == 'l'
                    || stage[(int) y - 1][(int) Math.round(x)] == 'v')
                return true;
        }
        return false;
    }

    public SpaceWarrior(double x, double y) {
        super(x, y, 1, 10);
        jumped = false;
    }

}
