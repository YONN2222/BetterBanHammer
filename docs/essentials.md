<p align="center">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="../assets/icons/methods-dark.svg">
    <img src="../assets/icons/methods-light.svg" alt="" width="96">
  </picture>
</p>

<h1 align="center">EssentialsX</h1>

<p align="center">How bans are executed.</p>

<p align="center"><a href="README.md">All docs</a></p>

## How it works

BetterBanHammer checks on every ban whether EssentialsX is installed and enabled. If so it runs the EssentialsX commands, otherwise it uses the server ban list. You do not have to configure anything.

| Case | Command |
|---|---|
| Permanent | `essentials:ban <player> <reason>` |
| Temporary | `essentials:tempban <player> <duration> <reason>` |

By default the player who hit runs the command, so EssentialsX checks that player's permissions, for example `essentials.ban` and `essentials.tempban`. With `ban.essentials.run-as: console` the console runs it. Hammers for one player always run as the console.

If EssentialsX does not accept a ban, the plugin falls back to the server ban list and logs a warning.

## Switching it off

`ban.essentials.enabled: false` always uses the server ban list.

## Without EssentialsX

Temporary bans use the profile ban list with an expiry, permanent bans have none. `ban.ip: true` also bans the IP address of the player.

The console shows which backend is used on start: `Using essentials bans` or `Using native bans`.

## Undoing a ban

Use `/unban <name>` or `/pardon <name>`, or the unban command of your ban plugin.
