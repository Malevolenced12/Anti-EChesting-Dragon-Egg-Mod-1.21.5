package net.malevolenced.noechestenderegg;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = NoechestEnderegg.MOD_ID)
public class ModConfig implements ConfigData {
    public boolean noDragonEggEnderChest = true;
}