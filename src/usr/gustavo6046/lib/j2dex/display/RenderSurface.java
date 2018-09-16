package usr.gustavo6046.lib.j2dex.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;

import javax.swing.JPanel;


/**
 * The JPanel surface to be painted on in RenderHandler. Use a Graphics2D
 * object, and avoid using RenderSurface objects directly!
 * 
 * @author Gustavo6046
 */
public class RenderSurface extends JPanel
{
    private static final long         serialVersionUID = -4627918565862930134L;
    private LinkedList<RenderHandler> renderHandlers   = new LinkedList<>();
    private ExtFrame                  frame;

    public RenderSurface(ExtFrame frame)
    {
        this.frame = frame;
        setFocusable(true);
    }

    public void add(RenderHandler rh)
    {
        renderHandlers.add(rh);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        for ( RenderHandler rh : renderHandlers )
            rh.Paint((Graphics2D) g);
        
        frame.bPainted = true;
    }
}
