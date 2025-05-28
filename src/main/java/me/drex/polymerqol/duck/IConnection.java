package me.drex.polymerqol.duck;

import me.drex.polymerqol.networking.ClientConfiguration;

public interface IConnection {
    void polymer_qol$setConfiguration(ClientConfiguration clientConfiguration);
    ClientConfiguration polymer_qol$configuration();
}
