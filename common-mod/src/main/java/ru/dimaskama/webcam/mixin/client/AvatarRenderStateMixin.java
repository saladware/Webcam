package ru.dimaskama.webcam.mixin.client;

import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import ru.dimaskama.webcam.client.DisplayingVideo;
import ru.dimaskama.webcam.client.duck.AvatarRenderStateDuck;

import javax.annotation.Nullable;

@Mixin(AvatarRenderState.class)
abstract class AvatarRenderStateMixin implements AvatarRenderStateDuck {

    @Unique
    private DisplayingVideo webcam_displayingVideo;

    @Override
    public void webcam_setDisplayingVideo(@Nullable DisplayingVideo displayingVideo) {
        webcam_displayingVideo = displayingVideo;
    }

    @Override
    @Nullable
    public DisplayingVideo webcam_getDisplayingVideo() {
        return webcam_displayingVideo;
    }

}
