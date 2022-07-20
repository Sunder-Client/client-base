package wtf.base.client.feature;

import wtf.base.client.Client;

public class ToggleFeature extends BaseFeature {
    protected boolean toggled = false;

    public ToggleFeature(String name) {
        super(name);
    }

    protected void onActivated() {

    }

    protected void onDeactivated() {

    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;

        if (toggled) {
            onActivated();
            Client.BUS.subscribe(this);
        } else {
            Client.BUS.unsubscribe(this);
            onDeactivated();
        }
    }

    public boolean isToggled() {
        return toggled;
    }

    /**
     * If the module is currently preforming an action.
     * Meant to be overrode
     * @return
     */
    public boolean isActive() {
        return isToggled();
    }
}
