package usr.gustavo6046.lib.j2dex.input;

/**
 * Allows for the usage of mouse input in your game. Just implement this
 * interface and its methods correspondingly, et voilá!
 * 
 * @author Gustavo6046
 */
public interface MouseInputHandler
{
    void onMouseMove(int deltaX, int deltaY, int newX, int newY);
    
    void onMouseDrag(int button, int deltaX, int deltaY, int newX, int newY);

    void onMouseClick(int button, int x, int y);

    void onMouseIn(int x, int y);

    void onMouseOut(int x, int y);

    void onMouseDown(int button, int x, int y);

    void onMouseUp(int button, int x, int y);
}
