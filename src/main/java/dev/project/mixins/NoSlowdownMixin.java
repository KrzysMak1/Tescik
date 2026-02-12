package dev.project.mixins;

import dev.project.modules.movement.NoSlowdown;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = LivingEntity.class, priority = 1000)
public abstract class NoSlowdownMixin {
    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;slowMovement(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Vec3d;)V"))
    private void hook(LivingEntity self, net.minecraft.block.BlockState state, Vec3d m){ if(!NoSlowdown.INSTANCE.isEnabled()) self.slowMovement(state,m); }
}
