package wtf.base.client.util.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

/**
 * An interface with all the common client implementations
 *
 * You should put things in here to make them easier to access
 */
public interface ClientImpl {

    // the minecraft instance - you'll be using this a lot...
    Minecraft mc = Minecraft.getMinecraft();

    // the client chat prefix. obviously you should change the name in here...
    String CHAT_PREFIX = EnumChatFormatting.RED + "[ClientBase] " + EnumChatFormatting.RESET;

    /**
     * Prints a message to the game chat
     * @param message the message
     */
    default void printToChat(String message) {
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(CHAT_PREFIX + message));
    }

    /**
     * Prints an editable message to the game chat based off the ID
     * @param id the chat message ID
     * @param message the message
     */
    default void printToChat(int id, String message) {
        mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(
                new ChatComponentText(CHAT_PREFIX + message), id);
    }

    /**
     * A simple method to make sure modules that interact with the local player
     * or local world will not throw a NullPointerException. If this returns true,
     * you should **NOT** interact with the local player or the world
     *
     * @return if the local player is null, or the local world is null
     */
    default boolean nullCheck() {
        return mc.thePlayer == null || mc.theWorld == null;
    }

    /**
     * @return the local player
     */
    default EntityPlayerSP player() {
        return mc.thePlayer;
    }

    /**
     * @return the local world
     */
    default WorldClient world() {
        return mc.theWorld;
    }
}
