import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 600;
        int boardheight = boardWidth;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardheight);  //600 x 600
        frame.setLocationRelativeTo(null); //center of screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //click X on window to close

        SnakeGame snakeGame = new SnakeGame(boardWidth, boardheight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus(); //snake game is listening to key presses
 
    }
}
