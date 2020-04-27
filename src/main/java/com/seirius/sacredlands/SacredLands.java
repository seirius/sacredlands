package com.seirius.sacredlands;

import com.seirius.sacredlands.common.blocks.InvokeSacredGod;
import com.seirius.sacredlands.common.blocks.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod("sacredlands")
public class SacredLands {
    public static final String MOD_ID = "sacredlands";

    private static final Logger LOGGER = LogManager.getLogger();

    public static SacredLands instance;

    public SacredLands() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(eventBus);

        instance = this;
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    //    private void enqueueIMC(final InterModEnqueueEvent event)
//    {
//        InterModComms.sendTo("sacredlands", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
//    }
//
//    private void processIMC(final InterModProcessEvent event)
//    {
//        LOGGER.info("Got IMC {}", event.getIMCStream().
//                map(m->m.getMessageSupplier().get()).
//                collect(Collectors.toList()));
//    }
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        InvokeSacredGod.register(event.getCommandDispatcher());
        LOGGER.info("HELLO from server starting");
    }

}
