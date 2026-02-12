package dev.project.mixins;

import dev.project.modules.exploit.Backtrack;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = ClientPlayerInteractionManager.class, priority = 1000)
public abstract class BacktrackMixin {
    @ModifyVariable(method = "attackEntity", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private Entity utilitymod$backtrackTarget(Entity target) {
        return Backtrack.INSTANCE.resolveAttackTarget(target);
    }
}
