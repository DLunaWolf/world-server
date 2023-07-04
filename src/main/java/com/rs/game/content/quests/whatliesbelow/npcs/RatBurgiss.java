package com.rs.game.content.quests.whatliesbelow.npcs;

import com.rs.engine.dialogue.Conversation;
import com.rs.engine.dialogue.Dialogue;
import com.rs.engine.dialogue.HeadE;
import com.rs.engine.quest.Quest;
import com.rs.game.content.achievements.AchievementSystemDialogue;
import com.rs.game.content.achievements.SetReward;
import com.rs.game.model.entity.player.Player;
import com.rs.plugin.annotations.PluginEventHandler;
import com.rs.plugin.handlers.NPCClickHandler;

@PluginEventHandler
public class RatBurgiss extends Conversation {
    private static final int ID = 5833;

    public static NPCClickHandler handleRatBurgiss = new NPCClickHandler(new Object[]{ID}, e -> e.getPlayer().startConversation(new RatBurgiss(e.getPlayer())));

    public RatBurgiss(Player player) {
        super(player);
        addOptions(ops -> {
            ops.add("About the Achievement System...", new AchievementSystemDialogue(player, ID, SetReward.VARROCK_ARMOR).getStart());

            if (!player.isQuestComplete(Quest.WHAT_LIES_BELOW) && Quest.WHAT_LIES_BELOW.meetsReqs(player)) {
                ops.add("Hello there!")
                        .addPlayer(HeadE.CHEERFUL, "Hello there!")
                        .addNext(getWhatLiesBelowDialogue(player).getHead());
            }
        });
        create();
    }

    private Dialogue getWhatLiesBelowDialogue(Player player) {
        return switch (player.getQuestStage(Quest.WHAT_LIES_BELOW)) {
            case 0 -> {
                Dialogue startQuestChain = new Dialogue()
                        .addPlayer(HeadE.CHEERFUL, "Of course! Tell me what you need me to do.")
                        .addNPC(ID, HeadE.CHEERFUL, "Right, now I heard those outlaws say something about having a small campsite somewhere to the west of the Grand Exchange. They headed off to the north-west of here, taking five pages with them.")
                        .addNPC(ID, HeadE.CHEERFUL, "Kill the outlaws and get those papers back from them for me. Here's a folder in which you can put the pages. Be careful, though; those outlaws are tough.", () -> {
                            player.getInventory().addItem(11003);
                            player.setQuestStage(Quest.WHAT_LIES_BELOW, 1);
                        })
                        .addNPC(ID, HeadE.CHEERFUL, "When you find all 5 pages, put them in the folder and bring them back to me!")
                        .addPlayer(HeadE.CHEERFUL, "Don't worry, Ratty! I won't let you down!")
                        .addNPC(ID, HeadE.SAD_MILD, "...")
                        .getHead();

                yield new Dialogue().addNPC(ID, HeadE.SAD_MILD, "Oh, hello. I'm Rat.")
                        .addPlayer(HeadE.CONFUSED, "You're a what?")
                        .addNPC(ID, HeadE.SAD_MILD, "No, no. My name is Rat. Rat Burgiss.")
                        .addPlayer(HeadE.CHEERFUL, "Ohhhhh, well, what's up, Ratty?")
                        .addNPC(ID, HeadE.SAD_MILD, "It's Rat, thank you. And, I uhh...heh...I seem to be in a bit of trouble here, as you can probably see.")
                        .addPlayer(HeadE.CONFUSED, "Why, what seems to be the matter?")
                        .addNPC(ID, HeadE.SKEPTICAL, "Well, I'm a trader by nature and I was on the way to Varrock with my cart here when I was set upon by outlaws! They ransacked my cart and stole some very important papers that I must get back.")
                        .addOptions(ops1 -> {
                            ops1.add("That's awful! You're lucky to be alive!")
                                    .addPlayer(HeadE.AMAZED, "That's awful! You're lucky to be alive!")
                                    .addNPC(ID, HeadE.SAD_MILD, "Yes, I know! I don't know how I survived.")
                                    .addPlayer(HeadE.CALM_TALK, "Maybe you weren't worth killing?")
                                    .addNPC(ID, HeadE.ANGRY, "...")
                                    .addNPC(ID, HeadE.ANGRY, "Look, do you want to help me or not?")
                                    .addQuestStart(Quest.WHAT_LIES_BELOW)
                                    .addNext(startQuestChain);

                            ops1.add("The papers were the only valuable thing you had?")
                                    .addPlayer(HeadE.CONFUSED, "The papers were the only valuable thing you had?")
                                    .addNPC(ID, HeadE.CONFUSED, "Uh...yes. I guess so.")
                                    .addPlayer(HeadE.CONFUSED, "So you don't sell anything worth having?")
                                    .addNPC(ID, HeadE.CONFUSED, "Ah! Uhh...no, not really.")
                                    .addPlayer(HeadE.CONFUSED, "You're not much of a trader are you?")
                                    .addNPC(ID, HeadE.ANGRY, "No...I mean yes... Look, that's beside the point.")
                                    .addNPC(ID, HeadE.SAD_MILD, "Can you help me?")
                                    .addQuestStart(Quest.WHAT_LIES_BELOW)
                                    .addNext(startQuestChain);

                            ops1.add("Shall I get them back for you?")
                                    .addPlayer(HeadE.CONFUSED, "Shall I get them back for you?")
                                    .addNPC(ID, HeadE.CONFUSED, "You mean you want to help?")
                                    .addQuestStart(Quest.WHAT_LIES_BELOW)
                                    .addNext(startQuestChain);

                            ops1.add("Oh dear. Well, I hope you get to Varrock okay!")
                                    .addPlayer(HeadE.CALM_TALK, "Oh dear. Well, I hope you get to Varrock okay!")
                                    .addNPC(ID, HeadE.CONFUSED, "Uh...so you can't help me?")
                                    .addPlayer(HeadE.CALM_TALK, "Sorry. I'm a bit busy right now.")
                                    .addNPC(ID, HeadE.CONFUSED, "Don't you want another quest?")
                                    .addPlayer(HeadE.CALM_TALK, "No thanks. I'm too busy right now.")
                                    .addNPC(ID, HeadE.ANGRY, "Well, fine!")
                                    .addPlayer(HeadE.ANGRY, "Fine!")
                                    .addNPC(ID, HeadE.ANGRY, "Good!")
                                    .addPlayer(HeadE.ANGRY, "Good!!")
                                    .addNPC(ID, HeadE.ANGRY, "So go then!")
                                    .addPlayer(HeadE.ANGRY, "I'm going!")
                                    .addNPC(ID, HeadE.ANGRY, "Go then!")
                                    .addPlayer(HeadE.ANGRY, "I'm gone!");
                        });
            }

            case 1 -> {
                if (player.getInventory().containsItem(11007)) {
                    yield new Dialogue()
                            .addNPC(ID, HeadE.CHEERFUL, "Ah, hello again.")
                            .addPlayer(HeadE.CHEERFUL, "I got your pages back!")
                            .addNPC(ID, HeadE.CHEERFUL, "Excellent! I knew you could help! Let me take those from you, there.")
                            .addNPC(ID, HeadE.CHEERFUL, "Now, I liked the way you handled yourself on that last little 'mission' I gave you there, so I'm going to let you in on a little secret!")
                            .addPlayer(HeadE.CHEERFUL, "Wait! Wait! Let me guess! You're actually a rich prince in disguise who wants to help poor people like me!?")
                            .addNPC(ID, HeadE.CONFUSED, "Uhhh...no. No, that's not it. You know, on second thought, I think I'll keep my secret for now. Look, instead, you can do another job for me.")
                            .addPlayer(HeadE.SAD_MILD, "All work and no play makes " + player.getDisplayName() + " a dull adventurer!")
                            .addNPC(ID, HeadE.CALM_TALK, "Yes, well, I'm sure that may be the case. However, what I want you to do is take this letter to someone for me. It's in a different language so, trust me, you won't be able to read it.")
                            .addPlayer(HeadE.SAD_MILD, "All work and no play makes " + player.getDisplayName() + " a dull adventurer!")
                            .addNPC(ID, HeadE.CALM_TALK, "Yes, well, I'm sure that may be the case. However, what I want you to do is take this letter to someone for me. It's in a different language so, trust me, you won't be able to read it.")
                            .addNPC(ID, HeadE.CALM_TALK, "Take it to a wizard named Surok Magic who resides in the Varrock Palace Library. I'll see about some sort of reward for your work when I get myself sorted out here.")
                            .addPlayer(HeadE.CHEERFUL, "Letter. Wizard. Varrock. Got it!", () -> {
                                player.getInventory().deleteItem(11007, 1);
                                player.getInventory().addItemDrop(11009, 1);
                                player.setQuestStage(Quest.WHAT_LIES_BELOW, 2);
                            })
                            .addNPC(ID, HeadE.CHEERFUL, "Yes, good luck then.");
                } else
                    yield new Dialogue()
                            .addNPC(ID, HeadE.CHEERFUL, "Hello again! How are things going?")
                            .addPlayer(HeadE.CONFUSED, "I can't remember what I am supposed to do!")
                            .addNPC(ID, HeadE.CHEERFUL, "Head north-west from here into the forest west of the Grand Exchange. Find the camp of outlaws and kill them.")
                            .addNPC(ID, HeadE.CHEERFUL, "Collect the pages of my document for me and put them into the folder I gave you. When the folder contains five pages, bring it to me!")
                            .addNextIf(() -> !player.containsAnyItems(11003, 11006, 11007), new Dialogue()
                                    .addPlayer(HeadE.SAD, "I lost the folder you gave me. Do you have another one?")
                                    .addNPC(ID, HeadE.CHEERFUL, "Sure. Here you go. I'll add it to your account.", () -> player.getInventory().addItem(11003))
                                    .addPlayer(HeadE.AMAZED, "My account? Am I in debt?")
                                    .addNPC(ID, HeadE.CHEERFUL, "No, it's just the way you're standing."))
                            .addPlayer(HeadE.CHEERFUL, "Okay, thanks!");
            }

            case 2 -> new Dialogue()
                    .addNPC(ID, HeadE.CHEERFUL, "Ah, hello. How is your task going?")
                    .addNextIf(() -> !player.containsItem(11009), new Dialogue()
                            .addPlayer(HeadE.SAD, "I think I lost that letter you gave me!")
                            .addNPC(ID, HeadE.CHEERFUL, "Goodness me! Not much of a messenger, are you? Here's another one; try not to lose it this time! I've charged the parchment to your account.", () -> player.getInventory().addItem(11009))
                            .addPlayer(HeadE.CONFUSED, "Will you take a check?")
                            .addNPC(ID, HeadE.CHEERFUL, "No thanks. I prefer tartan."))
                    .addPlayer(HeadE.CONFUSED, "What am I doing again?")
                    .addNPC(ID, HeadE.CALM_TALK, "Take that letter I gave you to Surok Magis, the wizard found in the Varrock Palace Library.")
                    .addPlayer(HeadE.CHEERFUL, "Oh yes! It's all flooding back now!")
                    .addNPC(ID, HeadE.CALM_TALK, "I see. Well, try not to drown yourself with all that brain usage.");

            case 3, 4 -> new Dialogue()
                    .addNPC(ID, HeadE.CHEERFUL, "Ah, " + player.getDisplayName() + "! Good to see you! I heard you got my letter to Surok. Well done!")
                    .addPlayer(HeadE.CHEERFUL, "That's right! I did your leg work. Now, how about a reward?")
                    .addNPC(ID, HeadE.CHEERFUL, "Yes, of course! I'd be happy to give you a reward, but I have a lot on my mind with...you know...trader...stuff!")
                    .addPlayer(HeadE.CONFUSED, "Indeed!");

            case 5 -> new Dialogue();

            default ->
                    throw new IllegalStateException("Unexpected value: " + player.getQuestStage(Quest.WHAT_LIES_BELOW));
        };
    }
}
