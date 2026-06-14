package name.modid;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SleepingBagBlock extends HorizontalDirectionalBlock {

    public static final MapCodec<SleepingBagBlock> CODEC = simpleCodec(SleepingBagBlock::new);
    public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;

    private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);

    public SleepingBagBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(PART, BedPart.FOOT)
        );
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    public static BlockPos getSleepPos(BlockState state, BlockPos pos) {
        return state.getValue(PART) == BedPart.HEAD
                ? pos
                : pos.relative(state.getValue(FACING));
    }

    public static BlockPos getOtherPartPos(BlockState state, BlockPos pos) {
        return state.getValue(PART) == BedPart.FOOT
                ? pos.relative(state.getValue(FACING))
                : pos.relative(state.getValue(FACING).getOpposite());
    }

    private static Direction getConnectionDirection(BlockState state) {
        return state.getValue(PART) == BedPart.FOOT
                ? state.getValue(FACING)
                : state.getValue(FACING).getOpposite();
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        BlockPos sleepPos = getSleepPos(state, pos);
        var result = player.startSleepInBed(sleepPos);

        result.left().ifPresent(problem -> {
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.sendSystemMessage(problem.message());
            }
        });

        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection();
        BlockPos pos = context.getClickedPos();
        BlockPos otherPos = pos.relative(facing);
        Level level = context.getLevel();

        if (!level.getWorldBorder().isWithinBounds(otherPos)) {
            return null;
        }

        if (!level.getBlockState(otherPos).canBeReplaced(context)) {
            return null;
        }

        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(PART, BedPart.FOOT);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        BlockPos headPos = pos.relative(state.getValue(FACING));
        level.setBlock(headPos, state.setValue(PART, BedPart.HEAD), 3);
    }

    @Override
    protected BlockState updateShape(
            BlockState state,
            LevelReader level,
            ScheduledTickAccess scheduledTickAccess,
            BlockPos pos,
            Direction direction,
            BlockPos neighborPos,
            BlockState neighborState,
            RandomSource random
    ) {
        if (direction == getConnectionDirection(state)) {
            boolean validOtherHalf = neighborState.is(this)
                    && neighborState.getValue(PART) != state.getValue(PART)
                    && neighborState.getValue(FACING) == state.getValue(FACING);

            if (!validOtherHalf) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide()) {
            BlockPos otherPos = getOtherPartPos(state, pos);
            BlockState otherState = level.getBlockState(otherPos);

            if (otherState.is(this)) {
                level.setBlock(otherPos, Blocks.AIR.defaultBlockState(), 3);
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}