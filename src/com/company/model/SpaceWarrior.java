package com.company.model;

import com.company.controller.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serhii Boiko on 11.08.2017.
 */
public class SpaceWarrior extends BaseObject {
    //вектор движения (-1 влево,+1 вправо,0 стоп)
    private double dx = 0;
    //вектор движения (-1 влево,+1 вправо,0 стоп)
    private double dy = 0;
    // прыжок
    private boolean jumped;
    // на ногах или в воздухе
    private boolean onFloor;
    // координата y перед прыжком
    private double beforeJumpY;
    // выстрел
    private boolean shot;
    // дальность стрельбы
    private double shotRange;

    public double getDirectedShotRange() {
        return shotRange * direction;
    }

    public List<Integer> getWarriorFrames() {
        return objectFrames;
    }

    @Override
    public void nextFrame() {
        if (frameIterator >= objectFrames.size())
            frameIterator = 0;
//        System.out.println("size="+objectFrames.size() + " i:" + frameIterator);
        objectFrame = objectFrames.get((int) frameIterator);
        frameIterator += 1;
//        System.out.println(frameIterator);
    }

    public void shoot(Controller controller) {
        shot = true;
        List<Enemy> enemies = controller.getEnemies();
        for (Enemy enemy : enemies) {
            double dx = x - enemy.getX();
            double dy = y - enemy.getY();
            double destination = Math.sqrt(dx * dx + dy * dy);
            double destination2 = shotRange + enemy.getRadius();
            if (destination < destination2) {
                System.out.println("damage catch");
                enemy.takeDamage(0); // TODO: 14.08.2017 implement sometime
            } else System.out.println("boss shot");
        }
    }

    public int getPlayerFrame() {
        return objectFrame;
    }

    public void setWarriorFrames(List<Integer> warriorFrames) {
        this.objectFrames = warriorFrames;
    }

    //    public void setWarriorFrames() {
//        List<Integer> warriorFrame = new ArrayList<Integer>();
//        warriorFrame.clear();
//        if (getDirection() != 0 && !isOnFloor()) warriorFrame.add(12);
//        else if (getDirection() == 0 && getDx() == 0) warriorFrame.add(20);
//        else if ((getDirection() > 0 || getDirection() < 0) && getDx() == 0)
//            warriorFrame.add(21 + (int) (Math.random() * 2));
//        else if (getDx() < 0 || getDx() > 0) {
//            warriorFrame.add(2);
//            warriorFrame.add(7);
//            warriorFrame.add(12);
//            warriorFrame.add(17);
//        }
//        objectFrames = warriorFrame;
//    }
    public void setWarriorFrames() {
        List<Integer> warriorFrame = new ArrayList<Integer>();
        warriorFrame.clear();
        if (shot) warriorFrame.add(12);
        else if (getDirection() != 0 && !isOnFloor()) warriorFrame.add(11);
        else if (getDirection() == 0 && getDx() == 0) warriorFrame.add(10);
        else if ((getDirection() > 0 || getDirection() < 0) && getDx() == 0)
            warriorFrame.add(10);
        else if (getDx() != 0) {
            for (int i = 0; i < 10; i++) {
                warriorFrame.add(i);
            }
        }
        objectFrames = warriorFrame;
    }

    public boolean isOnFloor() {
        return onFloor;
    }

    public boolean isJumped() {
        return jumped;
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
     * присесть
     */
    public void сrouch() {
//        dx = 0;
//        direction = 0;
    }

    /**
     * Устанавливаем вектор движения вверх
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

    private boolean shotContinue = false;

    @Override
    public void move(Controller controller) {
        checkShotRange(controller.getField());
        // shot interrupt
        if (shotContinue) setWarriorFrames();
        shotContinue = shot;
        if (shot) shot = false;
        // end shot interrupt

        double step = dx;
        onFloor = !falling(controller.getField());
        if (jumped) {
//            System.out.println("jump");
            if (beforeJumpY - 2 < y && !jumpInterrupted(controller.getField())) {
                y += dy;
                step = dx;
            } else jumped = false;
        } else if (!onFloor) {
//            System.out.println("fall");
            y += 0.25;
            step = dx / 2;
        } else {
//            System.out.println("moving");
            step = dx;
        }
        x += step;
//        System.out.println("dx=" + dx);
        if (!checkBorders(controller.getField())) {
            x -= step;
        }
    }

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

    private void checkShotRange(Field field) {
        char[][] stage = field.getStage();
        int y = (int) Math.round(this.y);
        int x = (int) Math.round(this.x);

        char[] barriers = field.getBarriers();
        if (y<0 || y > field.getHeight()-1) return;
        for (int i = 0; i < barriers.length; i++) {
            for (int k = x; k > x + direction * 4 && k >= 0 && k != field.getWidth(); k += direction) {
//                System.out.print("y=" + y + " x=" + k + " " + stage[y][k] + " ");
                if (stage[y][k] == barriers[i]) { // y - 0.2
                    shotRange = Math.abs(x - k) - 1;
                    System.out.println("cut range " + " " + (Math.abs(x - k) - 1));
                    return;
                }
            }
            for (int k = x; k < x + direction * 4 && k >= 0 && k != field.getWidth(); k += direction) {
//                System.out.print("y=" + y + " x=" + k + " " + stage[y][k] + " ");
                if (stage[y][k] == barriers[i]) { // y - 0.2
                    shotRange = Math.abs(x - k) - 1;
                    System.out.println("cut range " + " " + (Math.abs(x - k) - 1));
                    return;
                }
            }
        }
        shotRange = 3;
    }

    public SpaceWarrior(double x, double y) {
        super(x, y, 0.5, 10);
        jumped = false;
        shotRange = 3;
    }

}
