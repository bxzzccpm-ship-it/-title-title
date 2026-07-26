package net.xcvb.totem;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item TOTEM_XCVB_SIMPLE = register(TotemTier.SIMPLE.itemId, new Item.Settings().maxCount(1));
    public static final Item TOTEM_XCVB_MEDIUM = register(TotemTier.MEDIUM.itemId, new Item.Settings().maxCount(1));
    public static final Item TOTEM_XCVB_STRONG = register(TotemTier.STRONG.itemId, new Item.Settings().maxCount(1));

    private static Item register(String path, Item.Settings settings) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("xcvbtotem", path));
        Item item = new Item(settings.registryKey(key));
        return Registry.register(Registries.ITEM, key, item);
    }

    public static Item itemForTier(TotemTier tier) {
        return switch (tier) {
            case SIMPLE -> TOTEM_XCVB_SIMPLE;
            case MEDIUM -> TOTEM_XCVB_MEDIUM;
            case STRONG -> TOTEM_XCVB_STRONG;
        };
    }

    public static void register() {
        // Put the three totems in the combat creative tab, right after the vanilla totem of undying.
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(TOTEM_XCVB_SIMPLE);
            entries.add(TOTEM_XCVB_MEDIUM);
            entries.add(TOTEM_XCVB_STRONG);
        });
    }
}
