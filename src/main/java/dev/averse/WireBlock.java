package dev.averse;

import net.minecraft.block.FacingBlock;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class WireBlock extends FacingBlock implements Waterloggable {

    public static final MapCodec<FacingBlock> CODEC = FacingBlock.createCodec(WireBlock::new);

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");
    public static final DirectionProperty FACING = Properties.FACING;
     
    public WireBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(WATERLOGGED, false));
    }

    @Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED, WATERLOGGED);
	}

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        Direction dir = state.get(FACING);
		switch(dir) {
			case NORTH:
				return VoxelShapes.cuboid((1f/16f)*7f, (1f/16f)*7f, 0.0f, (1f/16f)*9f, (1f/16f)*9f, 1.0f);
			case SOUTH:
                return VoxelShapes.cuboid((1f/16f)*7f, (1f/16f)*7f, 0.0f, (1f/16f)*9f, (1f/16f)*9f, 1.0f);
			case EAST:
                return VoxelShapes.cuboid(0.0f, (1f/16f)*7f, (1f/16f)*7f, 1.0f, (1f/16f)*9f, (1f/16f)*9f);
			case WEST:
                return VoxelShapes.cuboid(0.0f, (1f/16f)*7f, (1f/16f)*7f, 1.0f, (1f/16f)*9f, (1f/16f)*9f);
            case UP:
                return VoxelShapes.cuboid((1f/16f)*7f, 0.0f, (1f/16f)*7f, (1f/16f)*9f, 1.0f, (1f/16f)*9f);
            case DOWN:
                return VoxelShapes.cuboid((1f/16f)*7f, 0.0f, (1f/16f)*7f, (1f/16f)*9f, 1.0f, (1f/16f)*9f);
			default:
				return VoxelShapes.fullCube();
		}
    }

    @Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return (BlockState)((BlockState)this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite()))
            .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER));
	}

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
 
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).getItem().equals(PowerExtended.POWER_WAND_ITEM)) {
            player.playSound(SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, 1, 1);
            world.setBlockState(pos, state.with(POWERED, true));
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    @Override
    protected MapCodec<? extends FacingBlock> getCodec() {
        return WireBlock.CODEC;
    }
}
