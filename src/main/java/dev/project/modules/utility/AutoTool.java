package dev.project.modules.utility;

import com.google.gson.JsonObject;
import dev.project.modules.*;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public final class AutoTool extends Module {
    public static final AutoTool INSTANCE = new AutoTool();
    private boolean preferSword=true, backupTool=true; private int previous=-1;
    private AutoTool(){ super("AutoTool", Category.UTILITY, new dev.project.configs.ModuleConfig()); }
    @Override public void onEnable(){ AttackBlockCallback.EVENT.register((p,w,h,pos,dir)->{ switchTool(p, w.getBlockState(pos)); return ActionResult.PASS;}); UseEntityCallback.EVENT.register((p,w,h,e,hr)->{ if(preferSword) sword(p); return ActionResult.PASS;}); }
    private void switchTool(PlayerEntity p, BlockState s){ int best=p.getInventory().selectedSlot; float sp=1f; for(int i=0;i<9;i++){ float m=p.getInventory().getStack(i).getMiningSpeedMultiplier(s); if(m>sp){sp=m;best=i;}} if(best!=p.getInventory().selectedSlot){ if(previous==-1) previous=p.getInventory().selectedSlot; p.getInventory().selectedSlot=best; }}
    private void sword(PlayerEntity p){ for(int i=0;i<9;i++) if(p.getInventory().getStack(i).isIn(net.minecraft.registry.tag.ItemTags.SWORDS)){ if(previous==-1) previous=p.getInventory().selectedSlot; p.getInventory().selectedSlot=i; return; } }
    @Override public void onTick(net.minecraft.client.MinecraftClient c){ if(backupTool&&previous!=-1&&c.player!=null&&!c.options.attackKey.isPressed()){ c.player.getInventory().selectedSlot=previous; previous=-1; }}
    @Override protected void applyConfig(JsonObject o){}
}
