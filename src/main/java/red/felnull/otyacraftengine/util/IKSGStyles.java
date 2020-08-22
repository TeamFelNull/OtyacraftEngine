package red.felnull.otyacraftengine.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class IKSGStyles {
    public static final Style BLANK = Style.field_240709_b_;

    public static Style withColor(Color cl) {
        return BLANK.func_240718_a_(cl);
    }

    public static Style withColor(TextFormatting tf) {
        return BLANK.func_240712_a_(tf);
    }

    public static Style withBold(Boolean b) {
        return BLANK.func_240713_a_(b);
    }

    public static Style withItalic(Boolean b) {
        return BLANK.func_240722_b_(b);
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
        return BLANK.func_240715_a_(ce);
    }

    public static Style withHoverEvent(HoverEvent he) {
        return BLANK.func_240716_a_(he);
    }

    public static Style withInsertion(String st) {
        return BLANK.func_240714_a_(st);
    }

    public static Style withFont(ResourceLocation rl) {
        return BLANK.func_240719_a_(rl);
    }

    public static IFormattableTextComponent withStyle(IFormattableTextComponent tftc, Style style) {
        return tftc.func_240703_c_(style);
    }
}
