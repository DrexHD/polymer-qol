package me.drex.polymerqol.mixin.client.mining;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import eu.pb4.polymer.core.api.client.ClientPolymerBlock;
import eu.pb4.polymer.core.api.client.PolymerClientUtils;
import me.drex.polymerqol.PolymerQOLClient;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static me.drex.polymerqol.PolymerQOLClient.config;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin {
    @WrapOperation(
        method = "getDestroyProgress",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;getDestroySpeed(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)F"
        )
    )
    public float clientSideGetDestroySpeed(
        BlockState instance, BlockGetter blockGetter, BlockPos blockPos, Operation<Float> original,
        @Share("clientBlock") LocalRef<ClientPolymerBlock.State> clientBlock
    ) {
        var state = PolymerClientUtils.getPolymerStateAt(blockPos);
        if (state != ClientPolymerBlock.NONE_STATE && config.clientMining()) {
            clientBlock.set(state);
            return state.block().hardness();
        }
        return original.call(instance, blockGetter, blockPos);
    }

    @WrapOperation(
        method = "getDestroyProgress",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;hasCorrectToolForDrops(Lnet/minecraft/world/level/block/state/BlockState;)Z"
        )
    )
    public boolean clientSideHasCorrectToolForDrops(
        Player instance, BlockState blockState, Operation<Boolean> original,
        @Share("clientBlock") LocalRef<ClientPolymerBlock.State> clientBlock
    ) {
        var prev = PolymerQOLClient.POLYMER_BLOCK_STATE.get();
        try {
            PolymerQOLClient.POLYMER_BLOCK_STATE.set(clientBlock.get());
            return original.call(instance, blockState);
        } finally {
            PolymerQOLClient.POLYMER_BLOCK_STATE.set(prev);
        }
    }

    @WrapOperation(
        method = "getDestroyProgress",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;getDestroySpeed(Lnet/minecraft/world/level/block/state/BlockState;)F"
        )
    )
    public float clientSideGetDestroySpeed(
        Player instance, BlockState blockState, Operation<Float> original,
        @Share("clientBlock") LocalRef<ClientPolymerBlock.State> clientBlock
    ) {
        var prev = PolymerQOLClient.POLYMER_BLOCK_STATE.get();
        try {
            PolymerQOLClient.POLYMER_BLOCK_STATE.set(clientBlock.get());
            return original.call(instance, blockState);
        } finally {
            PolymerQOLClient.POLYMER_BLOCK_STATE.set(prev);
        }
    }
}
