package org.exodevil.Conversations;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Prompt;

public class FirstPrompt extends FixedSetPrompt {
	  public FirstPrompt() {
		  super( "Polizei", "Abbruch" );
      }

      public String getPromptText(ConversationContext context) {
          return "Sie sind mit der Servicezentrale verbunden. Bitte wählen Sie ihre Weiterleitung. " + formatFixedSet();
      }

      @Override
      protected Prompt acceptValidatedInput(ConversationContext context, String s) {
          if (s.equalsIgnoreCase("Polizei")) {
        	  context.setSessionData("First", s);
              return new SecondPrompt();
          } else if (s.equalsIgnoreCase("Abbruch")) {
        	  context.setSessionData("First", s);
        	  return Prompt.END_OF_CONVERSATION;
          }
          context.getForWhom().sendRawMessage("Falsche Eingabe. Abbruch.");
          return Prompt.END_OF_CONVERSATION;
      }
}