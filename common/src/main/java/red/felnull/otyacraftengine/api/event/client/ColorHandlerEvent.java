package red.felnull.otyacraftengine.api.event.client;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import red.felnull.otyacraftengine.api.event.OEEvent;

public class ColorHandlerEvent extends OEEvent {
    public static class Block extends ColorHandlerEvent {
        private final BlockColors blockColors;

        public Block(BlockColors blockColors) {
            this.blockColors = blockColors;
        }

        public BlockColors getBlockColors() {
            return blockColors;
        }
    }

    public static class Item extends ColorHandlerEvent {
        private final ItemColors itemColors;
        private final BlockColors blockColors;

        public Item(ItemColors itemColors, BlockColors blockColors) {
            this.itemColors = itemColors;
            this.blockColors = blockColors;
        }

        public ItemColors getItemColors() {
            return itemColors;
        }

        public BlockColors getBlockColors() {
            return blockColors;
        }
    }
}
