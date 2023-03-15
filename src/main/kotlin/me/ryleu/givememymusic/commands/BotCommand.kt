package me.ryleu.givememymusic.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData

interface BotCommand {
    val command: SlashCommandData
    operator fun invoke(event: SlashCommandInteractionEvent)
}