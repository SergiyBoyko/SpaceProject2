package com.company.model;

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
        return new char[] {'v', 'l', 'e', 's'};
    }

    public char[][] getStage() {
        return stageDecoration.getMap();
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
