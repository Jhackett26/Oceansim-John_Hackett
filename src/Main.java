//Basic Game Application
// Basic Object, Image, Movement
// Threaded

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

//*******************************************************************************
public class Main implements Runnable {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    //obj vars go here
    public entity fish;
    public Image fishPic;
    public entity mine;
    public Image minePic;
    public Image background;
    public entity hook;
    public Image hookPic;
    public Image explosion;
    public int exTimer;
    public int hookTimer;
    public entity fisherman;
    public Image fishermanPic;
    public Boolean killFisherman = false;
    public int randomNumGen3 = (int)(Math.random()*2);


    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        Main ex = new Main();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method


    }


    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public Main() { // BasicGameApp constructor

        setUpGraphics();

        //variable and objects
        //create (construct) the objects needed for the game
        fish = new entity("fish",200,300);
        fishPic = Toolkit.getDefaultToolkit().getImage("fish.png");
        mine = new entity("mine",800,300);
        minePic = Toolkit.getDefaultToolkit().getImage("mine copy.png");
        background = Toolkit.getDefaultToolkit().getImage("oceanBackground2.jpeg");
        hook = new entity("hook",500,100);
        hookPic = Toolkit.getDefaultToolkit().getImage("fishHook.png");
        explosion = Toolkit.getDefaultToolkit().getImage("explosion.png");
        fisherman = new entity("fisherman",0,-100);
        fishermanPic = Toolkit.getDefaultToolkit().getImage("fisherman.png");
        mine.determineDx();
        mine.determineDy();
        fish.determineDx();
        fish.determineDy();
        if (randomNumGen3==0){
            hook.dx=2;
        }
        else{
            hook.dx=-2;
        }
        hook.dy = 1;
    } // end BasicGameApp constructor


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever.
        //game tics?
        while (true) {
            fisherman.x = hook.x;
            moveThings();  //move all the game objects
            collisions();
            render();  // paint the graphics
            pause(10); // sleep for 10 ms
            if(fisherman.y<600 && killFisherman == true) {
                fisherman.y += 1;
            }
            if (mine.isAlive  == false){
                exTimer++;
            }
            if (hook.isAlive  == true){
                hookTimer++;
                if(hookTimer>=65){
                    hook.dy=-hook.dy;
                    hookTimer=0;
                }
            }
        }
    }

    public void moveThings() {
        //call the move() code for each object
        fish.bounce();
        mine.bounce();
        hook.wrap();
    }

    public void collisions(){
        if(fish.rec.intersects(mine.rec)){
            fish.isAlive = false;
            mine.isAlive = false;
        }
        if(hook.rec.intersects(fish.rec)&&fish.isAlive==true){
            fish.dx=0;
            fish.dy=0;
            hook.dx=0;
            hook.dy=0;
            fish.y-=6;
            hook.y-=6;

        }
        if(hook.rec.intersects(mine.rec)&&mine.isAlive==true){
            mine.dx=0;
            mine.dy=0;
            hook.dx=0;
            hook.dy=0;
            mine.y-=6;
            hook.y-=6;
            if(mine.y<1){
                hook.y-=100;
                mine.isAlive=false;
                killFisherman=true;
            }
        }
    }
    //Paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw the images

        g.drawImage(background,0,0,WIDTH,HEIGHT,null);
        if(fish.isAlive == true) {
            g.drawImage(fishPic, fish.x, fish.y, fish.width, fish.height, null);
        }
        if(mine.isAlive == true) {
            g.drawImage(minePic, mine.x, mine.y, mine.width, mine.height, null);
        }
        if(hook.isAlive == true) {
            g.drawImage(hookPic, hook.x, hook.y, hook.width, hook.height, null);
        }
        if(mine.isAlive == false && (int) exTimer < 20) {
            g.drawImage(explosion, mine.x, mine.y, 200,200, null);
        }
        g.drawImage(fishermanPic, fisherman.x, fisherman.y, 100,100, null);
//        g.drawRect(fish.x, fish.y,fish.width, fish.height);
//        g.drawRect(fish.rec.x, fish.rec.y, fish.rec.width, fish.rec.height);
//        g.drawRect(mine.rec.x, mine.rec.y, mine.rec.width, mine.rec.height);
        g.dispose();
        bufferStrategy.show();
    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time ) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }

}
