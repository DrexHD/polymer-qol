package me.drex.polymerqol.mixin.client.mining;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import eu.pb4.polymer.core.api.client.ClientPolymerBlock;
import eu.pb4.polymer.core.api.client.ClientPolymerEntry;
import eu.pb4.polymer.core.impl.client.InternalClientRegistry;
import eu.pb4.polymer.core.impl.other.ImplPolymerRegistry;
import me.drex.polymerqol.PolymerQOLClient;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;
import java.util.Set;

@Mixin(Tool.class)
public abstract class ToolMixin {
    @SuppressWarnings("UnstableApiUsage")
    @WrapOperation(
        method = {"getMiningSpeed", "isCorrectForDrops"},
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/core/HolderSet;)Z"
        )
    )
    public boolean clientSideBlockTags(BlockState instance, HolderSet<Block> blocks, Operation<Boolean> original) {
        Optional<TagKey<Block>> optionalTagKey = blocks.unwrapKey();
        ClientPolymerBlock.State clientState = PolymerQOLClient.POLYMER_BLOCK_STATE.get();
        if (optionalTagKey.isPresent() && clientState != null) {
            TagKey<Block> tagKey = optionalTagKey.get();

            ImplPolymerRegistry<ClientPolymerEntry<?>> clientBlockRegistry = InternalClientRegistry.BY_VANILLA_ID.get(Registries.BLOCK.identifier());
            Set<ClientPolymerEntry<?>> clientTag = clientBlockRegistry.getTag(tagKey.location());
            return clientTag.contains(clientState.block());
        }
        return original.call(instance, blocks);
    }
}
