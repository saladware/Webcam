package ru.dimaskama.webcam.mixin.client;

import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.dimaskama.webcam.client.DisplayingVideoManager;
import ru.dimaskama.webcam.client.WebcamModClient;
import ru.dimaskama.webcam.client.duck.AvatarRenderStateDuck;
import ru.dimaskama.webcam.client.render.WebcamRenderLayer;

@Mixin(AvatarRenderer.class)
abstract class AvatarRendererMixin<AvatarLikeEntity extends Avatar & ClientAvatarEntity> extends LivingEntityRenderer<AvatarLikeEntity, AvatarRenderState, PlayerModel> {

    private AvatarRendererMixin() {
        super(null, null, 0.0F);
        throw new AssertionError();
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addWebcamRenderLayer(EntityRendererProvider.Context context, boolean slim, CallbackInfo ci) {
        addLayer(new WebcamRenderLayer<>(this, context.getEntityRenderDispatcher()));
    }

    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V",
            at = @At("TAIL")
    )
    private void updateRenderState(AvatarLikeEntity player, AvatarRenderState renderState, float partialTick, CallbackInfo ci) {
        ((AvatarRenderStateDuck) renderState).webcam_setDisplayingVideo(
                DisplayingVideoManager.INSTANCE.hasViewPermission() && WebcamModClient.CONFIG.getData().showWebcams()
                        ? DisplayingVideoManager.INSTANCE.get(player.getUUID())
                        : null
        );
    }

}
