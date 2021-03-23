package red.felnull.otyacraftengine.handler;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.block.entity.BlockEntity;
import red.felnull.otyacraftengine.blockentity.IInstructionBlockEntity;
import red.felnull.otyacraftengine.packet.BlockEntityInstructionMessage;
import red.felnull.otyacraftengine.packet.BlockEntityInstructionReturnMessage;
import red.felnull.otyacraftengine.packet.IPacketMessageServerHandler;
import red.felnull.otyacraftengine.util.IKSGPacketUtil;

public class BlockEntityInstructionMessageHandler implements IPacketMessageServerHandler<BlockEntityInstructionMessage> {

    @Override
    public boolean reversiveMessage(BlockEntityInstructionMessage message, ServerPlayer player, ServerGamePacketListenerImpl handler) {

        if (!player.level.dimension().location().equals(message.dimension))
            return true;

        BlockEntity be = player.level.getBlockEntity(message.pos);

        if (be == null)
            return true;

        ResourceLocation belocation = Registry.BLOCK_ENTITY_TYPE.getKey(be.getType());

        if (!(be instanceof IInstructionBlockEntity) || !belocation.equals(message.beName))
            return true;

        IInstructionBlockEntity ibe = (IInstructionBlockEntity) be;
        if (ibe.canInstructionWith(player, message.name, message.data)) {
            CompoundTag retag = ibe.instructionFromClient(player, message.name, message.data);
            if (retag != null) {
                IKSGPacketUtil.sendToClientPacket(player, new BlockEntityInstructionReturnMessage(message.dimension, message.pos, message.beName, message.name, retag));
            }
        }

        return true;
    }
}
