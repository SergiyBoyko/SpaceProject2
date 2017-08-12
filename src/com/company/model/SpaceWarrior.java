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
    private boolean onFoot;

    public boolean isOnFoot() {
        return onFoot;
    }

    public double getDirection() {
        return direction;
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
        dy = -1;
        onFoot = false;
    }

    /**
     * Устанавливаем вектор движения влево
     */
    public void moveLeft() {
        dx = -1;
        direction = -1;
    }

    /**
     * Устанавливаем вектор движения вправо
     */
    public void moveRight() {
        dx = 1;
        direction = 1;
    }

    @Override
    public void move(Controller controller) {
        if (falling(controller.getField())) {
//        System.out.println(falling(controller.getField()));
            y++;
        }
        x = x + dx;
        if (!checkBorders(controller.getField())) {
            x = x - dx;
        }
//        checkBorders(radius, Space.game.getWidth() - radius + 1, 1, Space.game.getHeight() + 1);
    }

    private boolean falling(Field field) {
        char[][] stage = field.getStage();
//        System.out.printf("falling: x=%d, y=%.2f field: x=%d, y=%d",
//          (int) Math.round(x * 0.05), y, stage[0].length, stage.length);
        if (stage.length > y/2 + 1 && stage[0].length > (int) Math.round(x * 0.05)) {
//            System.out.println("\n under you: " + stage[(int) y + 1][(int) Math.round(x * 0.05)]);
            if (stage[(int) y/2 + 1][(int) Math.round(x * 0.05)] == 'l')
                return false;
        }
        return true;
    }

    public SpaceWarrior(double x, double y) {
        super(x, y, 1, 10);
    }

}
