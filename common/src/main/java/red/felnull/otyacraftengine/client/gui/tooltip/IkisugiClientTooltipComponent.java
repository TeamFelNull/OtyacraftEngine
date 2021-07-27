package red.felnull.otyacraftengine.client.gui.tooltip;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;

public interface IkisugiClientTooltipComponent extends ClientTooltipComponent {
    default void render(PoseStack stack, int x, int y) {
    }
}
