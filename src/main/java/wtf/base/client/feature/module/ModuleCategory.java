package wtf.base.client.feature.module;

/**
 * An enum that holds all the categories a module can be put into
 *
 * A module can only have one category.
 */
public enum ModuleCategory {
    COMBAT("Combat"),
    MISCELLANEOUS("Miscellaneous"),
    MOVEMENT("Movement"),
    VISUALS("Visuals"),
    WORLD("World");

    public final String displayName;

    ModuleCategory(String displayName) {
        this.displayName = displayName;
    }
}
