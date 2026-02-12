package dev.project.mixin;

import dev.project.modules.movement.NoSlowdown;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = ClientPlayerEntity.class, priority = 1000)
public abstract class NoSlowdownMixin {
    @ModifyVariable(method = "tickMovement", at = @At("STORE"), ordinal = 0)
    private float utilitymod$keepSpeedWhileUsing(float value) {
        if (NoSlowdown.INSTANCE.enabledForFoodAndUse()) {
            return 1.0F;
        }
        return value;
    }
}
