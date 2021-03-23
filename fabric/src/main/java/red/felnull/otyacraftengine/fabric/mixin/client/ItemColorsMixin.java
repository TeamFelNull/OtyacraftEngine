package red.felnull.otyacraftengine.mixin.fabric.client;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import red.felnull.otyacraftengine.api.event.client.OEClientHooks;

@Mixin(ItemColors.class)
public class ItemColorsMixin {
    @Inject(method = "createDefault", at = @At("RETURN"))
    private static void createDefault(BlockColors blockColors, CallbackInfoReturnable<ItemColors> cir) {
        OEClientHooks.onItemColorsInit(cir.getReturnValue(), blockColors);
    }
}
