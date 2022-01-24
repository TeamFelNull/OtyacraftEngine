package dev.felnull.otyacraftengine.handler;

import dev.architectury.event.events.common.TickEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.dimension.DimensionType;

public class TestServerHandler {

    public static void init() {
        TickEvent.SERVER_LEVEL_PRE.register(TestServerHandler::onServerLevelTick);
    }

    public static void onServerLevelTick(ServerLevel serverLevel) {
        if (!serverLevel.dimension().location().equals(DimensionType.OVERWORLD_LOCATION.location())) return;
    /*    long time = System.currentTimeMillis();
        if (time - lastTime > 10000) {
            lastTime = time;*/
        // serverLevel.players().forEach(n -> n.getMainHandItem().setHoverName(new TextComponent(UUID.randomUUID().toString())));
        //  }
    }
}
