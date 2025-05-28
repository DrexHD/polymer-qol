package me.drex.polymerqol.mixin.mining;

import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import me.drex.polymerqol.PolymerQOL;
import me.drex.polymerqol.networking.ClientConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PolymerBlockUtils.class)
public abstract class PolymerBlockUtilsMixin {
    @Inject(
        method = "shouldMineServerSide",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void clientSideMining(ServerPlayer player, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        ClientConfiguration clientConfiguration = PolymerQOL.getConfiguration(player);
        if (clientConfiguration.shouldMineClientSide(state)) {
            cir.setReturnValue(false);
        }
    }
}
