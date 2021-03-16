package red.felnull.otyacraftengine.util;

import me.shedaniel.architectury.fluid.FluidStack;
import me.shedaniel.architectury.registry.DeferredRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import red.felnull.otyacraftengine.blockentity.IIkisugibleTankBlockEntity;
import red.felnull.otyacraftengine.fluid.FluidData;
import red.felnull.otyacraftengine.fluid.FluidProperties;
import red.felnull.otyacraftengine.fluid.IIkisugiFluidHandler;
import red.felnull.otyacraftengine.fluid.IkisugiFluid;
import red.felnull.otyacraftengine.impl.OEExpectPlatform;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class IKSGFluidUtil {
    private static final Map<ResourceLocation, IkisugiFluid> fluids = new HashMap<>();
    private static final Map<ResourceLocation, FlowingFluid> flowingFluids = new HashMap<>();
    private static final Map<ResourceLocation, Item> bucketItems = new HashMap<>();
    private static final Map<ResourceLocation, Block> liquidBlocks = new HashMap<>();

    public static Optional<FluidStack> getFluidContained(ItemStack container) {
        if (!container.isEmpty()) {
            return OEExpectPlatform.getFluidContained(container);
        }
        return Optional.empty();
    }

    public static IkisugiFluid register(ResourceLocation name, FluidProperties properties, CreativeModeTab tab, DeferredRegister<Fluid> fluidRegister, DeferredRegister<Item> itemRegister, DeferredRegister<Block> blockRegister) {
        fluids.put(name, new IkisugiFluid(properties, new FluidData(() -> fluids.get(name), () -> flowingFluids.get(name), () -> bucketItems.get(name), () -> liquidBlocks.get(name))));
        flowingFluids.put(name, fluids.get(name).createFlowingFluid());
        bucketItems.put(name, fluids.get(name).createBucketItem(new Item.Properties().tab(tab)));
        liquidBlocks.put(name, fluids.get(name).createLiquidBlock(BlockBehaviour.Properties.of(Material.WATER)));
        String path = name.getPath();
        fluidRegister.register(path, () -> fluids.get(name));
        fluidRegister.register(path + "_flowing", () -> flowingFluids.get(name));
        itemRegister.register(path + "_bucket", () -> bucketItems.get(name));
        blockRegister.register(path, () -> liquidBlocks.get(name));
        return fluids.get(name);
    }

    public static boolean interactWithFluidHandler(Player player, InteractionHand hand, Level level, BlockPos pos, Direction side) {
        return getFluidHandler(level, pos, side).map(n -> interactWithFluidHandler(player, hand, n)).orElse(false);
    }

    public static boolean interactWithFluidHandler(Player player, InteractionHand hand, IIkisugiFluidHandler handler) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (!heldItem.isEmpty()) {
            Optional<FluidStack> stack = getFluidContained(heldItem);
            if (!stack.isPresent())
                return false;

            FluidStack fs = handler.drain(stack.get(), IIkisugiFluidHandler.FluidAction.EXECUTE);

            System.out.println(fs.getAmount());

        }
        return false;
    }

    public static Optional<IIkisugiFluidHandler> getFluidHandler(Level level, BlockPos blockPos, Direction side) {
        BlockState state = level.getBlockState(blockPos);
        Block block = state.getBlock();
        if (IKSGBlockEntityUtil.hasBlockEntity(block)) {
            BlockEntity be = level.getBlockEntity(blockPos);
            if (be != null) {
                if (be instanceof IIkisugibleTankBlockEntity) {
                    return ((IIkisugibleTankBlockEntity) be).getFluidCapability();
                }
            }
        }
        return Optional.empty();
    }
}
