package dev.project.util;

import java.util.Comparator;
import java.util.Optional;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public final class Targeting {
    private Targeting() {
    }

    public static Optional<LivingEntity> nearestLiving(MinecraftClient client, double maxRange) {
        if (client.player == null || client.world == null) {
            return Optional.empty();
        }
        return client.world.getEntities().stream()
            .filter(entity -> entity instanceof LivingEntity)
            .map(entity -> (LivingEntity) entity)
            .filter(entity -> entity != client.player)
            .filter(Entity::isAlive)
            .filter(entity -> entity.distanceTo(client.player) <= maxRange)
            .min(Comparator.comparingDouble(entity -> entity.distanceTo(client.player)));
    }
}
