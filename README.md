# Polymer - Quality of Life

A client + server mod, which provides several quality of life improvements to players playing on servers powered
by [PolyMc](https://github.com/TheEpicBlock/PolyMc) and [polymer](https://github.com/Patbox/polymer). The mod is
required to be installed on both the client and server to work!

## Improvements

### Client Side Mining

If `client_mining` is enabled in the server config PolyMCs and polymers server side mining mechanics will be disabled
for players with polymer-qol. The clients use polymers block syncing to figure out modded block breaking times to
calculate them on the client. This allows for smoother block breaking *(especially useful on high ping)*.

### Preventing Arm Swing

If `prevent_swing` is enabled in the server config, players with polymer-qol won't swing their arms when interacting
with fake noteblocks (which are often used for modded blocks).

### No Block Updates

When polymer-qol is installed on the client breaking and placing blocks won't cause block updates on the client, which
removes visually distracting incorrect blocks *(especially useful with high ping)*.

## Server Config

The server config is located at `config/polymer-qol-server.json` and allows server administrators to disable certain
features.

```json
{
  "_c0": "Allows polymer-qol clients to perform block mining client-side",
  "client_mining": true,
  "_c1": "Prevent polymer-qol clients from swinging their arm when interacting with fake noteblocks",
  "prevent_swing": true
}
```