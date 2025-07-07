package me.drex.polymerqol.mixin.client.update;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import eu.pb4.polymer.core.api.client.ClientPolymerBlock;
import eu.pb4.polymer.core.api.client.PolymerClientUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockBehaviourMixin {
    @WrapOperation(
        method = "updateShape",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/Block;updateShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/world/level/ScheduledTickAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/util/RandomSource;)Lnet/minecraft/world/level/block/state/BlockState;"
        )
    )
    public BlockState dontUpdatePolymerBlocksOnClient(
        Block instance, BlockState asState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess,
        BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState, RandomSource randomSource,
        Operation<BlockState> original
    ) {
        ClientPolymerBlock.State state = PolymerClientUtils.getPolymerStateAt(blockPos);
        if (state != ClientPolymerBlock.NONE_STATE) {
            return asState;
        }
        return original.call(instance, asState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState, randomSource);
    }
}
