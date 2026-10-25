# BetterBanHammer

An admin ban hammer that bans on hit, with EssentialsX support. One jar for Spigot, Paper, Purpur and Folia, Minecraft 1.21 to 26.3.

## Features

- A mace as ban hammer, duration and reason stored in the item
- Uses EssentialsX `/ban` and `/tempban` when installed, the server ban list otherwise
- Works on creative players on Paper and its forks
- Fireworks, particles, sounds and optional lightning at the spot, all configurable
- Admin tool: needs `betterbanhammer.use`, players cannot pick the hammer up
- Optional hammers that only work on one chosen player
- Free duration input like `30m`, `1h30m`, `7d`, `permanent`
- Cooldown, sneak requirement, disabled worlds, operator protection, IP bans and a ban log
- 10 languages and every message is editable

## Commands

| Command | Permission |
|---|---|
| `/banhammer [duration] [reason]` | `betterbanhammer.give` |
| `/banhammer give <player> [duration] [reason]` | `betterbanhammer.give` |
| `/banhammer target <player> [holder] [duration] [reason]` | `betterbanhammer.target` |
| `/banhammer reload` | `betterbanhammer.reload` |

Hitting a player with the hammer needs `betterbanhammer.use`. Everything is configurable in `config.yml`.

Source code and documentation: https://github.com/YONN2222/BetterBanHammer
