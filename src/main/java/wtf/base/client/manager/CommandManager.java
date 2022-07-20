package wtf.base.client.manager;

import me.bush.eventbus.annotation.EventListener;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.EnumChatFormatting;
import wtf.base.client.Client;
import wtf.base.client.event.network.PacketEvent;
import wtf.base.client.feature.command.Command;
import wtf.base.client.feature.command.impl.VClip;
import wtf.base.client.util.common.ClientImpl;

import java.util.*;

/**
 * Manages command execution
 *
 * Default prefix is .
 */
public class CommandManager implements ClientImpl {
    public static String prefix = ".";

    public final Map<String, Command> commandMap = new HashMap<>();
    public final List<Command> commands = new ArrayList<>();

    public CommandManager() {
        // subscribe to the event bus
        Client.BUS.subscribe(this);

        add(new VClip());
    }

    private void add(Command command) {
        command.getAliases().forEach((alias) -> commandMap.put(alias.toLowerCase(), command));
        commands.add(command);
    }

    @EventListener
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof C01PacketChatMessage) {
            C01PacketChatMessage packet = event.getPacket();
            String message = packet.getMessage();

            if (message.startsWith(prefix)) {
                event.setCancelled(true);

                List<String> args = Arrays.asList(message
                        .substring(prefix.length())
                        .trim()
                        .split(" "));

                if (args.isEmpty()) {
                    printToChat(hashCode(), "No command name provided.");
                    return;
                }

                Command command = commandMap.get(args.get(0).toLowerCase());
                if (command == null) {
                    printToChat(hashCode(), "Invalid command name provided.");
                    return;
                }

                try {
                    int result = command.execute(args.subList(1, args.size()));
                    if (result == Command.USAGE_FAILURE) {
                        printToChat(hashCode(),
                                "Usage: " + prefix +
                                        EnumChatFormatting.RED +
                                        "[" + String.join("|", command.getAliases()) + "] " +
                                        EnumChatFormatting.RESET + command.getUsage());
                    }
                } catch (Exception exception) {
                    printToChat(hashCode(), "An exception occurred whilst parsing and executing this command.");
                    exception.printStackTrace();
                }
            }
        }
    }
}
