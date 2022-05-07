package dev.felnull.otyacraftengine.client.entrypoint;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Function;

public interface LayerRegister {
    <T extends LivingEntity> void addLayer(EntityType<T> entityType, Function<LivingEntityRenderer<T, ? extends EntityModel<T>>, RenderLayer<T, ? extends EntityModel<T>>> layer);
}
