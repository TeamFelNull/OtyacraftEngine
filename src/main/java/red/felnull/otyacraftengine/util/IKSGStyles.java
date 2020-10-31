package red.felnull.otyacraftengine.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class IKSGStyles {
    public static final Style BLANK = Style.EMPTY;

    public static Style withColor(Color cl) {
        return BLANK.setColor(cl);
    }

    public static Style withColor(TextFormatting tf) {
        return BLANK.setFormatting(tf);
    }

    public static Style withBold(Boolean b) {
        return BLANK.setBold(b);
    }

    public static Style withItalic(Boolean b) {
        return BLANK.setItalic(b);
    }

    public static Style withUnderlined(Boolean b) {
        return BLANK.setUnderlined(b);
    }

    public static Style withStrikethrough(Boolean b) {
        return BLANK.setStrikethrough(b);
    }

    public static Style withObfuscated(Boolean b) {
        return BLANK.setObfuscated(b);
    }

    public static Style withClickEvent(ClickEvent ce) {
        return BLANK.setClickEvent(ce);
    }

    public static Style withHoverEvent(HoverEvent he) {
        return BLANK.setHoverEvent(he);
    }

    public static Style withInsertion(String st) {
        return BLANK.setInsertion(st);
    }

    public static Style withFont(ResourceLocation rl) {
        return BLANK.setFontId(rl);
    }

    public static IFormattableTextComponent withStyle(IFormattableTextComponent tftc, Style style) {
        return tftc.mergeStyle(style);
    }

    public static IFormattableTextComponent withStyle(IFormattableTextComponent tftc, Style... styles) {
        IFormattableTextComponent textComponent = tftc;
        for (Style stylef : styles) {
            textComponent = withStyle(textComponent, stylef);
        }
        return textComponent;
    }
}
