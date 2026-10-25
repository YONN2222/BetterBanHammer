# Changelog

## [1.0.0]

- Ban players by hitting them with a mace created by `/banhammer [duration] [reason]`
- Duration and reason are stored in the hammer, durations like `30m`, `1h30m`, `7d`, `2w`, `1mo` or `permanent`
- Uses EssentialsX `/ban` and `/tempban` when installed, the server ban list otherwise
- Works on creative players on Paper and its forks
- Fireworks, particles, sounds and optional lightning at the spot of the ban
- Staff only hammer: needs `betterbanhammer.use`, players cannot pick it up
- Hammers that only work on one player with `/banhammer target`, switched on with `ban.targeted.enabled`
- Cooldown, sneak requirement, disabled worlds, operator protection, IP bans, consuming hammers and a ban log
- Tab completion for durations and reasons
- One jar for Spigot, Paper, Purpur and Folia, Minecraft 1.21 to 26.3
- Messages in 10 languages: en, zh, es, hi, fr, ar, pt, ru, de, ja
- Permissions: `betterbanhammer.give`, `.use`, `.target`, `.reload`, `.exempt`
