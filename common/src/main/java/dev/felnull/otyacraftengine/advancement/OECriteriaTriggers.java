package dev.felnull.otyacraftengine.advancement;

import net.minecraft.advancements.CriteriaTriggers;

public class OECriteriaTriggers {
    public static final ModRootTrigger MOD_ROOT_TRIGGER = new ModRootTrigger();

    public static void init() {
        CriteriaTriggers.register(MOD_ROOT_TRIGGER);
    }
}
