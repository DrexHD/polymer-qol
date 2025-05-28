package me.drex.polymerqol.mixin.client.update;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {
    @ModifyArg(
        method = "placeBlock",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"
        ),
        index = 2
    )
    public int dontUpdateNeighbours(int i) {
        return i | Block.UPDATE_KNOWN_SHAPE;
    }
}
