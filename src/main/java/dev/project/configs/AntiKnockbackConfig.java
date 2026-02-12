package dev.project.configs;
public final class AntiKnockbackConfig extends ModuleConfig {
    public double horizontal = 100; public double vertical = 100; public boolean players = true; public boolean mobs = true; public boolean explosions = true;
    @Override public void validate(){ horizontal=Math.max(0,Math.min(100,horizontal)); vertical=Math.max(0,Math.min(100,vertical)); }
}
