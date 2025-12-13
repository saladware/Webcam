package ru.dimaskama.webcam.mixin.client;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.state.LevelRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.dimaskama.webcam.client.render.WebcamWorldRenderer;

@Mixin(LevelRenderer.class)
abstract class LevelRendererMixin {

    @Shadow
    @Final
    private LevelRenderState levelRenderState;

    @Shadow
    @Final
    private SubmitNodeStorage submitNodeStorage;

    @Inject(method = "/method_62214|lambda\\$addMainPass\\$1/", at = @At(value = "CONSTANT", args = "stringValue=submitBlockEntities", ordinal = 0))
    private void afterEntitiesRender(CallbackInfo ci) {
        WebcamWorldRenderer.renderWorldWebcams(levelRenderState.cameraRenderState, submitNodeStorage);
    }

}
