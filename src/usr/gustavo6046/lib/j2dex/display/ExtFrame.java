package usr.gustavo6046.lib.j2dex.display;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.JFrame;

import usr.gustavo6046.lib.j2dex.input.KeyboardInput;
import usr.gustavo6046.lib.j2dex.input.MouseInput;


/**
 * This is the class you want to run when you are creating your own application.
 * 
 * I recommend, however, to just initialize an ExtFrame instead, and place your
 * logic inside RenderHandler, KeyInputHandler and TickHandler implementations
 * instead.
 * 
 * @author Gustavo6046
 */
public class ExtFrame extends JFrame implements Runnable
{
    private static final long       serialVersionUID = 5962331540375648461L;
    public KeyboardInput            kInput           = new KeyboardInput(this);
    public MouseInput               mInput           = new MouseInput();
    private LinkedList<TickHandler> tickHandlers     = new LinkedList<>();
    private double                  minDelay         = 1. / 60;
    public RenderSurface            surf;
    private int                     _stop            = -1;
    boolean                         bPainted         = false;

    public void stop()
    {
        _stop = 0;
    }

    /**
     * Registers a tick handler to be called every tick.
     * 
     * @param handler TickHandler to be registered.
     */
    public void add(TickHandler handler)
    {
        tickHandlers.add(handler);
    }

    public void stop(int status)
    {
        _stop = status;
    }

    public ExtFrame()
    {
        surf = new RenderSurface(this);
    }

    private class MyInputHandler extends KeyAdapter
    {
        public void keyPressed(KeyEvent e)
        {
            kInput.keyDown(e.getKeyCode(), e.getKeyChar());
        }

        public void keyReleased(KeyEvent e)
        {
            kInput.keyUp(e.getKeyCode(), e.getKeyChar());
        }
    }

    public void setDelaySecs(double delay)
    {
        this.minDelay = delay;
    }

    public void setDelayMillis(long delay)
    {
        this.minDelay = (double) (delay) / 1000;
    }

    @Override
    public void run()
    {
        add(surf);

        long beforeTime = System.currentTimeMillis();
        int timeDiff = 0;

        surf.addKeyListener(new MyInputHandler());
        surf.addMouseListener(mInput);
        surf.addMouseMotionListener(mInput);

        setVisible(true);

        while ( _stop < 0 )
        {
            repaint();

            do
            {
                long realDelay = (long) Math.max(0., minDelay * 1000);

                if ( realDelay > 0 )
                {
                    try
                    {
                        Thread.sleep(realDelay);
                    }

                    catch ( InterruptedException e )
                    {
                        break;
                    }
                }
            }

            while ( !bPainted );

            bPainted = false;

            for ( TickHandler th : tickHandlers )
                th.tick((double) timeDiff / 1000);

            long t = System.currentTimeMillis();
            timeDiff = (int) (t - beforeTime);
            beforeTime = t;
        }

        System.exit(0);
    }
}
