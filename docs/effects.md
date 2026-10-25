<p align="center">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="../assets/icons/countdown-dark.svg">
    <img src="../assets/icons/countdown-light.svg" alt="" width="96">
  </picture>
</p>

<h1 align="center">Effects</h1>

<p align="center">What happens where a player gets banned.</p>

<p align="center"><a href="README.md">All docs</a></p>

All effects play at the location of the banned player and can be changed or switched off in the `effects` section of `config.yml`.

## Fireworks

```yaml
effects:
  firework:
    enabled: true
    type: BALL_LARGE
    colors: ["#DFAB40", "#DF6440"]
    fade: ["#FFFFFF"]
    flicker: true
    trail: true
    count: 2
    spread: 0.6
```

`type` is `BALL`, `BALL_LARGE`, `STAR`, `BURST` or `CREEPER`. Colors are hex codes with six digits. `count` is limited to 1 to 10, `spread` is the scatter in blocks. The fireworks explode right away and do not damage anybody.

## Particles

Every entry in `bursts` is one burst of one particle type. Add as many as you like.

```yaml
effects:
  particles:
    enabled: true
    bursts:
      - type: totem_of_undying
        count: 40
        spread: 0.5
        speed: 0.35
```

Use the particle keys from the [Minecraft wiki](https://minecraft.wiki/w/Particles_(Java_Edition)). Particles that need extra data like dust cannot be used. An unknown particle is skipped with one warning in the console.

## Sounds

```yaml
effects:
  sounds:
    enabled: true
    cues:
      - key: entity.generic.explode
        volume: 0.6
        pitch: 1.0
```

Sound keys are listed on the [Minecraft wiki](https://minecraft.wiki/w/Sounds.json).

## Lightning

`effects.lightning: true` strikes a visual lightning bolt without damage or fire.
