package red.felnull.otyacraftengine.util;

import me.shedaniel.architectury.fluid.FluidStack;
import me.shedaniel.architectury.registry.DeferredRegister;
import me.shedaniel.architectury.utils.Fraction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
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
import red.felnull.otyacraftengine.fluid.IkisugiFluid;
import red.felnull.otyacraftengine.fluid.IkisugiFluidTank;
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

    public static ItemStack getEmptyFluidItem(ItemStack stack) {
        if (!stack.isEmpty()) {
            return OEExpectPlatform.getEmptyFluidItem(stack);
        }
        return ItemStack.EMPTY;
    }

    public static int getFluidItemMaxAmont(ItemStack stack) {
        if (!stack.isEmpty()) {
            return OEExpectPlatform.getFluidItemMaxAmont(stack);
        }
        return 0;
    }

    public static ItemStack getFilledNotIncompleteFluidItem(ItemStack stack, Fluid fluid) {
        if (!stack.isEmpty()) {
            return OEExpectPlatform.getFilledNotIncompleteFluidItem(stack, fluid);
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getReducedFluidItem(ItemStack stack, int reducedFluid) {
        if (canNotIncompleteFluidItem(stack)) {
            return getEmptyFluidItem(stack);
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getFilledFluidItem(ItemStack stack, FluidStack addFluid) {
        if (canNotIncompleteFluidItem(stack)) {
            if (getFluidItemMaxAmont(stack) <= addFluid.getAmount().intValue()) {
                return getFilledNotIncompleteFluidItem(stack, addFluid.getFluid());
            }
        }
        return ItemStack.EMPTY;
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

    public static boolean interactWithFluidTank(Player player, InteractionHand hand, Level level, BlockPos pos, Direction side) {
        return getFluidTank(level, pos, side).map(n -> interactWithFluidTank(player, hand, n)).orElse(false);
    }

    public static boolean interactWithFluidTank(Player player, InteractionHand hand, IkisugiFluidTank tank) {
        Level level = player.level;
        ItemStack heldItem = player.getItemInHand(hand);
        if (!heldItem.isEmpty()) {
            Optional<FluidStack> itmstack = getFluidContained(heldItem);

            if (!itmstack.isPresent())
                return false;

            player.awardStat(Stats.ITEM_USED.get(heldItem.getItem()));

            FluidStack tankStack = tank.getFluidStack();
            FluidStack itemStack = itmstack.get();

            if (!itemStack.isEmpty()) {
                if (!tank.isMaxCapacity()) {
                    int amari = tank.simulateAddFluidStack(itemStack);

                    if (amari > 0 && canNotIncompleteFluidItem(heldItem)) {
                        return false;
                    }

                    if (!level.isClientSide) {
                        int am = tank.getAmount();
                        tank.addFluidStack(itemStack);
                        int sa = tank.getAmount() - am;
                        ItemStack atoItem = getReducedFluidItem(heldItem, sa);

                        if (!player.getAbilities().instabuild) {
                            heldItem.shrink(1);
                            IKSGPlayerUtil.giveItem(player, atoItem);
                        }

                        SoundEvent soundevent = getEmptySound(itemStack);
                        if (soundevent != null) {
                            player.level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
                        }
                    }

                    return true;
                }
            } else if (!tankStack.isEmpty()) {
                int ra = tank.simulateReduceAmount(1000);
                if (ra > 0 && canNotIncompleteFluidItem(heldItem)) {
                    return false;
                }
                if (!level.isClientSide) {
                    int am = tank.getAmount();
                    tank.reduceAmount(1000);
                    int sa = am - tank.getAmount();
                    if (sa > 0) {
                        ItemStack rai = getFilledFluidItem(heldItem, FluidStack.create(tankStack.getFluid(), Fraction.ofWhole(sa)));
                       // if (!player.getAbilities().instabuild) {
                            heldItem.shrink(1);
                            IKSGPlayerUtil.giveItem(player, rai);
                    //    }
                        tankStack.getFluid().getPickupSound().ifPresent(n -> player.level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), n, SoundSource.BLOCKS, 1.0F, 1.0F));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static Optional<IkisugiFluidTank> getFluidTank(Level level, BlockPos blockPos, Direction side) {
        BlockState state = level.getBlockState(blockPos);
        Block block = state.getBlock();
        if (IKSGBlockEntityUtil.hasBlockEntity(block)) {
            BlockEntity be = level.getBlockEntity(blockPos);
            if (be != null) {
                if (be instanceof IIkisugibleTankBlockEntity) {
                    return ((IIkisugibleTankBlockEntity) be).getTank(side);
                }
            }
        }
        return Optional.empty();
    }


    public static SoundEvent getEmptySound(FluidStack stack) {
        if (stack.getFluid() instanceof IkisugiFluid) {
            return ((IkisugiFluid) stack.getFluid()).getProperties().getCustomEmptySound();
        }
        return OEExpectPlatform.getEmptySound(stack);
    }

    public static boolean canNotIncompleteFluidItem(ItemStack stack) {
        return OEExpectPlatform.canNotIncompleteFluidItem(stack);
    }
}
