package dev.felnull.otyacraftengine.fabric.client.handler;

import dev.felnull.otyacraftengine.client.callpoint.ClientCallPointManager;
import dev.felnull.otyacraftengine.client.callpoint.LayerRegister;
import dev.felnull.otyacraftengine.client.shape.ClientIVShapeManager;
import dev.felnull.otyacraftengine.fabric.client.shape.ClientIVShapeManagerFabric;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ClientHandlerFabric {
    private static List<LayerEntry<? extends LivingEntity>> LAYERS;

    public static void init() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener((ClientIVShapeManagerFabric) ClientIVShapeManager.getInstance());
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(ClientHandlerFabric::registerRenderers);
    }

    private static void registerRenderers(EntityType<? extends LivingEntity> entityType, LivingEntityRenderer<?, ?> entityRenderer, LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper registrationHelper, EntityRendererProvider.Context context) {
        if (LAYERS == null) {
            LAYERS = new ArrayList<>();

            ClientCallPointManager.getInstance().call().onLayerRegistry(new LayerRegister() {
                @Override
                public <T extends LivingEntity> void addLayer(EntityType<T> entityType, Function<LivingEntityRenderer<T, ? extends EntityModel<T>>, RenderLayer<T, ? extends EntityModel<T>>> layer) {
                    LayerEntry<T> entry = getLayers(entityType);
                    if (entry == null) {
                        entry = new LayerEntry<T>(entityType, new ArrayList<>());
                        LAYERS.add(entry);
                    }
                    entry.layer().add(livingEntityRenderer -> layer.apply((LivingEntityRenderer<T, ? extends EntityModel<T>>) livingEntityRenderer));
                }
            });
        }

        var ls = getLayers(entityType);
        if (ls != null) {
            for (Function<LivingEntityRenderer<?, ?>, ? extends RenderLayer<?, ? extends EntityModel<?>>> livingEntityRendererFunction : ls.layer()) {
                RenderLayer layer = livingEntityRendererFunction.apply(entityRenderer);
                registrationHelper.register(layer);
            }
        }
    }

    private static <T extends LivingEntity> LayerEntry<T> getLayers(EntityType<T> entityType) {
        var ls = LAYERS.stream().filter(n -> n.entityType() == entityType).findFirst();
        return (LayerEntry<T>) ls.orElse(null);
    }

    private static record LayerEntry<T extends LivingEntity>(EntityType<T> entityType,
                                                             List<Function<LivingEntityRenderer<?, ?>, RenderLayer<T, ? extends EntityModel<T>>>> layer) {
    }
}
