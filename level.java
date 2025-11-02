public class Level {
    private int level;
    private int experience;
    
    public Level() {
        this.level = 1;
        this.experience = 0;
    }

    public int getLevel() {
        return level;
    }

    public void addExperience(int points) {
        experience += points;
        checkLevelUp();
    }

    private void checkLevelUp() {
        // taga lvl mo need ug 100 points
        if (experience >= 100) {
            level++;
            experience = 0; // mo reset after lvl up
            System.out.println("Your pet leveled up to level " + level + "!");
        }
    }

    public int getExperience() {
        return experience;
    }
}
