package dev.project.modules.world;

import com.google.gson.JsonObject;
import dev.project.configs.ScaffoldConfig;
import dev.project.modules.*;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;

public final class Scaffold extends Module {
    public static final Scaffold INSTANCE = new Scaffold();
    private final ScaffoldConfig cfg = new ScaffoldConfig();
    private Scaffold(){ super("Scaffold", Category.WORLD, new ScaffoldConfig()); }
    @Override public void onTick(MinecraftClient c){
        if(c.player==null||c.world==null||c.interactionManager==null) return;
        if(cfg.safeWalk && !c.player.isSneaking() && c.player.isOnGround()){ Vec3d v=c.player.getVelocity(); if(!c.world.getBlockState(c.player.getBlockPos().down()).isSolidBlock(c.world,c.player.getBlockPos().down())) c.player.setVelocity(v.x*0.2,v.y,v.z*0.2); }
        BlockPos place = c.player.getBlockPos().down(); if(!c.world.getBlockState(place).isAir()) return;
        int slot = bestBlockSlot(c); if(slot==-1) return; c.player.getInventory().selectedSlot=slot;
        if(cfg.rotate){ c.player.setPitch(82f); }
        c.interactionManager.interactBlock(c.player, Hand.MAIN_HAND, new BlockHitResult(Vec3d.ofCenter(place), Direction.UP, place, false));
        if(cfg.tower && c.options.jumpKey.isPressed()) c.player.setVelocity(c.player.getVelocity().x, 0.42, c.player.getVelocity().z);
    }
    private int bestBlockSlot(MinecraftClient c){ int best=-1,count=0; for(int i=0;i<9;i++) if(c.player.getInventory().getStack(i).getItem() instanceof BlockItem bi && Block.getBlockFromItem(bi).getDefaultState().isFullCube(c.world,c.player.getBlockPos())){ int n=c.player.getInventory().getStack(i).getCount(); if(n>count){count=n;best=i;} } return best; }
    @Override protected void applyConfig(JsonObject o){}
}
