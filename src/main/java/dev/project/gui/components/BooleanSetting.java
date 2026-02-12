package dev.project.gui.components;
public final class BooleanSetting { public String name; public boolean value; public BooleanSetting(String name, boolean value){this.name=name;this.value=value;} public void toggle(){value=!value;} }
