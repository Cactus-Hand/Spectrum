package de.dafuqs.spectrum.entity.render;

import com.mojang.blaze3d.vertex.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.entity.models.*;
import de.dafuqs.spectrum.entity.variants.*;
import de.dafuqs.spectrum.registries.client.*;
import net.fabricmc.api.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.resources.*;
import org.jetbrains.annotations.*;

@Environment(EnvType.CLIENT)
public class KindlingEntityRenderer extends MobRenderer<KindlingEntity, KindlingEntityModel> {
	
	public static final ResourceLocation SADDLE_TEXTURE = SpectrumCommon.locate("textures/entity/kindling/saddle.png");
	public static final ResourceLocation DEFAULT_TEXTURE = SpectrumCommon.locate("textures/entity/kindling/kindling.png");
	public static final ResourceLocation ANGRY_TEXTURE = SpectrumCommon.locate("textures/entity/kindling/kindling_angry.png");
	public static final ResourceLocation ANGRY_CLIPPED_TEXTURE = SpectrumCommon.locate("textures/entity/kindling/kindling_angry_clipped.png");
	public static final ResourceLocation BLINK_TEXTURE = SpectrumCommon.locate("textures/entity/kindling/kindling_blink.png");
	public static final ResourceLocation BLINK_CLIPPED_TEXTURE = SpectrumCommon.locate("textures/entity/kindling/kindling_blink_clipped.png");
	public static final ResourceLocation CLIPPED_TEXTURE = SpectrumCommon.locate("textures/entity/kindling/kindling_clipped.png");
	
	public KindlingEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new KindlingEntityModel(context.bakeLayer(SpectrumModelLayers.KINDLING)), 0.7F);
		this.addLayer(new SaddleLayer<>(this, new KindlingEntityModel(context.bakeLayer(SpectrumModelLayers.KINDLING_SADDLE)), SADDLE_TEXTURE));
		this.addLayer(new KindlingEntityArmorFeatureRenderer(this, context.getModelSet()));
	}
	
	@Override
	public void render(KindlingEntity entity, float yaw, float tickDelta, PoseStack poseStack, MultiBufferSource vertexConsumerProvider, int light) {
		super.render(entity, yaw, tickDelta, poseStack, vertexConsumerProvider, light);
	}

	// Removed Variant texture compat for Connector compat
	
	@Override
	public ResourceLocation getTextureLocation(@NotNull KindlingEntity entity) {
		// KindlingVariant variant = entity.getKindlingVariant().value();
		boolean isClipped = entity.isClipped();
		if (entity.getRemainingPersistentAngerTime() > 0) {
			return isClipped ? ANGRY_CLIPPED_TEXTURE : ANGRY_TEXTURE;
		}
		
		boolean isBlinking = (entity.getId() - entity.level().getGameTime()) % 120 == 0; // based on the entities' id, so not all blink at the same time
		if (isClipped) {
			return isBlinking ? BLINK_CLIPPED_TEXTURE : CLIPPED_TEXTURE;
		}
		
		return isBlinking ? BLINK_TEXTURE :DEFAULT_TEXTURE;
	}

	@Override
	protected boolean isShaking(KindlingEntity entity) {
		return entity.getEepyTime() > 0;
	}
}
