import java.awt.*;

public class Drawer {
    public void update()
    {

    }
    public void drawShape(Graphics g,Shape shape)
    {

        for(int i=0;i<shape.getCoords().length;i++)
        {
            for(int j=0;j<shape.getCoords()[i].length;j++)
            {
                if(shape.getCoords()[i][j]!=0)
                {
                    g.drawImage(shape.getTile(),j*Constants.tileSize+shape.getVector().getX()*Constants.tileSize,i*Constants.tileSize+shape.getVector().getY()*Constants.tileSize,null);
                }
            }
        }
    }
    public void drawGameOver(Graphics graphics) {
        String prompt = "Game Over! ";
        Font font = new Font("Helvetica", Font.BOLD, 50);
        FontMetrics fontMetrics = graphics.getFontMetrics(font);
        int width = fontMetrics.stringWidth(prompt);
        graphics.setFont(font);
        graphics.setColor(Color.PINK);
        graphics.drawString(prompt, (Constants.WIDTH - width) / 2, (Constants.HEIGHT - 50) / 2);

    }
    public void drawNextShape(Graphics g,Shape shape)
    {
g.drawString("NEXT SHAPE : ",350,150);
        for(int i=0;i<shape.getCoords().length;i++)
        {
            for(int j=0;j<shape.getCoords()[i].length;j++)
            {
                if(shape.getCoords()[i][j]!=0)
                {
                    g.drawImage(shape.getTile(),j*Constants.tileSize+shape.getVector().getX()*Constants.tileSize+250,i*Constants.tileSize+shape.getVector().getY()*Constants.tileSize+200,null);
                }
            }
        }
    }
}
