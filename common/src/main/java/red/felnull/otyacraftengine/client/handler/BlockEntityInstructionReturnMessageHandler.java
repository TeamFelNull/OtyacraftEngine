package red.felnull.otyacraftengine.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import red.felnull.otyacraftengine.blockentity.IInstructionBlockEntity;
import red.felnull.otyacraftengine.client.gui.screen.IInstructionBEScreen;
import red.felnull.otyacraftengine.packet.BlockEntityInstructionReturnMessage;
import red.felnull.otyacraftengine.packet.IPacketMessageClientHandler;

public class BlockEntityInstructionReturnMessageHandler implements IPacketMessageClientHandler<BlockEntityInstructionReturnMessage> {
    @Override
    public boolean reversiveMessage(BlockEntityInstructionReturnMessage message, ClientPacketListener handler) {
        Minecraft mc = Minecraft.getInstance();

        if (!mc.level.dimension().location().equals(message.dimension))
            return true;

        if (!(mc.screen instanceof IInstructionBEScreen))
            return true;

        IInstructionBEScreen ics = (IInstructionBEScreen) mc.screen;

        BlockEntity be = ics.getInstructionBlockEntity();

        if (!(be instanceof IInstructionBlockEntity) || !be.getBlockPos().equals(message.pos))
            return true;

        ResourceLocation belocation = Registry.BLOCK_ENTITY_TYPE.getKey(be.getType());

        if (!message.beName.equals(belocation))
            return true;

        ics.instructionReturn(message.name, message.data);

        return true;
    }
}
