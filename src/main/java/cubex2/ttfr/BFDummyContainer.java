package cubex2.ttfr;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Arrays;

public class BFDummyContainer extends DummyModContainer
{
    public BFDummyContainer()
    {
        super(new ModMetadata());
        ModMetadata meta = getMetadata();
        meta.modId = "betterfonts";
        meta.name = "BetterFonts";
        meta.version = "1.7.4";
        meta.credits = "thvortex for original codes, iSuzutsuki";
        meta.authorList = Arrays.asList("CubeX2");
        meta.description = "OpenType font support for Minecraft";
        meta.url = "http://minecraft.curseforge.com/projects/truetype-font-replacement";
        meta.screenshots = new String[0];
        meta.logoFile = "";
    }

    @Override
    public String getGuiClassName()
    {
        return "cubex2.ttfr.ConfigGuiFactory";
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
        bus.register(this);
        return true;
    }

    @Subscribe
    public void modConstruction(FMLConstructionEvent evt)
    {

    }

    @Subscribe
    public void init(FMLInitializationEvent evt)
    {
    }


    @Subscribe
    public void preInit(FMLPreInitializationEvent evt)
    {
        Config.load(evt.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new Config());
    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent evt)
    {

    }
}
