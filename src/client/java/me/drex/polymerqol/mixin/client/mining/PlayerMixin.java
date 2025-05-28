package me.drex.polymerqol.mixin.client.mining;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import eu.pb4.polymer.core.api.client.ClientPolymerBlock;
import me.drex.polymerqol.PolymerQOLClient;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static eu.pb4.polymer.core.api.client.ClientPolymerBlock.MiningDeltaLogic.DEFAULT;
import static eu.pb4.polymer.core.api.client.ClientPolymerBlock.MiningDeltaLogic.TOOL_REQUIRED;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @WrapOperation(
        method = "hasCorrectToolForDrops",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;requiresCorrectToolForDrops()Z"
        )
    )
    public boolean clientSideRequiresCorrectToolForDrops(BlockState instance, Operation<Boolean> original) {
        var state = PolymerQOLClient.POLYMER_BLOCK_STATE.get();
        if (state != null) {
            ClientPolymerBlock.MiningDeltaLogic miningDeltaLogic = state.block().miningDeltaLogic();
            if (miningDeltaLogic == TOOL_REQUIRED) {
                return true;
            } else if (miningDeltaLogic == DEFAULT) {
                return false;
            }
        }
        return original.call(instance);
    }
}
