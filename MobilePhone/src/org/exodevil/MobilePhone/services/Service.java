package org.exodevil.MobilePhone.services;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.entity.Player;
import org.exodevil.MobilePhone.MobilePhone;

public class Service implements ConversationAbandonedListener {

	private static ConversationFactory conversationFactory;
	
	public Service () {
		this.conversationFactory = new ConversationFactory(MobilePhone.getInstance())
		.withModality(true)
		.withPrefix(new ServicePrefix())
		.withFirstPrompt(new WhichServicePrompt())
		.withEscapeSequence("/service Abbruch")
		.thatExcludesNonPlayersWithMessage("Go away evil console!")
		.addConversationAbandonedListener(this);
	}

	public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent) {
		if (abandonedEvent.gracefulExit()) {
			abandonedEvent.getContext().getForWhom().sendRawMessage("Anruf beendet");
		} else {
			abandonedEvent.getContext().getForWhom().sendRawMessage("Conversation cancelled by: " + abandonedEvent.getCanceller().getClass().getName());
		}
	}

	private class ServicePrefix implements ConversationPrefix {

        public String getPrefix(ConversationContext context) {
            String service = (String)context.getSessionData("serviceType");
            String reason = (String)context.getSessionData("count");
            
            if (service != null && reason == null) {
                return ChatColor.GREEN + "[Service] " + service + ": " + ChatColor.WHITE;
            }
            if (service != null && reason != null) {
                return ChatColor.GREEN + "[Service] " + service + " " + reason + ": " + "/n" + ChatColor.WHITE;
            }
            return ChatColor.GREEN + "[Service] " + ChatColor.WHITE;
        }
    }

	public static void start(CommandSender cmds) {
		System.out.println("Mobile " + cmds);
		if( cmds == null ) return;
		if( !(cmds instanceof Player) ) return;
		Player p = (Player) cmds;
		conversationFactory.buildConversation(p).begin();
	}

	
	
}
