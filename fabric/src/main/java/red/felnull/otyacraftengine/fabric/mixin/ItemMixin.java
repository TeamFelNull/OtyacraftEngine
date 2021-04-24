package red.felnull.otyacraftengine.fabric.mixin;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import red.felnull.otyacraftengine.util.IKSGRegistryUtil;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "getFoodProperties", at = @At("RETURN"), cancellable = true)
    private void getFoodProperties(CallbackInfoReturnable<FoodProperties> cir) {
        Item item = (Item) (Object) this;
        if (IKSGRegistryUtil.REPLACE_FOODS.containsKey(item)) {
            cir.setReturnValue(IKSGRegistryUtil.REPLACE_FOODS.get(item));
        }
    }

    @Inject(method = "isEdible", at = @At("RETURN"), cancellable = true)
    private void isEdible(CallbackInfoReturnable<Boolean> cir) {
        Item item = (Item) (Object) this;
        if (IKSGRegistryUtil.REPLACE_FOODS.containsKey(item)) {
            cir.setReturnValue(IKSGRegistryUtil.REPLACE_FOODS.get(item) != null);
        }
    }
}
