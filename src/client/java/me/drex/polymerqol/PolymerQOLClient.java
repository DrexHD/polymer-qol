package me.drex.polymerqol;

import eu.pb4.polymer.core.api.client.ClientPolymerBlock;
import me.drex.polymerqol.networking.ClientConfiguration;
import me.drex.polymerqol.networking.OfferConfigurationC2SPayload;
import me.drex.polymerqol.networking.ApplyConfigurationS2CPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;

public class PolymerQOLClient implements ClientModInitializer {
    public static final ThreadLocal<ClientPolymerBlock.State> POLYMER_BLOCK_STATE = new ThreadLocal<>();
    public static ClientConfiguration config = ClientConfiguration.DEFAULT;

    @Override
    public void onInitializeClient() {
        ClientConfigurationConnectionEvents.START.register((handler, server) -> {
            if (ClientConfigurationNetworking.canSend(OfferConfigurationC2SPayload.ID)) {
                PolymerQOL.LOGGER.info("Sending client qol configuration");
                ClientConfigurationNetworking.send(new OfferConfigurationC2SPayload(new ClientConfiguration(PolymerQOL.NETWORK_VERSION, true, true)));
            } else {
                PolymerQOL.LOGGER.info("Server doesn't understand qol configuration");
            }
        });
        ClientConfigurationNetworking.registerGlobalReceiver(ApplyConfigurationS2CPayload.ID, (payload, context) -> {
            config = payload.clientConfiguration();
        });
    }
}