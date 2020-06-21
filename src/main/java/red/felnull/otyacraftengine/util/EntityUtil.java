package red.felnull.otyacraftengine.util;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class EntityUtil {
    public static ItemEntity createItemEntity(ItemStack item, World worldIn, double x, double y, double z) {
        ItemEntity iteme = new ItemEntity(worldIn, x, y, z);
        iteme.setItem(item);
        iteme.setDefaultPickupDelay();
        return iteme;
    }

    public static ItemEntity createItemEntity(ItemStack item, World worldIn) {
        return createItemEntity(item, worldIn, 0, 0, 0);
    }

    public static void addMobDrop(LivingDropsEvent e, ItemStack item) {
        Entity en = e.getEntity();
        e.getDrops().add(
                createItemEntity(item, en.world, en.func_226277_ct_(), en.func_226278_cu_(), en.func_226281_cx_()));
    }

    public static void startZombiVillagerConverting(ZombieVillagerEntity zombie, int time) {
        CompoundNBT tag = zombie.serializeNBT();
        tag.putInt("ConversionTime", time);
        zombie.deserializeNBT(tag);
    }

    public static void setCreeperPowered(CreeperEntity creeper, boolean powered) {
        CompoundNBT tag = creeper.serializeNBT();
        tag.putBoolean("powered", powered);
        creeper.deserializeNBT(tag);
    }

    public static boolean isCreeperPowered(CreeperEntity creeper) {
        CompoundNBT tag = creeper.serializeNBT();
        return tag.getBoolean("powered");
    }

    public static void setMooshroomType(MooshroomEntity mooshroo, MooshroomEntity.Type type, boolean sound) {
        CompoundNBT tag = mooshroo.serializeNBT();
        tag.putString("Type", type.name());
        mooshroo.deserializeNBT(tag);
        if (sound) {
            mooshroo.world.playSound((PlayerEntity) null, mooshroo.getPosition(),
                    SoundEvents.ENTITY_MOOSHROOM_CONVERT,
                    SoundCategory.HOSTILE, 1.0F + mooshroo.getRNG().nextFloat(),
                    1);
        }
    }

    public static void dropEquipment(LivingEntity target, EquipmentSlotType type) {
        ItemStack item = target.getItemStackFromSlot(type);
        target.world.addEntity(createItemEntity(item.copy(), target.world, target.func_226277_ct_(),
                target.func_226278_cu_(), target.func_226281_cx_()));
        target.setItemStackToSlot(type, ItemStack.EMPTY);
    }

    public static void treatmentEntity(LivingEntity target) {
        if (target instanceof ZombiePigmanEntity) {
            replaceEntity(target, EntityType.PIG, false, true);
        } else if (target instanceof WitchEntity || target instanceof AbstractIllagerEntity) {
            replaceEntity(target, EntityType.VILLAGER, true, true);
        } else if (target instanceof ZombieHorseEntity || target instanceof SkeletonHorseEntity) {
            replaceEntity(target, EntityType.HORSE, true, true);
        } else if (target instanceof ZombieVillagerEntity) {
            startZombiVillagerConverting((ZombieVillagerEntity) target, 0);
        } else if (target instanceof CreeperEntity) {
            if (((CreeperEntity) target).func_225509_J__()) {
                setCreeperPowered((CreeperEntity) target, false);
                target.world.playSound((PlayerEntity) null, target.getPosition(),
                        SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED,
                        SoundCategory.HOSTILE, 1.0F + target.getRNG().nextFloat(),
                        1);
            }
        } else if (target instanceof MooshroomEntity) {
            if (((MooshroomEntity) target).getMooshroomType() != MooshroomEntity.Type.RED) {
                setMooshroomType((MooshroomEntity) target, MooshroomEntity.Type.RED, true);
            }
        }
    }

    public static void replaceEntity(LivingEntity before, EntityType<?> after, boolean equipment, boolean sound) {
        if (!(after.create(before.world) instanceof LivingEntity))
            return;
        LivingEntity pl = (LivingEntity) after.create(before.world);
        pl.setLocationAndAngles(before.prevPosX, before.prevPosY, before.prevPosZ,
                before.rotationYaw, 0.0F);
        pl.setRotationYawHead(before.getRotationYawHead());

        if (pl instanceof AgeableEntity) {
            if (before.isChild())
                ((AgeableEntity) pl).setGrowingAge(-24000);
        }
        before.world.addEntity(pl);
        if (sound) {
            pl.world.playSound((PlayerEntity) null, pl.getPosition(),
                    SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED,
                    SoundCategory.HOSTILE, 1.0F + pl.getRNG().nextFloat(),
                    1);
        }
        if (equipment) {
            pl.setHeldItem(Hand.MAIN_HAND, before.getHeldItemMainhand());
            pl.setHeldItem(Hand.OFF_HAND, before.getHeldItemOffhand());
            pl.setItemStackToSlot(EquipmentSlotType.HEAD, before.getItemStackFromSlot(EquipmentSlotType.HEAD));
            pl.setItemStackToSlot(EquipmentSlotType.CHEST, before.getItemStackFromSlot(EquipmentSlotType.CHEST));
            pl.setItemStackToSlot(EquipmentSlotType.LEGS, before.getItemStackFromSlot(EquipmentSlotType.LEGS));
            pl.setItemStackToSlot(EquipmentSlotType.FEET, before.getItemStackFromSlot(EquipmentSlotType.FEET));
        }
        before.setPosition(before.lastTickPosX, -1000, before.lastTickPosZ);
        before.setHealth(0);
    }
}
