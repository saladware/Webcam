package ru.dimaskama.webcam.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import ru.dimaskama.webcam.config.VideoDisplayShape;

public class WebcamRenderer {

    public static void render(Identifier textureId, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, float halfWidth, float halfHeight, VideoDisplayShape shape) {
        switch (shape) {
            case SQUARE -> submitNodeCollector.submitCustomGeometry(
                    poseStack,
                    WebcamRenderTypes.square(textureId),
                    (pose, consumer) ->
                            WebcamRenderer.renderSquare(pose, consumer, halfWidth, halfHeight)
            );
            case ROUND -> submitNodeCollector.submitCustomGeometry(
                    poseStack,
                    WebcamRenderTypes.round(textureId),
                    (pose, consumer) ->
                            WebcamRenderer.renderRound(pose, consumer, halfWidth, halfHeight)
            );
        }
    }

    public static void renderSquare(PoseStack.Pose pose, VertexConsumer consumer, float halfWidth, float halfHeight) {
        consumer.addVertex(pose, -halfWidth, -halfHeight, 0.0F).setUv(0.0F, 0.0F);
        consumer.addVertex(pose, -halfWidth, halfHeight, 0.0F).setUv(0.0F, 1.0F);
        consumer.addVertex(pose, halfWidth, halfHeight, 0.0F).setUv(1.0F, 1.0F);
        consumer.addVertex(pose, halfWidth, -halfHeight, 0.0F).setUv(1.0F, 0.0F);
    }

    public static void renderRound(PoseStack.Pose pose, VertexConsumer consumer, float halfWidth, float halfHeight) {
        int numVertices = Mth.clamp(Math.round(8.0F * Mth.TWO_PI * halfWidth), 48, 360);
        consumer.addVertex(pose, 0.0F, 0.0F, 0.0F).setUv(0.5F, 0.5F);
        for (int i = 0; i <= numVertices; i++) {
            float angle = Mth.TWO_PI * i / numVertices;
            float x = Mth.cos(angle);
            float y = -Mth.sin(angle);
            consumer.addVertex(pose, halfWidth * x, halfHeight * y, 0.0F).setUv(0.5F * (x + 1.0F), 0.5F * (y + 1.0F));
        }
    }

}
