package dev.felnull.otyacraftengine.handler;

import dev.architectury.event.EventResult;
import dev.felnull.otyacraftengine.event.MoreEntityEvent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class TestCommonHandler {
    public static void init() {
        MoreEntityEvent.ENTITY_TICK.register(TestCommonHandler::entityTick);
        MoreEntityEvent.ENTITY_DEFINE_SYNCHED_DATA.register(TestCommonHandler::onDefineSynchedData);
    }

    private static EventResult entityTick(Entity entity) {
        //   if (!(entity instanceof Player))
        //   return EventResult.interruptFalse();
        //  entity.setPos(entity.position().add(0, 0.5f, 0));
        return EventResult.pass();
    }

    private static void onDefineSynchedData(@NotNull Entity entity, @NotNull SynchedEntityData entityData) {
        //    System.out.println("ｳｧｧ!!ｵﾚﾓｲｯﾁｬｳｩｩｩ!!!ｳｳｳｳｳｳｳｳｳｩｩｩｩｩｩｩｩｳｳｳｳｳｳｳｳ!ｲｨｨｲｨｨｨｲｲｲｨｲｲｲｲ");
    }
}
