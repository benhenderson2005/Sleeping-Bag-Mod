package name.modid;

import java.util.Set;
import java.util.function.Function;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public final class ModBlocks {

    private static Block register(
            String name,
            Function<BlockBehaviour.Properties, Block> blockFactory,
            BlockBehaviour.Properties properties,
            boolean shouldRegisterItem
    ) {
        ResourceKey<Block> blockKey = ResourceKey.create(
                Registries.BLOCK,
                Identifier.fromNamespaceAndPath(SleepingBag.MOD_ID, name)
        );

        Block block = blockFactory.apply(properties.setId(blockKey));

        if (shouldRegisterItem) {
            ResourceKey<Item> itemKey = ResourceKey.create(
                    Registries.ITEM,
                    Identifier.fromNamespaceAndPath(SleepingBag.MOD_ID, name)
            );

            BlockItem blockItem = new BlockItem(
                    block,
                    new Item.Properties().setId(itemKey).useBlockDescriptionPrefix()
            );

            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    private static Block registerSleepingBag(String color) {
        return register(
                color + "_sleeping_bag",
                SleepingBagBlock::new,
                BlockBehaviour.Properties.of()
                        .strength(0.2F)
                        .noOcclusion(),
                true
        );
    }

    public static final Block WHITE_SLEEPING_BAG = registerSleepingBag("white");
    public static final Block ORANGE_SLEEPING_BAG = registerSleepingBag("orange");
    public static final Block MAGENTA_SLEEPING_BAG = registerSleepingBag("magenta");
    public static final Block LIGHT_BLUE_SLEEPING_BAG = registerSleepingBag("light_blue");
    public static final Block YELLOW_SLEEPING_BAG = registerSleepingBag("yellow");
    public static final Block LIME_SLEEPING_BAG = registerSleepingBag("lime");
    public static final Block PINK_SLEEPING_BAG = registerSleepingBag("pink");
    public static final Block GRAY_SLEEPING_BAG = registerSleepingBag("gray");
    public static final Block LIGHT_GRAY_SLEEPING_BAG = registerSleepingBag("light_gray");
    public static final Block CYAN_SLEEPING_BAG = registerSleepingBag("cyan");
    public static final Block PURPLE_SLEEPING_BAG = registerSleepingBag("purple");
    public static final Block BLUE_SLEEPING_BAG = registerSleepingBag("blue");
    public static final Block BROWN_SLEEPING_BAG = registerSleepingBag("brown");
    public static final Block GREEN_SLEEPING_BAG = registerSleepingBag("green");
    public static final Block RED_SLEEPING_BAG = registerSleepingBag("red");
    public static final Block BLACK_SLEEPING_BAG = registerSleepingBag("black");

    private static final Set<Block> ALL_SLEEPING_BAGS = Set.of(
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

    public static boolean isSleepingBag(BlockState state) {
        return ALL_SLEEPING_BAGS.contains(state.getBlock());
    }

    public static boolean isSleepingBag(Block block) {
        return ALL_SLEEPING_BAGS.contains(block);
    }

    public static void init() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COLORED_BLOCKS)
                .register(entries -> {
                    entries.accept(WHITE_SLEEPING_BAG.asItem());
                    entries.accept(ORANGE_SLEEPING_BAG.asItem());
                    entries.accept(MAGENTA_SLEEPING_BAG.asItem());
                    entries.accept(LIGHT_BLUE_SLEEPING_BAG.asItem());
                    entries.accept(YELLOW_SLEEPING_BAG.asItem());
                    entries.accept(LIME_SLEEPING_BAG.asItem());
                    entries.accept(PINK_SLEEPING_BAG.asItem());
                    entries.accept(GRAY_SLEEPING_BAG.asItem());
                    entries.accept(LIGHT_GRAY_SLEEPING_BAG.asItem());
                    entries.accept(CYAN_SLEEPING_BAG.asItem());
                    entries.accept(PURPLE_SLEEPING_BAG.asItem());
                    entries.accept(BLUE_SLEEPING_BAG.asItem());
                    entries.accept(BROWN_SLEEPING_BAG.asItem());
                    entries.accept(GREEN_SLEEPING_BAG.asItem());
                    entries.accept(RED_SLEEPING_BAG.asItem());
                    entries.accept(BLACK_SLEEPING_BAG.asItem());
                });
    }

    private ModBlocks() {}
}