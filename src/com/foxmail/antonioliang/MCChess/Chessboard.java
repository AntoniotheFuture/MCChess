/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//棋盘类记录一个棋盘
package com.foxmail.antonioliang.MCChess;
import java.util.Collection;
import static org.bukkit.Bukkit.getLogger;
import org.bukkit.Location;
import org.bukkit.Material;
import static org.bukkit.Material.AIR;
import static org.bukkit.Material.GRASS_BLOCK;
import static org.bukkit.Material.IRON_BLOCK;
import static org.bukkit.Material.OBSIDIAN;
import static org.bukkit.Material.WHITE_STAINED_GLASS_PANE;
import org.bukkit.entity.Entity;
import static org.bukkit.entity.EntityType.PLAYER;
/**
 *
 * @author 54705
 */
public class Chessboard {
    public Location CBLoc; //中心位置
    public int Longaxis = 0; //长轴位置，0：x轴，1：z轴
    public int Width; //宽度，x
    public int Long; //长度，z
    public int Height; //高度,y
    public Material Floor = OBSIDIAN;//地板材质
    public Material Wall = WHITE_STAINED_GLASS_PANE;//地板框架材质
    public Material Sky = IRON_BLOCK;//天花板材质
    public int SpawnLA = 5;//出生点在长轴上的位置
    public int SpawnSA = 0;//出生点在短轴上的位置，设0则在正中
    public int SpawnH = 3;//出生点的相对高度，默认2
    
    
    public Chessboard(Location l,int Width,int Long,int Height,int LA){
        this.CBLoc = l;
        this.Width = Width;
        this.Long = Long;
        this.Height = Height;
        this.Longaxis = LA;
        //this.Build();
    }
    
    public int getWidth(){
        return this.Width;
    }
    public int getHeight(){
        return this.Height;
    }
    public int getLong(){
        return this.Long;
    }
    public int getLA(){
        return this.Longaxis;
    }
    public void SetFloor(Material M){
        this.Floor = M;
    }
    public void SetWall(Material M){
        this.Wall = M;
    }
    public void SetSKY(Material M){
        this.Sky = M;
    }
    public void SetSpawnLA(int i){
        this.SpawnLA = i;
    }
    public void SetSpawnSA(int i){
        this.SpawnSA = i;
    }
    public void SetSpawnH(int i){
        this.SpawnH = i;
    }
    //获取A队出生地点
    public Location GetSpawnA(){
        Location Spawn = this.CBLoc.clone();
        if(Longaxis == 0){
            Spawn.setX(this.CBLoc.getBlockX() - this.SpawnLA);
            Spawn.setZ(this.CBLoc.getBlockZ() - this.SpawnSA);
        }else{
            Spawn.setZ(this.CBLoc.getBlockZ() - this.SpawnLA);
            Spawn.setX(this.CBLoc.getBlockX() - this.SpawnSA);
        }
        Spawn.setY(this.CBLoc.getBlockY()+this.SpawnH);
        return Spawn;
    }
     //获取B队出生地点
    public Location GetSpawnB(){
        Location Spawn = this.CBLoc.clone();
        if(this.Longaxis == 0){
            Spawn.setX(this.CBLoc.getBlockX() + this.SpawnLA);
            Spawn.setZ(this.CBLoc.getBlockZ() + this.SpawnSA);
        }else{
            Spawn.setZ(this.CBLoc.getBlockZ() + this.SpawnLA);
            Spawn.setX(this.CBLoc.getBlockX() + this.SpawnSA);
        }
        Spawn.setY(CBLoc.getBlockY()+SpawnH);
        return Spawn;
    }
    //获取最小x值
    public int GetMinx(){
        if(this.Longaxis == 0){
            return (this.CBLoc.getBlockX() - (this.Long/2));
        }
        else{
            return (this.CBLoc.getBlockX() - (this.Width/2));
        }
    }
    //获取最大x值
    public int GetMaxx(){
        if(this.Longaxis == 0){
            return (this.CBLoc.getBlockX() + (this.Long/2));
        }
        else{
            return (this.CBLoc.getBlockX() + (this.Width/2));
        }
    }
    //获取最小z值
    public int GetMinz(){
        if(this.Longaxis == 0){
            return (this.CBLoc.getBlockZ() - (this.Width/2));
        }
        else{
            return (this.CBLoc.getBlockZ() - (this.Long/2));
        }
    }
    //获取最大z值
    public int GetMaxz(){
        if(this.Longaxis == 0){
            return (this.CBLoc.getBlockZ() + (this.Width/2));
        }
        else{
            return (this.CBLoc.getBlockZ() + (this.Long/2));
        }
    }
    //地板
    public int GetMiny(){
        return this.CBLoc.getBlockY();
    }
    
    //最高
    public int GetMaxy(){
        return this.CBLoc.getBlockY() + this.Height;
    }
    
    //构建战场框架
    public void Build(){
        Rebuild();
        getLogger().info("清空内容:" +ClearContent());
        getLogger().info("建立地板:" + BuildFloor());
        getLogger().info("建立墙壁:" + BuildWall());
        getLogger().info("建立天花:" + BuildSky());
        
    }
    
    //构建地板
    public int BuildFloor(){
        int MinX = this.GetMinx();
        int MinZ = this.GetMinz();
        int MaxX = this.GetMaxx();
        int MaxZ = this.GetMaxz();
        getLogger().info(MinX + "," + MaxX + ";" + MinZ + "," + MaxZ);
        int c = 0;
        Location TB = this.CBLoc.clone();
        for(int x = MinX;x <= MaxX;x++){
            for(int z = MinZ;z <= MaxZ;z++){
                TB.setX(x);
                TB.setZ(z);
                TB.getBlock().setType(this.Floor);
                c ++;
            }
        }
        return c;
    }
    //构建墙壁
    public int BuildWall(){
        int MinY = this.GetMiny();
        int MinX = this.GetMinx();
        int MinZ = this.GetMinz();
        int MaxX = this.GetMaxx();
        int MaxZ = this.GetMaxz();
        int MaxY = MinY + this.Height;
        Location TB = this.CBLoc.clone();
        int c = 0;
        for(int x = MinX;x <= MaxX;x++){
            for(int y = MinY;y <= MaxY;y++){
                TB.setX(x);
                TB.setY(y);
                TB.setZ(MinZ);
                TB.getBlock().setType(this.Wall);
                c ++;
                TB.setZ(MaxZ);
                TB.getBlock().setType(this.Wall);
                c ++;
            }
        }
        for(int z = MinZ;z <= MaxZ;z++){
            for(int y = MinY;y <= MaxY;y++){
                TB.setZ(z);
                TB.setY(y);
                TB.setX(MinX);
                TB.getBlock().setType(this.Wall);
                c ++;
                TB.setX(MaxX);
                TB.getBlock().setType(this.Wall);
                c ++;
            }
        }
        return c;
    }
    
    //构建天花板
    public int BuildSky(){
        int MaxY = (int) (this.GetMiny() + this.Height);
        int MinX = this.GetMinx();
        int MinZ = this.GetMinz();
        int MaxX = this.GetMaxx();
        int MaxZ = this.GetMaxz();
        Location TB = this.CBLoc.clone();
        TB.setY(MaxY);
        int c = 0;
        for(int x = MinX;x <= MaxX;x++){
            for(int z = MinZ;z <= MaxZ;z++){
                TB.setX(x);
                TB.setZ(z);
                TB.getBlock().setType(this.Sky);
                c ++;
            }
        }
        return c;
    }
    
    //清空方块
    public int ClearContent(){
        int MinY = this.GetMiny() + 1;
        int MinX = this.GetMinx() + 1;
        int MinZ = this.GetMinz() + 1;
        int MaxX = this.GetMaxx() - 1;
        int MaxZ = this.GetMaxz() - 1;
        int MaxY = MinY + this.Height - 1;
        Location TB = this.CBLoc.clone();
        int c = 0;
        for(int x = MinX;x <= MaxX;x++){
            for(int z = MinZ;z <= MaxZ;z++){
                for(int y = MinY;y < MaxY;y++){
                    TB.setX(x);
                    TB.setY(y);
                    TB.setZ(z);
                    TB.getBlock().setType(AIR);
                    c ++;
                }
            }
        }
        return c;
    }
    //清空里面的怪物
    public void ClearMob(){
        Location CT = this.CBLoc.clone();
        CT.setY(CT.getBlockY() + this.Height / 2);
        Collection<Entity> Ens;
        if(this.Longaxis == 0){
            Ens = CT.getWorld().getNearbyEntities(CT, this.Long /2 + 2, this.Height/2 + 2, this.Width/2 + 2);
        }
        else{
            Ens = CT.getWorld().getNearbyEntities(CT, this.Width /2 + 2, this.Height/2 + 2, this.Long/2 + 2);
        }
        for(Entity e :Ens){
            if(!e.getType().equals(PLAYER)){
                e.remove();
            }
        }
    }
    //删除战场
    public void delete(){
        int MinY = this.GetMiny();
        int MinX = this.GetMinx();
        int MinZ = this.GetMinz();
        int MaxX = this.GetMaxx();
        int MaxZ = this.GetMaxz();
        Location TB = this.CBLoc.clone();
        for(int x = MinX;x <= MaxX;x++){
            for(int z = MinZ;z <= MaxZ;z++){
                for(int y = MinY;y <= MinY + this.Height;y++){
                    TB.setX(x);
                    TB.setY(y);
                    TB.setZ(z);
                    TB.getBlock().setType(AIR);
                }
            }
        }
    }
    //重建战场
    public void Rebuild(){
        ClearMob();
        getLogger().info("清空方块:" + ClearContent());
        int Y = this.GetMiny() + 1;
        int MinX = this.GetMinx() + 1;
        int MinZ = this.GetMinz() + 1;
        int MaxX = this.GetMaxx() - 1;
        int MaxZ = this.GetMaxz() - 1;
        Location TB = this.CBLoc.clone();
        TB.setY(Y);
        for(int x = MinX;x <= MaxX;x++){
            for(int z = MinZ;z <= MaxZ;z++){
                TB.setX(x);
                TB.setZ(z);
                TB.getBlock().setType(GRASS_BLOCK);
            }
        }
        
        
    }
    
    //读取配置并加载
    public void LoadConfig(){
        
    }

}
