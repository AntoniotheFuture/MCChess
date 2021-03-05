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
package com.foxmail.antonioliang.MCChess;

import java.util.List;
import org.bukkit.entity.Mob;

/**
 *
 * @author AntonioLiang
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
