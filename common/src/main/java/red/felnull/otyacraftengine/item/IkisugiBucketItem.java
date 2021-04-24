package red.felnull.otyacraftengine.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.fluid.IkisugiFluid;

public class IkisugiBucketItem extends BucketItem {
    private final IkisugiFluid content;

    public IkisugiBucketItem(IkisugiFluid fluid, Properties properties) {
        super(fluid, properties);
        this.content = fluid;
    }

    public int getFluidColor() {
        return content.getProperties().getColor();
    }

    public boolean isColoring() {
        return content.getProperties().isColoring();
    }

    public IkisugiFluid getFluid() {
        return content;
    }

    @Override
    protected void playEmptySound(@Nullable Player player, LevelAccessor levelAccessor, BlockPos blockPos) {
        if (!content.getProperties().isCustomEmptySound()) {
            super.playEmptySound(player, levelAccessor, blockPos);
            return;
        }
        SoundEvent soundEvent = content.getProperties().getCustomEmptySound();
        levelAccessor.playSound(player, blockPos, soundEvent, SoundSource.BLOCKS, 1.0F, content.getProperties().getCustomEmptySoundPitch());
        levelAccessor.gameEvent(player, GameEvent.FLUID_PLACE, blockPos);
    }

    @Override
    public Component getName(ItemStack itemStack) {

        if (!content.getProperties().isAutoBucketName())
            return super.getName(itemStack);

        return new TranslatableComponent("item." + OtyacraftEngine.MODID + ".ikisugibucket", new TranslatableComponent(content.getData().getLiquidBlock().getDescriptionId()));
    }



}
