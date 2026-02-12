package dev.project.configs;

public final class KillAuraConfig extends ModuleConfig {
    public String pvpMode = "1.9";
    public double attackCooldownThreshold = 0.85;
    public boolean weaponCooldownFactor = true;
    public String priorityMode = "distance";
    public int switchDelay = 2;
    public int cps = 10;
    public int delayTicks = 1;
    public boolean throughWalls = false;
    public boolean autoBlock = false;
    public int hitChance = 90;
    public double range = 4.2;
    @Override public void validate(){ attackCooldownThreshold=Math.max(0,Math.min(1,attackCooldownThreshold)); switchDelay=Math.max(0,Math.min(10,switchDelay)); cps=Math.max(1,Math.min(20,cps)); delayTicks=Math.max(0,Math.min(10,delayTicks)); hitChance=Math.max(1,Math.min(100,hitChance)); range=Math.max(1,Math.min(6,range)); }
}
