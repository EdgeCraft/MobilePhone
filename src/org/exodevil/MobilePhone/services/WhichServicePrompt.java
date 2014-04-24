package org.exodevil.MobilePhone.services;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.FixedSetPrompt;
import org.bukkit.conversations.Prompt;
import org.exodevil.MobilePhone.services.Emergency.Emergency;
import org.exodevil.MobilePhone.services.FireDepartment.FireDepartment;
import org.exodevil.MobilePhone.services.Hitman.Hitman;
import org.exodevil.MobilePhone.services.NewsAgent.NewsAgent;
import org.exodevil.MobilePhone.services.Police.Police;

public class WhichServicePrompt extends FixedSetPrompt {
	  public WhichServicePrompt() {
		  super( "Polizei", "Feuerwehr", "Notruf", "Auftragskiller", "Newsreporter", "Abbruch" );
      }

      public String getPromptText(ConversationContext context) {
          return "Sie sind mit der Servicezentrale verbunden. Bitte wählen Sie ihre Weiterleitung. " + formatFixedSet();
      }

      @Override
      protected Prompt acceptValidatedInput(ConversationContext context, String s) {
          if (s.equals("Polizei")) {
        	  context.setSessionData("serviceType", s);
              return new Police();
          } else if (s.equals("Feuerwehr")) {
        	  context.setSessionData("serviceType", s);
              return new FireDepartment();
          } else if (s.equals("Notruf")) {
        	  context.setSessionData("serviceType", s);
              return new Emergency();
          } else if (s.equals("Auftragskiller")) {
        	  context.setSessionData("serviceType", s);
              return new Hitman();
          } else if (s.equals("Newsreporter")) {
        	  context.setSessionData("serviceType", s);
              return new NewsAgent();
          } else if (s.equals("Abbruch")) {
        	  return Prompt.END_OF_CONVERSATION;
          }
          context.getForWhom().sendRawMessage("Falsche Eingabe. Abbruch.");
          return Prompt.END_OF_CONVERSATION;
      }
}
