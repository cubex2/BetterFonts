package cubex2.ttfr;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.DefaultGuiFactory;

public class ConfigGuiFactory extends DefaultGuiFactory
{
    public ConfigGuiFactory()
    {
        super("betterfonts", "Better Fonts");
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen)
    {
        return new ConfigGui(parentScreen);
    }
}
