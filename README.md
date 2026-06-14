# Sleeping Bag Mod

Multi-loader Minecraft 26.1.2 sleeping bag mod with Fabric and NeoForge projects.

The mod adds one sleeping bag block for each dye color. Sleeping bags can be slept in like beds, but sleeping in one does not change the player's spawn point.

## Project layout

- `fabric/` - Fabric version of the mod.
- `neoforge/` - NeoForge/ModDevGradle version of the mod.

## Building

Build each loader version from its own folder:

```sh
cd fabric
./gradlew build
```

```sh
cd neoforge
./gradlew build
```

## Setup

For Fabric setup instructions, see the [Fabric documentation](https://docs.fabricmc.net/develop/getting-started/creating-a-project#setting-up).

The NeoForge project uses ModDevGradle and can be opened or built with the included Gradle wrapper.

## License

This project is available under the CC0 license.
