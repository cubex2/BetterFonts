package cubex2.ttfr;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

public class ConfigGui extends GuiConfig
{
    public ConfigGui(GuiScreen parentScreen)
    {
        super(parentScreen,
              new ConfigElement(Config.cfg.getCategory(CATEGORY_GENERAL)).getChildElements(),
              "betterfonts", false, false, GuiConfig.getAbridgedConfigPath(Config.cfg.toString()));
    }
}
