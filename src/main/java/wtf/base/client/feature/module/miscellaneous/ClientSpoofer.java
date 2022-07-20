package wtf.base.client.feature.module.miscellaneous;

import wtf.base.client.feature.module.Module;
import wtf.base.client.feature.module.ModuleCategory;
import wtf.base.client.feature.setting.Setting;

// See ClientBrandRetriever#getClientModName
public class ClientSpoofer extends Module {
    public ClientSpoofer() {
        super("Client Spoof", ModuleCategory.MISCELLANEOUS);
    }

    public final Setting<Mode> mode = setting("Mode", Mode.VANILLA);

    @Override
    protected void onActivated() {
        super.onActivated();

        // the client brand is sent in a CPacketCustomPayload upon joining, so we must relog for the brand to be changed
        printToChat("Re-log for this module to take effect!");
    }

    public enum Mode {
        VANILLA("vanilla"),
        FML("fml,forge"),
        LUNAR("Lunar-Client");

        public final String brandName;

        Mode(String brandName) {
            this.brandName = brandName;
        }
    }
}
