package cubex2.ttfr;

import net.minecraftforge.common.config.Configuration;

import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class Config
{
    /** Java's logical font names that can always be used inside the font.name property of the configuration file. */
    private static final String LOGICAL_FONTS[] = {"Serif", "SansSerif", "Dialog", "DialogInput", "Monospaced"};

    /** List of all fonts on the system + logical font names; used for checking if font.name in the config file is valid. */
    private static final Font ALL_FONTS[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    private static final String ALL_FONT_NAMES[] = getAllFontNames();

    private static Configuration cfg;

    public static String fontName;
    public static int fontSize;
    public static boolean antiAlias;
    public static boolean dropShadow;

    public static void load(File file)
    {
        cfg = new Configuration(file);
        try
        {
            cfg.load();

            fontName = getActualFontName(cfg.getString("fontName", Configuration.CATEGORY_GENERAL, "SansSerif", "Valid font names: " + Arrays.toString(ALL_FONT_NAMES), ALL_FONT_NAMES));
            fontSize = cfg.getInt("fonSize", Configuration.CATEGORY_GENERAL, 18, 1, 100, "");
            antiAlias = cfg.getBoolean("antiAlias", Configuration.CATEGORY_GENERAL, false, "");
            dropShadow = cfg.getBoolean("dropShadow", Configuration.CATEGORY_GENERAL, true, "Setting this to \"false\" will disable drop shadows completely");

        } finally
        {
            cfg.save();
        }
    }

    private static String[] getAllFontNames()
    {
        String[] names = new String[ALL_FONTS.length];
        for (int i = 0; i < names.length; i++)
        {
            names[i] = ALL_FONTS[i].getName();
        }
        return names;
    }

    private static String getActualFontName(String fontName)
    {
        /*
         * Trim whitespace; convert to lowercase so the partial name lookups with indexOf() are case insensitive.
         * Max OSX also puts a - between the font family and style in the string returned by getName() so trim those too.
         */
        String searchName = fontName.replaceAll("[- ]", "").toLowerCase();

        /* Java's logical font names are always allowed in the font.name property */
        for (String font : LOGICAL_FONTS)
        {
            if (font.compareToIgnoreCase(searchName) == 0)
            {
                return font;
            }
        }

        /* Some fonts report their plain variety with "Medium" in the name so try exact search on that too */
        String altSearchName = searchName + " medium";

        /* If a font partially matches a user requested name, remember it here in case there are no exact matches */
        String partialMatch = null;

        /* Search through all available fonts installed on the system */
        for (Font font : ALL_FONTS)
        {
            /* Always prefer an exact match on the font face name which terminates the search with a result */
            String name = font.getName().replaceAll("[- ]", "");
            if (name.compareToIgnoreCase(searchName) == 0 || name.compareToIgnoreCase(altSearchName) == 0)
            {
                return font.getName();
            }

            /*
             * Remember partial name matches so they can be returned if no other exact matche exists. This match is done
             * with both the font family and font face concatenated together to handle the weird case of the "Latin Wide"
             * font. Always prefer to partial match the shortest possible font face name to match "Times New Roman" before
             * "Times New Roman Bold" for instance.
             */
            if ((name + font.getFamily()).replaceAll("[- ]", "").toLowerCase().contains(searchName))
            {
                if (partialMatch == null || partialMatch.length() > font.getName().length())
                {
                    partialMatch = font.getName();
                }
            }
        }

        /* If not exact match was found, then return the last partial match that was made */
        if (partialMatch != null)
        {
            return partialMatch;
        }

        System.out.println("Invalid font. Using " + fontName + " instead");
        return fontName;
    }
}
