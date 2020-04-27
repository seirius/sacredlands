package com.seirius.sacredlands.common.blocks;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

public class InvokeSacredGod {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("sacred").requires((permission) -> permission.hasPermissionLevel(4))
                .then(Commands.literal("invoke_land").executes((command) -> {
                    ServerPlayerEntity player = command.getSource().asPlayer();
                    player.inventory.addItemStackToInventory(new ItemStack(SacredBlock.sacred_land_center));
                    player.inventory.addItemStackToInventory(new ItemStack(SacredBlock.sacred_land_right));
                    player.inventory.addItemStackToInventory(new ItemStack(SacredBlock.sacred_land_left));
                    player.inventory.addItemStackToInventory(new ItemStack(SacredBlock.sacred_land_bottom));
                    player.inventory.addItemStackToInventory(new ItemStack(SacredBlock.sacred_land_top));
                    return 1;
                }))
        );
    }

}
