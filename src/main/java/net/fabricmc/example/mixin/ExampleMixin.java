package net.fabricmc.example.mixin;

import net.fabricmc.example.TorchPlacement;
import net.minecraft.block.Block;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(PickaxeItem.class)
public class ExampleMixin extends MiningToolItem {
	protected ExampleMixin(float attackDamage, float attackSpeed, ToolMaterial material, Set<Block> effectiveBlocks, Settings settings) {
		super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
			TorchPlacement.placeTorch(context);
		return ActionResult.PASS;
	}
}