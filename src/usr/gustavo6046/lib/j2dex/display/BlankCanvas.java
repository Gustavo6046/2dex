package usr.gustavo6046.lib.j2dex.display;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import usr.gustavo6046.lib.j2dex.input.MouseInputHandler;


/**
 * An useful ExtFrame subclass, that will help when quickly prototyping test
 * features of your game (or of the library, e.g. text input, et al), when you
 * don't need visually more than a white box you can paint on.
 * 
 * @author Gustavo6046
 */
public class BlankCanvas extends ExtFrame
{
    private static final long serialVersionUID = 1L;

    public BlankCanvas(String motive)
    {
        initUI(motive, 200, 200);
    }

    public BlankCanvas(String motive, int width, int height)
    {
        initUI(motive, width, height);
    }

    private void initUI(String motive, int width, int height)
    {
        setTitle(String.format("Blank Canvas (%s)", motive));
        setSize(width, height);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args)
    {
        BlankCanvas window = new BlankCanvas("Rainbow Demo", 500, 500);

        class ColorRenderer implements RenderHandler
        {
            protected double x       = 0;
            @SuppressWarnings("unused")
            protected double elapsed = 0.0;
            protected double tdiff;
            protected int    mx      = 0, my = 0;
            private boolean  bDown   = false;

            class ColorTickHandler implements TickHandler
            {
                public ColorRenderer rend;

                public ColorTickHandler(ColorRenderer r)
                {
                    rend = r;
                }

                @Override
                public void tick(double timeDelta)
                {
                    rend.tdiff = timeDelta;
                    rend.elapsed += timeDelta;
                }
            }

            class WhiteCircleHandler implements MouseInputHandler
            {
                @Override
                public void onMouseMove(int deltaX, int deltaY, int newX, int newY)
                {
                    mx = newX;
                    my = newY;
                }

                @Override
                public void onMouseDrag(int button, int deltaX, int deltaY, int newX, int newY)
                {
                    mx = newX;
                    my = newY;
                }

                @Override
                public void onMouseClick(int button, int x, int y)
                {
                }

                @Override
                public void onMouseIn(int x, int y)
                {
                }

                @Override
                public void onMouseOut(int x, int y)
                {
                }

                @Override
                public void onMouseDown(int button, int x, int y)
                {
                    bDown = true;
                }

                @Override
                public void onMouseUp(int button, int x, int y)
                {
                    bDown = false;
                }
            }

            public ColorRenderer()
            {
                window.mInput.add(new WhiteCircleHandler());
                window.add(new ColorTickHandler(this));
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * usr.gustavo6046.lib.j2dex.display.RenderHandler#Paint(java.awt.Graphics2D)
             */
            @Override
            public void Paint(Graphics2D g)
            {
                double nx = x;
                double mnx = mx + Math.random() * 30;
                double mny = my + Math.random() * 30;

                for ( int dx = -75; dx < window.getWidth() + 75; dx++ )
                {
                    for ( int dy = 0; dy < window.getHeight(); dy++ )
                    {
                        Color a = Color.getHSBColor((float) ((int) x % 256) / 256, 0.7f, 0.45f);

                        int rx = dx + (int) (Math.sin(dy * Math.PI * 2 / window.getHeight()) * 75);

                        float bright;

                        if ( bDown )
                            bright = (float) Math.sqrt((dx - mnx) * (dx - mnx) + (dy - mny) * (dy - mny));

                        else
                            bright = (float) Math.sqrt((dx - mx) * (dx - mx) + (dy - my) * (dy - my));

                        bright = Math.max(0, Math.min(1f, 0.1f + (float) (85 - bright) / 85));

                        if ( bright > 0 )
                            g.setColor(new Color(Math.min(1f, a.getRed() / 255f + bright),
                                    Math.min(1f, a.getGreen() / 255f + bright),
                                    Math.min(1f, a.getBlue() / 255f + bright)));

                        else
                            g.setColor(a);

                        g.drawRect(rx, dy, 1, 1);
                    }

                    x = nx + dx / 4;
                }

                x = nx + tdiff * 50;
                System.out.println(String.format("%f => %f (%f)", elapsed, x, tdiff));
            }
        }

        window.surf.add(new ColorRenderer());
        window.run();
    }
}
