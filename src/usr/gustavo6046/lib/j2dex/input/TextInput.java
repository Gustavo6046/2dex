package usr.gustavo6046.lib.j2dex.input;

import java.awt.Graphics2D;

import usr.gustavo6046.lib.j2dex.display.BlankCanvas;
import usr.gustavo6046.lib.j2dex.display.RenderHandler;


/**
 * Subclass this when dealing with text input in text fields and other similar
 * things.
 * 
 * This way, you have some abstraction, which you may use to deal with text
 * inputs at a slightly higher level.
 * 
 * You will want to implement onInput and onEnter. You might also optionally
 * want to implement onEsc, for e.g. pop-up dialogs (which may use TextInput
 * just to check for Esc presses, or might as well have some text input field
 * within them themselves), or onCapsLock (for e.g. toggling the "always run"
 * option when Caps Lock is activated).
 * 
 * @author Gustavo6046
 */
abstract public class TextInput implements KeyInputHandler
{
    private boolean      bShift, bAlt, bCtrl, bCaps;
    public String        buffer = "";
    public KeyboardInput descriptor;

    public static void main(String[] args)
    {
        BlankCanvas blankWindow = new BlankCanvas("Text Input Demo", 700, 120);

        blankWindow.kInput.defaultKeys();
        
        class DemoTextInputHandler implements RenderHandler
        {
            String buffer = "";

            @Override
            public void Paint(Graphics2D g)
            {
                g.drawString(buffer, 5, 20);
            }
        }

        class DemoTextInput extends TextInput
        {
            DemoTextInputHandler myRenderHandler = new DemoTextInputHandler();

            public DemoTextInput(KeyboardInput desc)
            {
                super(desc);
                blankWindow.surf.add(myRenderHandler);
            }

            @Override
            public void releaseInput(int keyCode, char keyChar)
            {
                // do nothing here, at the moment.
            }

            @Override
            public void onInput()
            {
                myRenderHandler.buffer = buffer;
            }

            @Override
            public void onEnter(String buffer2)
            {
                System.out.println(buffer2);
                buffer = "";
                myRenderHandler.buffer = "";
            }

            @Override
            public void onEsc()
            {
                blankWindow.stop();
            }

            @Override
            public void onCapsLock()
            {
                
            }
        }

        blankWindow.kInput.add(new DemoTextInput(blankWindow.kInput));
        blankWindow.run();
    }

    public TextInput(KeyboardInput desc)
    {
        descriptor = desc;
    }

    @Override
    public void setInput(long keyFlags)
    {
        bShift = descriptor.getDescription("Shift").get().inside(keyFlags);
        bAlt = descriptor.getDescription("Alt").get().inside(keyFlags);
        bCtrl = descriptor.getDescription("Ctrl").get().inside(keyFlags);
    }

    @Override
    public void instantInput(int keyCode, char keyChar)
    {
        if ( descriptor.getDescription("Enter").get().matches(keyCode) )
            onEnter(buffer);

        else if ( descriptor.getDescription("Caps").get().matches(keyCode) )
        {
            bCaps = !bCaps;
            onCapsLock();
        }

        else if ( descriptor.getDescription("Esc").get().matches(keyCode) )
            onEsc();

        else if ( descriptor.getDescription("Backspace").get().matches(keyCode) )
        {
            buffer = buffer.substring(0, buffer.length() - 1);
            onInput();
        }

        else if ( keyChar != 0xFFFF )
        {
            System.out.println(bCaps);

            if ( bShift ^ bCaps )
                buffer += String.valueOf(keyChar).toUpperCase();

            else
                buffer += String.valueOf(keyChar).toLowerCase();

            onInput();
        }
    }

    abstract public void onCapsLock();

    abstract public void onInput();

    abstract public void onEsc();

    abstract public void onEnter(String buffer2);

    public boolean shiftDown()
    {
        return bShift;
    }

    public boolean altDown()
    {
        return bAlt;
    }

    public boolean ctrlDown()
    {
        return bCtrl;
    }

    public boolean capsLock()
    {
        return bCaps;
    }
}
