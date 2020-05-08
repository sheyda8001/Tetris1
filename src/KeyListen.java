import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public  class KeyListen implements KeyListener {
    private Shape fallingShape;
public KeyListen(Shape shape)
{
    fallingShape=shape;
}
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_LEFT:fallingShape.getVector().addX(-1);break;
            case KeyEvent.VK_RIGHT:fallingShape.getVector().addX(+1);break;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
