package red.felnull.otyacraftengine.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import red.felnull.otyacraftengine.item.IElytraLayerItem;

public class CustomElytraLayer<T extends LivingEntity, M extends EntityModel<T>> extends ElytraLayer<T, M> {
    public CustomElytraLayer(IEntityRenderer rendererIn) {
        super(rendererIn);
    }

    @Override
    public boolean shouldRender(ItemStack stack, T entity) {
        return stack.getItem() instanceof IElytraLayerItem;
    }

    @Override
    public ResourceLocation getElytraTexture(ItemStack stack, T entity) {
        IElytraLayerItem ei = (IElytraLayerItem) stack.getItem();
        return ei.getElytraLayerTextuer();
    }
}
