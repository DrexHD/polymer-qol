package me.drex.polymerqol.mixin;

import me.drex.polymerqol.duck.IConnection;
import me.drex.polymerqol.networking.ClientConfiguration;
import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Connection.class)
public abstract class ConnectionMixin implements IConnection {
    @Unique
    private ClientConfiguration polymer_qol$ClientConfiguration = ClientConfiguration.DEFAULT;

    @Override
    public void polymer_qol$setConfiguration(ClientConfiguration clientConfiguration) {
        this.polymer_qol$ClientConfiguration = clientConfiguration;
    }

    @Override
    public ClientConfiguration polymer_qol$configuration() {
        return this.polymer_qol$ClientConfiguration;
    }
}
