package me.drex.polymerqol.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import static me.drex.polymerqol.PolymerQOL.MOD_ID;

public record ApplyConfigurationS2CPayload(ClientConfiguration clientConfiguration) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, ApplyConfigurationS2CPayload> CODEC = CustomPacketPayload.codec(ApplyConfigurationS2CPayload::write, ApplyConfigurationS2CPayload::new);
    public static final ResourceLocation PACKET_ID = ResourceLocation.fromNamespaceAndPath(MOD_ID, "apply_configuration");
    public static final Type<ApplyConfigurationS2CPayload> ID = new Type<>(PACKET_ID);

    private ApplyConfigurationS2CPayload(FriendlyByteBuf friendlyByteBuf) {
        this(ClientConfiguration.of(friendlyByteBuf));
    }

    private void write(FriendlyByteBuf friendlyByteBuf) {
        clientConfiguration.write(friendlyByteBuf);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
