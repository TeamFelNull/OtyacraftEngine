package red.felnull.otyacraftengine.api.event.client;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import red.felnull.otyacraftengine.api.event.OEEvent;

import java.util.List;

public class ItemTooltipEvent extends OEEvent {
    private final Player player;
    private final TooltipFlag flags;
    private final ItemStack itemStack;
    private final List<Component> toolTip;

    public ItemTooltipEvent(ItemStack itemStack, Player player, List<Component> list, TooltipFlag flags) {
        super();
        this.itemStack = itemStack;
        this.toolTip = list;
        this.flags = flags;
        this.player = player;
    }


    public TooltipFlag getFlags() {
        return flags;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }


    public List<Component> getToolTip() {
        return toolTip;
    }


    public Player getPlayer() {
        return player;
    }
}
