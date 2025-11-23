public class GameState {
    private Pet currentPet;
    private Level petLevel;
    private int currentChapter;
    private int currentScene;
    private static GameState instance;

    private GameState() {
        petLevel = new Level();
        currentChapter = 1;
        currentScene = 0;
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public void reset() {
        currentPet = null;
        petLevel = new Level();
        currentChapter = 1;
        currentScene = 0;
    }

    public Pet getCurrentPet() { return currentPet; }
    public void setCurrentPet(Pet pet) { this.currentPet = pet; }
    public Level getPetLevel() { return petLevel; }
    public int getCurrentChapter() { return currentChapter; }
    public void setCurrentChapter(int chapter) { this.currentChapter = chapter; }
    public int getCurrentScene() { return currentScene; }
    public void setCurrentScene(int scene) { this.currentScene = scene; }
}
