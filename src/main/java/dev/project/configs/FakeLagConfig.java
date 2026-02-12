package dev.project.configs;

public final class FakeLagConfig extends ModuleConfig {
    public enum Mode { STATIC, DYNAMIC, BLINK }

    public Mode mode = Mode.STATIC;
    public int delayTicks = 5;
    public double triggerRange = 4.0D;
    public boolean onlyWhenTargeted = false;
    public boolean visualIndicator = true;
    public boolean consumePackets = false;
    public boolean cancelOnDamage = true;
    public boolean flushOnDisable = true;
    public int maxBufferTime = 100;

    @Override
    public void validate() {
        delayTicks = Math.max(1, Math.min(20, delayTicks));
        triggerRange = Math.max(1.0D, Math.min(6.0D, triggerRange));
        maxBufferTime = Math.max(20, Math.min(200, maxBufferTime));
    }
}
