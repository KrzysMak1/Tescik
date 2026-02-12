package dev.project.gui.components;
import java.util.List;
public final class EnumSetting { public String name; public List<String> values; public int index; public EnumSetting(String name,List<String> values,String current){this.name=name;this.values=values;this.index=Math.max(0,values.indexOf(current));} public String current(){return values.get(index);} }
