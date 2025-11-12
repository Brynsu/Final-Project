public class level {
    private int level;
    private int experience;
    private int expToNextLevel;
 
    public level() {
        this.level = 1;
        this.experience = 0;
        this.expToNextLevel = 50;
    }
 
    public int getLevel() {
        return level;
    }
 
    public int getExperience() {
        return experience;
    }
 
    public void addExperience(int exp) {
        experience += exp;
        checkLevelUp();
    }
 
    private void checkLevelUp() {
        while (experience >= expToNextLevel) {
            experience -= expToNextLevel;
            level++;
            expToNextLevel += 25; // Leveling curve
            System.out.println("🎉 Your pet leveled up to Level " + level + "!");
        }
    }
}
