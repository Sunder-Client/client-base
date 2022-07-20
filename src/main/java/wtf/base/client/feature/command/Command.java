package wtf.base.client.feature.command;

import wtf.base.client.feature.BaseFeature;

import java.util.List;

/**
 * A command, used to well... execute commands
 */
public abstract class Command extends BaseFeature {
    public static final int COMMAND_SUCCESS = 1;
    public static final int USAGE_FAILURE = 2;

    private final List<String> aliases;
    private final String usage;
    private final String description;

    public Command(List<String> aliases, String usage, String description) {
        super(aliases.get(0));

        this.aliases = aliases;
        this.usage = usage;
        this.description = description;
    }

    public abstract int execute(List<String> args);

    public List<String> getAliases() {
        return aliases;
    }

    public String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }
}
