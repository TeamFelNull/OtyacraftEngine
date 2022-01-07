package dev.felnull.otyacraftengine.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.util.OEPlayerUtil;
import dev.felnull.otyacraftengine.util.OEVoxelShapeUtil;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TestItem extends Item implements IEquipmentItem {
    public TestItem(Properties properties) {
        super(properties);
    }

    private static final VoxelShape shape = OEVoxelShapeUtil.getShapeFromResource(new ResourceLocation(OtyacraftEngine.MODID, "sample"));

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);

        if (level.isClientSide()) {
           /* try {
                var img = FNURLUtil.getStream(new URL("https://cdn.discordapp.com/attachments/887769442019323924/892670356358324264/pinki.gif")).readAllBytes();
                var simg = OEImageUtil.reductionSize(img, 3145728L);
                Files.write(Paths.get("test.gif"), simg);
            } catch (Exception ex) {
                player.displayClientMessage(new TextComponent(ex.getLocalizedMessage()), false);
            }*/
/*            var edge = OEVoxelShapeUtil.getShapeFromResource(new ResourceLocation(OtyacraftEngine.MODID, "music_manager"));
            var shape = OEVoxelShapeUtil.getShapeFromResource(new ResourceLocation(OtyacraftEngine.MODID, "music_manager_simpl"));
            var genShape = TentativeVoxelShapeGenerator.generate(shape, edge);
            try {
                Files.writeString(Paths.get("genmodel.json"), new Gson().toJson(genShape));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            var uu = OEPlayerUtil.getUUIDByName("MoriMori_0317_jp");
            player.displayClientMessage(new TextComponent(uu.toString()), false);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    public static Item TEST_ITEM;

    public static void init() {
        DeferredRegister<Item> ITEM_REG = DeferredRegister.create(OtyacraftEngine.MODID, Registry.ITEM_REGISTRY);
        TEST_ITEM = new TestItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC));
        ITEM_REG.register("test_item", () -> TEST_ITEM);
        ITEM_REG.register();
    }

    @Override
    public EquipmentSlot getEquipmentSlotType(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }
}
