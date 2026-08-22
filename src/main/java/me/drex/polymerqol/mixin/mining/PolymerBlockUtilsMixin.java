package me.drex.polymerqol.mixin.mining;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import me.drex.polymerqol.PolymerQOL;
import me.drex.polymerqol.networking.ClientConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PolymerBlockUtils.class)
public abstract class PolymerBlockUtilsMixin {
    @WrapOperation(
        method = "shouldMineServerSide",
        at = @At(value = "INVOKE", target = "Leu/pb4/polymer/core/api/block/PolymerBlock;handleMiningOnServer(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/server/level/ServerPlayer;)Z")
    )
    private static boolean clientSideMining(PolymerBlock instance, ItemStack tool, BlockState state, BlockPos pos, ServerPlayer player, Operation<Boolean> original) {
        ClientConfiguration clientConfiguration = PolymerQOL.getConfiguration(player);
        if (clientConfiguration.shouldMineClientSide(state)) {
            return false;
        }
        return original.call(instance, tool, state, pos, player);
    }
}
