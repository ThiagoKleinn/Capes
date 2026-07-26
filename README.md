# Custom Capes

A Minecraft 1.8.9 Forge mod that lets any player use custom capes — no premium account required.

## Features

- Choose from a selection of capes included in the mod
- In-game GUI to select or remove your cape (press **P**)
- Cape choice is saved locally and restored on next launch
- Works on any client, premium or not

## Requirements

- Minecraft 1.8.9
- Forge `1.8.9-11.15.1.2318-1.8.9`

## Installation

1. Install [Minecraft Forge 1.8.9](https://files.minecraftforge.net)
2. Drop `customcapes-1.0.0.jar` into your `.minecraft/mods/` folder
3. Launch Minecraft with the Forge 1.8.9 profile

## Usage

| Action | How |
|---|---|
| Open cape menu | Press **P** in-game |
| Select a cape | Click a cape in the list → **Select** |
| Remove cape | Click **Remove Cape** |

## Adding Custom Capes

1. Add your PNG file (64×32 pixels) to:

src/main/resources/assets/customcapes/textures/capes/

2. Register it in `CapeManager.java`:
```java
   AVAILABLE_CAPES.put("My Cape", new ResourceLocation("customcapes", "textures/capes/mycape.png"));
```

## Building from Source

Requires JDK 8 — [Amazon Corretto 8](https://aws.amazon.com/corretto/) recommended.

```bash
export JAVA_HOME=/path/to/corretto-8
./gradlew setupDecompWorkspace
./gradlew build
```

The output JAR will be at `build/libs/customcapes-1.0.0.jar`.

## License

This project is licensed under the MIT License — see [LICENSE](LICENSE) for details.