import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.image.BufferedImage;

public class StoryScreen extends JPanel {
    private GameWindow window;
    private Image bg;
    private JLabel portraitLabel;
    private JLabel speakerLabel;
    private JTextArea dialogueArea;
    private JButton nextButton;
    private Timer typeTimer;
    private String currentText;
    private int charIndex;
    
    public StoryScreen(GameWindow window) {
        this.window = window;
        
        URL bgUrl = getClass().getResource("/Images/Arena.png");
        if (bgUrl != null) {
            bg = new ImageIcon(bgUrl).getImage();
        } else {
            bg = null;
        }
        
        setLayout(new BorderLayout());
        setupUI();
        showNextScene();
    }
    
    private void setupUI() {
        JPanel storyPanel = new JPanel(new BorderLayout());
        storyPanel.setOpaque(false);
        storyPanel.setBorder(BorderFactory.createEmptyBorder(80, 80, 80, 80));
        
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setOpaque(false);
        
        portraitLabel = new JLabel();
        portraitLabel.setPreferredSize(new Dimension(120, 120));
        topPanel.add(portraitLabel);
        
        speakerLabel = new JLabel("", SwingConstants.CENTER);
        speakerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        speakerLabel.setForeground(Color.WHITE);
        speakerLabel.setPreferredSize(new Dimension(300, 30));
        topPanel.add(speakerLabel);
        
        storyPanel.add(topPanel, BorderLayout.NORTH);
        
        JPanel dialoguePanel = new JPanel(new BorderLayout());
        dialoguePanel.setOpaque(false);
        dialoguePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        dialogueArea = new JTextArea();
        dialogueArea.setFont(new Font("Arial", Font.PLAIN, 16));
        dialogueArea.setForeground(Color.WHITE);
        dialogueArea.setBackground(new Color(0, 0, 0, 180));
        dialogueArea.setWrapStyleWord(true);
        dialogueArea.setLineWrap(true);
        dialogueArea.setEditable(false);
        dialogueArea.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        dialogueArea.setPreferredSize(new Dimension(400, 120));
        
        JScrollPane scrollPane = new JScrollPane(dialogueArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(450, 150));
        
        dialoguePanel.add(scrollPane, BorderLayout.CENTER);
        storyPanel.add(dialoguePanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.setPreferredSize(new Dimension(120, 40));
        nextButton.setBackground(new Color(70, 130, 180, 200));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        nextButton.addActionListener(e -> handleNext());
        buttonPanel.add(nextButton);
        
        storyPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(storyPanel, BorderLayout.CENTER);
    }
    
    private void showNextScene() {
        if (StoryManager.hasNextScene()) {
            StoryManager.StoryScene scene = StoryManager.getNextScene();
            displayScene(scene);
        } else {
            proceedToBattle();
        }
    }
    
    private void displayScene(StoryManager.StoryScene scene) {
        ImageIcon portrait = loadPortrait(scene.portrait);
        portraitLabel.setIcon(portrait);
        speakerLabel.setText(scene.speaker);
        
        currentText = scene.dialogue;
        charIndex = 0;
        dialogueArea.setText("");
        
        if (typeTimer != null && typeTimer.isRunning()) {
            typeTimer.stop();
        }
        
        typeTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (charIndex < currentText.length()) {
                    dialogueArea.append(String.valueOf(currentText.charAt(charIndex)));
                    charIndex++;
                } else {
                    typeTimer.stop();
                }
            }
        });
        typeTimer.start();
    }
    
    private ImageIcon loadPortrait(String path) {
        URL imgUrl = getClass().getResource(path);
        if (imgUrl != null) {
            Image img = new ImageIcon(imgUrl).getImage();
            img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return createPlaceholderPortrait();
    }
    
    private ImageIcon createPlaceholderPortrait() {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        
        g2d.setColor(new Color(70, 70, 70, 200));
        g2d.fillRect(0, 0, 100, 100);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("No Image", 25, 50);
        
        g2d.dispose();
        return new ImageIcon(img);
    }
    
    private void handleNext() {
        if (typeTimer != null && typeTimer.isRunning()) {
            typeTimer.stop();
            dialogueArea.setText(currentText);
        } else {
            if (StoryManager.isBattleScene()) {
                proceedToBattle();
            } else {
                showNextScene();
            }
        }
    }
    
    private void proceedToBattle() {
        window.switchScreen(new BattleScreen(window));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        } else {
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gradient = new GradientPaint(0, 0, new Color(20, 20, 40), 
                                                      getWidth(), getHeight(), new Color(50, 50, 80));
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRoundRect(40, 40, getWidth() - 80, getHeight() - 80, 25, 25);
        
        g2d.setColor(new Color(255, 255, 255, 50));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(40, 40, getWidth() - 80, getHeight() - 80, 25, 25);
    }
}
