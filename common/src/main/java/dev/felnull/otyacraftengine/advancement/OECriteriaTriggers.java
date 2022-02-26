package dev.felnull.otyacraftengine.advancement;

import dev.architectury.registry.level.advancement.CriteriaTriggersRegistry;

public class OECriteriaTriggers {
    public static final MODRootTrigger MOD_ROOT_TRIGGER = new MODRootTrigger();

    public static void init() {
        CriteriaTriggersRegistry.register(MOD_ROOT_TRIGGER);
    }
}
