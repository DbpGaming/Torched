package dbp.torched.mixin;

import dbp.torched.TorchPlacement;
import net.minecraft.block.Block;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;


import org.spongepowered.asm.mixin.Mixin;

@Mixin(PickaxeItem.class)
public class PickaxeMixin extends MiningToolItem {
	protected PickaxeMixin(float attackDamage, float attackSpeed, ToolMaterial material, Tag<Block> effectiveBlocks,
			Settings settings) {
		super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
			TorchPlacement.placeTorch(context);
		return ActionResult.PASS;
	}
}