public class Art {

    public static String fireDragon() {
        return
              "      /\\\n" +
              "   __/  \\__\n" +
              "  /  ___   \\\n" +
              " <  /   \\   )\n" +
              "  \\_\\___/__/ \n" +
              "      VV";
    }

    public static String waterTurtle() {
        return
              "     ____  \n" +
              "   _(    )_\n" +
              "  (  o  o  )__\n" +
              " <    __     |\n" +
              "  \\__|  |___/";
    }

    public static String earthGolem() {
        return
              "    ______\n" +
              "  _( ____ )_\n" +
              " (  | -- |  )__\n" +
              " <  |____|    |\n" +
              "  \\__|  |_____/";
    }

    public static String getPetArt(String name) {
        switch (name) {
            case "Fire Dragon": return fireDragon();
            case "Water Turtle": return waterTurtle();
            case "Earth Golem": return earthGolem();
            default: return "";
        }
    }

    public static String enemyGoblin() {
        return
              "       .--.\n" +
              "   __ ( o o )\\\n" +
              "  |   \\  ^  /\n" +
              "   \\___\\===/";
    }

    public static String enemyDragon() {
        return
              "      /\\____\n" +
              "  ___( o o  )\n" +
              " <    ----  )\n" +
              "  \\__^^__ /";
    }

    public static String enemyWolf() {
        return
              "      ____/\\\n" +
              "  ___( o  o )\n" +
              " <    ---  )\n" +
              "  \\_\\/\\___/";
    }

    public static String enemySkeleton() {
        return
              "      .-''-.\n" +
              "  __ (  o o )\n" +
              " <    ^     )\n" +
              "  \\_  '-'  /\n" +
              "    '-|_|-'";
    }

    public static String enemyTroll() {
        return
              "      .----.\n" +
              "   __( o o )\n" +
              "  <    >   )\n" +
              "   \\__---_/";
    }

    public static String getEnemyArt(String name) {
        switch (name) {
            case "Goblin": return enemyGoblin();
            case "Dragon": return enemyDragon();
            case "Wolf": return enemyWolf();
            case "Skeleton": return enemySkeleton();
            case "Troll": return enemyTroll();
            default: return "";
        }
    }
}
