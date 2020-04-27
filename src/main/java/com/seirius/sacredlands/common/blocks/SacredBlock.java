package com.seirius.sacredlands.common.blocks;

import com.seirius.sacredlands.SacredLands;
import com.seirius.sacredlands.common.items.SacredLandsItemGroup;
import com.seirius.sacredlands.common.tileentity.SacredBlockTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = SacredLands.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(SacredLands.MOD_ID)
public class SacredBlock extends Block {

    private final long checkSaintEvery = 20;
    private long gameLastTime;

    private boolean isEntity;

    public static final Block sacred_land_center = null;
    public static final Block sacred_land_left = null;
    public static final Block sacred_land_right = null;
    public static final Block sacred_land_top = null;
    public static final Block sacred_land_bottom = null;

    public static String REGISTRY_NAME_SACRED_LAND_CENTER = "sacred_land_center";
    public static String REGISTRY_NAME_SACRED_LAND_LEFT = "sacred_land_left";
    public static String REGISTRY_NAME_SACRED_LAND_RIGHT = "sacred_land_right";
    public static String REGISTRY_NAME_SACRED_LAND_TOP = "sacred_land_top";
    public static String REGISTRY_NAME_SACRED_LAND_BOTTOM = "sacred_land_bottom";

    public SacredBlock(Properties properties) {
        super(properties);
    }

    public SacredBlock(Properties properties, boolean isEntity) {
        super(properties);
        this.isEntity = isEntity;
    }

    @SubscribeEvent()
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        Block center = new SacredBlock(SacredBlock.getSacredBlockProperties(), true)
                .setRegistryName(REGISTRY_NAME_SACRED_LAND_CENTER);
        Block left = new SacredBlock(SacredBlock.getSacredBlockProperties())
                .setRegistryName(REGISTRY_NAME_SACRED_LAND_LEFT);
        Block top = new SacredBlock(SacredBlock.getSacredBlockProperties())
                .setRegistryName(REGISTRY_NAME_SACRED_LAND_TOP);
        Block right = new SacredBlock(SacredBlock.getSacredBlockProperties())
                .setRegistryName(REGISTRY_NAME_SACRED_LAND_RIGHT);
        Block bottom = new SacredBlock(SacredBlock.getSacredBlockProperties())
                .setRegistryName(REGISTRY_NAME_SACRED_LAND_BOTTOM);
        registry.register(center);
        registry.register(left);
        registry.register(top);
        registry.register(right);
        registry.register(bottom);
    }

    @SubscribeEvent()
    public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        BlockItem center = getSacredBlockItem(sacred_land_center, REGISTRY_NAME_SACRED_LAND_CENTER);
        BlockItem left = getSacredBlockItem(sacred_land_left, REGISTRY_NAME_SACRED_LAND_LEFT);
        BlockItem right = getSacredBlockItem(sacred_land_right, REGISTRY_NAME_SACRED_LAND_RIGHT);
        BlockItem top = getSacredBlockItem(sacred_land_top, REGISTRY_NAME_SACRED_LAND_TOP);
        BlockItem bottom = getSacredBlockItem(sacred_land_bottom, REGISTRY_NAME_SACRED_LAND_BOTTOM);
        registry.register(center);
        registry.register(left);
        registry.register(right);
        registry.register(top);
        registry.register(bottom);
    }

    public static BlockItem getSacredBlockItem(Block block, String registryName) {
        return (BlockItem) new BlockItem(block, getSacredItemProperties())
                .setRegistryName(registryName);
    }

    private static Block.Properties getSacredBlockProperties() {
        return Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(1f, 100f)
                .sound(SoundType.STONE);
    }

    private static Item.Properties getSacredItemProperties() {
        return new Item.Properties()
                .maxStackSize(1)
                .group(SacredLandsItemGroup.instance);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityWalk(worldIn, pos, entityIn);

        if (
                !worldIn.isRemote &&
                entityIn instanceof PlayerEntity &&
                worldIn.getBlockState(pos).hasTileEntity() &&
                canCheck(worldIn) && isLandComplete(worldIn, pos)
        ) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof SacredBlockTileEntity) {
                SacredBlockTileEntity sacredBlockTileEntity = (SacredBlockTileEntity) tileEntity;
                PlayerEntity playerEntity = (PlayerEntity) entityIn;
                if (sacredBlockTileEntity.canBuf(playerEntity.getScoreboardName(), worldIn.getDayTime())) {
                    sacredBlockTileEntity.buffPlayer(playerEntity);
                }
            }
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return isEntity;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.SACRED_TILE_ENTITY.get().create();
    }

    private boolean canCheck(World world) {
        long currentTime = world.getGameTime();
        if (currentTime - gameLastTime >= checkSaintEvery) {
            gameLastTime = currentTime;
            return true;
        }
        return false;
    }

    private static boolean isLandComplete(World world, BlockPos blockPos) {
        boolean isTopSacred = isSacredBlock(world,
                new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() - 1), REGISTRY_NAME_SACRED_LAND_TOP);
        if (!isTopSacred) {
            return false;
        }
        boolean isBottomSacred = isSacredBlock(world,
                new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() + 1), REGISTRY_NAME_SACRED_LAND_BOTTOM);
        if (!isBottomSacred) {
            return false;
        }
        boolean isLeftSacred = isSacredBlock(world,
                new BlockPos(blockPos.getX() - 1, blockPos.getY(), blockPos.getZ()), REGISTRY_NAME_SACRED_LAND_LEFT);
        if (!isLeftSacred) {
            return false;
        }
        boolean isRightSacred = isSacredBlock(world,
                new BlockPos(blockPos.getX() + 1, blockPos.getY(), blockPos.getZ()), REGISTRY_NAME_SACRED_LAND_RIGHT);
        if (!isRightSacred) {
            return false;
        }

        return isSacredBlock(world,
                new BlockPos(blockPos), REGISTRY_NAME_SACRED_LAND_CENTER);
    }

    private static boolean isSacredBlock(World world, BlockPos blockPos, String registryName) {
        Block block = world.getBlockState(blockPos).getBlock();
        return block instanceof SacredBlock && block.getRegistryName().toString().equals(String.format("%s:%s", SacredLands.MOD_ID, registryName));
    }

}
