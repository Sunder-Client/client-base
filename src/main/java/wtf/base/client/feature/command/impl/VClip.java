package wtf.base.client.feature.command.impl;

import net.minecraft.util.EnumChatFormatting;
import wtf.base.client.feature.command.Command;

import java.util.Arrays;
import java.util.List;

public class VClip extends Command {
    public VClip() {
        super(Arrays.asList("vclip", "clipv"), "[blocks]", "Vertically clips you");
    }

    @Override
    public int execute(List<String> args) {
        if (args.isEmpty()) {
            return Command.USAGE_FAILURE;
        }

        double blocks;
        try {
            blocks = Double.parseDouble(args.get(0));
        } catch (NumberFormatException ignored) {
            return Command.USAGE_FAILURE;
        }

        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + blocks, mc.thePlayer.posZ);

        printToChat("Clipped " + EnumChatFormatting.RED + blocks + EnumChatFormatting.RESET + " blocks.");

        return Command.COMMAND_SUCCESS;
    }
}
