package dev.project.util;

import java.util.UUID;
import net.minecraft.util.math.Vec3d;

public record PlayerSnapshot(UUID playerId, Vec3d position, int tick, float health, boolean alive) {}
