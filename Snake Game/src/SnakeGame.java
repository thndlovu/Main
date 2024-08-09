import java.awt.*; //for creating Graphical user interface
import java.awt.event.*; //for different types of events fired by awt
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener { //version of JPanel with more things we can add
    private class Tile {
        int x;
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25;
    
    //Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //Food
    Tile food;
    Random random;

    //game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false; //default

    SnakeGame(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth; //this is used to distinguish between paramater and viriable in class
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        
        snakeHead = new Tile(5, 5); //default starting position
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10); 
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //Grid
        // for(int i = 0; i < boardWidth/tileSize; i++){ //24 rows, 24 columns of squares
        //     //(x1, y1, x2, y2)
        //     g.setColor(Color.gray);
        //     g.drawLine(i * tileSize, 0, i * tileSize, boardHeight); //vertical lines (y is from 0 - boardHeight)
        //     g.drawLine(0, i * tileSize, boardWidth, i * tileSize); //horizontal lines (x is from 0 - boardWidth)
        // }


        //food
        g.setColor(Color.red);
        //g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true); 

        //Snake Head
        g.setColor(Color.green); //color of snake
       // g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize); //draw rectangle (x,y, w, h)
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        //Snake Body
        for(int i = 0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            //g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true); //boarder around tiles (each part is divided by boarder)
        }

        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 16)); 
        if(gameOver){
            g.setColor(Color.red);
            //display Game Over and score of how many times you've eaten the food (value, x position, y position) of where to display
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize); 
        } else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public void placeFood() { //function to place food randomly on board
        food.x = random.nextInt(boardWidth/tileSize); // x position is random number from 0 - 24
        food.y = random.nextInt(boardHeight/tileSize); // y position is random number from 0 - 24
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        //eat
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //each tile needs to catch up to the one before it before the snake head can move
        //Snake Body
        for(int i = snakeBody.size() - 1; i >= 0; i--) { //iterate backwards
            Tile snakePart = snakeBody.get(i);
            if(i == 0){ //first member of snake body (one that comes right after snake head)
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //Snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //game over conditions
        for(int i = 0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            if(collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }

        if(snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth || 
        snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeight){
        gameOver = true;
    }
    }

    @Override
    public void actionPerformed(ActionEvent e) { //every 100ms we will call this action performed which will repaint which will cause draw to repeat
        move();
        repaint();
        if(gameOver){
            gameLoop.stop(); //stop game
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){ //if up key is pressed
            velocityX = 0;
            velocityY = -1;
        } else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){ //if down key is pressed
            velocityX = 0;
            velocityY = 1;
        } else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){ //if left key is pressed
            velocityX = -1;
            velocityY = 0;
        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){ //if right key is pressed
            velocityX = 1;
            velocityY = 0;
        }
    }

    //not required
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
