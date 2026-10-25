<p align="center">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="../assets/icons/config-dark.svg">
    <img src="../assets/icons/config-light.svg" alt="" width="96">
  </picture>
</p>

<h1 align="center">Configuration reference</h1>

<p align="center">Every option and its default.</p>

<p align="center"><a href="README.md">All docs</a></p>

The file is `plugins/BetterBanHammer/config.yml` and every option is explained in it. Apply changes with `/banhammer reload`.

| Option | Default | About |
|---|---|---|
| `language` | `en` | Language file from `lang/` |
| `hammer.material` | `MACE` | Item used as the hammer |
| `hammer.glint` | `true` | Enchantment glint |
| `hammer.unbreakable` | `true` | Unbreakable item |
| `hammer.staff-only` | `true` | Players without `betterbanhammer.use` cannot pick it up and lose it on join |
| `hammer.enchantments` | none | Enchantments by key, for example `sharpness: 5` |
| `defaults.duration` | `permanent` | Duration when none is given |
| `defaults.reason` | `Banned with the ban hammer` | Reason when none is given |
| `ban.essentials.enabled` | `true` | Use EssentialsX when installed |
| `ban.essentials.run-as` | `player` | `player` or `console` |
| `ban.broadcast` | `true` | Announce bans to everybody |
| `ban.cancel-damage` | `true` | A banning hit does no damage |
| `ban.require-sneak` | `false` | Only ban while sneaking |
| `ban.ip` | `false` | Also ban the IP address |
| `ban.consume` | `false` | Remove the hammer after a ban |
| `ban.cooldown` | `0` | Seconds between two bans, `0` is off |
| `ban.protect-operators` | `false` | Operators cannot be banned |
| `ban.disabled-worlds` | empty | Worlds where the hammer does nothing |
| `ban.targeted.enabled` | `false` | Allow hammers for one player |
| `ban.targeted.consume` | `true` | Remove a targeted hammer after its ban |
| `ban.log` | `true` | Write bans to `bans.log` |
| `suggestions.durations` | list | Tab completion for durations |
| `suggestions.reasons` | list | Tab completion for reasons |
| `effects.*` | see [Effects](effects.md) | Lightning, fireworks, particles, sounds |

## Ban log

With `ban.log: true` every ban is appended to `plugins/BetterBanHammer/bans.log`:

```
2026-10-25T01:21:04+02:00 | Holder | Victim2 | <uuid> | 1d | Griefing
```

## Broken values

An unknown material, enchantment, color or particle is reported once in the console and the default is used. The plugin never fails to start because of a typo in the config.
