package com.seirius.sacredlands.common.blocks;

import com.seirius.sacredlands.SacredLands;
import com.seirius.sacredlands.common.tileentity.SacredBlockTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, SacredLands.MOD_ID);

    public static final RegistryObject<TileEntityType<SacredBlockTileEntity>> SACRED_TILE_ENTITY
            = TILE_ENTITY_TYPES.register(
                    "sacred_tile_entity",
                    () -> TileEntityType.Builder.create(SacredBlockTileEntity::new, SacredBlock.sacred_land_center).build(null)
    );

}
