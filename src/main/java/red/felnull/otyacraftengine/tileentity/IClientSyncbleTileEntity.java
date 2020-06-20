package red.felnull.otyacraftengine.tileentity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.PacketDistributor;
import red.felnull.otyacraftengine.packet.ClientTileEntitySyncMessage;
import red.felnull.otyacraftengine.packet.PacketHandler;

public interface IClientSyncbleTileEntity {

    public CompoundNBT sendToClient(CompoundNBT tag);

    public void receiveToClient(CompoundNBT tag);

    default public void syncbleTick(TileEntity tile) {

        if (tile.getWorld().isRemote)
            return;

        Chunk ch = (Chunk) tile.getWorld().getChunk(tile.getPos());
        PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> ch),
                new ClientTileEntitySyncMessage(tile.getWorld().dimension.getDimension().getType().getId(), tile.getPos(),
                        tile.getClass().toString(), this.sendToClient(new CompoundNBT())));
    }

}
