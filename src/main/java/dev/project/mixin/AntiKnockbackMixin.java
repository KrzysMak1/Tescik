package dev.project.mixin;

import dev.project.modules.combat.AntiKnockback;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value = LivingEntity.class, priority = 1000)
public abstract class AntiKnockbackMixin {
    @ModifyArgs(method = "takeKnockback", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;addVelocity(DDD)V"))
    private void utilitymod$scaleKnockback(Args args) {
        if (!AntiKnockback.INSTANCE.isEnabled()) {
            return;
        }
        double mult = AntiKnockback.INSTANCE.multiplier();
        args.set(0, (double) args.get(0) * mult);
        args.set(2, (double) args.get(2) * mult);
    }
}
