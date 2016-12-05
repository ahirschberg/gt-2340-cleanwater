package model;


public enum PermissionLevel {
    USER(0),
    WORKER(1),
    MANAGER(2),
    ADMIN(3);

    public final int level;

    /**
     * initializes permission level
     * @param level level of permission
     */
    PermissionLevel(int level) {
        this.level = level;
    }

    /**
     * retrieves level
     * @return the permission level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Returns a permission level from an integer giving the level
     * @param level permission level
     * @return the created PermissionLevel
     */
    public static PermissionLevel fromInt(int level) {
        for (PermissionLevel pl : values()) {
            if (level == pl.level) {
                return pl;
            }
        }
        throw new IllegalArgumentException("No permission with level " + level);
    }

    public String toString() {
	    switch(this.level) {
	    case 0:
		    return "User";
	    case 1:
		    return "Manager";
	    case 2:
		    return "Worker";
	    case 3:
		    return "Admin";
	    default:
		    return "ERROR";
	    }
    }
}
