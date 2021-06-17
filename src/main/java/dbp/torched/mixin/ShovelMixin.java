package dbp.torched.mixin;

import dbp.torched.TorchPlacement;
import net.minecraft.block.Block;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShovelItem.class)
public class ShovelMixin extends MiningToolItem {
	protected ShovelMixin(float attackDamage, float attackSpeed, ToolMaterial material, Set<Block> effectiveBlocks, Settings settings) {
		super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (!context.getPlayer().isSneaking()){
			TorchPlacement.placeTorch(context);
			return ActionResult.PASS;
		}
		return super.useOnBlock(context);
	}
}