package usr.gustavo6046.lib.j2dex.input;

import java.util.LinkedList;
import java.util.Optional;

import usr.gustavo6046.lib.j2dex.display.BlankCanvas;
import usr.gustavo6046.lib.j2dex.display.ExtFrame;


/**
 * Helps a lot when handling with keyboard events. Avoid using directly, though
 * (except when setting the default key descriptions, or getting one of them
 * with the help of an internal name).
 * 
 * @author Gustavo
 */
public class KeyboardInput
{
    private LinkedList<KeyInputHandler> handlers    = new LinkedList<>();
    private LinkedList<KeyDescription>  keyTypes    = new LinkedList<>();

    private long                        curKeyFlags = 0;

    /**
     * "Describes" a keyboard bit:
     * 
     * @param bitPos The left-shift position of the bit.
     * @return The corresponding description to the key bit, if any.
     */
    public Optional<KeyDescription> describe(int bitPos)
    {
        for ( KeyDescription kd : keyTypes )
            if ( kd.matches(bitPos) )
                return Optional.of(kd);

        return Optional.empty();
    }

    public void addDescription(int code, String humanName, String shortName)
    {
        keyTypes.add(new KeyDescription(humanName, shortName, code));
    }

    public void keyDown(int code, char keyChar)
    {
        // System.out.println(String.format("0x%h (+ 0x%h) => %h", curKeyFlags, 1 <<
        // code, (curKeyFlags & (1 << code))));

        if ( (curKeyFlags & (1 << code)) == 0 )
        {
            curKeyFlags |= (1 << code);

            for ( KeyInputHandler ih : handlers )
            {
                ih.instantInput(code, keyChar);
                ih.setInput(curKeyFlags);
            }
        }
    }

    public void keyUp(int code, char keyChar)
    {
        // System.out.println(String.format("0x%h (- 0x%h) => %h", curKeyFlags, 1 <<
        // code, (curKeyFlags & (1 << code))));

        if ( (curKeyFlags & (1 << code)) == (1 << code) )
        {
            curKeyFlags &= ~(1 << code);

            for ( KeyInputHandler ih : handlers )
            {
                ih.releaseInput(code, keyChar);
                ih.setInput(curKeyFlags);
            }
        }
    }

    public void add(KeyInputHandler ih)
    {
        handlers.add(ih);
    }

    public KeyboardInput(ExtFrame window)
    {
        if ( window != null )
        {
            window.kInput = this;
        }
    }

    public KeyboardInput()
    {
        this(null);
    }

    public void defaultKeys()
    {
        for ( int num = 0; num <= 9; num++ )
            addDescription(0x30 + num, String.format("Numbar %d", num), String.valueOf(num));

        for ( int num = 0; num <= 9; num++ )
            addDescription(0x60 + num, String.format("Numpad %d", num), String.format("Grey%d", num));

        addDescription(0x6A, "Numpad Asterisk", "GreyStar");
        addDescription(0x6F, "Numpad Bar", "GreyBar");
        addDescription(0x6B, "Numpad Plus", "GreyPlus");
        addDescription(0x6D, "Numpad Minus", "GreyMinus");
        addDescription(0xDE, "Tilde", "Tilde");
        addDescription(0x10, "Shift", "Shift");
        addDescription(0x11, "Control", "Ctrl");
        addDescription(0x12, "Alt", "Alt");
        addDescription(0x20C, "Windows/Super key", "Super");
        addDescription(0xA, "Enter", "Enter");
        addDescription(0x8, "Backspace", "Backspace");
        addDescription(0x7F, "Delete", "Del");
        addDescription(0x23, "End", "End");
        addDescription(0x24, "Home", "Home");
        addDescription(0x21, "Page Up", "PgUp");
        addDescription(0x22, "Page Down", "PgDn");
        addDescription(0x13, "Pause/Break", "Pause");
        addDescription(0x91, "Scroll Lock", "Scroll");
        addDescription(0x6E, "Numpad Comma", "GreyComma");
        addDescription(0x26, "Up Arrow", "KeyUp");
        addDescription(0x28, "Down Arrow", "KeyDown");
        addDescription(0x25, "Left Arrow", "KeyLeft");
        addDescription(0x27, "Right Arrow", "KeyRight");
        addDescription(0x1B, "Escape", "Esc");
        addDescription(0x20, "Space Bar", "Space");
        addDescription(0x5B, "Right Bracket", "RBracket");
        addDescription(0x5D, "Backslash", "Baskslash");
        addDescription(0x81, "Left Bracket", "LBracket");
        addDescription(0x83, "Quote", "Quote");
        addDescription(0x14, "Caps Lock", "Caps");

        for ( char letter = 'A'; letter < 'Z'; letter++ )
            addDescription((int) letter, String.valueOf(letter), String.valueOf(letter));
    }

    public static void main(String[] args)
    {
        BlankCanvas blankWindow = new BlankCanvas("Input Demo");
        KeyboardInput inp = new KeyboardInput(blankWindow);

        inp.defaultKeys();

        class DemoInputHandler implements KeyInputHandler
        {
            @Override
            public void setInput(long keyboardFlags)
            {
            }

            @Override
            public void instantInput(int code, char keyChar)
            {
                Optional<KeyDescription> desc = inp.describe(code);

                if ( desc.isPresent() )
                    System.out.println(String.format("Key down:\n  - Code: 0x%h\n  - Name: %s\n  - Short name: %s\n",
                            code, desc.get().humanName, desc.get().shortName));

                else
                    System.out.println(String.format("Unknown key down:\n  - Code: 0x%h\n", code));
            }

            @Override
            public void releaseInput(int code, char keyChar)
            {
                Optional<KeyDescription> desc = inp.describe(code);

                if ( desc.isPresent() )
                    System.out.println(String.format("Key up:\n  - Code: 0x%h\n  - Name: %s\n  - Short name: %s\n",
                            code, desc.get().humanName, desc.get().shortName));

                else
                    System.out.println(String.format("Unknown key up:\n  - Code: 0x%h\n", code));
            }
        }

        inp.add(new DemoInputHandler());
        blankWindow.run();
    }

    public Optional<KeyDescription> getDescription(String shortName)
    {
        for ( KeyDescription kd : keyTypes )
            if ( kd.shortName == shortName )
                return Optional.of(kd);

        return Optional.empty();
    }
}
