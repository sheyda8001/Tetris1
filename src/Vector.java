public class Vector {

    private int x,y;
public Vector(int x,int y)
{
    this.x=x;
    this.y=y;
}

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

   protected void addX(int x)
    {
        this.x+=x;
    }
    protected  void addY(int y)
    {
        this.y+=y;
    }
}
