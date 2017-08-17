package com.company.model;

import com.company.controller.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serhii Boiko on 16.08.2017.
 */
public class BarrierSystem extends BaseObject {
    private List<Barrier> barriers = new ArrayList<Barrier>();

    public BarrierSystem(double x, double y) {
        super(x, y, 0.2, 0.1);
    }

    public void addBarrier(int x, int y) {
        barriers.add(new Barrier(x, y));
    }

    public List<Barrier> getBarriers() {
        return barriers;
    }

    public Barrier getBarrierByCoors(int x, int y) {
        for (Barrier barrier: barriers) {
            if (barrier.getX() == x
                    && barrier.getY() == y) {
                return barrier;
            }
        }
        return null;
    }

    @Override
    public void move(Controller controller) {
//        System.out.println(x + " " + y);
//        if (!isAlive()) System.out.println("destroyed");
        for (Barrier barrier : barriers) {
            barrier.setLocked(isAlive());
        }
    }

    public class Barrier {
        private int x;
        private int y;
        private boolean locked;

        public Barrier(int x, int y) {
            this.x = x;
            this.y = y;
            locked = true;
        }

        public boolean isLocked() {
            return locked;
        }

        private void setLocked(boolean locked) {
            this.locked = locked;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
