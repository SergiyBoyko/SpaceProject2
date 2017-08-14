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
        super(x, y, 1, 4, 5);
        setXenomorphFrames();
    }

    public void setXenomorphFrames() {
        List<Integer> xenomorphFrame = new ArrayList<Integer>();
        xenomorphFrame.clear();

        if (targetAchieved) {
            for (int i = 6; i < 12; i++) {
                xenomorphFrame.add(i);
            }
            xenomorphFrame.add(2);
        }
        if (getDx() == 0) xenomorphFrame.add(2);
        else if (getDx() != 0) {
            xenomorphFrame.add(0);
            xenomorphFrame.add(1);
//            xenomorphFrame.add(2);
        }
        objectFrames = xenomorphFrame;
    }

    @Override
    public void attack(BaseObject o) {
        direction = x > o.getX() ? -1 : 1;
        if (x - 1 > o.getX()) {
            targetAchieved = false;
            moveLeft();
        } else if (x + 1 < o.getX()) {
            targetAchieved = false;
            moveRight();
        } else {
            targetAchieved = true;
            stopMove();
            o.takeDamage(0); // TODO: 14.08.2017 implement sometime
        }
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
        if (isExcited(controller.getWarrior())) {
            attack(controller.getWarrior());
        } else {
            Random r = new Random();
            double random = r.nextInt(100);
            if (random < 2) {
                if ((int) (Math.random() * 2) == 0) {
                    moveLeft();
                } else moveRight();
            } else if (random < 4) dx = 0;

        }
        boolean changeMove = false;
        x = x + dx * 10;
        if (!checkBorders(controller.getField()) || falling(controller.getField())) {
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

//        System.out.println(oldDx + " " + dx);
//        System.out.println(targetAchieved);
        if (oldDx != dx) {
            setXenomorphFrames();
//            System.out.println("change move " + dx +  ": " + objectFrames.size());
        }
        oldDx = dx;
    }
}
