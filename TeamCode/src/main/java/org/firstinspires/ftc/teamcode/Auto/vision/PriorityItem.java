package org.firstinspires.ftc.teamcode.Auto.vision;

import org.opencv.core.Rect;

public class PriorityItem implements Comparable<PriorityItem> {
    private Rect item;
    private double priorityValue;

    public PriorityItem(Rect item, double priorityValue) {
        this.item = item;
        this.priorityValue = priorityValue;
    }

    public Rect item() {
        return this.item;
    }

    public double priorityValue() {
        return this.priorityValue;
    }

    @Override
    public int compareTo(PriorityItem o) {
        double diff = this.priorityValue - o.priorityValue;
        if (diff > 0) {
            return 1;
        } else if (diff < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() == o.getClass()) {
            PriorityItem p = (PriorityItem) o;
            return p.item.equals(item);
        }
        return false;
    }
}
