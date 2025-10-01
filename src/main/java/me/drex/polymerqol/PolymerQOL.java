package me.drex.polymerqol;

import me.drex.polymerqol.config.ConfigManager;
import me.drex.polymerqol.duck.IConnection;
import me.drex.polymerqol.mixin.ServerCommonPacketListenerImplAccessor;
import me.drex.polymerqol.networking.ClientConfiguration;
import me.drex.polymerqol.networking.OfferConfigurationC2SPayload;
import me.drex.polymerqol.networking.ApplyConfigurationS2CPayload;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolymerQOL implements ModInitializer {
    public static final String MOD_ID = "polymer-qol";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final int NETWORK_VERSION = 1;

    @Override
    public void onInitialize() {
        ConfigManager.load();
        PayloadTypeRegistry.configurationC2S()
            .register(OfferConfigurationC2SPayload.ID, OfferConfigurationC2SPayload.CODEC);
        PayloadTypeRegistry.configurationS2C()
            .register(ApplyConfigurationS2CPayload.ID, ApplyConfigurationS2CPayload.CODEC);
        ServerConfigurationNetworking.registerGlobalReceiver(OfferConfigurationC2SPayload.ID, (payload, context) -> {
            ClientConfiguration clientConfiguration = payload.configurationOffer();
            LOGGER.info("Received configuration from {}: {}", context.networkHandler().getOwner().name(), clientConfiguration);

            Connection connection = ((ServerCommonPacketListenerImplAccessor) context.networkHandler()).getConnection();
            ClientConfiguration modifiedClientConfiguration = clientConfiguration.applyServerConfiguration();
            ((IConnection) connection).polymer_qol$setConfiguration(modifiedClientConfiguration);

            LOGGER.info("Sending modified configuration to {}: {}", context.networkHandler().getOwner().name(), modifiedClientConfiguration);
            ServerConfigurationNetworking.send(context.networkHandler(), new ApplyConfigurationS2CPayload(modifiedClientConfiguration));
        });
    }

    public static ClientConfiguration getConfiguration(ServerPlayer player) {
        Connection connection = ((ServerCommonPacketListenerImplAccessor) player.connection).getConnection();
        return ((IConnection) connection).polymer_qol$configuration();
    }
}
