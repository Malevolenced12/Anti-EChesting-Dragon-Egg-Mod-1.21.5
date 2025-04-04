package net.malevolenced.noechestenderegg.item;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.malevolenced.noechestenderegg.item.ModItems;
import net.malevolenced.noechestenderegg.NoechestEnderegg;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.item.ItemUsage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.OggAudioStream;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.item.ItemUsageContext;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RadioItem extends Item {
    public RadioItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.noechestenderegg.radio.tooltip").formatted(Formatting.GOLD));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            world.playSound(null, user.getBlockPos(), ModItems.CustomSounds.KSI, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }


            return ActionResult.SUCCESS;
        }
        public static void registerTooltipCallback() {
            ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
                // Check if the item is an instance of RadioItem
                if (!itemStack.isOf(ModItems.RADIO)) {

                    return;
                }
                list.add(Text.translatable("item.noechestenderegg_radio.tooltip").formatted(Formatting.GOLD));
            });
        }
}