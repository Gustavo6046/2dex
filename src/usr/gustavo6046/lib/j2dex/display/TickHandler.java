package usr.gustavo6046.lib.j2dex.display;

/**
 * Another simple but important interface; this will allow you to add logic to
 * your game every time ExtFrame renders (those events are called ticks).
 * 
 * @author Gustavo6046
 */
public interface TickHandler
{
    /**
     * This function will be called every tick.
     * 
     * @param d Time elapsed since the last tick.
     */
    public void tick(double d);
}
