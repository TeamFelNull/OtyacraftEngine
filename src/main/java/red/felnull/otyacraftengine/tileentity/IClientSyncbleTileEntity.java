package red.felnull.otyacraftengine.tileentity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.PacketDistributor;
import red.felnull.otyacraftengine.packet.ClientTileEntitySyncMessage;
import red.felnull.otyacraftengine.packet.PacketHandler;

public interface IClientSyncbleTileEntity {
    //サーバーのクライアントに同期させる情報
    default public CompoundNBT sendToClient(TileEntity tile, CompoundNBT tag) {
        return tile.write(tag);
    }

    //クライアントでサーバーからの情報を同期させる
    default public void receiveToClient(TileEntity tile, CompoundNBT tag) {
        tile.func_230337_a_(tile.getBlockState(), tag);
    }

    //同期を発生させる
    default public void syncble(TileEntity tile) {

        if (tile.getWorld().isRemote)
            return;

        Chunk ch = (Chunk) tile.getWorld().getChunk(tile.getPos());
        PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> ch), new ClientTileEntitySyncMessage(tile.getWorld().func_230315_m_().getClass().toString(), tile.getPos(), tile.getClass().toString(), this.sendToClient(tile, new CompoundNBT())));
    }

}
