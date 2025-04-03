package net.malevolenced.noechestenderegg;

import net.fabricmc.api.ModInitializer;
import net.malevolenced.noechestenderegg.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.brigadier.arguments.IntegerArgumentType;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class NoechestEnderegg implements ModInitializer {
	public static final String MOD_ID = "noechestenderegg";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		registerConfig();
		registerEvents();
		registerCommands();
	}

	private void registerConfig() {
		AutoConfig.register(ModConfig.class, GsonConfigSerializer::new); // Directly instantiate the serializer
	}

	private void registerEvents() {
		// Register the server tick event to handle the Ender Chest check
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			if (AutoConfig.getConfigHolder(ModConfig.class).getConfig().noDragonEggEnderChest) {
				server.getPlayerManager().getPlayerList().forEach(player -> {
					EnderChestInventory enderChestInventory = player.getEnderChestInventory();

					for (int i = 0; i < enderChestInventory.size(); i++) {
						if (enderChestInventory.getStack(i).getItem() == Items.DRAGON_EGG) {
							enderChestInventory.setStack(i, ItemStack.EMPTY);
							player.giveItemStack(new ItemStack(Items.DRAGON_EGG));
							player.sendMessage(Text.literal("You cannot keep the dragon egg in your ender chest!")
									.formatted(Formatting.DARK_RED), true);
						}
					}
				});
			}
		});
	}

	private void registerCommands() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			// Correctly register the "noechestenderegg" command
			dispatcher.register(CommandManager.literal("dragonegg")
					.executes(context -> {
						// Get the source (ServerCommandSource) and send feedback
						ServerCommandSource source = context.getSource();
						source.sendFeedback(() -> Text.literal("The Dragon Egg cannot be put in the Ender Chest!"), false);
						return 1;
					})
			);
		});
	}
}