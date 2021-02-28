/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foxmail.antonioliang.MCChess;


import static com.foxmail.antonioliang.MCChess.MCChess.Games;
import static com.foxmail.antonioliang.MCChess.MCChess.Players;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import static org.bukkit.entity.EntityType.PLAYER;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;
import org.bukkit.event.entity.CreatureSpawnEvent;
import static org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.NATURAL;
import static org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.SPAWNER_EGG;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author 54705
 */
//生物生成事件：设置生物AI
//左键点击事件：放置时怪物删除/战斗中无效化
//生物死亡事件：转移另一队目标
//玩家放置事件：

public class MCChessListener implements Listener{
        /*
        监听生物放置生成事件
    
    */
    
    //判断玩家在哪个游戏
    public ChessGame GetChessGameByPlayer(Player p){
        for(ChessGame CG : Games){
            if(CG.PlayerA.equals(p)) return CG;
            if(CG.PlayerB.equals(p)) return CG;
        }
        return null;
    }
    
    //判断怪物在哪个游戏
    public ChessGame GetChessGameByMob(Entity e){
        for(ChessGame CG : Games){
            //Chessboard CB = CG.CB;
            for(Mob m: CG.TAMob){
                if(e.equals(m)){
                    return CG;
                }
            }
            for(Mob m: CG.TBMob){
                if(e.equals(m)){
                    return CG;
                }
            }
        }
        return null;
    }
    
    //玩家放置事件,判断能不能放置
    @EventHandler
    public void onPlace(PlayerInteractEvent e) {
        if(Games == null) return;
        if(Games.isEmpty()) return;
        if(Players.isEmpty()) return;
        ChessGame CG = GetChessGameByPlayer(e.getPlayer());
        if(CG == null) return;
        if(e.getAction() != RIGHT_CLICK_BLOCK) return;
        if(CG.Status != 1){
            e.getPlayer().sendMessage("你现在不能放置棋子");
            e.setCancelled(true);
        }
        int LA = CG.CB.getLA();
        int MaxLA = 0;
        int MinLA = 0;
        if(e.getPlayer().equals(CG.PlayerA)){
            if(LA == 0){
                MaxLA = CG.CB.CBLoc.getBlockX();
                MinLA = CG.CB.GetMinx();
            }else{
                MaxLA = CG.CB.CBLoc.getBlockZ();
                MinLA = CG.CB.GetMinz();
            }
        }
        if(e.getPlayer().equals(CG.PlayerB)){
            if(LA == 0){
                MaxLA = CG.CB.GetMaxx(); 
                MinLA = CG.CB.CBLoc.getBlockX();
            }else{
                MaxLA = CG.CB.GetMaxz(); 
                MinLA = CG.CB.CBLoc.getBlockZ();
            }
        }
        int L = 0;
        if(LA ==0){
            L = e.getClickedBlock().getLocation().getBlockX();
        }else{
            L = e.getClickedBlock().getLocation().getBlockZ();
        }
        if(CG.IsTest) return;
        if(L > MaxLA||L<MinLA){
            e.getPlayer().sendMessage("你不能在那里放置");
            e.setCancelled(true);
        }
        
     }
    
    //左键点击事件：放置时怪物删除/战斗中无效化
    @EventHandler
    public void onDel(PlayerInteractEntityEvent e) {
        if(Games == null) return;
        if(Games.isEmpty()) return;
        if(Players.isEmpty()) return;
        ChessGame CG = GetChessGameByPlayer(e.getPlayer());
        Player p = e.getPlayer();
        if(CG == null) return;
        e.setCancelled(true);
        if(CG.Status == 1){
            Entity ent = e.getRightClicked();
            
            if(CG.PlayerA.equals(p)){
                 for(Mob m :CG.TAMob){
                     if(m.equals(ent)){
                         p.getInventory().addItem(new ItemStack(GetSpawnEgg(m),1));
                         CG.TAMob.remove((Mob) ent);
                         ent.remove();
                     }
                 }
            }
        }
    }
    
    
    //屏蔽玩家放置、摧毁方块事件
    @EventHandler
    public void onClickBlock(PlayerInteractEvent e) {
        if(Games == null) return;
        if(Games.isEmpty())return;
        if(Players.isEmpty()) return;
    }
        /*
        监听生物死亡事件,如果一方的某个生物失去，则另一方的全部生物都重置目标
    
    */
    
    //生物死亡事件：转移另一队目标
    @EventHandler
    public void onMobDeath(EntityDeathEvent e) {
        
        if(Games == null) return;
        if(Games.isEmpty()) return;
        if(Players.isEmpty()) return;
        Entity ent = e.getEntity();
        ChessGame CG = GetChessGameByMob(ent);
        if(CG == null) return;
        if(CG.Status != 2) return;
        if(CG.TAMob.contains((Mob) ent)){
            CG.TAMob.remove((Mob) ent);
            //SB.getTeam("A队").getScoreboard().getObjective("剩余数")
            if(CG.TAMob.isEmpty()){
                CG.Status = 3;
                return;
            }
            for(Mob m : CG.TBMob){
                m.setTarget(CG.TAMob.get(0));
            }
            return;
        }
        if(CG.TBMob.contains((Mob) ent)){
            CG.TBMob.remove((Mob) ent);
            if(CG.TBMob.isEmpty()){
                CG.Status = 3;
                return;
            }
            for(Mob m : CG.TAMob){
                m.setTarget(CG.TBMob.get(0));
            }
            return;
        }
        
    }
    
    /*
        监听生物生成事件,设置为无AI
    */
    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent e) {
        if(Games == null) return;
        if(Games.isEmpty()) return;
        if(Players.isEmpty()) return;
        //判断生物生成位置
        ChessGame CG1 = null;
        Location loc = e.getLocation();
        for(ChessGame CG:Games){
            if(loc.getBlockX() <= CG.CB.GetMaxx() && loc.getBlockX() >= CG.CB.GetMinx() &&loc.getBlockY() <= CG.CB.GetMaxy() && loc.getBlockY() >= CG.CB.GetMiny() && loc.getBlockZ() <= CG.CB.GetMaxz() && loc.getBlockZ() >= CG.CB.GetMinz()){
                CG1 = CG;
                break;
            }
        }
        if(CG1 == null) return;
        //如果是自然生成，直接删除
        if(e.getSpawnReason().equals(NATURAL)){
            e.setCancelled(true);
            return;
        }
        if(e.getSpawnReason().equals(SPAWNER_EGG)){
            LivingEntity le = e.getEntity();
            le.setAI(false);
            le.setCustomNameVisible(true);
        }
    }
    /*
        生物受伤事件
    */
    @EventHandler
    public void onMobDamage(EntityDamageByEntityEvent e) {
        if(Games == null) return;
        if(Games.isEmpty()) return;
        if(Players.isEmpty()) return;
        Entity ent = e.getEntity();
        ChessGame CG = GetChessGameByMob(ent);
        if(CG == null) return;
        if(CG.Status != 2) return;
        Mob le = (Mob) ent;
        if(e.getDamager().getType().equals(PLAYER)){
            e.setCancelled(true);
            return;
        }
        if(CG.TAMob.contains(le)){
            if(CG.TAMob.contains(e.getDamager())){
                e.setCancelled(true);
                return;
            }
            le.setCustomName("A队" + le.getHealth());
        }
        if(CG.TBMob.contains(le)){
            if(CG.TBMob.contains(e.getDamager())){
                e.setCancelled(true);
                return;
            }
            le.setCustomName("B队" + le.getHealth());
        }
        le.setCustomNameVisible(true);
        //如果伤害来自本队
        
    }
    
    //生物转换为刷怪蛋
    public Material GetSpawnEgg(Entity e){
        String EggName = e.getType().toString() + "_SPAWN_EGG";
        if(EggName.equals("PIG_ZOMBIE_SPAWN_EGG")) EggName = "ZOMBIE_PIGMAN_SPAWN_EGG";
        Material m = Material.getMaterial(EggName);
        return m;
    }
    
}
