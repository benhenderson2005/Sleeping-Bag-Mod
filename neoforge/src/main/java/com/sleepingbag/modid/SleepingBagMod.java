package com.sleepingbag.modid;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerSetSpawnEvent;
import org.slf4j.Logger;

@Mod(SleepingBagMod.MODID)
public final class SleepingBagMod {
    public static final String MODID = "sleeping_bag";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SleepingBagMod(IEventBus modEventBus) {
        ModBlocks.register(modEventBus);
        modEventBus.addListener(this::addCreativeTabItems);
        NeoForge.EVENT_BUS.register(this);

        LOGGER.info("Sleeping Bag initialized.");
    }

    private void addCreativeTabItems(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
            ModBlocks.SLEEPING_BAGS.forEach(block -> event.accept(block.asItem()));
        }
    }

    @SubscribeEvent
    public void onPlayerSetSpawn(PlayerSetSpawnEvent event) {
        if (event.getNewSpawn() != null && ModBlocks.isSleepingBag(event.getEntity().level().getBlockState(event.getNewSpawn()))) {
            event.setCanceled(true);
        }
    }
}
