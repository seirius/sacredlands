package com.seirius.sacredlands.common.items;

import com.seirius.sacredlands.common.blocks.SacredBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class SacredLandsItemGroup extends ItemGroup {
    public static final SacredLandsItemGroup instance
            = new SacredLandsItemGroup(ItemGroup.GROUPS.length, "sacredlandstab");

    public SacredLandsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(SacredBlock.sacred_land_center);
    }
}
