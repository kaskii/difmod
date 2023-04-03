
# DifMod - Modify difficulty of each player

This mod will allow you to modify difficulty for various events of each player. Using this mod
you can easily make the game easier for some players and harder for others, for example kids could take 50%
of normal damage and prevent death on fire damage or all damage and adults can take 150% of damage to make game harder.



## Features

You can modify following values:
* Player can die to damage
* Player can die to fire damage
* Damage type modifier
  * Can die
  * Can die to fire
  * Fire damage
  * Projectile damage
  * Magic damage
  * Explosion damage
  * Fall damage
  * Any other damage type



## Planned features

Following features are planned to be released on some next releases:
* Can die to hunger
* Hunger drain speed (jump, sprint..)
* Can drown
* Oxygen drain speed



## Supported versions

Forge server, Minecraft 1.19.2.



## Installation

1. Add mod to the server mod folder
2. Create a config file `damage-config.json`. This won't be created by default.


## Configuration

The mod is server side only. You can easily include it in any other mod pack without any client changes. Configuration
file is read on each damage event a player receives so editing file will apply new settings right away without any
restarts needed.

The configuration file is in default configuration path, named as `damage-config.json`.
Note the file is an original JSON file, not TOML or Javascript JSON is allowed.

`PlayerName` is display name of the player (same name as on player list). The damage modifier values are percentages!

* 0.5 = 50% damage
* 1 = 100% damage
* 1.5 = 150% damage
* 0 = 0% damage

```
{
  "PlayerDamages": {
    "PlayerName": {
      "CanDie": true,
      "DieToFire": false,
      "FireDamageModifier": 0.1,
      "ProjectileDamageModifier": 0.1,
      "MagicDamageModifier": 0.1,
      "ExplosionDamageModifier": 0.1,
      "FallDamageModifier": 0.1,
      "OtherDamageModifier": 0.1
    }
  }
}
```
