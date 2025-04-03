package net.malevolenced.noechestenderegg;

import net.fabricmc.api.ModInitializer;

import net.malevolenced.noechestenderegg.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoechestEnderegg implements ModInitializer {
	public static final String MOD_ID = "noechestenderegg";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
	}
}






	private void registerEvents() {

		ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

		if (config.disableTotem) {
			ServerTickEvents.END_SERVER_TICK.register(server -> {
				server.getPlayerManager().getPlayerList().forEach(player -> {
					ItemStack mainHandStack = player.getMainHandStack();
					ItemStack offHandStack = player.getOffHandStack();
					Integer totemAmount = player.getInventory().count(Items.TOTEM_OF_UNDYING);

					if (mainHandStack.getItem() == Items.TOTEM_OF_UNDYING
							|| offHandStack.getItem() == Items.TOTEM_OF_UNDYING) {
						player.getMainHandStack().decrement(totemAmount);
						player.getOffHandStack().decrement(totemAmount);
					}
				});
			});
		}

		if (config.disableCPVP) {
			AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
				if (entity.getType() == EntityType.END_CRYSTAL) {
					entity.kill();
					player.sendMessage(
							Text.literal("Crystals are disabled on this server!").formatted(Formatting.RED), true);
					return ActionResult.SUCCESS;
				}
				return ActionResult.PASS;
			});
			UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
				Boolean playerInNether = world.getDimension().respawnAnchorWorks();
				if (playerInNether
						&& world.getBlockState(hitResult.getBlockPos()).getBlock() instanceof RespawnAnchorBlock) {
					return ActionResult.PASS;
				} else if (world.getBlockState(hitResult.getBlockPos()).getBlock() instanceof RespawnAnchorBlock) {
					player.sendMessage(
							Text.literal("Respawns anchors are disabled on this server.").formatted(Formatting.RED),
							true);
					return ActionResult.FAIL;
				}
				return ActionResult.PASS;
			});
		}
		if (config.disableEnderPearl) {
			UseItemCallback.EVENT.register((player, world, hand) -> {
				if (player.getStackInHand(hand).getItem() == Items.ENDER_PEARL) {
					player.sendMessage(
							Text.literal("Ender pearls are disabled on this server!").formatted(Formatting.RED),
							true);
					return TypedActionResult.fail(player.getStackInHand(hand));
				}
				return TypedActionResult.pass(player.getStackInHand(hand));
			});
		}

		if (config.disableNetherite) {
			ServerTickEvents.END_SERVER_TICK.register(server -> {
				server.getPlayerManager().getPlayerList().forEach(player -> {
					ItemStack mainHandStack = player.getMainHandStack();
					ItemStack offHandStack = player.getOffHandStack();

					if (mainHandStack.getItem() == Items.NETHERITE_SWORD
							|| offHandStack.getItem() == Items.NETHERITE_SWORD) {
						player.getMainHandStack().decrement(1);
						player.getOffHandStack().decrement(1);
						player.sendMessage(Text.literal("Netherite swords are disabled on this server!")
								.formatted(Formatting.RED), true);
					}

					if (mainHandStack.getItem() == Items.NETHERITE_AXE
							|| offHandStack.getItem() == Items.NETHERITE_AXE) {
						player.getMainHandStack().decrement(1);
						player.getOffHandStack().decrement(1);
						player.sendMessage(Text.literal("Netherite axes are disabled on this server!")
								.formatted(Formatting.RED), true);
					}

					if (player.getEquippedStack(EquipmentSlot.HEAD).getItem() == Items.NETHERITE_HELMET) {
						player.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
						player.sendMessage(Text.literal("Netherite armor is disabled on this server!")
								.formatted(Formatting.RED), true);
					}

					if (player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.NETHERITE_CHESTPLATE) {
						player.equipStack(EquipmentSlot.CHEST, ItemStack.EMPTY);
						player.sendMessage(Text.literal("Netherite armor is disabled on this server!")
								.formatted(Formatting.RED), true);
					}

					if (player.getEquippedStack(EquipmentSlot.LEGS).getItem() == Items.NETHERITE_LEGGINGS) {
						player.equipStack(EquipmentSlot.LEGS, ItemStack.EMPTY);
						player.sendMessage(Text.literal("Netherite armor is disabled on this server!")
								.formatted(Formatting.RED), true);
					}

					if (player.getEquippedStack(EquipmentSlot.FEET).getItem() == Items.NETHERITE_BOOTS) {
						player.equipStack(EquipmentSlot.FEET, ItemStack.EMPTY);
						player.sendMessage(Text.literal("Netherite armor is disabled on this server!")
								.formatted(Formatting.RED), true);
					}
				});
			});
		}

		if (config.noDragonEggEnderChest) {
			ServerTickEvents.END_SERVER_TICK.register(server -> {
				server.getPlayerManager().getPlayerList().forEach(player -> {
					EnderChestInventory enderChestInventory = player.getEnderChestInventory();

					for (int i = 0; i < enderChestInventory.size(); i++) {
						if (enderChestInventory.getStack(i).getItem() == Items.DRAGON_EGG) {
							enderChestInventory.setStack(i, ItemStack.EMPTY);
							player.giveItemStack(new ItemStack(Items.DRAGON_EGG));
							player.sendMessage(Text.literal("You cannot keep the dragon egg in your ender chest!")
									.formatted(Formatting.RED), true);
						}
					}
				});
			});
		}

		if (config.riptideCooldownEnabled) {
			UseItemCallback.EVENT.register((player, world, hand) -> {
				if (world.isClient) {
					return TypedActionResult.pass(player.getStackInHand(hand));
				}

				ItemStack itemStack = player.getStackInHand(hand);

				if (itemStack.getItem() == Items.TRIDENT && player.isUsingRiptide()) {
					if (player.getItemCooldownManager().isCoolingDown(Items.TRIDENT)) {
						return TypedActionResult.fail(itemStack);
					} else {
						player.getItemCooldownManager().set(Items.TRIDENT, config.riptideCooldown);
						return TypedActionResult.success(itemStack);
					}
				}

				return TypedActionResult.pass(itemStack);
			});
		}