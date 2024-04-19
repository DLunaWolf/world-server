package com.rs.game.content.quests.elderkiln

import com.rs.engine.quest.Quest
import com.rs.engine.quest.QuestHandler
import com.rs.engine.quest.QuestOutline
import com.rs.game.model.entity.player.Player
import com.rs.game.model.entity.player.Skills
import com.rs.lib.game.Item
import com.rs.plugin.annotations.ServerStartupEvent

const val STAGE_UNSTARTED = 0
const val STAGE_HATCH_EGG = 1
const val STAGE_GAAL_TO_LIBRARY = 2
const val STAGE_COMPLETE = 45

@QuestHandler(
    quest = Quest.ELDER_KILN,
    startText = "Speak to TzHaar-Mej-Jeh by the Birthing Pools in the TzHaar City.",
    itemsText = "Adamant pickaxe (or better), melee, magic, or ranged armor, weapons, potins, and food.",
    combatText = "Several waves of Tok-Haar creatures and a few high level players.",
    rewardsText =
            "100,000 XP lamp (for either Attack<br>" +
            "Strength, Defence, Magic, or Ranged)<br>" +
            "50,000 Magic XP<br>" +
            "30,000 Agility XP<br>" +
            "Tokkul-Zo (boosts 10% damage when<br>" +
            "fighting against lava or obsidian creatures)<br>" +
            "Access to the Fight Kiln",
    completedStage = STAGE_COMPLETE
)
class ElderKiln : QuestOutline() {
    override fun getJournalLines(player: Player, stage: Int) = when (stage) {
        STAGE_UNSTARTED -> listOf("I should speak to TzHaar-Mej-Jeh by the Birthing Pools in the TzHaar City.")
        STAGE_HATCH_EGG -> listOf("I need to help the TzHaar-Mej hatch their egg by regulating the temperature.")
        STAGE_COMPLETE -> listOf("QUEST COMPLETE!")
        else -> listOf("Invalid quest stage. Report this to an administrator.")
    }

    override fun complete(player: Player) {
        player.skills.addXpQuest(Skills.MAGIC, 50000.0)
        player.skills.addXpQuest(Skills.AGILITY, 30000.0)
        player.inventory.addItemDrop(Item(23643, 1).addMetaData("tzhaarCharges", 4000))
        player.inventory.addItemDrop(23645, 1)
        sendQuestCompleteInterface(player, 23643)
    }

    override fun updateStage(player: Player, stage: Int) {
        when(stage) {
            STAGE_GAAL_TO_LIBRARY -> {
                player.vars.setVarBit(10809, 25)
                player.vars.setVarBit(10833, 2)
            }

        }
    }
}

@ServerStartupEvent
fun mapElderKiln() {

}