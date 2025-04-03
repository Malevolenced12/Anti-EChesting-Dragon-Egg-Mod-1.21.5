package net.malevolenced.noechestenderegg.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.malevolenced.noechestenderegg.NoechestEnderegg;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item RADIO = RegisterItems("radio", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(NoechestEnderegg.MOD_ID, "radio")))));

    private static Item RegisterItems(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(NoechestEnderegg.MOD_ID, name), item);
    }

    public static void registerModItems() {
        NoechestEnderegg.LOGGER.info("Registering Mod Items for " + NoechestEnderegg.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> {
            entries.add(RADIO);
        });
    }
}
