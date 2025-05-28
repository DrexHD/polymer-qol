package me.drex.polymerqol.networking;

import eu.pb4.polymer.core.impl.networking.entry.PolymerBlockEntry;
import me.drex.polymerqol.PolymerQOL;
import me.drex.polymerqol.config.ConfigManager;
import me.drex.polymerqol.config.ServerConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.state.BlockState;

public record ClientConfiguration(int version, boolean clientMining, boolean preventSwing) {
    public static final ClientConfiguration DEFAULT = new ClientConfiguration(PolymerQOL.NETWORK_VERSION, false, false);

    public static ClientConfiguration of(FriendlyByteBuf friendlyByteBuf) {
        int version = friendlyByteBuf.readInt();
        boolean clientMining = friendlyByteBuf.readBoolean();
        boolean noSwing = friendlyByteBuf.readBoolean();
        return new ClientConfiguration(version, clientMining, noSwing);
    }

    void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(version);
        friendlyByteBuf.writeBoolean(clientMining);
        friendlyByteBuf.writeBoolean(preventSwing);
    }

    public ClientConfiguration applyServerConfiguration() {
        ServerConfig serverConfig = ConfigManager.serverConfig;
        return new ClientConfiguration(
            PolymerQOL.NETWORK_VERSION,
            clientMining && serverConfig.clientMining,
            preventSwing && serverConfig.preventSwing
        );
    }

    @SuppressWarnings("UnstableApiUsage")
    public boolean shouldMineClientSide(BlockState state) {
        PolymerBlockEntry blockEntry = PolymerBlockEntry.of(state.getBlock());
        PolymerBlockEntry.MiningDeltaLogic miningDeltaLogic = blockEntry.miningDeltaLogic();
        return miningDeltaLogic != PolymerBlockEntry.MiningDeltaLogic.CUSTOM_SERVER && clientMining;
    }
}
