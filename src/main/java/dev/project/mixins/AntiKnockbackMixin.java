package dev.project.mixins;

import dev.project.modules.combat.AntiKnockback;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = LivingEntity.class, priority = 1000)
public abstract class AntiKnockbackMixin {
    @ModifyVariable(method = "takeKnockback", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private double modifyStrength(double s){ return AntiKnockback.INSTANCE.isEnabled() ? s * AntiKnockback.INSTANCE.h() : s; }
}
