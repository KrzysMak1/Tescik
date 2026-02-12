package dev.project.configs;

public final class BacktrackConfig extends ModuleConfig {
    public enum AttackMode { CLOSEST, FURTHEST, OLDEST, NEWEST }

    public int historyTicks = 10;
    public boolean visualize = false;
    public int visualizeColor = 0x88FFFF00;
    public AttackMode attackMode = AttackMode.CLOSEST;
    public boolean pauseOnLag = true;
    public boolean ignoreInvisible = false;
    public double maxDistance = 6.0D;

    @Override
    public void validate() {
        historyTicks = Math.max(5, Math.min(40, historyTicks));
        maxDistance = Math.max(1.0D, Math.min(10.0D, maxDistance));
    }
}
