/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foxmail.antonioliang.MCChess;

import java.util.List;
import org.bukkit.entity.Mob;

/**
 *
 * @author 54705
 */

//羁绊列表
public enum Bondage {
    ZombiesLV1("尸潮I"),
    ZombiesLV2("尸潮II"),
    Vulcan("火神"),
    UndeadLV1("亡灵I"),
    UndeadLV2("亡灵II"),
    Spiders("蜘蛛"),
    Wise("智者"),
    Poisoner("毒物"),
    Behemoth("巨兽"),
    Saboteur("破坏者"),
    SelfExplosive("自爆"),
    Darker("暗域"),
    Hunter("狩猎者");
    private String name;
    
    private Bondage(String name) { 
        this.name = name;
    }
    // get set 方法  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }
    
    public List<Bondage> CheckBondages(List<Mob> Mobs){
        List<Bondage> R = null;
        int ZombieCount = 0;
        for(Mob m : Mobs){
           if(m.getName() == "Zombie"){
               ZombieCount ++;
           }
            
        }
        if(ZombieCount >4&&ZombieCount<8){
            R.add(ZombiesLV1);
        }else if(ZombieCount >7){
            R.add(ZombiesLV2);
        }
        
        return R;
        
    }
    
    
}
