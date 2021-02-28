/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foxmail.antonioliang.MCChess;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.MonsterEggs;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
/**
 *
 * @author 54705
 */
public class MCChess extends JavaPlugin{
    
    public static MonsterEggs[] L1eggs; //1级怪物蛋
    
    //计分板
    public static Scoreboard SB = null;
    
    //战局、玩家列表
    
    public static List<ChessGame> Games;
    public static List<Player> Players;
    
    
    
    /**
     * @param args the command line arguments
     */
    
    @Override
    public void onEnable() {
        //注册监听事件
        getServer().getPluginManager().registerEvents(new MCChessListener(), this);
        getLogger().info("MC自走棋插件已启用");
        Games = new ArrayList();
        Players = new ArrayList();
    }
    @Override
    public void onDisable() {
    getLogger().info("MC自走棋插件已禁用");
    }
    /*
        设置某地点为战场位置并生成战场
    */
    
    //0:所在的位置不适合，1：成功
    public int setBTF(Location loc){
        loc.setY(loc.getBlockY() - 1);
        if(loc.getBlockY() + 5 > 256) return 0;
        Chessboard CB = new Chessboard(loc,18,20,8,0);
        CB.Build();
        ChessGame CG = new ChessGame(CB,false);
        Games.add(CG);
        return 1;
    }
    
    /*
        传送玩家到指定位置
    0:找不到玩家,1:玩家1就绪,2:玩家2就绪,3,玩家太多,-1:未设置战场
    */
    public int tpplayer(String playername){
        if(Games.isEmpty()) return -1;
        ChessGame CG = Games.get(0);
        //判断玩家是否存在
        Player pl = null;
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if(p.getName().equals(playername)) pl = p;
        }
        if(pl== null) return 0;
        Players.add(pl);
        return CG.Join(pl);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        int r;
        if (cmd.getName().equalsIgnoreCase("setbtf")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("这个指令只能让玩家使用。");
                return false;
            }
            Player player = (Player) sender;
            r = setBTF(player.getLocation());
            if(r == 0){
                sender.sendMessage("您所在的位置不适合");
                return false;
            }else{
                sender.sendMessage("战场创建成功");
                return true;
            }
        } else if (cmd.getName().equalsIgnoreCase("addplayer")) {
            if (args.length > 1) {
                sender.sendMessage("参数太多，正确用法为：/addplayer username");
                return false;
            } 
            if (args.length < 1) {
                sender.sendMessage("参数太多，正确用法为：/addplayer username");
                return false;
            }
            r = tpplayer(args[0]);
            if(r == -1){
                sender.sendMessage("未设置战场");
                return false;
            }
            if(r == 0){
                sender.sendMessage("找不到玩家");
                return false;
            }
            if(r == 1){
                sender.sendMessage("玩家1准备完毕");
                return true;
            }
            if(r == 2){
                sender.sendMessage("玩家2准备完毕");
                return true;
            }
            if(r == 3){
                sender.sendMessage("不能再添加了");
                return false;
            }

        }else if (cmd.getName().equalsIgnoreCase("clearbtf")) {
            if(Games.isEmpty()){
                sender.sendMessage("找不到战场");
                return false;
            }
            Games.get(0).CB.ClearMob();
            sender.sendMessage("战场已清空");
            return true;
        }else if (cmd.getName().equalsIgnoreCase("delbtf")) {
            if(Games.isEmpty()){
                sender.sendMessage("找不到战场");
                return false;
            }
            Games.get(0).CB.delete();
            sender.sendMessage("战场已删除");
            return true;
        }else if (cmd.getName().equalsIgnoreCase("startgame")) {
            if(Games.isEmpty()){
                sender.sendMessage("找不到战场");
                return false;
            }
            ChessGame CG = Games.get(0);
            //检查玩家是否到位
            if(CG.PlayerA== null||CG.PlayerB== null){
                sender.sendMessage("玩家数量不足!");
                return false;
            }
            CG.SetPlayers();
            CG.Start();
            sender.sendMessage("对战开始!");
            return true;
        }else if (cmd.getName().equalsIgnoreCase("testgame")) {
            if(Games.isEmpty()){
                sender.sendMessage("找不到战场");
                return false;
            }
            ChessGame CG = Games.get(0);
            //检查玩家是否到位
            if(CG.PlayerA== null){
                sender.sendMessage("玩家数量不足!");
                return false;
            }
            CG.IsTest = true;
            CG.SetPlayers();
            CG.Start();
            sender.sendMessage("对战开始!");
            return true;
        }else if (cmd.getName().equalsIgnoreCase("btinfo")) {
            if(Games.isEmpty()){
                sender.sendMessage("找不到战场");
                return false;
            }
            ChessGame CG = Games.get(0);
            sender.sendMessage("中心点" + CG.CB.CBLoc.toString() + ",长度" +  CG.CB.getLong() + ",宽度" +  CG.CB.getWidth()+ ",高度" +  CG.CB.getHeight());
            return true;
        }else{
            return false;
        }
        return false;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        
        
    }
    
    
}
