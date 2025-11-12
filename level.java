public class Level {
    private int level;
    private int experience;
    private static final int MAX_LEVEL = 100;
    private static final int BASE_XP = 100;
    private static final int XP_SCALING = 25;
    
    // Default constructor
    public Level() {
        this.level = 1;
        this.experience = 0;
    }
    
    // Constructor for loading saved data
    public Level(int level, int experience) {
        this.level = Math.min(level, MAX_LEVEL);
        this.experience = Math.max(experience, 0);
    }
    
    // Getters
    public int getLevel() { 
        return level; 
    }
    
    public int getExperience() { 
        return experience; 
    }
    
    public int getMaxLevel() {
        return MAX_LEVEL;
    }
    
    // Add experience points with validation
    public void addExperience(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Experience points cannot be negative");
        }
        experience += points;
        checkLevelUp();
    }
    
    // Check and handle level ups (supports multiple level ups at once)
    private void checkLevelUp() {
        while (experience >= getExperienceForNextLevel() && level < MAX_LEVEL) {
            experience -= getExperienceForNextLevel();
            level++;
            System.out.println("🎉 Your pet leveled up to level " + level + "!");
        }
        
        // Cap experience if at max level
        if (level >= MAX_LEVEL) {
            experience = 0;
            System.out.println("⭐ Your pet has reached maximum level!");
        }
    }
    
    // Calculate XP needed for next level (scales with level)
    public int getExperienceForNextLevel() {
        if (level >= MAX_LEVEL) {
            return 0;
        }
        return BASE_XP + (level - 1) * XP_SCALING;
    }
    
    // Get remaining XP needed to level up
    public int getExperienceToNextLevel() {
        if (level >= MAX_LEVEL) {
            return 0;
        }
        return getExperienceForNextLevel() - experience;
    }
    
    // Get level progress as percentage
    public double getLevelProgress() {
        if (level >= MAX_LEVEL) {
            return 100.0;
        }
        return ((double) experience / getExperienceForNextLevel()) * 100.0;
    }
    
    // Calculate stat bonus based on level
    public int getStatBonus() {
        return level * 5;
    }
    
    // Check if pet is at max level
    public boolean isMaxLevel() {
        return level >= MAX_LEVEL;
    }
    
    // Display level information
    public void displayInfo() {
        System.out.println("=== Level Info ===");
        System.out.println("Current Level: " + level);
        System.out.println("Experience: " + experience + "/" + getExperienceForNextLevel());
        System.out.println("Progress: " + String.format("%.1f", getLevelProgress()) + "%");
        System.out.println("XP to Next Level: " + getExperienceToNextLevel());
        System.out.println("Stat Bonus: +" + getStatBonus());
        System.out.println("==================");
    }
    
    // Save format for persistence
    @Override
    public String toString() {
        return level + "," + experience;
    }
    
    // Load from save format
    public static Level fromString(String data) {
        String[] parts = data.split(",");
        int savedLevel = Integer.parseInt(parts[0]);
        int savedExperience = Integer.parseInt(parts[1]);
        return new Level(savedLevel, savedExperience);
    }
}
