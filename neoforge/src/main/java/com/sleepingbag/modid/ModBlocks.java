package com.sleepingbag.modid;

import java.util.List;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SleepingBagMod.MODID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SleepingBagMod.MODID);

    public static final DeferredBlock<SleepingBagBlock> WHITE_SLEEPING_BAG = registerSleepingBag("white");
    public static final DeferredBlock<SleepingBagBlock> ORANGE_SLEEPING_BAG = registerSleepingBag("orange");
    public static final DeferredBlock<SleepingBagBlock> MAGENTA_SLEEPING_BAG = registerSleepingBag("magenta");
    public static final DeferredBlock<SleepingBagBlock> LIGHT_BLUE_SLEEPING_BAG = registerSleepingBag("light_blue");
    public static final DeferredBlock<SleepingBagBlock> YELLOW_SLEEPING_BAG = registerSleepingBag("yellow");
    public static final DeferredBlock<SleepingBagBlock> LIME_SLEEPING_BAG = registerSleepingBag("lime");
    public static final DeferredBlock<SleepingBagBlock> PINK_SLEEPING_BAG = registerSleepingBag("pink");
    public static final DeferredBlock<SleepingBagBlock> GRAY_SLEEPING_BAG = registerSleepingBag("gray");
    public static final DeferredBlock<SleepingBagBlock> LIGHT_GRAY_SLEEPING_BAG = registerSleepingBag("light_gray");
    public static final DeferredBlock<SleepingBagBlock> CYAN_SLEEPING_BAG = registerSleepingBag("cyan");
    public static final DeferredBlock<SleepingBagBlock> PURPLE_SLEEPING_BAG = registerSleepingBag("purple");
    public static final DeferredBlock<SleepingBagBlock> BLUE_SLEEPING_BAG = registerSleepingBag("blue");
    public static final DeferredBlock<SleepingBagBlock> BROWN_SLEEPING_BAG = registerSleepingBag("brown");
    public static final DeferredBlock<SleepingBagBlock> GREEN_SLEEPING_BAG = registerSleepingBag("green");
    public static final DeferredBlock<SleepingBagBlock> RED_SLEEPING_BAG = registerSleepingBag("red");
    public static final DeferredBlock<SleepingBagBlock> BLACK_SLEEPING_BAG = registerSleepingBag("black");

    public static final List<DeferredBlock<SleepingBagBlock>> SLEEPING_BAGS = List.of(
            WHITE_SLEEPING_BAG,
            ORANGE_SLEEPING_BAG,
            MAGENTA_SLEEPING_BAG,
            LIGHT_BLUE_SLEEPING_BAG,
            YELLOW_SLEEPING_BAG,
            LIME_SLEEPING_BAG,
            PINK_SLEEPING_BAG,
            GRAY_SLEEPING_BAG,
            LIGHT_GRAY_SLEEPING_BAG,
            CYAN_SLEEPING_BAG,
            PURPLE_SLEEPING_BAG,
            BLUE_SLEEPING_BAG,
            BROWN_SLEEPING_BAG,
            GREEN_SLEEPING_BAG,
            RED_SLEEPING_BAG,
            BLACK_SLEEPING_BAG
    );

    private static DeferredBlock<SleepingBagBlock> registerSleepingBag(String color) {
        String name = color + "_sleeping_bag";
        DeferredBlock<SleepingBagBlock> block = BLOCKS.registerBlock(
                name,
                SleepingBagBlock::new,
                properties -> properties.strength(0.2F).noOcclusion()
        );
        DeferredItem<BlockItem> ignored = ITEMS.registerSimpleBlockItem(name, block);
        return block;
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
    }

    public static boolean isSleepingBag(BlockState state) {
        return state.getBlock() instanceof SleepingBagBlock;
    }

    public static boolean isSleepingBag(Block block) {
        return block instanceof SleepingBagBlock;
    }

    private ModBlocks() {}
}
