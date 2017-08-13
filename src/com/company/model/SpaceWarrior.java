package com.company.model;

import com.company.controller.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serhii Boiko on 11.08.2017.
 */
public class SpaceWarrior extends BaseObject {

//    private List<Integer> warriorFrames;
//    // кадр
//    private int playerFrame;
//    // индекс кадра
//    private double frameIterator = 0;

    //вектор движения (-1 влево,+1 вправо,0 стоп)
    private double dx = 0;
    //вектор движения (-1 влево,+1 вправо,0 стоп)
    private double dy = 0;
    //направление (-1 влево,+1 вправо,0 напротив)
    private double direction = 0;
    // прыжок
    private boolean jumped;
    // на ногах или в воздухе
    private boolean onFloor;
    // координата y перед прыжком
    private double beforeJumpY;

    public List<Integer> getWarriorFrames() {
        return objectFrames;
    }

    @Override
    public void nextFrame() {
        if (frameIterator >= objectFrames.size())
            frameIterator = 0;
//        System.out.println("size="+objectFrames.size() + " i:" + frameIterator);
        objectFrame = objectFrames.get((int) frameIterator);
        frameIterator += 0.3;
//        System.out.println(frameIterator);
    }

    public int getPlayerFrame() {
        return objectFrame;
    }

    public void setWarriorFrames(List<Integer> warriorFrames) {
        this.objectFrames = warriorFrames;
    }

    public void setWarriorFrames() {
        List<Integer> warriorFrame = new ArrayList<Integer>();
        warriorFrame.clear();
        if (getDirection() != 0 && !isOnFloor()) warriorFrame.add(12);
        else if (getDirection() == 0 && getDx() == 0) warriorFrame.add(20);
        else if ((getDirection() > 0 || getDirection() < 0) && getDx() == 0)
            warriorFrame.add(21 + (int) (Math.random() * 2));
        else if (getDx() < 0 || getDx() > 0) {
            warriorFrame.add(2);
            warriorFrame.add(7);
            warriorFrame.add(12);
            warriorFrame.add(17);
        }
        objectFrames = warriorFrame;
    }

    public boolean isOnFloor() {
        return onFloor;
    }

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

//    private boolean falling(Field field) {
//        char[][] stage = field.getStage();
//        if (stage.length > y + 1 && stage[0].length > x) {//(int) Math.round(x)
//            //// TODO: 13.08.2017 check char array
//            char[] barriers = field.getBarriers();
//            for (int k = 0; k < barriers.length; k++) {
//                if (stage[(int) y + 1][(int) Math.round(x + 0.3)] == barriers[k]
//                        || stage[(int) y + 1][(int) Math.round(x - 0.3)] == barriers[k]) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

    private boolean jumpInterrupted(Field field) {
        char[][] stage = field.getStage();
//        System.out.printf("falling: x=%d, y=%.2f field: x=%d, y=%d",
//          (int) Math.round(x * 0.05), y, stage[0].length, stage.length);
        if (stage.length > y - 1 && stage[0].length > (int) Math.round(x)) {
//            System.out.println("\n under you: " + stage[(int) y + 1][(int) Math.round(x)]);
            // TODO: 13.08.2017 check char array
            // warning was (y - 1)
            char[] barriers = field.getBarriers();
            for (int k = 0; k < barriers.length; k++) {
                if (stage[(int) (y - 0.2)][(int) Math.round(x)] == barriers[k])
                    return true;
            }
        }
        return false;
    }

    public SpaceWarrior(double x, double y) {
        super(x, y, 0.5, 10);
        jumped = false;
//        warriorFrames = new ArrayList<>();
    }

}
