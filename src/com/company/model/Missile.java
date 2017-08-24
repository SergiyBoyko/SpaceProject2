package com.company.model;

import com.company.controller.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serhii Boiko on 24.08.2017.
 */
public class Missile extends BaseObject {
    //разовый урон
    private double damage;
    //последний цикл кадров
    private boolean lastFrames = false;

    public Missile(double x, double y, double direction) {
        super(x, y, 0.2, 1);
        this.direction = direction;
        this.dx = direction * 0.25;
        damage = 4;
        setMissileFrames();
    }

    public int getMissileFrame() {
        return objectFrame;
    }

    /**
     * Устанавливает нужный массив кадров
     */
    public void setMissileFrames() {
        List<Integer> missileFrame = new ArrayList<Integer>();
        missileFrame.clear();
        if (!isAlive()) {
            for (int i = 13; i < 29; i++) {
                missileFrame.add(i);
            }
        } else {
            for (int i = 0; i < 13; i++) {
                missileFrame.add(i);
            }
        }
        objectFrames = missileFrame;
    }

    @Override
    public void nextFrame() {
        if (frameIterator >= objectFrames.size())
            frameIterator = 0;
//        System.out.println("size="+objectFrames.size() + " i:" + frameIterator);
        objectFrame = objectFrames.get((int) frameIterator);
        frameIterator += 0.4;
//        System.out.println(frameIterator);
    }
    
    @Override
    public void move(Controller controller) {
        if (isAlive()) {
            ArrayList<Enemy> enemies = new ArrayList<Enemy>(controller.getEnemies());
            for (Enemy enemy : enemies) {
                if (enemy.isIntersect(this)) {
                    enemy.takeDamage(damage);
                    this.die();
                }
            }
            x += dx;
            if (!checkBorders(controller.getField(), controller.getBarrierSystems())) {
                die();
            }
        }else if (!lastFrames) {
//            System.out.println("last Frames begin");
            lastFrames = true;
            frameIterator = 0.4;
            setMissileFrames();
        } else if (frameIterator <= 0.4) {
            System.out.println("kaboom");
            controller.getMissiles().remove(this);
        }
//        else System.out.println("iterator=" + frameIterator);
    }
}
