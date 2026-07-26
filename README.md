# XCVB Totem Mod (Fabric 1.21.1)

Adds three craftable tiers of a custom "XCVB" totem: Simple, Medium, Strong.
Holding one in your main or off hand saves you from death exactly like the
vanilla Totem of Undying, but each tier grants longer and stronger effects
(Regeneration, Absorption, Fire Resistance) than the one before it. Implemented
via a single, narrow mixin into `LivingEntity#tryUseTotem` that only activates
when you're holding one of these three items — it does not touch or override
any vanilla item, so it should not conflict with other mods unless they also
mixin into that exact method.

## Recipes
- **Simple**: 1 Totem of Undying + 4 Emeralds (shapeless)
- **Medium**: 1 Simple totem + 4 Diamonds (shapeless)
- **Strong**: 1 Medium totem + 2 Nether Stars + 2 Diamond Blocks (shapeless)

## Effects on trigger
| Tier   | Regeneration       | Absorption | Fire Resistance |
|--------|--------------------|------------|------------------|
| Simple | II, 45s            | I          | 10s              |
| Medium | III, 80s           | II         | 20s              |
| Strong | IV, 120s           | III        | 30s              |

All values are in `TotemTier.java` — change the numbers there to rebalance.

## How to build (I can't compile this myself — no internet access in my sandbox)
You need a PC with internet and **JDK 21** installed.

1. Unzip this project.
2. Open a terminal in the project folder.
3. If you don't already have Gradle installed, install it once, then run:
   ```
   gradle wrapper --gradle-version 8.8
   ```
   (this generates `gradlew` / `gradlew.bat` — only needs to be done once)
4. Build the mod:
   - Windows: `gradlew.bat build`
   - Mac/Linux: `./gradlew build`
5. The finished jar appears in `build/libs/xcvb-totem-mod-1.0.0.jar`.

Alternatively, open the folder directly in **IntelliJ IDEA** with the Gradle
plugin — it will detect `build.gradle` and offer to import/build it for you
with no manual Gradle install needed.

## Install
1. Make sure the server (and every client, since this changes client-visible
   items) is running **Fabric Loader 0.16.9+** on **Minecraft 1.21.1**.
2. Install **Fabric API 0.115.0+1.21.1** as well (it's a dependency).
3. Drop the built jar into the `mods` folder.
4. Restart the server/client.

⚠️ Note: your existing self-hosted server (from our earlier conversations) was
set up on **Fabric 1.20.1**. This mod is targeted at **1.21.1** as you
requested — it will not load on the 1.20.1 server unless you update that
server to 1.21.1 (which would also require updating any other 1.20.1-only
mods you have installed there).

## Custom textures
Placeholder 16x16 icons are included (green/blue/purple) so the items don't
look like question marks — swap the PNGs in
`src/main/resources/assets/xcvbtotem/textures/item/` with your own art anytime,
no code changes needed.
