package red.felnull.otyacraftengine.api.event.client;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.eventbus.api.Event;

public class ReturnInstructionEvent extends Event {
    private final String name;
    private final CompoundNBT data;
    private final BlockPos pos;

    public ReturnInstructionEvent(BlockPos pos, String name, CompoundNBT data) {
        this.name = name;
        this.pos = pos;
        this.data = data;
    }

    public BlockPos getPos() {
        return pos;
    }

    public String getName() {
        return name;
    }

    public CompoundNBT getData() {
        return data;
    }
}
