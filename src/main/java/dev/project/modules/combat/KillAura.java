package dev.project.modules.combat;

import com.google.gson.*;
import dev.project.configs.KillAuraConfig;
import dev.project.modules.*;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;

public final class KillAura extends Module {
    public static final KillAura INSTANCE = new KillAura();
    private final KillAuraConfig cfg = new KillAuraConfig();
    private int delay; private LivingEntity target;
    private KillAura(){ super("KillAura", Category.COMBAT, new KillAuraConfig()); }

    @Override public void onTick(MinecraftClient c){
        if(c.player==null||c.world==null||c.interactionManager==null) return;
        cfg.validate(); if(delay>0){delay--; return;}
        List<LivingEntity> list = c.world.getEntitiesByClass(LivingEntity.class, new Box(c.player.getBlockPos()).expand(cfg.range), this::valid);
        if(list.isEmpty()) return;
        target = select(c, list);
        if(cfg.pvpMode.equals("1.9") && c.player.getAttackCooldownProgress(0.5f) < cfg.attackCooldownThreshold) return;
        if(ThreadLocalRandom.current().nextInt(100)>=cfg.hitChance) return;
        if(!cfg.throughWalls && !c.player.canSee(target)) return;
        face(c, target);
        c.interactionManager.attackEntity(c.player, target);
        c.player.swingHand(Hand.MAIN_HAND);
        if(cfg.autoBlock && c.player.getOffHandStack().isOf(net.minecraft.item.Items.SHIELD)) c.interactionManager.interactItem(c.player, Hand.OFF_HAND);
        int spread = ThreadLocalRandom.current().nextInt(-3, 4);
        delay = Math.max(cfg.delayTicks, Math.max(1, 20 / Math.max(1, cfg.cps + spread)));
    }
    private boolean valid(LivingEntity e){ return e.isAlive() && !(e.isRemoved()) && !(e instanceof net.minecraft.client.network.ClientPlayerEntity) && (e instanceof PlayerEntity || e instanceof Monster || e instanceof AnimalEntity); }
    private LivingEntity select(MinecraftClient c, List<LivingEntity> l){
        Comparator<LivingEntity> cmp = switch (cfg.priorityMode) {
            case "health" -> Comparator.comparingDouble(LivingEntity::getHealth);
            case "hurtTime" -> Comparator.comparingInt(LivingEntity::getTimeUntilRegen);
            case "angle" -> Comparator.comparingDouble(e->Math.abs(c.player.getYaw()-yawTo(c,e)));
            default -> Comparator.comparingDouble(e->e.distanceTo(c.player));
        };
        return l.stream().min(cmp).orElse(l.getFirst());
    }
    private static float yawTo(MinecraftClient c, Entity e){ double dx=e.getX()-c.player.getX(), dz=e.getZ()-c.player.getZ(); return (float)(Math.toDegrees(Math.atan2(dz,dx))-90f); }
    private void face(MinecraftClient c, Entity e){ c.player.setYaw(yawTo(c,e)); double dy=(e.getBodyY(0.5)-c.player.getEyeY()); double d=Math.sqrt(Math.pow(e.getX()-c.player.getX(),2)+Math.pow(e.getZ()-c.player.getZ(),2)); c.player.setPitch((float)-Math.toDegrees(Math.atan2(dy,d))); }
    @Override protected void applyConfig(JsonObject o){ new Gson().fromJson(o, KillAuraConfig.class).validate(); }
}
