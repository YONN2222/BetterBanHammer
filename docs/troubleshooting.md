<p align="center">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="../assets/icons/help-dark.svg">
    <img src="../assets/icons/help-light.svg" alt="" width="96">
  </picture>
</p>

<h1 align="center">Troubleshooting</h1>

<p align="center">Common questions and fixes.</p>

<p align="center"><a href="README.md">All docs</a></p>

## Hitting does not ban

- The holder needs `betterbanhammer.use`, see [Commands](commands.md#permissions).
- The hammer must be in the main hand and created with `/banhammer`. A normal mace does nothing.
- `ban.require-sneak` needs sneaking, `ban.disabled-worlds` can switch the world off, `ban.cooldown` can still be running.
- The target can be protected by `betterbanhammer.exempt`, `essentials.ban.exempt` or `ban.protect-operators`.

## I am in creative mode and nothing happens

On Paper and its forks creative hits are detected through the attack event. On Spigot creative players cannot be hit by the normal damage event, so test with a survival target or use Paper.

## A player lost the hammer

With `hammer.staff-only` players without `betterbanhammer.use` cannot keep a hammer. Give them the permission or switch the option off.

## The wrong ban plugin is used

Check the console on start for `Using essentials bans` or `Using native bans`. See [EssentialsX](essentials.md).

## "Targeted ban hammers are switched off"

Set `ban.targeted.enabled: true` and run `/banhammer reload`.

## Particles or sounds do nothing

Check the console for a warning about an unknown key. Particles that need data like dust are not supported.

## Special characters show as ?

The server console may not use UTF-8. Start Java with `-Dfile.encoding=UTF-8`. In game the characters are shown correctly.
