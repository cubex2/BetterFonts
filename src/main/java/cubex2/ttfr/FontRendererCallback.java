package cubex2.ttfr;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@SuppressWarnings("unused")
public class FontRendererCallback
{
    public static boolean betterFontsEnabled = true;

    @SuppressWarnings("unused")
    public static void constructor(IBFFontRenderer font, ResourceLocation location)
    {
        // Disable for splash font renderer
        if (((FontRenderer) font).getClass() != FontRenderer.class) return;

        if (location.getResourcePath().equalsIgnoreCase("textures/font/ascii.png") && font.getStringRenderer() == null)
        {
            int[] colorCode = ObfuscationReflectionHelper.getPrivateValue(FontRenderer.class, (FontRenderer) font, "colorCode", "field_78285_g", "f");
            StringCache cache = new StringCache();
            StringRenderer renderer = new StringRenderer(cache, colorCode);
            font.setStringRenderer(renderer);
            Config.applyFont(font);
        }
    }

    @SuppressWarnings("unused")
    public static String bidiReorder(IBFFontRenderer font, String text)
    {
        if (betterFontsEnabled && font.getStringRenderer() != null)
        {
            return text;
        }

        try
        {
            Bidi bidi = new Bidi((new ArabicShaping(8)).shape(text), 127);
            bidi.setReorderingMode(0);
            return bidi.writeReordered(2);
        } catch (ArabicShapingException var3)
        {
            return text;
        }
    }
}
