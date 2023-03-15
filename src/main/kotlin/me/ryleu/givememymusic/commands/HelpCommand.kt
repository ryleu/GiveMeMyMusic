package me.ryleu.givememymusic.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands

object HelpCommand : BotCommand {
    override val command = Commands.slash("help", "Get help with a command")
        .addOption(OptionType.STRING, "command", "Command to get help with", false)

    override fun invoke(event: SlashCommandInteractionEvent) {
        event.reply("lol you really think i got time to make that nonsense? use your eyes, discord spent " +
                "thousands of dollars worth of dev time on that ui, you'd best use it").queue()
    }
}