package dev.felnull.otyacraftengine.advancement;

import com.google.gson.JsonObject;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MODRootTrigger extends SimpleCriterionTrigger<MODRootTrigger.TriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation(OtyacraftEngine.MODID, "mod_root");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected TriggerInstance createInstance(JsonObject jsonObject, EntityPredicate.@NotNull Composite composite, @NotNull DeserializationContext deserializationContext) {
        String mid = jsonObject.has("modid") ? jsonObject.get("modid").getAsString() : null;
        return new TriggerInstance(composite, mid);
    }

    public void trigger(ServerPlayer serverPlayer, ItemStack itemStack) {
        this.trigger(serverPlayer, (triggerInstance) -> triggerInstance.matches(itemStack));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        @Nullable
        private final String modId;

        public TriggerInstance(EntityPredicate.Composite composite, @Nullable String modId) {
            super(ID, composite);
            this.modId = modId;
        }

        private boolean matches(ItemStack stack) {
            var id = Registry.ITEM.getKey(stack.getItem());
            return id.getNamespace().equals(modId);
        }

        @Override
        public JsonObject serializeToJson(@NotNull SerializationContext serializationContext) {
            var jo = super.serializeToJson(serializationContext);
            if (modId != null)
                jo.addProperty("modid", modId);
            return jo;
        }
    }
}
