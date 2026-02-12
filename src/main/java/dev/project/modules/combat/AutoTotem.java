package dev.project.modules.combat;

import com.google.gson.JsonObject;
import dev.project.modules.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public final class AutoTotem extends Module {
    public static final AutoTotem INSTANCE = new AutoTotem();
    private int ticks; private double healthThreshold=14.0; private int checkTicks=2; private boolean fallbackGapple=true;
    private AutoTotem(){ super("AutoTotem", Category.COMBAT, new dev.project.configs.ModuleConfig()); }
    @Override public void onTick(MinecraftClient c){
        if(c.player==null||c.interactionManager==null) return;
        if(++ticks<checkTicks) return; ticks=0;
        if(c.player.getHealth()+c.player.getAbsorptionAmount()>healthThreshold && c.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) return;
        int slot=find(c, Items.TOTEM_OF_UNDYING); if(slot==-1 && fallbackGapple) slot=find(c, Items.GOLDEN_APPLE); if(slot==-1) return;
        c.interactionManager.clickSlot(c.player.playerScreenHandler.syncId, map(slot), 40, SlotActionType.SWAP, c.player);
    }
    private int find(MinecraftClient c, net.minecraft.item.Item i){ for(int s=0;s<36;s++) if(c.player.getInventory().getStack(s).isOf(i)) return s; return -1; }
    private int map(int s){ return s<9?s+36:s; }
    @Override protected void applyConfig(JsonObject o){ if(o.has("healthThreshold")) healthThreshold=o.get("healthThreshold").getAsDouble(); if(o.has("checkTicks")) checkTicks=Math.max(1,o.get("checkTicks").getAsInt()); }
}
