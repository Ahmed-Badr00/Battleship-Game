// Board.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Random;

public class Board extends JPanel implements ActionListener {
    private Cell[][] grid = new Cell[10][10];
    private int attempts, maxAttempts, hits, misses;
    private BattleShip parent;
    private boolean gameOver = false;
    
    // Track ships and their positions
    private HashMap<String, int[]> shipSizes = new HashMap<>();  // name -> [size, hits]
    private HashMap<Point, String> shipLocations = new HashMap<>();  // position -> ship name

    public Board(BattleShip parent) {
        this.parent = parent;
        setLayout(new GridLayout(10, 10));
        initializeShipInfo();
    }

    private void initializeShipInfo() {
        shipSizes.put("Carrier", new int[]{5, 0});      // [size, hits]
        shipSizes.put("Battleship", new int[]{4, 0});
        shipSizes.put("Cruiser", new int[]{3, 0});
        shipSizes.put("Submarine", new int[]{3, 0});
        shipSizes.put("Destroyer", new int[]{2, 0});
    }

    public void init(int maxAttempts) {
        this.maxAttempts = maxAttempts;
        this.attempts = 0;
        this.hits = 0;
        this.misses = 0;
        this.gameOver = false;
        removeAll();
        
        // Reset ship tracking
        initializeShipInfo();
        shipLocations.clear();

        // Create and add cells to the grid
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = new Cell(this, false);
                add(grid[i][j]);
            }
        }
        revalidate();
        repaint();
        placeAllShips();
        parent.updateAttemptsLabel(maxAttempts);
    }

    private void placeAllShips() {
        Random random = new Random();
        for (String shipName : shipSizes.keySet()) {
            int size = shipSizes.get(shipName)[0];
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(10);
                int col = random.nextInt(10);
                boolean horizontal = random.nextBoolean();
                if (canPlaceShip(row, col, size, horizontal)) {
                    placeShip(row, col, size, horizontal, shipName);
                    placed = true;
                }
            }
        }
    }

    private boolean canPlaceShip(int row, int col, int size, boolean horizontal) {
        if (horizontal && col + size > 10) return false;
        if (!horizontal && row + size > 10) return false;

        // Check surrounding cells to prevent ships from touching
        for (int i = -1; i <= size; i++) {
            for (int j = -1; j <= 1; j++) {
                int checkRow = horizontal ? row + j : row + i;
                int checkCol = horizontal ? col + i : col + j;
                
                if (checkRow >= 0 && checkRow < 10 && checkCol >= 0 && checkCol < 10) {
                    if (grid[checkRow][checkCol].hasShip()) return false;
                }
            }
        }
        return true;
    }

    private void placeShip(int row, int col, int size, boolean horizontal, String shipName) {
        for (int i = 0; i < size; i++) {
            int shipRow = horizontal ? row : row + i;
            int shipCol = horizontal ? col + i : col;
            grid[shipRow][shipCol].setHasShip(true);
            shipLocations.put(new Point(shipRow, shipCol), shipName);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            JOptionPane.showMessageDialog(this, "Game is over! Start a new game.");
            return;
        }

        Cell cell = (Cell) e.getSource();
        if (cell.isIdle() && attempts < maxAttempts) {
            attempts++;
            int remainingAttempts = maxAttempts - attempts;
            
            if (cell.hasShip()) {
                hits++;
                cell.fire(true);
                
                // Find which ship was hit
                Point hitPoint = null;
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (grid[i][j] == cell) {
                            hitPoint = new Point(i, j);
                            break;
                        }
                    }
                }
                
                if (hitPoint != null) {
                    String shipName = shipLocations.get(hitPoint);
                    if (shipName != null) {
                        int[] shipInfo = shipSizes.get(shipName);
                        shipInfo[1]++; // Increment hits
                        
                        // Check if ship is sunk
                        if (shipInfo[1] == shipInfo[0]) {
                            JOptionPane.showMessageDialog(this, 
                                "You've sunk the " + shipName + " (" + shipInfo[0] + " cells)!");
                        }
                    }
                }
                
                // Check for game win
                boolean allShipsSunk = true;
                for (int[] shipInfo : shipSizes.values()) {
                    if (shipInfo[1] < shipInfo[0]) {
                        allShipsSunk = false;
                        break;
                    }
                }
                
                if (allShipsSunk) {
                    gameOver = true;
                    revealShips();
                    JOptionPane.showMessageDialog(this, "Congratulations! You won with " + 
                        remainingAttempts + " attempts remaining!");
                }
            } else {
                misses++;
                cell.fire(false);
            }
            
            parent.updateAttemptsLabel(remainingAttempts);
            
            if (attempts >= maxAttempts && !gameOver) {
                gameOver = true;
                revealShips();
                JOptionPane.showMessageDialog(this, "Game over! No more attempts remaining.");
            }
        }
    }

    public void revealShips() {
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (cell.hasShip() && cell.isIdle()) {
                    cell.reveal();
                }
            }
        }
    }

    public void printStatistics() {
        int remainingAttempts = maxAttempts - attempts;
        
        StringBuilder stats = new StringBuilder("Game Statistics:\n");
        stats.append("Attempts Used: ").append(attempts).append("\n");
        stats.append("Remaining Attempts: ").append(remainingAttempts).append("\n");
        stats.append("Hits: ").append(hits).append("\n");
        stats.append("Misses: ").append(misses).append("\n");
        stats.append("Accuracy: ").append(String.format("%.1f%%", (hits * 100.0 / (hits + misses)))).append("\n\n");
        
        stats.append("Ships Status:\n");
        for (String shipName : shipSizes.keySet()) {
            int[] shipInfo = shipSizes.get(shipName);
            stats.append(shipName).append(": ")
                 .append(shipInfo[1]).append("/").append(shipInfo[0])
                 .append(" hits (")
                 .append(shipInfo[1] == shipInfo[0] ? "Sunk" : "Afloat")
                 .append(")\n");
        }
        
        JOptionPane.showMessageDialog(this, stats.toString());
    }
}