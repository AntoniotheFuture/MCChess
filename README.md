## Minecraft spigot MCChess我的世界怪物自走棋

在[我的世界Minecraft](https://www.minecraft.net/) 里生成棋盘，摆放怪物作为棋子，让他们为你战斗吧！

## 需求

- [Spigot](https://www.spigotmc.org/) 1.16.1+

## 注意！
这是一个开发中的插件，可能有很多BUG，不代表最终品质

## 游玩流程
1. 管理员在游戏中选择一个地方作为棋盘。
2. 管理员将这块棋盘创建为一局自走棋游戏。
3. 管理员将要参加游戏的双方玩家（每队一人）添加到游戏中。
4. 游戏开始时，玩家会被传送到棋盘中。
5. 玩家在他们的物品栏中挑选若干个刷怪蛋作为棋子，在限定的时间内将这些棋子摆放到自己在棋盘的地盘中。
6. 战斗开始后，双方的怪物会互相攻击
7. 一方的怪物被消灭干净或时间结束，结算双方分数

## 规则
- 游戏开始时，玩家会被设置为无敌状态，且场内的怪物不会设置他们为攻击目标。
- 一方的怪物会误伤队友。
- 爆炸的怪物会对地形造成破坏（棋盘底部为黑曜石）
- 怪物之间的组合会产生特定增益效果（开发中）
- 玩家离开棋盘的话会直接退出游戏并判负
- 游戏开始前，玩家的物品会被保存，退出时返还
- 待补充


### 怪物分类（1.16）
| 生物       | 等级 | 类型         | 攻击力(默认) | 生命值(默认) | 特点         | 刷怪蛋名                  |
|------------|------|--------------|--------|--------|--------------|---------------------------|
| 蜜蜂       |   1  | 自爆         |    2   |   10   |     飞行     | BEE_SPAWN_EGG             |
| 蜘蛛       |   1  | 蜘蛛         |    2   |   16   |     爬墙     | SPIDER_SPAWN_EGG          |
| 岩浆怪     |   1  | 火神         |    6   |   16   |     缓慢     | MAGMA_CUBE_SPAWN_EGG      |
| 僵尸       |   1  | 亡灵         |    3   |   20   |      　      | ZOMBIE_SPAWN_EGG          |
| 狼         |   1  | 狩猎         |    4   |    8   |      　      | WOLF_SPAWN_EGG            |
| 洞穴蜘蛛   |   2  | 蜘蛛、毒物   |    2   |   12   |  爬墙、中毒  | CAVE_SPIDER_SPAWN_EGG     |
| 僵尸猪人   |   2  | 亡灵         |    9   |   20   |   高攻击力   | ZOMBIE_PIGMAN_SPAWN_EGG   |
| 掠夺者     |   2  | 魔法         |    3   |   24   |     射手     | VINDICATOR_SPAWN_EGG      |
| 骷髅       |   2  | 亡灵         |    2   |   20   |     射手     | SKELETON_SPAWN_EGG        |
| 女巫       |   2  | 魔法、毒物   |    6   |   26   |     中毒     | WITCH_SPAWN_EGG           |
| 末影人     |   3  | 暗域、狩猎   |    7   |   40   |     瞬移     | ENDERMAN_SPAWN_EGG        |
| 烈焰人     |   3  | 火神         |    6   |   20   |  飞行、火焰  | BLAZE_SPAWN_EGG           |
| 苦力怕     |   3  | 破坏者、自爆 |   49   |   20   |     自爆     | CREEPER_SPAWN_EGG         |
| 卫道士     |   3  | 魔法         |   13   |   24   |   高攻击力   | VINDICATOR_SPAWN_EGG      |
| 凋零骷髅   |   3  | 亡灵、毒物   |    8   |   20   |     迅捷     | WITHER_SKELETON_SPAWN_EGG |
| 唤魔者     |   4  | 魔法         |    6   |   24   |     法师     | EVOKER_SPAWN_EGG          |
| 僵尸疣猪兽 |   5  | 巨兽、亡灵   |    5   |   40   |    高抗性    |                           |
| 掠夺兽     |   6  | 巨兽、破坏者 |   12   |   100  | 高抗、高攻击 |                           |

### 怪物羁绊（正在做）
| 组合   | 等级 | 需要           | 效果                                                        |
|--------|------|----------------|-------------------------------------------------------------|
|  尸潮  |   1  |      5僵尸     | 只要场上还有僵尸，每8秒生成一个新的僵尸                     |
|  尸潮  |   2  |      8僵尸     | 只要场上还有僵尸，每4秒生成一个新的僵尸                     |
|  火神  |   1  |  两种火神生物  | 这些生物的周围会生成火焰，打击它们的生物会着火              |
|  亡灵  |   1  |  两种亡灵生物  | 亡灵们有20%的几率重生                                       |
|  亡灵  |   2  |  四种亡灵生物  | 亡灵们有40%的几率重生                                       |
|  蜘蛛  |   1  |  两种蜘蛛生物  | 蜘蛛们的攻击会造成减速效果                                  |
|  魔法  |   1  |  三种魔法生物  | 每次攻击有25%几率召唤一次闪电                               |
|  毒物  |   1  |  两种毒物生物  | 中毒效果时间+50%                                            |
|  巨兽  |   1  |                | 一方不能同时存在两个以上巨兽生物                            |
| 破坏者 |   1  | 两种破坏者生物 | 只要还有破坏者存在，场上**所有**生物每5秒受到2伤害              |
|  自爆  |   1  | 自爆生物数量>3 | 自爆生物造成的伤害有50%概率导致目标立即死亡，攻击者也会死亡 |
|  暗域  |   1  |                | 暗域生物受到的伤害有30%的概率失效                           |
|  狩猎  |   1  |  两种狩猎生物  | 狩猎生物会对落单（周围3格无队友）的生物造成200%伤害         |


## 管理员指令
1. 在脚下建立战场,大小:40 * 30 * 5，最底层为黑曜石，一层草地，墙壁和天花板是玻璃
```
/setbtf y
```
2. 添加玩家到战场
```
/addplayer playername
```
3. 开始一局游戏
```
/startgame
```

4. 测试模式
```
/testgame
```

5. 清空战场（包括里面的玩家）
```
/clearbtf
```

6. 删除战场
```
/delbtf y
```
7. 将战场保存到文件中
```
/savebt filename
```
8. 显示战场信息
```
/btinfo
```

## 玩家指令

1. 随机匹配一局对局
```
/mccmatch
```


## 已知的问题
- 战场生成位置有偏差
- 玩家在对局中退出游戏会导致其永远无敌，物品栏丢失。
- 无法使用预设的战场


## 发展方向
- 采用WorldEdit 进行棋盘的保存和生成
- 完善怪物之间的羁绊


## 编译
- 使用IDEA进行编译
- [已编译版本](https://github.com/AntoniotheFuture/MCChess/releases)

## 说明
欢迎大家提交建议和BUG

## LICENSE
本项目基于 [Apache](http://www.apache.org/licenses/LICENSE-2.0.html) 协议