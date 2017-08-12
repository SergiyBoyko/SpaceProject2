package com.company.model;

import com.company.controller.Controller;

/**
 * Created by Serhii Boiko on 12.08.2017.
 */
public class Enemy extends BaseObject{
    //вектор движения (-1 влево,+1 вправо,0 стоп)
    private double dx = 0;
    //дальность  обзора у объекта
    private double visibility;
    //спокойный объект или атакует
    private boolean excited;



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
        dx = -1;
    }

    /**
     * Устанавливаем вектор движения вправо
     */
    public void moveRight() {
        dx = 1;
    }

    @Override
    public void move(Controller controller) {
        x = x + dx;

//        checkBorders(radius, Space.game.getWidth() - radius + 1, 1, Space.game.getHeight() + 1);
    }


    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public Enemy(double x, double y, double radius, double health) {
        super(x, y, radius, health);
        excited = false;
    }

}
