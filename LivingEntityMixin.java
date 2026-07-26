package net.xcvb.totem.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.xcvb.totem.ModItems;
import net.xcvb.totem.TotemTier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Hooks into LivingEntity#tryUseTotem, the exact same method vanilla uses for the
 * normal Totem of Undying. We only act when the entity is holding one of the three
 * XCVB totems; otherwise this mixin does nothing and vanilla behaves exactly as before.
 * This avoids touching/overriding any vanilla item logic, so it should not conflict
 * with other mods that don't also mixin into this same method.
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void xcvbtotem$tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;

        Hand usedHand = null;
        TotemTier tier = null;

        for (Hand hand : Hand.values()) {
            ItemStack stack = self.getStackInHand(hand);
            TotemTier found = tierOf(stack);
            if (found != null) {
                usedHand = hand;
                tier = found;
                break;
            }
        }

        // Not holding an XCVB totem in either hand: let vanilla logic (or the real
        // Totem of Undying) run completely untouched.
        if (tier == null) {
            return;
        }

        ItemStack usedStack = self.getStackInHand(usedHand);

        if (!self.getWorld().isClient) {
            self.setHealth(1.0F);
            tier.getEffects().forEach(self::addStatusEffect);

            // 35 is the vanilla network status id for "play totem of undying effect".
            // It is stable across versions and works for any item, not just the vanilla totem.
            self.getWorld().sendEntityStatus(self, (byte) 35);

            self.getWorld().playSound(
                    null,
                    self.getX(), self.getY(), self.getZ(),
                    SoundEvents.ITEM_TOTEM_USE,
                    SoundCategory.PLAYERS,
                    1.0F, 1.0F
            );

            if (self instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
                usedStack.decrement(1);
            }
        }

        cir.setReturnValue(true);
        cir.cancel();
    }

    private static TotemTier tierOf(ItemStack stack) {
        if (stack.isEmpty()) return null;
        if (stack.isOf(ModItems.TOTEM_XCVB_SIMPLE)) return TotemTier.SIMPLE;
        if (stack.isOf(ModItems.TOTEM_XCVB_MEDIUM)) return TotemTier.MEDIUM;
        if (stack.isOf(ModItems.TOTEM_XCVB_STRONG)) return TotemTier.STRONG;
        return null;
    }
}
