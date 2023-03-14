package me.ryleu.givememymusic

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import java.awt.Color
import java.io.File
import java.util.*

fun main() {
    // get token
    val file = File("token.txt")
    val scanner = Scanner(file)
    val token = scanner.nextLine()
    scanner.close()

    // determine whether to generate slash commands
    // TODO: save some state concerning whether this has been done already
    print("Would you like to (re-)create slash commands (y/N)?")
    BotListener.generateSlashCommands = (readlnOrNull() ?: "").lowercase().getOrElse(0) { 'n' } == 'y'

    JDABuilder
        .createDefault(token)
        .addEventListeners(BotListener)
        .build()
}

object BotListener : ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {
        println("logged in")

        if (generateSlashCommands) {
            event
                .jda
                .updateCommands()
                .addCommands(
                    Commands.slash("info", "Get info about GiveMeMyMusic"),
                    Commands.slash("help", "Get help with a command")
                        .addOption(OptionType.STRING, "command", "Command to get help with", false)
                ).queue()
        }
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        println(event.name)
        when (event.name) {
            "info" -> infoCommand(event)
            "help" -> helpCommand(event)
            else -> {
                event.reply("this command is disabled right now").queue()
            }
        }
    }

    private fun infoCommand(event: SlashCommandInteractionEvent) {
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

    private fun helpCommand(event: SlashCommandInteractionEvent) {
        event.reply("lol you really think i got time to make that nonsense? use your eyes, discord spent " +
                "thousands of dollars worth of dev time on that ui").queue()
    }

    var generateSlashCommands = false
}