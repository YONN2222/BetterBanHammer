<p align="center">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="../assets/icons/languages-dark.svg">
    <img src="../assets/icons/languages-light.svg" alt="" width="96">
  </picture>
</p>

<h1 align="center">Languages</h1>

<p align="center">Ten languages included, every text editable.</p>

<p align="center"><a href="README.md">All docs</a></p>

The default Minecraft font cannot display Hindi and Arabic properly, use a resource pack with a suitable font if you need them.

## Choosing a language

Set `language` in `config.yml` and run `/banhammer reload`.

| Code | Language |
|---|---|
| `en` | English |
| `zh` | Chinese |
| `es` | Spanish |
| `hi` | Hindi |
| `fr` | French |
| `ar` | Arabic |
| `pt` | Portuguese |
| `ru` | Russian |
| `de` | German |
| `ja` | Japanese |

## Your own texts

The files are copied to `plugins/BetterBanHammer/lang/` on the first start. Edit them or copy one, rename it, for example `nl.yml`, and set `language: nl`. Missing keys fall back to the bundled English text. Texts use [MiniMessage](https://docs.advntr.dev/minimessage/format.html).

## Placeholders

| Placeholder | Used in |
|---|---|
| `<prefix>` | All messages |
| `<player>` | Messages about a player |
| `<staff>` | Broadcast and kick messages |
| `<duration>` | Duration of a ban or hammer |
| `<reason>` | Reason of a ban or hammer |
| `<target>` | Hammers for one player |
| `<seconds>` | Cooldown message |
| `<label>`, `<input>` | Usage and invalid duration |
