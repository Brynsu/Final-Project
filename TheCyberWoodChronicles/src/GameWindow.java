import javax.swing.*;

public class GameWindow extends JFrame {
    private GameState gameState;

    public GameWindow() {
        gameState = GameState.getInstance();
        setTitle("The CyberWood Chronicles");
        setSize(GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);
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

    public GameState getGameState() {
        return gameState;
    }

    public static void main(String[] args) {
        new GameWindow();
    }
}
