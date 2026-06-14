package name.modid;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.util.EventResult;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SleepingBag implements ModInitializer {
    public static final String MOD_ID = "sleeping-bag";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModBlocks.init();

        EntitySleepEvents.ALLOW_BED.register((entity, sleepingPos, state, vanillaResult) -> {
            if (ModBlocks.isSleepingBag(state)) {
                return EventResult.ALLOW;
            }
            return EventResult.PASS;
        });

        EntitySleepEvents.MODIFY_SLEEPING_DIRECTION.register((entity, sleepingPos, sleepingDirection) -> {
            BlockState state = entity.level().getBlockState(sleepingPos);
            if (ModBlocks.isSleepingBag(state)) {
                return state.getValue(SleepingBagBlock.FACING);
            }
            return sleepingDirection;
        });

        EntitySleepEvents.MODIFY_WAKE_UP_POSITION.register((entity, sleepingPos, bedState, wakeUpPos) -> {
            if (ModBlocks.isSleepingBag(bedState)) {
                Direction direction = bedState.getValue(SleepingBagBlock.FACING);

                return BedBlock.findStandUpPosition(
                        entity.getType(),
                        entity.level(),
                        sleepingPos,
                        direction,
                        entity.getYRot()
                ).orElse(wakeUpPos);
            }
            return wakeUpPos;
        });

        EntitySleepEvents.ALLOW_SETTING_SPAWN.register((player, sleepingPos) -> {
            return !ModBlocks.isSleepingBag(player.level().getBlockState(sleepingPos));
        });

        LOGGER.info("Sleeping Bag initialized!");
    }
}