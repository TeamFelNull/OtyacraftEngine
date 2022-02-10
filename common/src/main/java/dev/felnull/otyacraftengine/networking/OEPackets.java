package dev.felnull.otyacraftengine.networking;

import dev.architectury.networking.NetworkManager;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.handler.ClientMessageHandler;
import dev.felnull.otyacraftengine.server.handler.ServerMessageHandler;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class OEPackets {
    public static final ResourceLocation TEST = new ResourceLocation(OtyacraftEngine.MODID, "test");
    public static final ResourceLocation BLOCK_ENTITY_SYNC = new ResourceLocation(OtyacraftEngine.MODID, "block_entity_sync");
    public static final ResourceLocation BLOCK_ENTITY_INSTRUCTION = new ResourceLocation(OtyacraftEngine.MODID, "block_entity_instruction");
    public static final ResourceLocation BLOCK_ENTITY_INSTRUCTION_RETURN = new ResourceLocation(OtyacraftEngine.MODID, "block_entity_instruction_return");
    public static final ResourceLocation ITEM_INSTRUCTION = new ResourceLocation(OtyacraftEngine.MODID, "item_instruction");
    public static final ResourceLocation ITEM_INSTRUCTION_RETURN = new ResourceLocation(OtyacraftEngine.MODID, "item_instruction_return");

    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.c2s(), BLOCK_ENTITY_INSTRUCTION, (friendlyByteBuf, packetContext) -> ServerMessageHandler.onBlockEntityInstructionMessage(new BlockEntityInstructionMessage(friendlyByteBuf), packetContext));
        NetworkManager.registerReceiver(NetworkManager.c2s(), ITEM_INSTRUCTION, (friendlyByteBuf, packetContext) -> ServerMessageHandler.onItemInstructionMessage(new ItemInstructionMessage(friendlyByteBuf), packetContext));
    }

    public static void clientInit() {
        if (OtyacraftEngine.CONFIG.testMode)
            NetworkManager.registerReceiver(NetworkManager.s2c(), TEST, (friendlyByteBuf, packetContext) -> ClientMessageHandler.onTestMessage(new TestMessage(friendlyByteBuf), packetContext));

        NetworkManager.registerReceiver(NetworkManager.s2c(), BLOCK_ENTITY_SYNC, (friendlyByteBuf, packetContext) -> ClientMessageHandler.onBlockEntitySyncMessage(new BlockEntitySyncMessage(friendlyByteBuf), packetContext));
        NetworkManager.registerReceiver(NetworkManager.s2c(), BLOCK_ENTITY_INSTRUCTION_RETURN, (friendlyByteBuf, packetContext) -> ClientMessageHandler.onBlockEntityInstructionReturn(new BlockEntityInstructionMessage(friendlyByteBuf), packetContext));
        NetworkManager.registerReceiver(NetworkManager.s2c(), ITEM_INSTRUCTION_RETURN, (friendlyByteBuf, packetContext) -> ClientMessageHandler.onItemInstructionReturn(new ItemInstructionMessage(friendlyByteBuf), packetContext));
    }

    public static class ItemInstructionMessage implements PacketMessage {
        public final UUID instructionScreenID;
        public final ItemExistence itemExistence;
        public final String name;
        public final int num;
        public final CompoundTag data;

        public ItemInstructionMessage(FriendlyByteBuf bf) {
            this(bf.readUUID(), ItemExistence.readFBB(bf), bf.readUtf(), bf.readInt(), bf.readNbt());
        }

        public ItemInstructionMessage(UUID instructionScreenID, ItemExistence itemExistence, String name, int num, CompoundTag data) {
            this.instructionScreenID = instructionScreenID;
            this.itemExistence = itemExistence;
            this.name = name;
            this.num = num;
            this.data = data;
        }

        @Override
        public FriendlyByteBuf toFBB() {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeUUID(instructionScreenID);
            itemExistence.writeFBB(buf);
            buf.writeUtf(name);
            buf.writeInt(num);
            buf.writeNbt(data);
            return buf;
        }
    }

    public static class BlockEntityInstructionMessage implements PacketMessage {
        public final UUID instructionScreenID;
        public final BlockEntityExistence blockEntityExistence;
        public final String name;
        public final int num;
        public final CompoundTag data;

        public BlockEntityInstructionMessage(FriendlyByteBuf bf) {
            this(bf.readUUID(), BlockEntityExistence.readFBB(bf), bf.readUtf(), bf.readInt(), bf.readNbt());
        }

        public BlockEntityInstructionMessage(UUID instructionScreenID, BlockEntityExistence blockEntityExistence, String name, int num, CompoundTag data) {
            this.instructionScreenID = instructionScreenID;
            this.blockEntityExistence = blockEntityExistence;
            this.name = name;
            this.num = num;
            this.data = data;
        }

        @Override
        public FriendlyByteBuf toFBB() {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeUUID(instructionScreenID);
            blockEntityExistence.writeFBB(buf);
            buf.writeUtf(name);
            buf.writeInt(num);
            buf.writeNbt(data);
            return buf;
        }
    }

    public static class BlockEntitySyncMessage implements PacketMessage {
        public final BlockEntityExistence blockEntityExistence;
        public final CompoundTag syncedData;

        public BlockEntitySyncMessage(FriendlyByteBuf bf) {
            this(BlockEntityExistence.readFBB(bf), bf.readNbt());
        }

        public BlockEntitySyncMessage(BlockEntityExistence blockEntityExistence, CompoundTag syncedData) {
            this.blockEntityExistence = blockEntityExistence;
            this.syncedData = syncedData;
        }

        @Override
        public FriendlyByteBuf toFBB() {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            blockEntityExistence.writeFBB(buf);
            buf.writeNbt(syncedData);
            return buf;
        }
    }

    public static class TestMessage implements PacketMessage {
        public final String str;
        public final int num;

        public TestMessage(FriendlyByteBuf bf) {
            this(bf.readUtf(), bf.readInt());
        }

        public TestMessage(String str, int num) {
            this.str = str;
            this.num = num;
        }

        public FriendlyByteBuf toFBB() {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeUtf(str);
            buf.writeInt(num);
            return buf;
        }
    }
}
