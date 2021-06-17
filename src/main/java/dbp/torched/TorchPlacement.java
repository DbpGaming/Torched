package dbp.torched;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class TorchPlacement {
	public static void placeTorch(ItemUsageContext context) {
		PlayerEntity player = context.getPlayer();
		ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
		World world = context.getWorld();
		BlockState state = getPlacementState(itemPlacementContext);
		BlockPos pos = itemPlacementContext.getBlockPos();

		for (int i = 0; i < player.inventory.size(); i++) {
			if (player.inventory.getStack(i).getItem() == Items.TORCH && state != null) {
				if (context.getWorld().setBlockState(itemPlacementContext.getBlockPos(), state, 11)) {
					BlockSoundGroup blockSoundGroup = state.getSoundGroup();
					world.playSound(player, pos, blockSoundGroup.getPlaceSound(), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0F) / 2.0F, blockSoundGroup.getPitch() * 0.8F);
					if (player == null || !player.abilities.creativeMode) {
						player.inventory.getStack(i).decrement(1);
					}
				}
			}
		}
	}

	public static BlockState getPlacementState(ItemPlacementContext context) {
		BlockState state = null;
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		Direction[] directions = context.getPlacementDirections();

		for (int i = 0; i < directions.length; i++) {
			Direction direction = directions[i];
			if (direction != Direction.UP) {
				BlockState state1 = direction == Direction.DOWN ? Blocks.TORCH.getPlacementState(context): Blocks.WALL_TORCH.getPlacementState(context);
				if (state1 != null && state1.canPlaceAt(world, pos)) {
					state = state1;
					break;
				}
			}
		}
		return state != null && world.canPlace(state, pos, ShapeContext.absent()) ? state : null;
	}

	public static boolean canPlace(ItemPlacementContext context, BlockState state) {
		PlayerEntity playerEntity = context.getPlayer();
		ShapeContext shapeContext = playerEntity == null ? ShapeContext.absent() : ShapeContext.of(playerEntity);
		return (false || state.canPlaceAt(context.getWorld(), context.getBlockPos())) && context.getWorld().canPlace(state, context.getBlockPos(), shapeContext);
	}
}
