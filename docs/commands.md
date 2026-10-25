<p align="center">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="../assets/icons/commands-dark.svg">
    <img src="../assets/icons/commands-light.svg" alt="" width="96">
  </picture>
</p>

<h1 align="center">Commands and permissions</h1>

<p align="center">Give hammers, aim them and control who can use them.</p>

<p align="center"><a href="README.md">All docs</a></p>

## Commands

| Command | What it does |
|---|---|
| `/banhammer [duration] [reason]` | Give yourself a ban hammer. Without arguments the defaults from the config are used. |
| `/banhammer give <player> [duration] [reason]` | Give a hammer to another player. |
| `/banhammer target <player> [duration] [reason]` | Give yourself a hammer that only works on one player. Needs `ban.targeted.enabled`. |
| `/banhammer target <player> <holder> [duration] [reason]` | The same, but the hammer goes to the holder. |
| `/banhammer reload` | Reload the configuration and the languages. |

Alias: `/bbh`. Tab completion suggests durations and reasons from the config.

## Duration

| Input | Meaning |
|---|---|
| `30s`, `5m`, `2h` | seconds, minutes, hours |
| `7d`, `2w` | days, weeks |
| `1mo`, `1y` | months, years |
| `1d12h` | combinations |
| `permanent`, `perm`, `forever`, `never` | no end |

An invalid duration is rejected, it never becomes a permanent ban.

## Using the hammer

Hold the hammer in your main hand and hit a player. The hammer stores its own duration and reason, so you can carry several hammers for different offences.

If `ban.require-sneak` is on you have to sneak while hitting. If `ban.cancel-damage` is on the hit does no damage.

## Staff only

The ban hammer is an admin tool. A hit only bans when the holder has `betterbanhammer.use`. With `hammer.staff-only` (default on) players without that permission also cannot pick the hammer up and lose it when they join.

## Hammer for one player

With `ban.targeted.enabled: true` an admin can hand a normal player a hammer for exactly one person:

```
/banhammer target Steve 7d Griefing
```

You get a hammer that only works on Steve. Drop it and anybody who picks it up can ban Steve with it, for 7 days with the reason Griefing, without any permission. You can also hand it over directly:

```
/banhammer target Steve Alex 7d Griefing
```

Players cannot keep normal hammers, but targeted hammers can be dropped, picked up and held by everybody. Hitting anybody else does nothing and shows a message. The hammer is removed after the ban unless `ban.targeted.consume` is `false`. Operators, `betterbanhammer.exempt` and the world and cooldown rules still apply. The ban is always executed by the console, so the holder needs no permission for EssentialsX either.

## Permissions

| Permission | Default | Allows |
|---|---|---|
| `betterbanhammer.give` | op | Get and give hammers |
| `betterbanhammer.use` | op | Ban players with a hammer |
| `betterbanhammer.target` | op | Create hammers for one player |
| `betterbanhammer.reload` | op | Reload the configuration |
| `betterbanhammer.exempt` | nobody | Cannot be banned with a hammer |
| `betterbanhammer.*` | op | All of the above |

With EssentialsX installed players with `essentials.ban.exempt` are protected as well.
