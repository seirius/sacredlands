package com.seirius.sacredlands.common.tileentity;

import com.seirius.sacredlands.common.blocks.ModTileEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.HashMap;

public class SacredBlockTileEntity extends TileEntity {

    private static final long checkEvery = 24000;

    private final HashMap<String, Long> data = new HashMap<>();

    public SacredBlockTileEntity(final TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public SacredBlockTileEntity() {
        this(ModTileEntityTypes.SACRED_TILE_ENTITY.get());
    }

    public boolean hasPlayer(String player) {
        return data.containsKey(player);
    }

    public boolean canBuf(String player, long time) {
        if (!hasPlayer(player)) {
            data.put(player, time);
            return true;
        }
        long storedTime = data.get(player);
        if (time - storedTime >= checkEvery) {
            data.put(player, time);
            return true;
        }
        return false;
    }

    public void buffPlayer(PlayerEntity playerEntity) {
        playerEntity.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 6000, 3));
        playerEntity.addPotionEffect(new EffectInstance(Effects.GLOWING, 6000, 5));
        playerEntity.addPotionEffect(new EffectInstance(Effects.SPEED, 6000, 2));
        playerEntity.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 6000, 1));
        playerEntity.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 6000, 3));
    }
}
