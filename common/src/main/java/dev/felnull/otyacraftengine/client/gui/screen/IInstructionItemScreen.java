package dev.felnull.otyacraftengine.client.gui.screen;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IInstructionItemScreen extends IInstructionScreen {
    public static void instructionItem(IInstructionItemScreen screen, ItemStack itemStack, String name, int num, CompoundTag data) {
       /* if (Minecraft.getInstance().screen == screen && blockEntity instanceof IInstructionBlockEntity) {
            var mc = Minecraft.getInstance();
            NetworkManager.sendToServer(OEPackets.BLOCK_ENTITY_INSTRUCTION, new OEPackets.BlockEntityInstructionMessage(screen.getInstructionID(), BlockEntityExistence.getByBlockEntity(blockEntity), name, num, data).toFBB());
        }*/
    }
}
