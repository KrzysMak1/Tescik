package dev.project.gui.components;
public final class SliderSetting { public String name; public double min,max,value; public SliderSetting(String name,double min,double max,double value){this.name=name;this.min=min;this.max=max;this.value=Math.max(min,Math.min(max,value));} }
