package red.felnull.otyacraftengine.util;

import me.shedaniel.architectury.registry.DeferredRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import red.felnull.otyacraftengine.fluid.FluidData;
import red.felnull.otyacraftengine.fluid.FluidProperties;
import red.felnull.otyacraftengine.fluid.IkisugiFluid;

import java.util.HashMap;
import java.util.Map;

public class IKSGRegistryUtil {
    private static final Map<ResourceLocation, IkisugiFluid> FLUIDS = new HashMap<>();
    private static final Map<ResourceLocation, FlowingFluid> FLOWINGFLUIDS = new HashMap<>();
    private static final Map<ResourceLocation, Item> BUCKETITEMS = new HashMap<>();
    private static final Map<ResourceLocation, Block> LIQUIDBLOCKS = new HashMap<>();
    public static final Map<Item, FoodProperties> REPLACE_FOODS = new HashMap<>();

    public static IkisugiFluid registerFluid(ResourceLocation name, FluidProperties properties, CreativeModeTab tab, DeferredRegister<Fluid> fluidRegister, DeferredRegister<Item> itemRegister, DeferredRegister<Block> blockRegister) {
        FLUIDS.put(name, new IkisugiFluid(properties, new FluidData(() -> FLUIDS.get(name), () -> FLOWINGFLUIDS.get(name), () -> BUCKETITEMS.get(name), () -> LIQUIDBLOCKS.get(name))));
        FLOWINGFLUIDS.put(name, FLUIDS.get(name).createFlowingFluid());
        BUCKETITEMS.put(name, FLUIDS.get(name).createBucketItem(new Item.Properties().tab(tab)));
        LIQUIDBLOCKS.put(name, FLUIDS.get(name).createLiquidBlock(BlockBehaviour.Properties.of(Material.WATER)));
        String path = name.getPath();
        fluidRegister.register(path, () -> FLUIDS.get(name));
        fluidRegister.register(path + "_flowing", () -> FLOWINGFLUIDS.get(name));
        itemRegister.register(path + "_bucket", () -> BUCKETITEMS.get(name));
        blockRegister.register(path, () -> LIQUIDBLOCKS.get(name));
        return FLUIDS.get(name);
    }

    public static void replaceFood(Item item, FoodProperties food) {
        REPLACE_FOODS.put(item, food);
    }

    public static void registerCompostable(float cont, ItemLike itemLike) {
        ComposterBlock.COMPOSTABLES.put(itemLike.asItem(), cont);
    }

}
