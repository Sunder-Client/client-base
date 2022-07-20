package wtf.base.client.feature;

import wtf.base.client.util.common.ClientImpl;

public class BaseFeature implements ClientImpl {
    protected final String name;

    public BaseFeature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
