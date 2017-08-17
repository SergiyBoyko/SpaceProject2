package com.company.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serhii Boiko on 12.08.2017.
 */
public enum StageDecoration {
    LANDING,
    BASE,
    UDERGROUND,
    BOSS;

    /**
     * DESCRIPTION:
     * s - start line platform
     * l - line horizontal platform
     * e - end line platform
     * v - vertical line platform (wall)
     * d - down end vertical line platform (wall)
     * m - move up/down (stairs)
     * g - generator -- only for eyes
     * b - barrier (door) -- need to been synchronized in method loadBarrierSystems
     * f - final (purpose of the level)
     *
     */

    char[][] getMap() {
        int id = this.ordinal();
        char[][] testMap = {
                //0    1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   16   17   18   19   20   21   22   23   24   25   26   27   28   29
       /*0*/    {'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l'},      //0
       /*1*/    {'v', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'b', '.', '.', '.', '.', '.', 'v', '.', '.', '.', 'b', '.', '.', '.', '.', '.', 'v'},      //1
       /*2*/    {'v', 'g', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'b', '.', '.', '.', '.', 'g', 'v', '.', '.', '.', 'b', '.', '.', '.', '.', 'g', 'v'},      //2
       /*3*/    {'v', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 's', 'l', 'l', 'l', 'l', 'l', 'l', 'e', '.', 'm', 's', 'l', 'l', 'l', 'l', 'l', 'v'},      //3
       /*4*/    {'v', 's', 'l', 'l', 'l', 'l', 'e', 'm', '.', '.', 's', 'e', 'm', 'v', '.', '.', '.', '.', '.', '.', '.', '.', 'm', '.', '.', '.', '.', '.', '.', 'v'},      //4
       /*5*/    {'v', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'm', 'v', 'g', '.', '.', '.', '.', '.', '.', '.', 'm', '.', '.', '.', '.', '.', '.', 'v'},      //5
       /*6*/    {'v', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'm', 'v', '.', '.', '.', '.', '.', '.', '.', 's', 'l', 'l', 'l', 'l', 'l', 'e', '.', 'v'},      //6
       /*7*/    {'v', 'm', '.', '.', '.', '.', '.', '.', '.', '.', 's', 'l', 'e', 'v', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'v'},      //7
       /*8*/    {'v', 'm', '.', 's', 'l', 'l', 'l', 'e', '.', '.', '.', '.', '.', 'v', 's', 'l', 'l', 'l', 'e', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'v'},      //8
       /*9*/    {'v', 'm', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'v', '.', '.', '.', '.', '.', '.', '.', 's', 'l', 'l', 'e', '.', '.', '.', '.', 'v'},      //9
      /*10*/    {'v', 'm', '.', '.', '.', '.', '.', '.', 's', 'l', 'e', '.', 'm', 'v', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'm', '.', '.', 'v', 'v'},      //10
      /*11*/    {'v', 'm', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'm', 'd', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'm', 'v', '.', 'd', 'd'},      //11
      /*12*/    {'v', 'm', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'b', '.', '.', '.', '.', 'v', '.', '.', '.', '.', '.', '.', 'm', 'v', '.', 'b', '.'},      //12
      /*13*/    {'v', 'm', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', 'b', '.', '.', '.', 'v', 'v', '.', '.', '.', '.', '.', '.', 'm', 'v', '.', 'b', 'f'},      //13
      /*14*/    {'l', 'l', 'l', 'l', 'l', 'l', 'l', 'e', '.', '.', '.', '.', 's', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'l'}       //14
        };
        return testMap;
    }

    List<Enemy> loadEnemies() {
        List<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(new XenomorphEnemy(3, 3));
        enemies.add(new XenomorphEnemy(5, 7));
        enemies.add(new XenomorphEnemy(16, 2));
        enemies.add(new XenomorphEnemy(16, 7));
        enemies.add(new XenomorphEnemy(21, 13));
        enemies.add(new XenomorphEnemy(26, 2));
        enemies.add(new XenomorphEnemy(25, 5));
        return enemies;
    }

    List<BarrierSystem> loadBarrierSystems() {
        List<BarrierSystem> systems = new ArrayList<>();
        BarrierSystem system = new BarrierSystem(1, 2);
        system.addBarrier(13, 13);
        system.addBarrier(13, 12);
        systems.add(system);
        system = new BarrierSystem(28, 2);
        system.addBarrier(13, 1);
        system.addBarrier(13, 2);
        systems.add(system);
        system = new BarrierSystem(18, 2);
        system.addBarrier(28, 12);
        system.addBarrier(28, 13);
        systems.add(system);
        system = new BarrierSystem(14, 5);
        system.addBarrier(23, 1);
        system.addBarrier(23, 2);
        systems.add(system);
        return systems;
    }
}
