package dev.felnull.otyacraftengine.client.gui.screen;

import dev.architectury.networking.NetworkManager;
import dev.felnull.otyacraftengine.item.IInstructionItem;
import dev.felnull.otyacraftengine.item.location.IPlayerItemLocation;
import dev.felnull.otyacraftengine.networking.ItemExistence;
import dev.felnull.otyacraftengine.networking.OEPackets;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IInstructionItemScreen extends IInstructionScreen {
    public static void instructionItem(IInstructionItemScreen screen, ItemStack itemStack, IPlayerItemLocation location, String name, int num, CompoundTag data) {
        if (Minecraft.getInstance().screen == screen && itemStack.getItem() instanceof IInstructionItem && location != null) {
            NetworkManager.sendToServer(OEPackets.ITEM_INSTRUCTION, new OEPackets.ItemInstructionMessage(screen.getInstructionID(), ItemExistence.getByItemLocation(itemStack, location), name, num, data).toFBB());
        }
    }
}
