package dev.felnull.otyacraftengine.api.event.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientChatEvent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public interface RenderPlayerEvent {
    Event<RenderHand> RENDER_HAND = EventFactory.createEventResult();

    public interface RenderHand {
        EventResult renderHand(PoseStack poseStack, MultiBufferSource multiBufferSource, InteractionHand hand, int packedLight, float partialTicks, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack);
    }
}
