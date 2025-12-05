package me.drex.polymerqol.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import static me.drex.polymerqol.PolymerQOL.MOD_ID;

public record OfferConfigurationC2SPayload(ClientConfiguration configurationOffer) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, OfferConfigurationC2SPayload> CODEC = CustomPacketPayload.codec(OfferConfigurationC2SPayload::write, OfferConfigurationC2SPayload::new);
    public static final Identifier PACKET_ID = Identifier.fromNamespaceAndPath(MOD_ID, "offer_configuration");
    public static final Type<OfferConfigurationC2SPayload> ID = new Type<>(PACKET_ID);

    private OfferConfigurationC2SPayload(FriendlyByteBuf friendlyByteBuf) {
        this(ClientConfiguration.of(friendlyByteBuf));
    }

    private void write(FriendlyByteBuf friendlyByteBuf) {
        configurationOffer.write(friendlyByteBuf);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
