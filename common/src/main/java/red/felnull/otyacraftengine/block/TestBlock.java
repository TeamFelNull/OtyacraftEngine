package red.felnull.otyacraftengine.block;

import me.shedaniel.architectury.registry.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import red.felnull.otyacraftengine.OtyacraftEngine;

public class TestBlock extends Block {
    public static final DeferredRegister<Block> MOD_BLOCKS_REGISTER = DeferredRegister.create(OtyacraftEngine.MODID, Registry.BLOCK_REGISTRY);
    public static final DeferredRegister<Item> MOD_BLOCKITEMS_REGISTER = DeferredRegister.create(OtyacraftEngine.MODID, Registry.ITEM_REGISTRY);
    public static final Block TEST_BLOCK = register("test_block", new TestBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(1, 10f)));

    public TestBlock(Properties properties) {
        super(properties);
    }


    private static Block register(String name, Block block) {
        return register(name, block, new BlockItem(block, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    }

    private static Block register(String name, Block block, Item blockitem) {
        MOD_BLOCKS_REGISTER.register(name, () -> block);
        MOD_BLOCKITEMS_REGISTER.register(name, () -> blockitem);
        return block;
    }

    public static void init() {
        MOD_BLOCKS_REGISTER.register();
        MOD_BLOCKITEMS_REGISTER.register();
    }
}
