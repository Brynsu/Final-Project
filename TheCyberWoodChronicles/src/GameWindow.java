import javax.swing.*;

public class GameWindow extends JFrame {

    public static Pet pet;
    public static Level petLevel = new Level();

    public GameWindow() {
        setTitle("The CyberWood Chronicles");
        setSize(850, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(new MenuScreen(this));
        setVisible(true);
    }

    public void switchScreen(JPanel panel) {
        setContentPane(panel);
        revalidate();
        repaint();
    }

    public void startBattle() {
        BattleScreen battle = new BattleScreen(this);
        switchScreen(battle);
    }

    public static void main(String[] args) {
        new GameWindow();
    }
}