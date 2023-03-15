package me.ryleu.givememymusic

import me.ryleu.givememymusic.commands.HelpCommand
import me.ryleu.givememymusic.commands.InfoCommand
import me.ryleu.givememymusic.commands.PlayCommand
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.io.File
import java.io.FileNotFoundException
import java.util.*

fun main() {
    // get config data
    val token = getConfig("token")
    try {
        val skipCommandGeneration = getConfig("skip_command_generation")
        BotListener.generateSlashCommands = skipCommandGeneration.lowercase().getOrElse(0) { 'n' } != 'y'
    } catch (_: FileNotFoundException) {}


    //AudioSourceManagers.registerRemoteSources(PlayerManager())

    // run the bot
    JDABuilder
        .createDefault(token)
        .addEventListeners(BotListener)
        .build()
}

fun getConfig(fileName: String): String {
    val file = File("config/$fileName")
    val scanner = Scanner(file)
    val output = scanner.nextLine()
    scanner.close()
    return output
}

object BotListener : ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {
        println("logged in")

        if (generateSlashCommands) {
            event
                .jda
                .updateCommands()
                .addCommands(
                    InfoCommand.command,
                    HelpCommand.command,
                    PlayCommand.command
                ).queue()
        }
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        when (event.name) {
            "info" -> InfoCommand(event)
            "help" -> HelpCommand(event)
            "play" -> PlayCommand(event)

            else -> event.reply("this command is disabled right now").queue()
        }
    }

    var generateSlashCommands = false
}
