package me.drex.polymerqol.mixin.client.swing;

import eu.pb4.polymer.core.api.client.ClientPolymerBlock;
import eu.pb4.polymer.core.api.client.PolymerClientUtils;
import me.drex.polymerqol.PolymerQOLClient;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.drex.polymerqol.PolymerQOLClient.config;

@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin {
    @Inject(
        method = "useWithoutItem",
        at = @At("HEAD"),
        cancellable = true
    )
    public void useWithoutItem(
        BlockState blockState, Level level, BlockPos blockPos, Player player,
        BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir
    ) {
        ClientPolymerBlock.State state = PolymerClientUtils.getPolymerStateAt(blockPos);
        if (state != ClientPolymerBlock.NONE_STATE && config.preventSwing()) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }
}
