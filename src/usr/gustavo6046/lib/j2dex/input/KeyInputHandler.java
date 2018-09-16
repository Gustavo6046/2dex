package usr.gustavo6046.lib.j2dex.input;

/**
 * You *WILL* want to implement this interface when dealing with direct key
 * input, e.g. game controls. For a higher level text input see TextInput.
 * 
 * @author Gustavo6046
 */
public interface KeyInputHandler
{
    void setInput(long keyFlags);

    void instantInput(int keyCode, char keyChar);

    void releaseInput(int keyCode, char keyChar);
}
