package com.company.model;

import com.company.controller.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Serhii Boiko on 20.08.2017.
 */
public class GorillaEnemy extends Enemy {
    private static final double EXCITED_SPEED = 0.2;
    private static final double NORMAL_SPEED = 0.1;


    public GorillaEnemy(double x, double y) {
        super(x, y, 1, 3.5, 7);
        setGorillaFrames();
        damage = 0.25;
        speed = NORMAL_SPEED;
    }

    public void setGorillaFrames() {
        List<Integer> gorillaFrame = new ArrayList<Integer>();
        gorillaFrame.clear();

        if (!isAlive()) {
            gorillaFrame.add(1);
            gorillaFrame.add(1);
            gorillaFrame.add(10);
            gorillaFrame.add(13);
            gorillaFrame.add(14);
            gorillaFrame.add(14);
            gorillaFrame.add(16);
            gorillaFrame.add(16);
            gorillaFrame.add(16);
        } else if (targetAchieved) {
            for (int i = 5; i < 13; i++) {
                gorillaFrame.add(i);
            }
        } else if (getDx() == 0) gorillaFrame.add(1);
        else if (getDx() != 0) {
            gorillaFrame.add(0);
            gorillaFrame.add(2);
//            xenomorphFrame.add(2);
        }
        objectFrames = gorillaFrame;
    }

    @Override
    public boolean isExcited(BaseObject o) {
        double dx = x - o.x;
        double dy = y - o.y;
        double destination = Math.sqrt(dx * dx + dy * dy);

        excited = (destination <= visibility)
                && (Math.round(y + 1) == Math.round(o.getY())
                || Math.round(y) == Math.round(o.getY())
                || (Math.round(y - 1) == Math.round(o.getY())));
        return excited;
    }

    @Override
    public void attack(BaseObject o) {
        // need to be fixed
        direction = x > o.getX() ? -1 : 1;

        if (x - 2 > o.getX()) {
            targetAchieved = false;
            moveLeft();
        } else if (x + 2 < o.getX()) {
            targetAchieved = false;
            moveRight();
        } else if (Math.round(y) == Math.round(o.getY())) {
            targetAchieved = true;
            stopMove();
            o.takeDamage(damage); // TODO: 14.08.2017 implement sometime
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
        if (isAlive()) {
            checkHealthVector(controller);
            //joke and delete
//            boolean enemyExcited = false;
//        for (Enemy enemy: controller.getEnemies()) {
//            if (!(enemy instanceof GorillaEnemy) && isExcited(enemy)) {
//                enemyExcited = excited;
//                attack(enemy);
//            }
//        }
//
//            if (!enemyExcited)
            //keep calm or excited
            if (isExcited(controller.getWarrior())) {
                speed = EXCITED_SPEED;
                attack(controller.getWarrior());
            } else {
                speed = NORMAL_SPEED;
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

//            System.out.println("g "+ dx);
            // reFrame if it needed

            if (oldDx != dx) {
                setGorillaFrames();
            }
            oldDx = dx;

            //die
        } else if (!lastFrames) {
//            System.out.println("last Frames begin");
            lastFrames = true;
            frameIterator = 0.3;
            setGorillaFrames();
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
