package dev.project.configs;
public final class FlightConfig extends ModuleConfig {
    public String mode = "vanilla";
    public double speed = 0.12;
    public boolean antiKick = true;
    public int antiKickTicks = 10;
    public double acceleration = 0.15;
    public double friction = 0.91;
    public double glideFallSpeed = 0.04;
    @Override public void validate(){ speed=Math.max(0.01,Math.min(2.0,speed)); antiKickTicks=Math.max(2,Math.min(40,antiKickTicks)); acceleration=Math.max(0.01,Math.min(1.0,acceleration)); friction=Math.max(0.1,Math.min(0.99,friction)); glideFallSpeed=Math.max(0.01,Math.min(0.3,glideFallSpeed)); }
}
