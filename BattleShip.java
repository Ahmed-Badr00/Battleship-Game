// BattleShip.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BattleShip extends JFrame implements ActionListener {
    private Board board;
    private JButton newGameButton, revealButton, statsButton;
    private JLabel attemptsLabel;
    private JComboBox<String> difficultyCombo;
    private int maxAttempts = 40; // Default to easy

    public BattleShip() {
        setTitle("Battleship Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize components
        board = new Board(this);
        newGameButton = new JButton("New Game");
        revealButton = new JButton("Reveal Ships");
        statsButton = new JButton("Statistics");
        attemptsLabel = new JLabel("Remaining Attempts: " + maxAttempts);
        difficultyCombo = new JComboBox<>(new String[]{"Easy (40)", "Medium (30)", "Hard (20)"});

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Add components to control panel
        controlPanel.add(new JLabel("Difficulty:"));
        controlPanel.add(difficultyCombo);
        controlPanel.add(newGameButton);
        controlPanel.add(revealButton);
        controlPanel.add(statsButton);
        controlPanel.add(attemptsLabel);

        // Add components to frame
        add(board, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // Add action listeners
        newGameButton.addActionListener(this);
        revealButton.addActionListener(this);
        statsButton.addActionListener(this);
        difficultyCombo.addActionListener(this);

        // Initialize the game
        board.init(maxAttempts);
        
        // Center the window on screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            board.init(maxAttempts);
        } else if (e.getSource() == revealButton) {
            board.revealShips();
        } else if (e.getSource() == statsButton) {
            board.printStatistics();
        } else if (e.getSource() == difficultyCombo) {
            String selection = difficultyCombo.getSelectedItem().toString();
            if (selection.startsWith("Easy")) {
                maxAttempts = 40;
            } else if (selection.startsWith("Medium")) {
                maxAttempts = 30;
            } else if (selection.startsWith("Hard")) {
                maxAttempts = 20;
            }
            board.init(maxAttempts);  // Start new game with new difficulty
        }
    }

    public void updateAttemptsLabel(int remainingAttempts) {
        attemptsLabel.setText("Remaining Attempts: " + remainingAttempts);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BattleShip());
    }
}
