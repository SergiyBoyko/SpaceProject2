package com.company.model;

import java.util.List;

/**
 * Created by Serhii Boiko on 11.08.2017.
 */
public class Field {
    private int width;
    private int height;
    private StageDecoration stageDecoration;

    public Field(int stage) {
        if (stage >= StageDecoration.values().length) {
            System.err.println("Stage not found with id=" + stage);
            System.exit(0);
        }
        stageDecoration = StageDecoration.values()[stage];
        width = stageDecoration.getMap()[0].length;
        height = stageDecoration.getMap().length;
    }

    public char[] getBarriers() {
        return new char[]{'v', 'l', 'e', 's', 'd', 'u', 'b'};
    }

    public List<BarrierSystem> getBarrierSystems() {
        return stageDecoration.loadBarrierSystems();
    }

    public List<Enemy> getEnemies() {
        return stageDecoration.loadEnemies();
    }

    public char[][] getStage() {
        return stageDecoration.getMap();
    }

    public char[][] getStage(List<BarrierSystem> bs) {
        char[][] stageArray = stageDecoration.getMap();
        for (BarrierSystem system : bs) {
            for (BarrierSystem.Barrier bar : system.getBarriers()) {
                if (!bar.isLocked()) {
                    stageArray[bar.getY()][bar.getX()] = '.';
                }
            }
        }
        return stageArray;
    }

    public StageDecoration getStageDecoration() {
        return stageDecoration;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setStageDecoration(StageDecoration stageDecoration) {
        this.stageDecoration = stageDecoration;
    }
}
