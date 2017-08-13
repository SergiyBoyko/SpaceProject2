package com.company.model;

import com.company.controller.Controller;

import java.util.ArrayList;
import java.util.List;

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
        if (x - 1 > o.getX()) {
            moveLeft();
        }
        else if (x + 1 < o.getX()) {
            moveRight();
        }
        else {
            stopMove();
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
            excited = false;
            dx = 0;
        }
        x = x + dx;
        if (!checkBorders(controller.getField()) || falling(controller.getField())) {
            x = x - dx;
            dx = -1 * dx;
        }

//        System.out.println(oldDx + " " + dx);
        if (oldDx != dx) {
            setXenomorphFrames();
            System.out.println("change move " + objectFrames.size());
        }
        oldDx = dx;
    }
}
