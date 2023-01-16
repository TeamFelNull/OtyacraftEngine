package dev.felnull.otyacraftengine.forge.client.handler;

import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.client.OtyacraftEngineClient;
import dev.felnull.otyacraftengine.client.callpoint.LayerRegister;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = OtyacraftEngine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientBusHandler {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(ClientHandlerForge.class);
        MinecraftForge.EVENT_BUS.register(RenderHandlerForge.class);
        OtyacraftEngineClient.init();
    }

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers e) {
        ClientCallPointManagerOld.getInstance().call().onLayerRegistry(new LayerRegister() {
            @Override
            public <T extends LivingEntity> void addLayer(EntityType<T> entityType, Function<LivingEntityRenderer<T, ? extends EntityModel<T>>, RenderLayer<T, ? extends EntityModel<T>>> layer) {
                if (entityType == EntityType.PLAYER) {
                    for (String skin : e.getSkins()) {
                        LivingEntityRenderer<? extends Player, ? extends EntityModel<?>> renderer = e.getSkin(skin);
                        RenderLayer theLayer = layer.apply((LivingEntityRenderer<T, ? extends EntityModel<T>>) renderer);
                        renderer.addLayer(theLayer);
                    }
                } else {
                    LivingEntityRenderer<T, ? extends EntityModel<T>> renderer = e.getRenderer(entityType);
                    RenderLayer theLayer = layer.apply(renderer);
                    renderer.addLayer(theLayer);
                }
            }
        });
    }
}
