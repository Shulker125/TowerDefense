# TowerDefense

Bathroom Battle is a tower defense game, inspired partially by the Bloons TD series. It includes virus attackers and various disinfectant defenders.  

#### Background
The menu background shows a bathroom.

![Image](https://github.com/Shulker125/TowerDefense/blob/master/Tower_Defense/src/imgs/homescreen.png?raw=true)

The game background shows the track that the viruses follow.

![Image](https://github.com/Shulker125/TowerDefense/blob/master/Tower_Defense/src/imgs/Background.png?raw=true)

#### Frame


#### Modes
There are three modes: easy, medium, and hard.

The harder the mode, the more expensive the defenders 

#### Virus
There are six levels of viruses, indicated by their color. 

![Image](https://github.com/Shulker125/TowerDefense/blob/master/Tower_Defense/src/imgs/virus1.png?raw=true)
![Image](https://github.com/Shulker125/TowerDefense/blob/master/Tower_Defense/src/imgs/virus2.png?raw=true)
![Image](https://github.com/Shulker125/TowerDefense/blob/master/Tower_Defense/src/imgs/virus3.png?raw=true)
![Image](https://github.com/Shulker125/TowerDefense/blob/master/Tower_Defense/src/imgs/virus4.png?raw=true)
![Image](https://github.com/Shulker125/TowerDefense/blob/master/Tower_Defense/src/imgs/virus5.png?raw=true)
![Image](https://github.com/Shulker125/TowerDefense/blob/master/Tower_Defense/src/imgs/virus6.png?raw=true)

Red is the weakest and slowest, while gray is the fastest and strongest.

If a defensive projectile hits a virus of another color, the virus will lose a level, which means that it will change into the next weakest virus. For example, if a green virus is hit by a projectile, it will turn into a blue virus, and if the blue virus is hit, it will turn into a red virus. When a red virus is hit, the virus will disappear and the player will earn money. 

If a virus reaches the end of the track without being defeated, the player's hp will decrease. Stronger viruses do more damage.

For each level, the number of viruses spawned is double the level number. Earlier levels consist of weaker viruses, and as the player progresses, stronger viruses will begin to spawn in greater quantities. 

#### Defenders
The four defenders, from cheapest to most expensive, are soap, sanitizer, bleach, and flamethrower. 

More expensive defenders have faster rates of fire. Bleach and flamethrower have the same rate of fire but are faster than soap and sanitizer. Flamethrower is the only defender that does three "levels" of damage, which means that it can take out three red viruses, or one blue virus and one red virus, with one projectile. The other defenders only deal one "level" of damage.

Defenders can be upgraded for a certain amount of money that is greater than the initial cost in the shop. Upgrading doubles the damage that projectiles deal and increases the fire rate.

Defenders can be sold for 3/4 of the original price. If a defender is upgraded and then sold, the cost of upgrading is not returned to the player.



#### Contributors
- [Zayne](https://github.com/Shulker125)
- [Jonathan](https://github.com/nwhee)
