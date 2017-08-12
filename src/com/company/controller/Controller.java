package com.company.controller;

import com.company.View;
import com.company.model.BaseObject;
import com.company.model.Field;
import com.company.model.SpaceWarrior;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Serhii Boiko on 11.08.2017.
 */
public class Controller extends KeyAdapter implements MouseListener {
    private View view;
    private Field field;
    private SpaceWarrior warrior;
    private int tempX;
    private List<Integer> warriorFrames;
    private int playerFrame;

    public int getPlayerFrame() {
        return playerFrame;
    }
//    public List<Integer> getWarriorFrames() {
//        return warriorFrames;
//    }

    public int getTempX() {
        return tempX;
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
        field = new Field(0);
        warrior = new SpaceWarrior(10, 1); // x * 0.05, y
    }

    public void run() {
//        SpaceWarrior warrior = new SpaceWarrior(1, 13);
        warriorFrames = setWarriorFrame();
        tempX = 0;
        double frameIterator = 0;
//        warriorFrame.add(20);
        int dx = 1;

        while (true) {
            if (this.hasKeyEvents()) {
                if (this.getEventFromTop().getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
            if (frameIterator >= warriorFrames.size())
                frameIterator = 0;
            playerFrame = warriorFrames.get((int) frameIterator);
            frameIterator += 0.3;
            view.repaint();
            sleep(60);
            moveAllItems();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.print(e.getKeyChar());
        keyEvents.add(e);
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            warrior.moveLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            warrior.moveRight();
        }
        warriorFrames = setWarriorFrame();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //do nothing
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        System.out.println(e.getKeyChar());
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            warrior.stopMove();
            warriorFrames = setWarriorFrame();
        }
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
            object.move(this);
        }
    }

    public List<Integer> setWarriorFrame() {
        List<Integer> warriorFrame = new ArrayList<Integer>();
        warriorFrame.clear();
        if (warrior.getDirection() == 0 && warrior.getDx() == 0) warriorFrame.add(20);
        else if ((warrior.getDirection() > 0 || warrior.getDirection() < 0) && warrior.getDx() == 0)
            warriorFrame.add(21 + (int) (Math.random() * 2));
        else if (warrior.getDx() < 0 || warrior.getDx() > 0) {
            warriorFrame.add(2);
            warriorFrame.add(7);
            warriorFrame.add(12);
            warriorFrame.add(17);
        }
        return warriorFrame;
    }

    /**
     * Метод возвращает общий список, который содержит все объекты игры
     */
    public List<BaseObject> getAllItems() {
        ArrayList<BaseObject> list = new ArrayList<BaseObject>();
        list.add(warrior);
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
