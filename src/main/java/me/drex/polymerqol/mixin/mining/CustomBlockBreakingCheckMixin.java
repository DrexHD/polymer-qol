package me.drex.polymerqol.mixin.mining;

import io.github.theepicblock.polymc.impl.mixin.CustomBlockBreakingCheck;
import me.drex.polymerqol.PolymerQOL;
import me.drex.polymerqol.networking.ClientConfiguration;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CustomBlockBreakingCheck.class)
public abstract class CustomBlockBreakingCheckMixin {
    @Inject(
        method = "needsCustomBreaking(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/level/block/state/BlockState;)Z",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void clientSideMining(ServerPlayer player, BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
        ClientConfiguration clientConfiguration = PolymerQOL.getConfiguration(player);
        if (clientConfiguration.shouldMineClientSide(blockState)) {
            cir.setReturnValue(false);
        }
    }
}
