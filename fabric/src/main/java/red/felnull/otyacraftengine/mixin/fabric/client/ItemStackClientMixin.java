package red.felnull.otyacraftengine.mixin.fabric.client;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import red.felnull.otyacraftengine.api.event.client.OEClientHooks;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackClientMixin {
    @Inject(method = "getTooltipLines", at = @At("RETURN"))
    private void getTooltipLines(Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir) {
        OEClientHooks.onItemTooltip((ItemStack) (Object) this, player, cir.getReturnValue(), tooltipFlag);
    }
}
