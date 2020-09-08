package red.felnull.otyacraftengine.item;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import red.felnull.otyacraftengine.client.util.IKSGTextureUtil;

//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TestItem extends ElytraItem implements IElytraLayerItem {

    // public static final Item TEST = new TestItem(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(OtyacraftEngine.MODID, "test");

    public TestItem(Properties properties) {
        super(properties.maxStackSize(1).maxDamage(10));
        ItemModelsProperties.func_239418_a_(this, new ResourceLocation("broken"),
                new IItemPropertyGetter() {

                    @Override
                    public float call(ItemStack stack, ClientWorld arg1, LivingEntity arg2) {
                        return stack.getDamage() < stack.getMaxDamage() - 1 ? 0 : 1;
                    }

                });
    }

    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> e) {
        //   e.getRegistry().register(TEST);
    }

    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return EquipmentSlotType.CHEST;
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return !this.isDamageable() || super.canElytraFly(stack, entity);
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {

        return !this.isDamageable() || super.elytraFlightTick(stack, entity, flightTicks);
    }

    @Override
    public ResourceLocation getElytraLayerTextuer() {
        return IKSGTextureUtil.getLoadingIconTextuer();
    }


}
