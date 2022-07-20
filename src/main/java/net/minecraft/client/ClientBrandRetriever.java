package net.minecraft.client;

import wtf.base.client.Client;
import wtf.base.client.feature.module.Module;
import wtf.base.client.feature.module.miscellaneous.ClientSpoofer;

public class ClientBrandRetriever
{
    public static String getClientModName() {
        // Rewrote method by aesthetical

        Module module = Client.moduleManager.moduleMap.getOrDefault(ClientSpoofer.class, null);
        if (module == null) {
            return "vanilla";
        }

        return ((ClientSpoofer) module).mode.getValue().brandName;
    }
}
