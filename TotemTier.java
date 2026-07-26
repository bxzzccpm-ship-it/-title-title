package net.xcvb.totem;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.util.List;

/**
 * The three power levels of the XCVB Totem.
 * Each tier grants longer and stronger effects than the one below it,
 * on top of the normal "totem of undying" death-cancelling behaviour.
 */
public enum TotemTier {

    // name, regen amplifier, effect duration (ticks), absorption amplifier, fire resist ticks, extra hearts shown
    SIMPLE("totem_xcvb_simple", 1, 900, 0, 200),   // ~45s regen II, absorption I, ~10s fire resist
    MEDIUM("totem_xcvb_medium", 2, 1600, 1, 400),  // ~80s regen III, absorption II, ~20s fire resist
    STRONG("totem_xcvb_strong", 3, 2400, 2, 600);  // 120s regen IV, absorption III, 30s fire resist

    public final String itemId;
    private final int regenAmplifier;
    private final int effectDurationTicks;
    private final int absorptionAmplifier;
    private final int fireResistanceDurationTicks;

    TotemTier(String itemId, int regenAmplifier, int effectDurationTicks, int absorptionAmplifier, int fireResistanceDurationTicks) {
        this.itemId = itemId;
        this.regenAmplifier = regenAmplifier;
        this.effectDurationTicks = effectDurationTicks;
        this.absorptionAmplifier = absorptionAmplifier;
        this.fireResistanceDurationTicks = fireResistanceDurationTicks;
    }

    /**
     * The status effects applied to the player the instant this tier of totem saves them from death.
     * These stack on top of vanilla's own regen II (900 ticks) + absorption I (100 ticks) that
     * the base game applies during tryUseTotem - ours simply overrides with stronger/longer values.
     */
    public List<StatusEffectInstance> getEffects() {
        return List.of(
                new StatusEffectInstance(StatusEffects.REGENERATION, effectDurationTicks, regenAmplifier, false, true, true),
                new StatusEffectInstance(StatusEffects.ABSORPTION, effectDurationTicks, absorptionAmplifier, false, true, true),
                new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, fireResistanceDurationTicks, 0, false, true, true)
        );
    }

    public static TotemTier next(TotemTier tier) {
        return switch (tier) {
            case SIMPLE -> MEDIUM;
            case MEDIUM -> STRONG;
            case STRONG -> STRONG;
        };
    }
}
