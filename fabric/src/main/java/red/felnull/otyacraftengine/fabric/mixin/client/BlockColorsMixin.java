package red.felnull.otyacraftengine.fabric.mixin.client;

import net.minecraft.client.color.block.BlockColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import red.felnull.otyacraftengine.api.event.client.OEClientHooks;

@Mixin(BlockColors.class)
public class BlockColorsMixin {
    @Inject(method = "createDefault", at = @At("RETURN"))
    private static void createDefault(CallbackInfoReturnable<BlockColors> cir) {
        OEClientHooks.onBlockColorsInit(cir.getReturnValue());
    }
}
