package model;


import java.util.Arrays;

/**
 * Created by alex on 10/15/16.
 */
public enum PermissionLevel {
    USER(0),
    WORKER(1),
    MANAGER(2),
    ADMIN(3);

    public final int level;
    PermissionLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static PermissionLevel fromInt(int level) {
        for (PermissionLevel pl : values()) {
            if (level == pl.level) {
                return pl;
            }
        }
        throw new IllegalArgumentException("No permission with level " + level);
    }
}
