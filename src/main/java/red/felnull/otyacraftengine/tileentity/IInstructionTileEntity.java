package red.felnull.otyacraftengine.tileentity;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public interface IInstructionTileEntity {
    //クライアントからの指示
    CompoundNBT instructionFromClient(ServerPlayerEntity player, String name, CompoundNBT data);

    //クライアントからの指示を受けるかどうか
    boolean canInteractWith(ServerPlayerEntity player, String name, CompoundNBT data);
}
