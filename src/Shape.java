import java.awt.image.BufferedImage;
import java.util.Random;

public class Shape {
    private Drawer drawer;
    private int color;
    private BufferedImage tile;
    private int[][] coords;
    private Board borad;
    private long time,lastTime;
    private int shapeSpeed=Constants.shapeSpeed,nowSpeed;
    private int downSpeed=Constants.downSpeed;
    private boolean finishedMove=false,canMoveX=false;
private Vector vector;
public Shape( int[][] coords,BufferedImage tile, Board borad,int num,int color) {
    vector=new Vector(3,0);
        this.tile = tile;
        this.color=color;
        this.coords = coords;
        this.borad = borad;
        nowSpeed=shapeSpeed;
        time=0;
        lastTime=System.currentTimeMillis();
        drawer=new Drawer();
    }

    public BufferedImage getTile() {
        return tile;
    }
public void update()
{
    if(isFinishedMove())
    {
        for(int i=0;i<coords.length;i++) {
            for (int j = 0; j < coords[i].length; j++) {
                if(coords[i][j]!=0)
                {
borad.getTable()[vector.getY()+i][vector.getX()+j]=color;
                }
            }
        }
        borad.deleteLines();
        borad.setNewShape();
        borad.setNextShape();
    }
    time+=System.currentTimeMillis()-lastTime;
    lastTime=System.currentTimeMillis();
if(time>nowSpeed) {
    addY(1);
    time=0;
}

}


    public int[][] getCoords() {
        return coords;
    }
    public Vector getVector()
    {
        return this.vector;
    }
public void addX(int x)
{
    if(!(vector.getX()+x+coords[0].length>Constants.boardWidth)&&!(vector.getX()+x<0))
    {
        for(int i=0;i<coords.length;i++) {
            for (int j = 0; j < coords[i].length; j++) {
                if(coords[i][j]!=0)
                {
                    if(borad.getTable()[vector.getY()+i][j+vector.getX()+x]!=0)
                    {
                        canMoveX=false;
                    }
                }}}
        if(canMoveX)
        vector.addX(x);
    }


}
private void addY(int y)
{
    if(!(vector.getY()+y+coords.length>Constants.boardHeight))
    {
        for(int i=0;i<coords.length;i++) {
            for (int j = 0; j < coords[i].length; j++) {
                if(coords[i][j]!=0)
                {
                    if(borad.getTable()[vector.getY()+i+1][j+vector.getX()]!=0)
                    {
                        finishedMove=true;
                    }
                }}}
        if(!finishedMove)
        vector.addY(y);
    }
    else{
        finishedMove=true;
    }
    canMoveX=true;
}
public void reverse()
{
    vector.setX(3);
    vector.setY(0);
}
public void dropDown()
{
nowSpeed=downSpeed;
}
public void setShapeSpeed(){nowSpeed=shapeSpeed;}
public void rotate()
{
    if(isFinishedMove())
        return;
    if(vector.getX()+getRotated(coords)[0].length>Constants.boardWidth||vector.getY()+getRotated(coords).length>Constants.boardHeight)
        return;
    for(int i=0;i<getRotated(coords).length;i++)
    {
        for(int j=0;j<getRotated(coords)[0].length;j++)
        {
if(borad.getTable()[vector.getY()+i][vector.getX()+j]!=0)
{
    return;
}
        }
    }
coords=getRotated(coords);
}
private  int[][] getRotated(int[][] shape)
{
int [][]A=new int[shape[0].length][shape.length];
for(int i=0;i<A.length;i++)
{
    for(int j=0;j<A[0].length;j++)
    {
        A[i][j]=shape[j][i];
    }
}
A=getBack(A);
return A;
}
private    int[][] getBack(int[][] shape)
    {
        int mid=shape.length/2;
        for(int i=0;i<mid;i++)
        {
            int [] a=shape [i];
            shape[i]=shape[shape.length-1-i];
            shape[shape.length-1-i]=a;

        }
        return shape;
    }

    private boolean isFinishedMove() {
        return finishedMove;
    }
    public int getColor()
    {
        return this.color;
    }
}
