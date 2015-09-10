/*
 * PwnFilter -- Regex-based User Filter Plugin for Bukkit-based Minecraft servers.
 * Copyright (c) 2013 Pwn9.com. Tremor77 <admin@pwn9.com> & Sage905 <patrick@toal.ca>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 */

package com.pwn9.PwnFilter.bukkit.command;

import com.pwn9.PwnFilter.api.ClientManager;
import com.pwn9.PwnFilter.bukkit.PwnFilterPlugin;
import com.pwn9.PwnFilter.rules.RuleManager;
import com.pwn9.PwnFilter.util.LogManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Reload the PwnFilter config.
 * User: ptoal
 * Date: 13-08-10
 * Time: 9:23 AM
 *
 * @author ptoal
 * @version $Id: $Id
 */
public class pfreload implements CommandExecutor {

    private final PwnFilterPlugin plugin;

    /**
     * <p>Constructor for pfreload.</p>
     *
     * @param plugin a {@link PwnFilterPlugin} object.
     */
    public pfreload(PwnFilterPlugin plugin) {
        this.plugin = plugin;
    }

    /** {@inheritDoc} */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.RED + "Reloading config.yml and rules/*.txt files.");

        LogManager.logger.info("Disabling all listeners");
        ClientManager.getInstance().disableClients();

        plugin.reloadConfig();
        plugin.configurePlugin();

        LogManager.logger.config("Reloaded config.yml as requested by " + sender.getName());

        RuleManager.getInstance().reloadAllConfigs();
        LogManager.logger.config("All rules reloaded by " + sender.getName());

        // Re-register our listeners
        ClientManager.getInstance().enableClients();
        LogManager.logger.info("All listeners re-enabled");

        return true;

    }

}