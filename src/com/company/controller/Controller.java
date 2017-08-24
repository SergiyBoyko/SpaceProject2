package com.company.controller;

import com.company.View;
import com.company.model.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Serhii Boiko on 11.08.2017.
 */
public class Controller extends KeyAdapter implements MouseListener {
    private View view;
    private Field field;

    private List<Enemy> enemies = new ArrayList<Enemy>();
    private List<Missile> missiles = new ArrayList<Missile>();
    private Map<Double, Double> bloodEffects = new HashMap<Double, Double>();
    private List<BarrierSystem> barrierSystems;

    private SpaceWarrior warrior;
    private boolean gameOver;
    private boolean levelComplete;
    private int level;

    public int getLevel() {
        return level;
    }

    public boolean isLevelComplete() {
        return levelComplete;
    }

    public void levelComplete() {
        this.levelComplete = true;
    }

    public void gameOver() {
        gameOver = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Map<Double, Double> getBloodEffects() {
        Map<Double, Double> temp = new HashMap<Double, Double>(bloodEffects);
        bloodEffects.clear();
//        System.out.println("size controller b=" + temp.size());
        return temp;
    }

    public void addBlood(double x, double y) {
        bloodEffects.put(x, y);
    }

    public void addMissile (double x, double y, double direction) {
        missiles.add(new Missile(x, y, direction));
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<BarrierSystem> getBarrierSystems() {
        return barrierSystems;
    }

    public View getView() {
        return view;
    }

    public Field getField() {
        return field;
    }

    public char[][] getStage() {
        return field.getStage();
    }

    public SpaceWarrior getWarrior() {
        return warrior;
    }

    private Queue<KeyEvent> keyEvents = new ArrayBlockingQueue<KeyEvent>(50);

    public Controller() {
        view = new View(this);
//        field = new Field(0);
        field = new Field(1);
        level = 1;
        barrierSystems = field.getBarrierSystems();
        warrior = new SpaceWarrior(4, 12); // x, y
    }
    private boolean ladder;

    public boolean isLadder() {
        return ladder;
    }
    private void animationLadder() {
        ladder = false;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        sleep(250);
                        ladder = !ladder;
                    }
                } catch (Exception e) {
                    System.err.println("animation interrupted");
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void run() {
        enemies = field.getEnemies();
        gameOver = false;
        levelComplete = false;
        animationLadder();
//        warrior.setWarriorFrames();

//        boolean onFloor = warrior.isOnFloor();
//        int dx = 1;

        while (!levelComplete) {
            while (!gameOver) {
                reExitSystem();
//            if (onFloor != warrior.isOnFloor()) {
//                warrior.setWarriorFrames();
//                onFloor = warrior.isOnFloor();

                view.repaint();
                sleep(60);
                moveAllItems();
            }
            view.repaint();
            reExitSystem();
        }
        while (true) {
            view.repaint();
            if (hasKeyEvents()) System.exit(0);
        }
    }

    private void reExitSystem() {
        if (this.hasKeyEvents()) {
            if (this.getEventFromTop().getKeyCode() == KeyEvent.VK_ESCAPE) {
                sleep(500);
                if (this.hasKeyEvents() && this.getEventFromTop().getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                } else {
                    restart();
//                        warrior.setWarriorFrames();
                }
            }
        }
    }

    private void restart() {
        warrior = new SpaceWarrior(4, 12);
        enemies = field.getEnemies();
        missiles.clear();
        barrierSystems = field.getBarrierSystems();
        levelComplete = false;
        gameOver = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.print(e.getKeyChar());
        keyEvents.add(e);
        if (e.getKeyCode() == KeyEvent.VK_UP) {
//            warrior.jump();
            warrior.up(field);
            warrior.setWarriorFrames();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            warrior.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            warrior.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            warrior.crouch(true);
//            warrior.setDirection(0);
//            warrior.stopMove();
            warrior.setWarriorFrames();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            warrior.shoot(this);
            warrior.setWarriorFrames();
        } else if (e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_2) {
            warrior.rearm(Integer.parseInt(String.valueOf(e.getKeyChar())));
        }
//        warrior.setWarriorFrames();

    }

    @Override
    public void keyTyped(KeyEvent e) {
        //do nothing
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        System.out.println(e.getKeyChar());
        if (e.getKeyCode() == KeyEvent.VK_LEFT
                || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            warrior.stopMove();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            warrior.crouch(false);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            warrior.climb(false);
        }
//        warrior.setWarriorFrames();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //do nothing
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("mousePressed" + e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        System.out.println("MouseEvent" + e);
        //do nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        System.out.println("mouseEntered" + e);
        //do nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        System.out.println("MouseEvent" + e);
        //do nothing
    }


    /**
     * Двигаем все объекты игры
     */
    public void moveAllItems() {
        for (BaseObject object : getAllItems()) {
            object.nextFrame(); // if first round < 0.5 (current 0.3)
            object.move(this);
        }
    }

//    public List<Integer> setWarriorFrame() {
//        List<Integer> warriorFrame = new ArrayList<Integer>();
//        warriorFrame.clear();
//        if (warrior.getDirection() != 0 && !warrior.isOnFloor()) warriorFrame.add(12);
//        else if (warrior.getDirection() == 0 && warrior.getDx() == 0) warriorFrame.add(20);
//        else if ((warrior.getDirection() > 0 || warrior.getDirection() < 0) && warrior.getDx() == 0)
//            warriorFrame.add(21 + (int) (Math.random() * 2));
//        else if (warrior.getDx() < 0 || warrior.getDx() > 0) {
//            warriorFrame.add(2);
//            warriorFrame.add(7);
//            warriorFrame.add(12);
//            warriorFrame.add(17);
//        }
//        return warriorFrame;
//    }

    /**
     * Метод возвращает общий список, который содержит все объекты игры
     */
    public List<BaseObject> getAllItems() {
        ArrayList<BaseObject> list = new ArrayList<BaseObject>();
        list.add(warrior);
        list.addAll(enemies);
        list.addAll(barrierSystems);
        list.addAll(missiles);
//        list.addAll(ufos);
//        list.addAll(bombs);
//        list.addAll(rockets);
//        list.addAll(bosses);
        return list;
    }

    private void draw() {
        char[][] mapDec = getStage();
        for (int i = 0; i < mapDec.length; i++) {
            for (int j = 0; j < mapDec[0].length; j++) {
                System.out.print(mapDec[i][j]);
                if (mapDec[i][j] == 'l') {
                }
            }
            System.out.println();
        }
    }


    /**
     * Метод делает паузу длинной delay миллисекунд.
     */
    public static void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
    }

    public boolean hasKeyEvents() {
        return !keyEvents.isEmpty();
    }

    public KeyEvent getEventFromTop() {
        return keyEvents.poll();
    }
}
