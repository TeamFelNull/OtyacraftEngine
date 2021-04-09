package red.felnull.otyacraftengine.util;

import me.shedaniel.architectury.fluid.FluidStack;
import me.shedaniel.architectury.registry.DeferredRegister;
import me.shedaniel.architectury.utils.Fraction;
import net.minecraft.client.renderer.BiomeColors;
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
import net.minecraft.world.level.material.*;
import red.felnull.otyacraftengine.blockentity.storage.IFluidTankBlockEntity;
import red.felnull.otyacraftengine.fluid.FluidData;
import red.felnull.otyacraftengine.fluid.FluidProperties;
import red.felnull.otyacraftengine.fluid.IkisugiFluid;
import red.felnull.otyacraftengine.fluid.storage.FluidTank;
import red.felnull.otyacraftengine.impl.OEExpectPlatform;
import red.felnull.otyacraftengine.item.storage.IFluidTankItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class IKSGFluidUtil {
    private static final Map<ResourceLocation, IkisugiFluid> fluids = new HashMap<>();
    private static final Map<ResourceLocation, FlowingFluid> flowingFluids = new HashMap<>();
    private static final Map<ResourceLocation, Item> bucketItems = new HashMap<>();
    private static final Map<ResourceLocation, Block> liquidBlocks = new HashMap<>();

    public static Optional<FluidStack> getFluidContained(ItemStack stack) {
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof IFluidTankItem && ((IFluidTankItem) stack.getItem()).isFluidTankItem(stack)) {
                return ((IFluidTankItem) stack.getItem()).getFluidTank(stack).map(FluidTank::getFluidStack);
            }
            return OEExpectPlatform.getFluidContained(stack);
        }
        return Optional.empty();
    }

    public static ItemStack getEmptyFluidItem(ItemStack stack) {
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof IFluidTankItem && ((IFluidTankItem) stack.getItem()).isFluidTankItem(stack)) {
                return ((IFluidTankItem) stack.getItem()).getEmptyFluidTankItem();
            }
            return OEExpectPlatform.getEmptyFluidItem(stack);
        }
        return ItemStack.EMPTY;
    }

    public static int getFluidItemMaxAmont(ItemStack stack) {
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof IFluidTankItem && ((IFluidTankItem) stack.getItem()).isFluidTankItem(stack)) {
                return ((IFluidTankItem) stack.getItem()).getCapacity(stack);
            }
            return OEExpectPlatform.getFluidItemMaxAmont(stack);
        }
        return 0;
    }

    public static Optional<ItemStack> getFilledNotIncompleteFluidItem(ItemStack stack, Fluid fluid) {
        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof IFluidTankItem && ((IFluidTankItem) stack.getItem()).isFluidTankItem(stack)) {
                IFluidTankItem ifti = ((IFluidTankItem) stack.getItem());
                FluidTank tank = new FluidTank(ifti.getCapacity(stack));
                tank.setFluid(fluid);
                tank.setAmount(ifti.getCapacity(stack));
                return ifti.setFluidTank(stack, tank);
            }
            return OEExpectPlatform.getFilledNotIncompleteFluidItem(stack, fluid);
        }
        return Optional.empty();
    }

    public static Optional<ItemStack> getReducedFluidItem(ItemStack stack, int reducedFluid) {
        if (!stack.isEmpty()) {
            if (canNotIncompleteFluidItem(stack)) {
                return Optional.of(getEmptyFluidItem(stack));
            }
            if (stack.getItem() instanceof IFluidTankItem && ((IFluidTankItem) stack.getItem()).isFluidTankItem(stack)) {
                IFluidTankItem ifti = ((IFluidTankItem) stack.getItem());
                if (ifti.getFluidTank(stack).isPresent()) {
                    FluidTank tank = ifti.getFluidTank(stack).get();
                    tank.reduceAmount(reducedFluid);
                    return ifti.setFluidTank(stack, tank);
                } else {
                    return Optional.of(stack);
                }
            }
        }
        return Optional.empty();
    }

    public static Optional<ItemStack> getFilledFluidItem(ItemStack stack, FluidStack addFluid) {
        if (canNotIncompleteFluidItem(stack)) {
            if (getFluidItemMaxAmont(stack) <= addFluid.getAmount().intValue()) {
                return getFilledNotIncompleteFluidItem(stack, addFluid.getFluid());
            }
        }
        if (stack.getItem() instanceof IFluidTankItem && ((IFluidTankItem) stack.getItem()).isFluidTankItem(stack)) {
            IFluidTankItem ifti = ((IFluidTankItem) stack.getItem());
            if (ifti.getFluidTank(stack).isPresent()) {
                FluidTank tank = ifti.getFluidTank(stack).get();
                tank.setFluidStack(addFluid);
                return ifti.setFluidTank(stack, tank);
            } else {
                return Optional.of(stack);
            }

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

    public static boolean interactWithFluidTank(Player player, InteractionHand hand, Level level, BlockPos pos, Direction side) {
        return getFluidTank(level, pos, side).map(n -> interactWithFluidTank(player, hand, n)).orElse(false);
    }

    public static boolean interactWithFluidTank(Player player, InteractionHand hand, FluidTank tank) {
        Level level = player.level;
        ItemStack heldItem = player.getItemInHand(hand);
        if (!heldItem.isEmpty()) {
            Optional<FluidStack> itmstack = getFluidContained(heldItem);

            if (!itmstack.isPresent())
                return false;

            player.awardStat(Stats.ITEM_USED.get(heldItem.getItem()));

            FluidStack tankStack = tank.getFluidStack();
            FluidStack itemStack = itmstack.get();

            if (tankStack.isEmpty() || tankStack.getFluid() == tankStack.getFluid()) {
                if (!itemStack.isEmpty()) {
                    if (!tank.isMaxCapacity() && (tankStack.isEmpty() || tankStack.getFluid() == tankStack.getFluid())) {
                        int amari = tank.simulateAddFluidStack(itemStack);

                        if (amari > 0 && canNotIncompleteFluidItem(heldItem)) {
                            return false;
                        }

                        if (!level.isClientSide) {
                            int am = tank.getAmount();
                            int sa = Math.min(am + itemStack.getAmount().intValue(), tank.getCapacity()) - am;
                            Optional<ItemStack> atoItem = getReducedFluidItem(IKSGItemUtil.copyStackWithSize(heldItem, 1), sa);
                            if (atoItem.isPresent()) {
                                tank.addFluidStack(itemStack);
                                IKSGPlayerUtil.changeOrGiveItem(player, hand, atoItem.get());
                                SoundEvent soundevent = getEmptySound(itemStack);
                                if (soundevent != null) {
                                    player.level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
                                }
                            }
                        }

                        return true;
                    }
                } else if (!tankStack.isEmpty()) {
                    int ra = tank.simulateReduceAmount(getFluidItemMaxAmont(heldItem));
                    if (ra > 0 && canNotIncompleteFluidItem(heldItem)) {
                        return false;
                    }
                    if (!level.isClientSide) {
                        int am = tank.getAmount();
                        int sa = am - Math.max(am - getFluidItemMaxAmont(heldItem), 0);
                        if (sa > 0) {
                            Optional<ItemStack> rai = getFilledFluidItem(IKSGItemUtil.copyStackWithSize(heldItem, 1), FluidStack.create(tankStack.getFluid(), Fraction.ofWhole(sa)));
                            if (rai.isPresent()) {
                                tank.reduceAmount(getFluidItemMaxAmont(heldItem));
                                IKSGPlayerUtil.changeOrGiveItem(player, hand, rai.get());
                                tankStack.getFluid().getPickupSound().ifPresent(n -> player.level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), n, SoundSource.BLOCKS, 1.0F, 1.0F));
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static Optional<FluidTank> getFluidTank(Level level, BlockPos blockPos, Direction side) {
        BlockState state = level.getBlockState(blockPos);
        Block block = state.getBlock();
        if (IKSGBlockEntityUtil.hasBlockEntity(block)) {
            BlockEntity be = level.getBlockEntity(blockPos);
            if (be != null) {
                if (be instanceof IFluidTankBlockEntity) {
                    return ((IFluidTankBlockEntity) be).getFluidTank(side);
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
        if (stack.getItem() instanceof IFluidTankItem && ((IFluidTankItem) stack.getItem()).isFluidTankItem(stack)) {
            return ((IFluidTankItem) stack.getItem()).canNotIncompleteFluidTankItem();
        }
        return OEExpectPlatform.canNotIncompleteFluidItem(stack);
    }

    public static FluidProperties createVanillaFluidProperties(Fluid fluid) {

        if (fluid instanceof EmptyFluid)
            return new FluidProperties().stillTexture(null).flowingTexture(null).color(0).density(0).temperature(0).lightLevel(0).viscosity(0);

        if (fluid instanceof WaterFluid)
            return new FluidProperties().stillTexture(new ResourceLocation("block/water_still")).flowingTexture(new ResourceLocation("block/water_flow")).color(0xFF3F76E4).density(0).temperature(0).lightLevel(0).viscosity(0).worldColor((n, m) -> BiomeColors.getAverageWaterColor(n, m) | 0xFF000000);

        if (fluid instanceof LavaFluid)
            return new FluidProperties().stillTexture(new ResourceLocation("block/lava_still")).flowingTexture(new ResourceLocation("block/lava_flow")).density(3000).temperature(1300).lightLevel(15).viscosity(6000);

        throw new RuntimeException("Can't create not Vanilla Fluid properties");
    }
}
