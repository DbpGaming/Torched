package net.fabricmc.example;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class TorchPlacement {
	public static void placeTorch(ItemUsageContext context){
		PlayerEntity player = context.getPlayer();
		ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
		BlockState state = getPlacementState(itemPlacementContext);

		for (int i = 0; i < player.inventory.size() ;i++){
			System.out.print(player.inventory.getStack(i));
			if (player.inventory.getStack(i).getItem() == Items.TORCH && state != null){
				if (context.getWorld().setBlockState(itemPlacementContext.getBlockPos(), state, 11)){
					player.inventory.getStack(i).decrement(1);
				}
			}
		}
	}

	public static BlockState getPlacementState(ItemPlacementContext context) {
		BlockState blockState = Blocks.WALL_TORCH.getPlacementState(context);
		BlockState blockState2 = null;
		WorldView worldView = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		Direction[] var6 = context.getPlacementDirections();
		int var7 = var6.length;
  
		for(int var8 = 0; var8 < var7; ++var8) {
		   Direction direction = var6[var8];
		   if (direction != Direction.UP) {
			  BlockState blockState3 = direction == Direction.DOWN ? Blocks.TORCH.getPlacementState(context) : blockState;
			  if (blockState3 != null && blockState3.canPlaceAt(worldView, blockPos)) {
				 blockState2 = blockState3;
				 break;
			  }
		   }
		}
		return blockState2 != null && worldView.canPlace(blockState2, blockPos, ShapeContext.absent()) ? blockState2 : null;
	}
	
	public static boolean canPlace(ItemPlacementContext context, BlockState state) {
		PlayerEntity playerEntity = context.getPlayer();
		ShapeContext shapeContext = playerEntity == null ? ShapeContext.absent() : ShapeContext.of(playerEntity);
		return (false || state.canPlaceAt(context.getWorld(), context.getBlockPos())) && context.getWorld().canPlace(state, context.getBlockPos(), shapeContext);
	 }
}
