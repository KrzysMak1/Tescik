package dev.project.mixin;

import dev.project.modules.visual.XRay;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockRenderManager.class, priority = 1000)
public abstract class XrayBlockRenderMixin {
    @Inject(method = "renderBlock", at = @At("HEAD"), cancellable = true)
    private void utilitymod$filterBlock(BlockState state, net.minecraft.util.math.BlockPos pos,
                                        net.minecraft.world.BlockRenderView world,
                                        net.minecraft.client.util.math.MatrixStack matrices,
                                        net.minecraft.client.render.VertexConsumer vertexConsumer,
                                        boolean cull, net.minecraft.util.math.random.Random random,
                                        CallbackInfoReturnable<Boolean> cir) {
        if (!XRay.INSTANCE.isEnabled()) {
            return;
        }
        Identifier id = Registries.BLOCK.getId(state.getBlock());
        if (!XRay.INSTANCE.visibleBlocks().contains(id.toString())) {
            cir.setReturnValue(false);
        }
    }
}
