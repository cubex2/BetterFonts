package cubex2.ttfr;

public interface IBFFontRenderer
{
    StringRenderer getStringRenderer();

    void setStringRenderer(StringRenderer value);

    boolean isDropShadowEnabled();

    void setDropShadowEnabled(boolean value);

    boolean isEnabled();

    void setEnabled(boolean value);
}
