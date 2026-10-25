<center>

![BetterBanHammer](https://raw.githubusercontent.com/YONN2222/BetterBanHammer/main/assets/banner.png)

![Java 21+](https://img.shields.io/badge/Java-21+-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spigot, Paper, Folia](https://img.shields.io/badge/Spigot%20%7C%20Paper%20%7C%20Folia-2C2C2C?style=flat-square)
![Minecraft 1.21 to 26.3](https://img.shields.io/badge/Minecraft-1.21%20to%2026.3-DFAB40?style=flat-square)
![GPLv3](https://img.shields.io/badge/License-GPLv3-DF6440?style=flat-square)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-2C2C2C?style=flat-square&logo=github)](https://github.com/YONN2222/BetterBanHammer)
[![Documentation](https://img.shields.io/badge/Documentation-Docs-DFAB40?style=flat-square)](https://github.com/YONN2222/BetterBanHammer/tree/main/docs)
[![Issues](https://img.shields.io/badge/Issues-Report%20a%20bug-DF6440?style=flat-square)](https://github.com/YONN2222/BetterBanHammer/issues)

</center>

Run `/banhammer 7d Griefing`, hit a player with the mace and they are banned for 7 days, with fireworks, particles and sounds at the spot.

One jar runs on Spigot, Paper, Purpur and Folia, Minecraft 1.21 to 26.3. Open source under GPLv3, EssentialsX is optional.

![Ban with fireworks](https://raw.githubusercontent.com/YONN2222/BetterBanHammer/main/assets/gallery/ban-hammer.gif)

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

## Hammer for one player

Hand a player a hammer that only works on one person. They can ban exactly that player and nobody else.

![Hammer for one player](https://raw.githubusercontent.com/YONN2222/BetterBanHammer/main/assets/gallery/targeted-hammer.gif)

![Hammer in hand](https://raw.githubusercontent.com/YONN2222/BetterBanHammer/main/assets/gallery/01-hammer.png)

![Fireworks](https://raw.githubusercontent.com/YONN2222/BetterBanHammer/main/assets/gallery/02-fireworks.png)
