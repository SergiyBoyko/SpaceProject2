package com.company.model;

/**
 * Created by Serhii Boiko on 12.08.2017.
 */
public class Enemy extends BaseObject {
    //вектор движения (-1 влево,+1 вправо,0 стоп)
    protected double dx = 0;
    //вектор движения (-1 влево,+1 вправо,0 стоп)
    protected double oldDx = 0;
    //направление (-1 влево,+1 вправо,0 напротив)
    protected double direction = 0;
    //дальность  обзора у объекта
    protected double visibility;
    //спокойный объект или атакует
    protected boolean excited;

    public int getEnemyFrame() {
        return objectFrame;
    }

    public double getDirection() {
        return direction;
    }

    public double getDx() {
        return dx;
    }

    public boolean isExcited() {
        return excited;
    }

    /**
     * Возвращает контакт с объектом
     */
    public boolean isExcited(BaseObject o) {
        double dx = x - o.x;
        double dy = y - o.y;
        double destination = Math.sqrt(dx * dx + dy * dy);

        excited = destination <= visibility;
        return excited;
    }

    /**
     * Устанавливаем вектор движения стоп
     */
    public void stopMove() {
        dx = 0;
    }

    /**
     * Устанавливаем вектор движения влево
     */
    public void moveLeft() {
        dx = -0.1;
        direction = -1;
    }

    /**
     * Устанавливаем вектор движения вправо
     */
    public void moveRight() {
        dx = 0.1;
        direction = 1;
    }



    public void attack(BaseObject o) {
        // TODO: 13.08.2017
        // implement in override
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public Enemy(double x, double y, double radius, double visibility, double health) {
        super(x, y, radius, health);
        this.visibility = visibility;
        excited = false;
    }

}
