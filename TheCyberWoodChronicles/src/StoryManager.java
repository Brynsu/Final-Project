import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;

public class StoryManager {
    private static int currentChapter = 1;
    private static int currentScene = 0;
    
    public static class StoryScene {
        public String speaker;
        public String dialogue;
        public String portrait;
        
        public StoryScene(String speaker, String dialogue, String portrait) {
            this.speaker = speaker;
            this.dialogue = dialogue;
            this.portrait = portrait;
        }
    }
    
    private static List<StoryScene> chapter1Scenes = new ArrayList<>();
    private static List<StoryScene> chapter2Scenes = new ArrayList<>();
    private static List<StoryScene> chapter3Scenes = new ArrayList<>();
    private static List<StoryScene> chapter4Scenes = new ArrayList<>();
    
    static {
        chapter1Scenes.add(new StoryScene("Mysterious Stranger", "You! You're from that lab, aren't you? What have you done to these creatures?", "/Images/MysteriousStranger.jpg"));
        chapter1Scenes.add(new StoryScene("Player", "Wait, I'm just a hiker! I don't know what you're talking about!", "/Images/player.jpg"));
        chapter1Scenes.add(new StoryScene("Mysterious Stranger", "Don't play dumb! I've seen what your kind does to the forest!", "/Images/MysteriousStranger.jpg"));
        chapter1Scenes.add(new StoryScene("System", "The stranger prepares for battle!", "/Images/system.jpg"));
        
        chapter2Scenes.add(new StoryScene("Mysterious Stranger", "You... you're not with them? Your techniques are too pure.", "/Images/MysteriousStranger.jpg"));
        chapter2Scenes.add(new StoryScene("Player", "I told you, I'm just exploring. What's this about a lab?", "/Images/player.jpg"));
        chapter2Scenes.add(new StoryScene("Mysterious Stranger", "A secret lab is experimenting on forest creatures. They're corrupting them!", "/Images/MysteriousStranger.jpg"));
        chapter2Scenes.add(new StoryScene("Player", "We should investigate together. Let me help!", "/Images/player.jpg"));
        chapter2Scenes.add(new StoryScene("System", "As you venture deeper, corrupted creatures appear!", "/Images/system.jpg"));
        
        chapter3Scenes.add(new StoryScene("Mysterious Stranger", "This is it - the hidden lab entrance!", "/Images/MysteriousStranger.jpg"));
        chapter3Scenes.add(new StoryScene("Player", "Something's coming... it's huge!", "/Images/player.jpg"));
        chapter3Scenes.add(new StoryScene("Lab Guardian", "INTRUDERS DETECTED. ELIMINATE THREAT.", "/Images/LabGuardianAvatar.jpg"));
        chapter3Scenes.add(new StoryScene("System", "The lab's ultimate security guardian attacks!", "/Images/system.jpg"));
        
        chapter4Scenes.add(new StoryScene("Player", "We did it! The guardian is defeated!", "/Images/player.jpg"));
        chapter4Scenes.add(new StoryScene("Player", "Stranger? Where did you go?", "/Images/player.jpg"));
        chapter4Scenes.add(new StoryScene("Player", "That's strange... he was just here a moment ago.", "/Images/player.jpg"));
        chapter4Scenes.add(new StoryScene("System", "A mysterious device drops from the stranger's cloak...", "/Images/system.jpg"));
        chapter4Scenes.add(new StoryScene("Player", "What's this? It has the lab's logo... and his fingerprints?", "/Images/player.jpg"));
        chapter4Scenes.add(new StoryScene("Player", "Wait... was HE working for the lab all along?", "/Images/player.jpg"));
        chapter4Scenes.add(new StoryScene("System", "TO BE CONTINUED...", "/Images/system.jpg"));
    }
    
    public static boolean hasNextScene() {
        List<StoryScene> currentChapterScenes = getCurrentChapterScenes();
        return currentScene < currentChapterScenes.size();
    }
    
    public static StoryScene getNextScene() {
        List<StoryScene> currentChapterScenes = getCurrentChapterScenes();
        if (currentScene < currentChapterScenes.size()) {
            return currentChapterScenes.get(currentScene++);
        }
        return null;
    }
    
    public static void startChapter(int chapter) {
        currentChapter = chapter;
        currentScene = 0;
    }
    
    public static int getCurrentChapter() {
        return currentChapter;
    }
    
    public static boolean isBattleScene() {
        List<StoryScene> currentChapterScenes = getCurrentChapterScenes();
        if (currentScene > 0 && currentScene <= currentChapterScenes.size()) {
            StoryScene scene = currentChapterScenes.get(currentScene - 1);
            return scene.dialogue.contains("battle") || scene.dialogue.contains("attacks") || scene.dialogue.contains("prepares for battle");
        }
        return false;
    }
    
    private static List<StoryScene> getCurrentChapterScenes() {
        return switch (currentChapter) {
            case 1 -> chapter1Scenes;
            case 2 -> chapter2Scenes;
            case 3 -> chapter3Scenes;
            case 4 -> chapter4Scenes;
            default -> chapter1Scenes;
        };
    }
    
    public static Enemy getChapterEnemy() {
        return switch (currentChapter) {
            case 1 -> createEnemy("Stranger's Pet", 150, 40, "/Images/StrangerPet.jpg");
            case 2 -> createEnemy("Corrupted Wolf", 250, 60, "/Images/Wolf.jpg");
            case 3 -> createBossEnemy("Lab Guardian", 380, 85, "/Images/MechaLabGuard.jpg");
            default -> new Enemy();
        };
    }
    
    private static Enemy createEnemy(String name, int maxHealth, int attackPower, String imagePath) {
        Enemy enemy = new Enemy();
        enemy.setStats(name, maxHealth, attackPower, imagePath);
        return enemy;
    }
    
    private static Enemy createBossEnemy(String name, int maxHealth, int attackPower, String imagePath) {
        Enemy enemy = new Enemy();
        enemy.setStats(name, maxHealth, attackPower, imagePath);
        enemy.setBoss(true);
        return enemy;
    }
    
    public static void nextChapter() {
        currentChapter++;
        currentScene = 0;
    }
    
    public static boolean isGameComplete() {
        return currentChapter > 4;
    }
}