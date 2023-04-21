package com.sansyy.sansyyauth;

import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class MapGenerator implements CommandExecutor {
    private final JavaPlugin plugin;
    private final GoogleAuthService authService;
    private final Map<UUID, String> codeMap;

    public MapGenerator(JavaPlugin plugin, GoogleAuthService authService, Map<UUID, String> codeMap) {
        this.plugin = plugin;
        this.authService = authService;
        this.codeMap = codeMap;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only a player can use this command");
            return true;
        }
        Player player = (Player) sender;
        if (args.length > 0) {
            player.sendMessage(ChatColor.RED + "Usage: /2fa");
        }
        if (player.hasPermission("a.a")) {
            player.sendMessage(ChatColor.GREEN + "You are already authenticated.");
            return true;
        }
        GoogleAuthenticatorKey key = authService.createSecretKey();
        codeMap.put(player.getUniqueId(), key.getKey());

        String url = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("SansyyAuth", player.getName(), key);
        try {
            byte[] qrCode = generateQRCode(url);
            ItemStack mapItem = createMapItem(qrCode);
            player.getInventory().addItem(mapItem);
        } catch (IOException | WriterException e) {
            player.sendMessage(ChatColor.RED + "Error Generating QR code.");
            e.printStackTrace();
        }

        player.sendMessage(ChatColor.GREEN + "Scan the QR code in your authenticator app and enter the code in chat to authenticate.");
        return true;
    }

    private byte[] generateQRCode(String url) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

    }
}
