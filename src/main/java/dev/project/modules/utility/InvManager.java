package dev.project.modules.utility;

import com.google.gson.JsonObject;
import dev.project.modules.*;
import java.util.*;
import net.minecraft.client.MinecraftClient;

public final class InvManager extends Module {
    public static final InvManager INSTANCE = new InvManager();
    private final Set<String> trashList = new HashSet<>(Set.of("minecraft:dirt","minecraft:cobblestone"));
    private int replenishThreshold=8;
    private InvManager(){ super("InvManager", Category.UTILITY, new dev.project.configs.ModuleConfig()); }
    @Override public void onTick(MinecraftClient c){ if(c.player==null) return;
        for(int i=9;i<36;i++){ var st=c.player.getInventory().getStack(i); if(trashList.contains(net.minecraft.registry.Registries.ITEM.getId(st.getItem()).toString())){ c.player.dropItem(st.copy(), true); c.player.getInventory().removeStack(i); break; }}
        for(int h=0;h<9;h++){ var hs=c.player.getInventory().getStack(h); if(hs.isEmpty()||hs.getCount()>=replenishThreshold) continue; for(int i=9;i<36;i++){ var s=c.player.getInventory().getStack(i); if(net.minecraft.item.ItemStack.areItemsAndComponentsEqual(hs,s)){ hs.increment(Math.min(hs.getMaxCount()-hs.getCount(), s.getCount())); s.decrement(Math.min(hs.getMaxCount()-hs.getCount(), s.getCount())); break; } } }
    }
    @Override protected void applyConfig(JsonObject o){}
}
