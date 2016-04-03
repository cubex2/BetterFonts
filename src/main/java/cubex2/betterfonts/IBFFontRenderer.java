package cubex2.betterfonts;

public interface IBFFontRenderer
{
    StringCache getStringCache();

    void setStringCache(StringCache value);

    boolean isDropShadowEnabled();

    void setDropShadowEnabled(boolean value);

    boolean isEnabled();

    void setEnabled(boolean value);
}
