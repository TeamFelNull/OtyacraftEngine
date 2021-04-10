package red.felnull.otyacraftengine.fabric.mixin.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import red.felnull.otyacraftengine.api.event.client.OEClientHooks;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Inject(method = "renderGuiItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    private void renderGuiItemDecorations(Font font, ItemStack itemStack, int i, int j, String string, CallbackInfo ci) {
        if (OEClientHooks.onRenderGuiItemDecorationss((ItemRenderer) (Object) this, font, itemStack, i, j, string))
            ci.cancel();
    }
}
