package dev.project.configs;
public final class ESPConfig extends ModuleConfig {
    public boolean box = true; public boolean outline = true; public boolean tracer = true;
    public int color = 0xFF00FF00; public int tracerColor = 0xFFFFFFFF; public double maxRange = 64;
    @Override public void validate(){ maxRange=Math.max(4,Math.min(256,maxRange)); }
}
