package me.ryleu.givememymusic.commands

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame
import net.dv8tion.jda.api.audio.AudioSendHandler
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import java.nio.ByteBuffer


object PlayCommand : BotCommand {
    override val command = Commands.slash("play", "Plays audio from a URL")
        .addOption(OptionType.STRING, "url", "URL to play audio from", true)

    override fun invoke(event: SlashCommandInteractionEvent) {
        if (!event.isFromGuild) {
            event.reply("This command can only be used in a server.").queue()
            return
        }

        val manager = event.guild!!.audioManager
        val channel = event.member?.voiceState?.channel

        val player = audioPlayerManager.createPlayer()
        val trackScheduler = TrackScheduler(player)
        player.addListener(trackScheduler)

        if (channel == null) {
            event.reply("You must be in a voice channel to use this command.").queue()
            return
        }

        event.reply("​").queue()
    }

    private val audioPlayerManager = DefaultAudioPlayerManager()

    init {
        AudioSourceManagers.registerRemoteSources(audioPlayerManager)
    }
}

class AudioPlayerSendHandler(val audioPlayer: AudioPlayer, val trackScheduler: TrackScheduler) : AudioSendHandler {
    private var lastFrame: AudioFrame? = null
    override fun canProvide(): Boolean {
        lastFrame = audioPlayer.provide()
        return lastFrame != null
    }

    override fun provide20MsAudio(): ByteBuffer? = ByteBuffer.wrap(lastFrame!!.data)

    override fun isOpus() = true
}

class TrackScheduler(val player: AudioPlayer) : AudioEventAdapter() {
    override fun onPlayerPause(player: AudioPlayer) {
        // Player was paused
    }

    override fun onPlayerResume(player: AudioPlayer) {
        // Player was resumed
    }

    override fun onTrackStart(player: AudioPlayer, track: AudioTrack) {
        // A track started playing
    }

    override fun onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason) {
        if (endReason.mayStartNext) {
            // Start next track
        }

        // endReason == FINISHED: A track finished or died by an exception (mayStartNext = true).
        // endReason == LOAD_FAILED: Loading of a track failed (mayStartNext = true).
        // endReason == STOPPED: The player was stopped.
        // endReason == REPLACED: Another track started playing while this had not finished
        // endReason == CLEANUP: Player hasn't been queried for a while, if you want you can put a
        //                       clone of this back to your queue
    }

    override fun onTrackException(player: AudioPlayer, track: AudioTrack, exception: FriendlyException) {
        // An already playing track threw an exception (track end event will still be received separately)
    }

    override fun onTrackStuck(player: AudioPlayer, track: AudioTrack, thresholdMs: Long) {
        // Audio track has been unable to provide us any audio, might want to just start a new track
    }
}