package net.xcvb.totem;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XcvbTotemMod implements ModInitializer {

    public static final String MOD_ID = "xcvbtotem";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.register();
        LOGGER.info("XCVB Totem mod loaded - 3 tiers ready (simple / medium / strong)");
    }
}
