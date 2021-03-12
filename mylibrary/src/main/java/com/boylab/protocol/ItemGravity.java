package com.boylab.protocol;

import android.view.Gravity;

public enum ItemGravity {
    START(0, Gravity.LEFT | Gravity.CENTER_VERTICAL),
    END(1, Gravity.END | Gravity.CENTER_VERTICAL),
    CENTER(2, Gravity.CENTER);

    private final int id;
    private int gravity;

    ItemGravity(int id, int gravity) {
        this.id = id;
        this.gravity = gravity;
    }

    public int getId() {
        return id;
    }

    public int getGravity() {
        return gravity;
    }

    public static ItemGravity fromId(int id) {
        for (ItemGravity value : values()) {
            if (value.id == id) return value;
        }
        return CENTER;
    }
}