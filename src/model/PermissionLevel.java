package model;


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
}
