<p align="center">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="../assets/icons/install-dark.svg">
    <img src="../assets/icons/install-light.svg" alt="" width="96">
  </picture>
</p>

<h1 align="center">Installation</h1>

<p align="center">Get BetterBanHammer running in a minute.</p>

<p align="center"><a href="README.md">All docs</a></p>

## Requirements

- Any Bukkit based server on Minecraft 1.21 to 26.3: Spigot, Paper, Purpur, Folia and forks of them
- Java 21 or newer
- EssentialsX is optional, see [EssentialsX](essentials.md)

The same jar runs on all of them. The plugin only uses the Bukkit API, picks the Folia schedulers by itself and uses a Paper event for creative hits when it exists. Proxies like Velocity or BungeeCord and mod loaders are not supported.

## Steps

1. Download the jar from [Modrinth](https://modrinth.com/plugin/better-ban-hammer), [Hangar](https://hangar.papermc.io/YONN2222/BetterBanHammer) or the [GitHub releases](https://github.com/YONN2222/BetterBanHammer/releases).
2. Put it into the `plugins` folder.
3. Start the server. The plugin creates `plugins/BetterBanHammer/config.yml` and a `lang` folder.
4. Give your staff `betterbanhammer.give` and `betterbanhammer.use`, operators already have them. See [Commands](commands.md#permissions).

## First test

Run this in game:

```
/banhammer 1h Test
```

You get a mace. Hit a second account with it and watch the fireworks. Undo it with `/pardon <name>` or `/unban <name>`.

## Updating

Replace the jar and restart the server. New options are not added to an existing `config.yml` automatically, compare it with the [configuration reference](configuration.md) after an update. Missing options use their defaults.
