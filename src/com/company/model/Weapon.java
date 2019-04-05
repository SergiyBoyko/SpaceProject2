package com.company.model;

/**
 * Created by Serhii Boiko on 24.08.2017.
 */
public enum Weapon {
    PISTOL,
    RPG;


    int getRate() {
        int rate = 0;
        if (this == PISTOL) {
            rate = 200; // 0.2 sec
        } else if (this == RPG) {
            rate = 3000; // 3 sec
        }
        return rate;
    }

    void shot() {

    }
}
