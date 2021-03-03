package red.felnull.otyacraftengine.impl.fabric;

import me.shedaniel.architectury.fluid.FluidStack;
import me.shedaniel.architectury.utils.Fraction;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import red.felnull.otyacraftengine.OtyacraftEngine;
import red.felnull.otyacraftengine.api.IOEIntegration;

import java.util.List;
import java.util.Optional;

public class OEExpectPlatformImpl {
    public static List<IOEIntegration> getIntegrations() {
        return FabricLoader.getInstance().getEntrypoints(OtyacraftEngine.MODID, IOEIntegration.class);
    }

    public static TagCollection<Fluid> getFluidAllTags() {
        return FluidTags.HELPER.getAllTags();
    }

    public static TagCollection<GameEvent> getGameEventAllTags() {
        return GameEventTags.HELPER.getAllTags();
    }

    public static final Fraction BUCKET_VOLUME = Fraction.ofWhole(1000);

    public static Optional<FluidStack> getFluidContained(ItemStack container) {
        Item item = container.getItem();
        if (item instanceof BucketItem) {
            return Optional.of(FluidStack.create(((BucketItem) item).content, BUCKET_VOLUME));
        }
        return Optional.empty();
    }
}
