/*
 * Copyright 2021 AntonioLiang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//一场棋局
package com.foxmail.antonioliang.MCChess;
import static com.foxmail.antonioliang.MCChess.MCChess.Games;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import static org.bukkit.Material.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import static org.bukkit.entity.EntityType.PLAYER;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Random;
import static org.bukkit.Bukkit.getServer;

/**
 *
 * @author AntonioLiang
 */
public class ChessGame {
    public int No;//对局编号
    public Chessboard CB;//这场棋局的棋盘
    public Player PlayerA = null; //A方
    public Player PlayerB = null; //B方
    public volatile int Status;//0:准备中,1:放棋中,2:战斗中,3：等待玩家
    public ItemStack[] PAIS;//玩家A的暂存物品
    public ItemStack[] PBIS;//玩家A的暂存物品
    public Location PASL; //玩家来之前的地点
    public Location PBSL; //玩家来之前的地点
    public boolean IsTest = false;
    public List<Mob> TAMob; //A队放置的怪物
    public List<Mob> TBMob; //B队放置的怪物
    public List<Bondage> TAB;//A对拥有的特性
    public List<Bondage> TBB;//A对拥有的特性
    
    //public static List<Material> shop; //商店里可选的怪物
    //public Inventory shop;//商店
    public Collection<ItemStack> shop;
    
    
    //新建一场对局
    public ChessGame(Chessboard C,boolean IsTest){
        TAMob = new ArrayList();
        TBMob = new ArrayList();
        TAB = new ArrayList();
        TBB = new ArrayList();
        this.CB = C;
        this.IsTest = IsTest;
        shop = new ArrayList();
        ItemStack IS;
        IS = new ItemStack(SPIDER_SPAWN_EGG,16);
        shop.add(IS);
        IS = new ItemStack(MAGMA_CUBE_SPAWN_EGG,16);
        shop.add(IS);
        IS = new ItemStack(ZOMBIE_SPAWN_EGG,16);
        shop.add(IS);
        IS = new ItemStack(WOLF_SPAWN_EGG,16);
        shop.add(IS);
        IS = new ItemStack(CAVE_SPIDER_SPAWN_EGG,10);
        shop.add(IS);
        IS = new ItemStack(ZOMBIE_PIGMAN_SPAWN_EGG,10);
        shop.add(IS);
        IS = new ItemStack(VINDICATOR_SPAWN_EGG,10);
        shop.add(IS);
        IS = new ItemStack(SKELETON_SPAWN_EGG,10);
        shop.add(IS);
        IS = new ItemStack(WITCH_SPAWN_EGG,10);
        shop.add(IS);
        IS = new ItemStack(ENDERMAN_SPAWN_EGG,6);
        shop.add(IS);
        IS = new ItemStack(BLAZE_SPAWN_EGG,6);
        shop.add(IS);
        IS = new ItemStack(CREEPER_SPAWN_EGG,6);
        shop.add(IS);
        IS = new ItemStack(VINDICATOR_SPAWN_EGG,6);
        shop.add(IS);
        IS = new ItemStack(WITHER_SKELETON_SPAWN_EGG,6);
        shop.add(IS);
        IS = new ItemStack(EVOKER_SPAWN_EGG,2);
        shop.add(IS);
    }
    
    
    //将玩家置于准备状态，0:玩家不存在,1:准备好了
    public int SetPlayers(){
        if(PlayerA != null){
            PASL = PlayerA.getLocation();
            PlayerA.teleport(CB.GetSpawnA());
            PlayerA.setHealth(PlayerA.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            //玩家设置不能捡起东西
            PlayerA.setCanPickupItems(false);
            //玩家除去所有药水效果
            PlayerA.removePotionEffect(PotionEffectType.HEAL);
            PlayerA.removePotionEffect(PotionEffectType.HEALTH_BOOST);
            PlayerA.removePotionEffect(PotionEffectType.POISON);
            //玩家饭量设置为5
            PlayerA.setSaturation(5);
            //保存玩家所有道具到数组
            PAIS = PlayerA.getInventory().getContents().clone();
            PlayerA.getInventory().clear();
            //设置玩家为无敌
            PlayerA.setInvulnerable(true);
            if(!IsTest){
                PBSL = PlayerB.getLocation();
                PlayerB.teleport(CB.GetSpawnB());
                PlayerB.setHealth(PlayerB.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                PlayerB.setCanPickupItems(false);
                PlayerB.removePotionEffect(PotionEffectType.HEAL);
                PlayerB.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                PlayerB.removePotionEffect(PotionEffectType.POISON);
                PlayerB.setSaturation(5);
                PBIS = PlayerB.getInventory().getContents().clone();
                PlayerB.setInvulnerable(true);
                PlayerB.getInventory().clear();
            }

            return 1;
        }else{
            return 0;
        }
    }
    
    //玩家退出
    public void PlayerExit(Player p){
        boolean hit = false;
        if(PlayerA.equals(p)){
            hit = true;
            p.teleport(PASL);
            p.getInventory().setContents(PAIS);
        }
        if(PlayerB.equals(p)){
            hit = true;
            p.teleport(PBSL);
            p.getInventory().setContents(PBIS);
        }
        if(hit){
            p.setCanPickupItems(true);
            //玩家除去所有药水效果
            p.removePotionEffect(PotionEffectType.HEAL);
            p.removePotionEffect(PotionEffectType.HEALTH_BOOST);
            p.removePotionEffect(PotionEffectType.POISON);
            p.setInvulnerable(false);
            p.getInventory().clear();
        }
    }
    
    
    //加入一场对局 ,0:找不到对局,1:加入成功,2:人满了
    public int Join(Player p){
        if(PlayerA== null){
            PlayerA = p;
            return 1;
        }
        if(PlayerB== null){
            PlayerB = p;
            return 1;
        }
        if(PlayerA.equals(p)||PlayerB.equals(p)){
            return 1;
        }
        if(PlayerA != null && PlayerB != null){
            return 2;
        }
        return 0;
    }
    //重设玩家位置
    public void ResetPlayer(){
        if(PlayerA != null) PlayerA.teleport(CB.GetSpawnA());
        if(PlayerB != null) PlayerB.teleport(CB.GetSpawnB());
    }
    
    //开始一场对局 0:没棋盘，1：开始成功，2：玩家没准备好
    public int Start(){
        if(!IsPlayerReady()){
            return 2;
        }
        if(this.CB== null){
            return 0;
        }
        this.Status = 0;
        CB.Rebuild();
        ResetPlayer();
        new BukkitRunnable(){
            int time = 5;
            @Override
            public void run(){
                time--;
                notice("","自走棋准备开始"+ time);
                if(time <= 0){
                    picking();
                    cancel();
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("MCChess"),0L,20L);
        return 1;
    }
    
    /*
        挑选、摆放棋子
    */
    public void picking(){
        this.Status = 1;
        ItemStack TIS;
        //玩家背包里放入物品
        for(ItemStack IS :shop){
            TIS = IS.clone();
            Random rand = new Random();
            TIS.setAmount(rand.nextInt(TIS.getAmount()));
            if(Math.random() < 0.5){
                PlayerA.getInventory().addItem(TIS);
            }else{
                if(!IsTest){
                    PlayerB.getInventory().addItem(TIS);
                }
            }
        }
        new BukkitRunnable(){
            int time = 30;
            @Override
            public void run(){
                time--;
                notice("","挑选并放置棋子"+ time);
                if(time <= 0){
                    Grouping();
                    flight();
                    cancel();
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("MCChess"),0L,20L);
    }
    
    /*
        分队
    */
    public void Grouping(){
        Location CT = CB.CBLoc.clone();
        CT.setY(CT.getBlockY() + CB.Height / 2);
        Collection<Entity> Ens;
        Mob m = null;
        TAMob.clear();
        TBMob.clear();
        int ELA;//怪物在长轴上的位置
        int LACT;//长轴中心点位置
        if(CB.Longaxis == 0){
            Ens = CT.getWorld().getNearbyEntities(CT, CB.Long /2, CB.Height/2, CB.Width /2);
            LACT = CT.getBlockX();
        }else{
            Ens = CT.getWorld().getNearbyEntities(CT, CB.Width /2,CB.Height/2, CB.Long /2);
            LACT = CT.getBlockZ();
        }
        for(Entity e :Ens){
            if(e.getType() != PLAYER){
                try{
                    m = (Mob) e;
                    if(CB.Longaxis == 0){
                        ELA = m.getLocation().getBlockX();
                    }else{
                        ELA = m.getLocation().getBlockY();
                    }
                    if(ELA <= LACT){
                        try{
                            TAMob.add(m);
                        }catch(Exception e1){
                        }
                    }else{
                        try{
                            TBMob.add(m);
                        }catch(Exception e1){
                        }
                    }
                }catch(Exception e1){
                }
                
            }
        }
    }
    //public volatile boolean exit = false; 
        /*
        棋子战斗
    */
    public void flight(){
        this.Status = 2;
        for(Mob m :TAMob){
            m.setAI(true);
            m.setCustomName("A队");
            m.setCustomNameVisible(true);
            if(TBMob.isEmpty()){
                m.setAI(false);
            }else{
                m.setTarget(TBMob.get(0));
            }
        }
        for(Mob m :TBMob){
            m.setAI(true);
            m.setCustomName("B队");
            m.setCustomNameVisible(true);
            if(TAMob.isEmpty()){
                m.setAI(false);
            }else{
                m.setTarget(TAMob.get(0));
            }
        }
        //战斗若干秒
        new BukkitRunnable(){
            int time = 60;
            @Override
            public void run(){
                if(Games.get(0).Status != 2){
                    endflight();
                    cancel();
                }
                time--;
                notice("","战斗中"+time);
                if(time <= 0){
                    endflight();
                    cancel();
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("MCChess"),0L,20L);
    }
    /*
        战斗结算
    */
    public void endflight(){
        this.Status = 3;
        int def = TAMob.size() - TBMob.size();
        String Winp = null;
        //设置怪物AI
        for(Mob m:TAMob){
            m.setAI(false);
        }
        for(Mob m:TBMob){
            m.setAI(false);
        }
        if(def >0 && PlayerA != null){
            PlayerA.damage(def);
            Winp = PlayerA.getName() + "胜利";
        }
        if(def <0 && PlayerB != null){
            PlayerB.damage(-def);
            Winp = PlayerB.getName() + "胜利";
        }
        if(def == 0){
            Winp = "平手！";
        }
        notice(Winp,"对战结束");
        //等待若干秒
        new BukkitRunnable(){
            int time = 10;
            @Override
            public void run(){
                time--;
                if(time <= 0){
                    CB.ClearMob();
                    cancel();
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("MCChess"),0L,20L);
    }
    
    
    //判断玩家是否准备好
    public boolean IsPlayerReady(){
        if(IsTest){
            if(PlayerA== null) return false;
            if(!PlayerA.isOnline()) return false; 
            return true; 
        }
        if(PlayerA== null|| PlayerB== null){
            return false;
        }
        if(PlayerA.isOnline()&& PlayerB.isOnline()){
            return true;
        }else{
            return false;
        }
        
    }
    
    //推送标题消息到所有玩家
    public void notice(String Title,String SubTitle){
        if(PlayerA !=null && PlayerA.isOnline()){
            PlayerA.sendTitle(Title, SubTitle,10,70,20);
        }
        if(PlayerB !=null && PlayerB.isOnline()){
            PlayerB.sendTitle(Title, SubTitle,10,70,20);
        }
    }
    
    
    
    
}
