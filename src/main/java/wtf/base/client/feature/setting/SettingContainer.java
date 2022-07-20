package wtf.base.client.feature.setting;

import wtf.base.client.feature.ToggleFeature;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A container holding settings for features
 *
 * Extends a toggleable feature because we assume if you want settings,
 * you'll want feature states
 */
public class SettingContainer extends ToggleFeature {
    public final Map<String, Setting> settingMap = new LinkedHashMap<>();
    public final List<Setting> settingsList = new LinkedList<>();

    public SettingContainer(String name) {
        super(name);
    }

    protected Setting<Boolean> boolSetting(String name, boolean value) {
        return setting(name, value);
    }

    protected Setting<Integer> intSetting(String name, int value, int min, int max) {
        return number(name, value, min, max);
    }

    protected Setting<Double> doubleSetting(String name, double value, double min, double max) {
        return number(name, value, min, max);
    }

    protected Setting<Float> floatSetting(String name, float value, float min, float max) {
        return number(name, value, min, max);
    }

    protected Bind bindSetting(String name, int bind) {
        Bind setting = new Bind(name, bind);

        settingMap.put(setting.getName(), setting);
        settingsList.add(setting);

        return setting;
    }

    protected <T extends Number> Setting<T> number(String name, T value, T min, T max) {
        Setting<T> setting = new Setting<>(name, value, min, max);

        settingMap.put(setting.getName(), setting);
        settingsList.add(setting);

        return setting;
    }

    protected <T> Setting<T> setting(String name, T value) {
        Setting<T> setting = new Setting<>(name, value);

        settingMap.put(setting.getName(), setting);
        settingsList.add(setting);

        return setting;
    }
}
