package usr.gustavo6046.lib.j2dex.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import usr.gustavo6046.lib.j2dex.display.BlankCanvas;


/**
 * This class will allow you to make use of mouse events, like mouse input,
 * directly into your game or project.
 * 
 * @author Gustavo6046
 */
public class MouseInput implements MouseListener, MouseMotionListener
{
    private LinkedList<MouseInputHandler> handlers = new LinkedList<>();
    private int                           lastX    = 0, lastY = 0;

    public void add(MouseInputHandler h)
    {
        handlers.add(h);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent evt)
    {
        for ( MouseInputHandler mih : handlers )
            mih.onMouseClick(evt.getButton(), evt.getX(), evt.getY());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseEntered(MouseEvent evt)
    {
        for ( MouseInputHandler mih : handlers )
            mih.onMouseIn(evt.getX(), evt.getY());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseExited(MouseEvent evt)
    {
        for ( MouseInputHandler mih : handlers )
            mih.onMouseOut(evt.getX(), evt.getY());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent evt)
    {
        for ( MouseInputHandler mih : handlers )
            mih.onMouseDown(evt.getButton(), evt.getX(), evt.getY());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent evt)
    {
        for ( MouseInputHandler mih : handlers )
            mih.onMouseUp(evt.getButton(), evt.getX(), evt.getY());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseDragged(MouseEvent evt)
    {
        for ( MouseInputHandler mih : handlers )
            mih.onMouseDrag(evt.getButton(), evt.getX() - lastX, evt.getY() - lastY, evt.getX(), evt.getY());

        lastX = evt.getX();
        lastY = evt.getY();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseMoved(MouseEvent evt)
    {
        for ( MouseInputHandler mih : handlers )
            mih.onMouseMove(evt.getX() - lastX, evt.getY() - lastY, evt.getX(), evt.getY());

        lastX = evt.getX();
        lastY = evt.getY();
    }

    public static void main(String[] args)
    {
        BlankCanvas blankWindow = new BlankCanvas("Mouse Input Demo");

        class DemoMouseInputHandler implements MouseInputHandler
        {
            @Override
            public void onMouseMove(int deltaX, int deltaY, int newX, int newY)
            {
                System.out.println(String.format("Moved <%d, %d> towards (%d, %d)!", deltaX, deltaY, newX, newY));
            }

            @Override
            public void onMouseDrag(int button, int deltaX, int deltaY, int newX, int newY)
            {
                System.out.println(String.format("Dragged button %d <%d, %d> towards (%d, %d)!", button, deltaX, deltaY,
                        newX, newY));
            }

            @Override
            public void onMouseClick(int button, int x, int y)
            {
                System.out.println(String.format("Clicked button %d at (%d, %d)!", button, x, y));
            }

            @Override
            public void onMouseIn(int x, int y)
            {
                System.out.println(String.format("Mouse entered window at (%d, %d)!", x, y));
            }

            @Override
            public void onMouseOut(int x, int y)
            {
                System.out.println(String.format("Mouse left window at (%d, %d)!", x, y));
            }

            @Override
            public void onMouseDown(int button, int x, int y)
            {
                System.out.println(String.format("Pressing button %d at (%d, %d)!", button, x, y));
            }

            @Override
            public void onMouseUp(int button, int x, int y)
            {
                System.out.println(String.format("Released button %d at (%d, %d)!", button, x, y));
            }
        }

        blankWindow.mInput.add(new DemoMouseInputHandler());
        blankWindow.run();
    }
}
