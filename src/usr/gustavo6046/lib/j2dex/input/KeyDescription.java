package usr.gustavo6046.lib.j2dex.input;

/**
 * A small class that will help you when labeling and finding keys.
 * 
 * @author Gustavo6046
 */
public class KeyDescription
{
    public String humanName;
    public String shortName;
    public int    myBit;

    public KeyDescription(String readableName, String internalName, int bitPos)
    {
        shortName = internalName;
        humanName = readableName;
        myBit = bitPos;
    }

    public long getBit()
    {
        return (long) 1 << myBit;
    }

    public boolean inside(long flags)
    {
        return (flags & (getBit())) == getBit();
    }

    public boolean matches(int code)
    {
        return code == myBit;
    }
}
