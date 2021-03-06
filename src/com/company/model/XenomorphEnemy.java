package com.company.model;

import com.company.controller.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Serhii Boiko on 13.08.2017.
 */
public class XenomorphEnemy extends Enemy {

    public XenomorphEnemy(double x, double y) {
        super(x, y, 1, 5, 5);
        damage = 0.15;
        speed = 0.1;
        setXenomorphFrames();
    }

    public void setXenomorphFrames() {
        List<Integer> xenomorphFrame = new ArrayList<Integer>();
        xenomorphFrame.clear();

        if (!isAlive()) {
            xenomorphFrame.add(2);
            xenomorphFrame.add(2);
            xenomorphFrame.add(16);
            xenomorphFrame.add(16);
            xenomorphFrame.add(2);
            xenomorphFrame.add(17);
            xenomorphFrame.add(17);
            xenomorphFrame.add(17);
            xenomorphFrame.add(5);
            xenomorphFrame.add(5);
            xenomorphFrame.add(5);
        } else if (targetAchieved) {
            for (int i = 6; i < 12; i++) {
                xenomorphFrame.add(i);
            }
            xenomorphFrame.add(2);
            xenomorphFrame.add(2);
        } else if (getDx() == 0) xenomorphFrame.add(2);
        else if (getDx() != 0) {
            xenomorphFrame.add(0);
            xenomorphFrame.add(1);
//            xenomorphFrame.add(2);
        }
        objectFrames = xenomorphFrame;
    }

    @Override
    public boolean isExcited(BaseObject o) {
        double dx = x - o.x;
        double dy = y - o.y;
        double destination = Math.sqrt(dx * dx + dy * dy);

        excited = (destination <= visibility)
                && Math.round(y) == Math.round(o.getY());
        return excited;
    }

    @Override
    public void attack(BaseObject o) {
        // need to be fixed
        direction = x > o.getX() ? -1 : 1;

        if (x - 1.5 > o.getX()) {
            targetAchieved = false;
            moveLeft();
        } else if (x + 1.5 < o.getX()) {
            targetAchieved = false;
            moveRight();
        } else if (Math.round(y) == Math.round(o.getY())) {
            targetAchieved = true;
//            System.out.println();
            stopMove();
            o.takeDamage(damage); // TODO: 14.08.2017 implement sometime
        }
//        System.out.println("x " + targetAchieved);
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

    @Override
    public void move(Controller controller) {
        if (isAlive()) {
            checkHealthVector(controller);
            //joke and delete
//            boolean enemyExcited = false;
//            for (Enemy enemy: controller.getEnemies()) {
//            if (!(enemy instanceof XenomorphEnemy) && isExcited(enemy)) {
//                enemyExcited = excited;
//                attack(enemy);
//            }
//        }
//
//            if (!enemyExcited)
            //keep calm or excited
            if (isExcited(controller.getWarrior())) {
                attack(controller.getWarrior());
            } else {
                targetAchieved = false;
                Random r = new Random();
                double random = r.nextInt(100);
                if (random < 2) {
                    if ((int) (Math.random() * 2) == 0) {
                        moveLeft();
                    } else moveRight();
                } else if (random < 4) dx = 0;

            }

            // checkBorders
            boolean changeMove = false;
            x = x + dx * 10;
            if (!checkBorders(controller.getField(), controller.getBarrierSystems())
                    || falling(controller.getField(), controller.getBarrierSystems())) {
                x = x - dx;
                if (!excited) {
                    dx = -1 * dx;
                    direction = -1 * direction;
                    changeMove = true;
                }
            }
            if (!changeMove) x = x - dx * 9;
            else {
                x = x + dx * 9;
//            dx = 0;
            }

            // reFrame if it needed
            if (oldDx != dx) setXenomorphFrames();
            oldDx = dx;

            //die
        } else if (!lastFrames) {
//            System.out.println("last Frames begin");
            lastFrames = true;
            frameIterator = 0.3;
            setXenomorphFrames();
        } else if (frameIterator <= 0.3) {
            System.out.println("goodbye");
            controller.getEnemies().remove(this);
        }
//        else System.out.println("iterator=" + frameIterator);
    }

    @Override
    protected void checkHealthVector(Controller controller) {
        if (healthVector > health) {
            controller.addBlood(x + (direction / 2), y);
        }
        healthVector = health;
    }
}
