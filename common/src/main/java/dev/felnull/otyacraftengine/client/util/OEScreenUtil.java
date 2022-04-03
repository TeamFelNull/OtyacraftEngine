package dev.felnull.otyacraftengine.client.util;

import com.google.common.collect.ImmutableList;
import dev.felnull.otyacraftengine.mixin.client.ScreenAccessor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OEScreenUtil {
    @NotNull
    public static List<Widget> getRenderableWidgets(@NotNull Screen screen) {
        ScreenAccessor sa = (ScreenAccessor) screen;
        return sa.getRenderables();
    }

    @NotNull
    public static List<AbstractWidget> getRenderableAbstractWidgets(@NotNull Screen screen) {
        ImmutableList.Builder<AbstractWidget> builder = ImmutableList.builder();
        var wds = getRenderableWidgets(screen);
        for (Widget wd : wds) {
            if (wd instanceof AbstractWidget abstractWidget)
                builder.add(abstractWidget);
        }
        return builder.build();
    }
}
