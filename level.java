public class Level {
    private int level;
    private int experience;

    public Level() {
        this.level = 1;
        this.experience = 0;
    }

    public int getLevel() { return level; }

    public void addExperience(int points) {
        experience += points;
        checkLevelUp();
    }

    private void checkLevelUp() {
        if (experience >= 100) {
            level++;
            experience -= 100;
            System.out.println("Your pet leveled up to level " + level + "!");
        }
    }

    public int getExperience() { return experience; }
}
