package red.felnull.otyacraftengine.client.gui;

import net.minecraft.client.gui.DialogTexts;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IkisugiDialogTexts {
    public static final ITextComponent ON = DialogTexts.OPTIONS_ON;
    public static final ITextComponent OFF = DialogTexts.OPTIONS_OFF;
    public static final ITextComponent DONE = DialogTexts.GUI_DONE;
    public static final ITextComponent CANCEL = DialogTexts.GUI_CANCEL;
    public static final ITextComponent YES = DialogTexts.GUI_YES;
    public static final ITextComponent NO = DialogTexts.GUI_NO;
    public static final ITextComponent PROCEED = DialogTexts.GUI_PROCEED;
    public static final ITextComponent BACK = DialogTexts.GUI_BACK;

    public static final ITextComponent CRATE = new TranslationTextComponent("gui.create");
    public static final ITextComponent TRAINING = new TranslationTextComponent("gui.training");
    public static final ITextComponent JOIN = new TranslationTextComponent("gui.join");
    public static final ITextComponent NEXT = new TranslationTextComponent("gui.next");
    public static final ITextComponent START = new TranslationTextComponent("gui.start");
    public static final ITextComponent STOP = new TranslationTextComponent("gui.stop");

}
