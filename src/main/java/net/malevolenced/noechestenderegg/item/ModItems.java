package net.malevolenced.noechestenderegg.item;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.malevolenced.noechestenderegg.NoechestEnderegg;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import java.util.List;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.malevolenced.noechestenderegg.NoechestEnderegg;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import java.util.List;
import java.util.function.Consumer;

public class RadioItem extends Item {

    public RadioItem(Settings settings) {
        super(settings);
    }


    }

public class ModItems {
    public static final Item RADIO = new RadioItem(new Item.Settings().group(ItemGroup.MISC));

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier("noechestenderegg", "radio"), RADIO);
    }

    // Static class for Custom Sounds
    public static class CustomSounds {
        private CustomSounds() {
            // Private constructor to prevent instantiation
        }

         public static final SoundEvent KSI = registerSound("ksi");
         private static SoundEvent registerSound(String id) {
            Identifier identifier = Identifier.of(NoechestEnderegg.MOD_ID, id);

            if (Registries.SOUND_EVENT.containsId(identifier)) {
                return Registries.SOUND_EVENT.get(identifier);
            }

            return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
         }

    public static void initialize() {
       NoechestEnderegg.LOGGER.info("Registering " + NoechestEnderegg.MOD_ID + " Sounds");
    }
}

    public static final Item RADIO = RegisterItems("radio", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(NoechestEnderegg.MOD_ID, "radio")))) {
    });

    private static Item RegisterItems(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(NoechestEnderegg.MOD_ID, name), item);
    }

    public static void registerModItems() {
        NoechestEnderegg.LOGGER.info("Registering Mod Items for " + NoechestEnderegg.MOD_ID);
        registerItem("radio", RADIO)
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> {
            entries.add(RADIO);
        });
    }
}