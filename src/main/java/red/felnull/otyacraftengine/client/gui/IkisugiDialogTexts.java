package red.felnull.otyacraftengine.client.gui;

import net.minecraft.client.gui.DialogTexts;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IkisugiDialogTexts {
    public static final ITextComponent ON = DialogTexts.field_240630_a_;
    public static final ITextComponent OFF = DialogTexts.field_240631_b_;
    public static final ITextComponent DONE = DialogTexts.field_240632_c_;
    public static final ITextComponent CANCEL = DialogTexts.field_240633_d_;
    public static final ITextComponent YES = DialogTexts.field_240634_e_;
    public static final ITextComponent NO = DialogTexts.field_240635_f_;
    public static final ITextComponent PROCEED = DialogTexts.field_240636_g_;
    public static final ITextComponent BACK = DialogTexts.field_240637_h_;

    public static final ITextComponent CRATE = new TranslationTextComponent("gui.create");
}
