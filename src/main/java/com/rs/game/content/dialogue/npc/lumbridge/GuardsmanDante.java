package com.rs.game.content.dialogue.npc.lumbridge;

import com.rs.game.content.dialogue.Conversation;
import com.rs.game.content.dialogue.HeadE;
import com.rs.game.model.entity.player.Player;
import com.rs.plugin.annotations.PluginEventHandler;
import com.rs.plugin.events.NPCClickEvent;
import com.rs.plugin.handlers.NPCClickHandler;

@PluginEventHandler
public class GuardsmanDante extends Conversation {

    //Identify NPC by ID
    private static int npcId = 7885;

    public static NPCClickHandler GuardsmanDante = new NPCClickHandler(new Object[]{npcId}) {
        @Override
        //Handle Right-Click
        public void handle(NPCClickEvent e) {
            switch (e.getOption()) {
                //Start Conversation
                case "Talk-to" -> e.getPlayer().startConversation(new GuardsmanDante(e.getPlayer()));
            }
        }
    };

    public GuardsmanDante(Player player) {
        super(player);
        //TODO replace placeholder conversation
        addNPC(npcId, HeadE.FRUSTRATED, "Not right now, I'm Busy!");
        create();
    }
}
