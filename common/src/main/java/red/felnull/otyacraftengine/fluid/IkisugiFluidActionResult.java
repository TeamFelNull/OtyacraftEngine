package red.felnull.otyacraftengine.fluid;

import net.minecraft.world.item.ItemStack;

public class IkisugiFluidActionResult {
    public static final IkisugiFluidActionResult FAILURE = new IkisugiFluidActionResult(false, ItemStack.EMPTY);

    public final boolean success;
    public final ItemStack result;

    public IkisugiFluidActionResult(ItemStack result) {
        this(true, result);
    }

    private IkisugiFluidActionResult(boolean success, ItemStack result) {
        this.success = success;
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }


    public ItemStack getResult() {
        return result;
    }
}
