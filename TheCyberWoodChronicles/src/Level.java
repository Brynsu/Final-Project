public class Level {
    private int level;
    private int experience;

    public Level() { level = 1; experience = 0; }

    public int getLevel() { return level; }

    public void addExperience(int points) {
        experience += points;
        if (experience >= 100) { level++; experience -= 100; }
    }

    public int getExperience() { return experience; }
}
