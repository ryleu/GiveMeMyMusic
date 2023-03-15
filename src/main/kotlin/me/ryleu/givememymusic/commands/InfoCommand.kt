package me.ryleu.givememymusic.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.Commands
import java.awt.Color

object InfoCommand : BotCommand {
    override val command = Commands.slash("info", "Get info about GiveMeMyMusic")
    override fun invoke(event: SlashCommandInteractionEvent) {
        event
            .reply("")
            .addEmbeds(
                EmbedBuilder()
                    .setTitle("GiveMeMyMusic")
                    .setColor(Color.RED)
                    .setDescription("Howdy! I'm GiveMeMyMusic.")
                    .addField("I am...", "...one of the last bastions of free music on Discord.", false)
                    .addField("I can...", "...play songs from a variety of sources, including YouTube.", false)
                    .addField("You should...", "...type `/help` to get started!", false)
                    .addField("Extra", "**»** I was written by [ryleu](https://github.com/ryleu).\n" +
                            "**»** I was written in [Kotlin](https://kotlinlang.org) " +
                            "with [JDA](https://jda.wiki) " +
                            "and [LavaPlayer](https://github.com/Walkyst/lavaplayer-fork).\n" +
                            "**»** I was inspired by [Jagrosh's music bot](https://github.com/jagrosh/MusicBot).\n" +
                            "**»** My source code can be found [here](https://github.com/ryleu/GiveMeMyMusic).",
                        true
                    )
                    .build()
            )
            .queue()
    }
}