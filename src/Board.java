import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.imageio.ImageIO;

public class Board extends JPanel implements KeyListener {
    private Drawer drawer=new Drawer();
    private BufferedImage tiles, background, pause, refresh;

    private int[][] table=new int[Constants.boardHeight][Constants.boardWidth];
    private Shape[] shapes= new Shape[7];
    private Shape fallingShape,nextShape;
    private int [] topten={0,0,0,0,0,0,0,0,0,0};
   private Timer timer;
   private int score=0;
   private boolean gameOver=false;
    public Board()
    {
        try {
            tiles = ImageIO.read(Board.class.getResource("/tiles.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

timer =new Timer(Constants.delay, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }
});
        timer.start();
makeShapes();
    }
    private void setTopTen()
    {
        int i=0;
        try {
            File myObj = new File("topten.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()&&i<10) {
                String data = myReader.nextLine();
                topten[i]=Integer.valueOf(data);
                i++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for(int j=0;j<i;j++)
        {
            for(int q=j+1;q<i;q++)
            {
                if(topten[j]<topten[q])
                {
                    int m=topten[j];
                    topten[j]=topten[q];
                    topten[q]=m;
                }
            }
        }
    }
    private void update()
    {
        fallingShape.update();
        if(gameOver)
        {
            playSound("Various-01.wav");
            timer.stop();
        }
    }

    public int[][] getTable() {
        return table;
    }

    @Override
    public void paintComponent(Graphics g)
    {
super.paintComponent(g);
        drawer.drawShape(g,fallingShape);
        drawer.drawNextShape(g,nextShape);
for(int i=0;i<table.length;i++)
{
    for(int j=0;j<table[i].length;j++)
    {
if(table[i][j]!=0)
        g.drawImage(tiles.getSubimage((table[i][j]-1)*Constants.tileSize,0,Constants.tileSize,Constants.tileSize),j*Constants.tileSize,i*Constants.tileSize,null);
    }
}
for (int i=0;i<=Constants.boardHeight;i++)
{
    g.drawLine(0,i*Constants.tileSize,Constants.boardWidth*Constants.tileSize,i*Constants.tileSize);
}
        for (int i=0;i<=Constants.boardWidth;i++)
        {
            g.drawLine(i*Constants.tileSize,0,i*Constants.tileSize,Constants.boardHeight*Constants.tileSize);
        }

        g.drawString("SCORE : "+score, 380, 20);
        g.drawString("topten :",350,330);
        for (int q=0;q<10;q++)
        {
            g.drawString((q+1)+" :"+topten[q]+"",350,350+20*q);
        }

    }
    public void deleteLines()
    {
int h=table.length-1;
for(int i=h;i>0;i--)
{
    int numFulls=0;
    for(int j=0;j<table[0].length;j++)
    {
        if(table[i][j]!=0)
        {
            numFulls++;
        }
        table[h][j]=table[i][j];
    }
    if(numFulls==table[0].length)
        score++;
    if(numFulls<table[0].length)
    {
        h--;

    }
}
    }
    private void makeShapes()
    {
        // Ladder
        shapes[0] = new Shape(new int[][]{
                {1, 1, 1, 1}
        }, tiles.getSubimage(0, 0, Constants.tileSize, Constants.tileSize), this,1,1);
//mount
        shapes[1] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 1, 0},
        }, tiles.getSubimage(Constants.tileSize, 0, Constants.tileSize, Constants.tileSize), this, 2,2);
//rightfoot
        shapes[2] = new Shape(new int[][]{
                {1, 1, 1},
                {1, 0, 0},
        }, tiles.getSubimage(Constants.tileSize*2, 0, Constants.tileSize, Constants.tileSize), this, 3,3);
//leftfoot
        shapes[3] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 0, 1},
        }, tiles.getSubimage(Constants.tileSize*3, 0, Constants.tileSize, Constants.tileSize), this, 4,4);
//rightduck
        shapes[4] = new Shape(new int[][]{
                {0, 1, 1},
                {1, 1, 0},
        }, tiles.getSubimage(Constants.tileSize*4, 0, Constants.tileSize, Constants.tileSize), this, 5,5);
//leftduck
        shapes[5] = new Shape(new int[][]{
                {1, 1, 0},
                {0, 1, 1},
        }, tiles.getSubimage(Constants.tileSize*5, 0, Constants.tileSize, Constants.tileSize), this, 6,6);
//window
        shapes[6] = new Shape(new int[][]{
                {1, 1},
                {1, 1},
        }, tiles.getSubimage(Constants.tileSize*6, 0, Constants.tileSize, Constants.tileSize), this, 7,7);
        setNewShape();
        setNextShape();
    }
    public void setNextShape()
    {
        Random r=new Random();
        int num=Math.abs (r.nextInt(7));
        Shape newShape=new Shape(shapes[num].getCoords(),shapes[num].getTile(),this,num,shapes[num].getColor());
        nextShape=newShape;
    }
    public void setNewShape()
    {if(fallingShape==null){
        Random r=new Random();
        int num=Math.abs (r.nextInt(7));
        Shape newShape=new Shape(shapes[num].getCoords(),shapes[num].getTile(),this,num,shapes[num].getColor());
        fallingShape=newShape;}
        else{
            fallingShape=nextShape;
    }
        for(int i=0;i<fallingShape.getCoords().length;i++) {
            for (int j = 0; j < fallingShape.getCoords()[i].length; j++) {
                if(fallingShape.getCoords()[i][j]!=0)
                {
                    if(table[i][j+3]!=0)
                    {
                        gameOver=true;
                        timer.stop();
drawer.drawGameOver(getGraphics());
                        playSound("Various-01.wav");
                        if(score>topten[9])
                        {topten[9]=score;
                            try {
                                FileWriter myWriter = new FileWriter("topten.txt");
                                for(int i1=0;i1<10;i1++)
                                myWriter.write(""+topten[i1]);
                                myWriter.close();
                                System.out.println("Successfully wrote to the file.");
                            } catch (IOException e) {
                                System.out.println("An error occurred.");
                                e.printStackTrace();
                            }
                        }

                    }
                }
    }}}
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_LEFT:fallingShape.addX(-1);break;
            case KeyEvent.VK_RIGHT:fallingShape.addX(+1);break;
            case KeyEvent.VK_DOWN:fallingShape.dropDown();break;
            case KeyEvent.VK_UP:fallingShape.rotate();break;
            case KeyEvent.VK_SPACE:fallingShape.reverse();break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                fallingShape.setShapeSpeed();
                break;
        }
    }
    public void playSound(String soundName)
    {
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile( ));
            Clip clip = AudioSystem.getClip( );
            clip.open(audioInputStream);
            clip.start( );
        }
        catch(Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace( );
        }
    }
}
