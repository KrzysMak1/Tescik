package dev.project.modules.combat;

import com.google.gson.JsonObject;
import dev.project.modules.*;
import java.util.Comparator;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;

public final class CrystalAura extends Module {
    public static final CrystalAura INSTANCE = new CrystalAura();
    private double placeRange=4.5, breakRange=5.0, facePlaceHealth=10.0; private boolean autoSwap=true, antiSuicide=true;
    private CrystalAura(){ super("CrystalAura", Category.COMBAT, new dev.project.configs.ModuleConfig()); }
    @Override public void onTick(MinecraftClient c){
        if(c.player==null||c.world==null||c.interactionManager==null) return;
        var enemy = c.world.getPlayers().stream().filter(p->p!=c.player).min(Comparator.comparingDouble(p->p.distanceTo(c.player))).orElse(null);
        if(enemy==null) return;
        c.world.getEntitiesByClass(EndCrystalEntity.class, new Box(c.player.getBlockPos()).expand(breakRange), e->true).stream().min(Comparator.comparingDouble(e->e.distanceTo(enemy))).ifPresent(cr->{
            if(!antiSuicide || c.player.distanceTo(cr)>3.5f) c.interactionManager.attackEntity(c.player, cr);
        });
        if(autoSwap && !c.player.getMainHandStack().isOf(Items.END_CRYSTAL)) for(int i=0;i<9;i++) if(c.player.getInventory().getStack(i).isOf(Items.END_CRYSTAL)) c.player.getInventory().selectedSlot=i;
        if(!c.player.getMainHandStack().isOf(Items.END_CRYSTAL)) return;
        BlockPos p=BlockPos.ofFloored(enemy.getPos()).down(); if(c.player.squaredDistanceTo(Vec3d.ofCenter(p))>placeRange*placeRange) return;
        if((c.world.getBlockState(p).isOf(Blocks.OBSIDIAN)||c.world.getBlockState(p).isOf(Blocks.BEDROCK)) && c.world.getBlockState(p.up()).isAir()) c.interactionManager.interactBlock(c.player, Hand.MAIN_HAND, new BlockHitResult(Vec3d.ofCenter(p), Direction.UP, p, false));
    }
    @Override protected void applyConfig(JsonObject o){}
}
