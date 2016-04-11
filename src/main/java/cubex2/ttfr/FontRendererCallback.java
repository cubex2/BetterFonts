package cubex2.ttfr;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class FontRendererCallback
{
    public static boolean betterFontsEnabled = true;

    public static void constructor(IBFFontRenderer font, ResourceLocation location)
    {
        // Disable for splash font renderer
        if (((FontRenderer) font).getClass() != FontRenderer.class) return;

        if (location.getResourcePath().equalsIgnoreCase("textures/font/ascii.png") && font.getStringCache() == null)
        {
            font.setDropShadowEnabled(Config.dropShadow);

            int[] colorCode = ObfuscationReflectionHelper.getPrivateValue(FontRenderer.class, (FontRenderer) font, "colorCode", "field_78285_g", "f");
            font.setStringCache(new StringCache(colorCode));
            font.getStringCache().setDefaultFont(Config.fontName, Config.fontSize, Config.antiAlias);
        }
    }

    public static String bidiReorder(IBFFontRenderer font, String text)
    {
        if (betterFontsEnabled && font.getStringCache() != null)
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
