package net.fabicraft3d.lavaplayer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.fabicraft3d.DiscordMusicBot;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for (GuildMusicManager value : musicManagers.values()) {
                    AudioTrack playingTrack = value.audioPlayer.getPlayingTrack();
                    if(playingTrack != null){
                        JsonObject currentData = DiscordMusicBot.getCurrentData();
                        JsonArray channels = currentData.getAsJsonArray("channels");
                        for (JsonElement jsonElement : channels) {
                            JsonObject asJsonObject = jsonElement.getAsJsonObject();
                            if (asJsonObject.get("stream_url").getAsString().equalsIgnoreCase(playingTrack.getInfo().uri)) {
                                String title = asJsonObject.get("title").getAsString();
                                String artist = asJsonObject.get("artist").getAsString();
                                DiscordMusicBot.jda.getPresence().setActivity(Activity.listening(title + " - " + artist));
                                break;
                            }
                        }
                    }
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 10000, 10000);

    }

    public static PlayerManager getInstance() {
        if (INSTANCE == null) INSTANCE = new PlayerManager();
        return INSTANCE;
    }

    public GuildMusicManager getMusicManager(Guild guild) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
            return guildMusicManager;
        });
    }

    public void loadAnyPlay(VoiceChannel channel, String trackUrl) {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {

            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                musicManager.scheduler.queue(audioTrack);

                Thread newThread = new Thread(() -> {
                    JsonObject currentData = DiscordMusicBot.getCurrentData();
                    JsonArray channels = currentData.getAsJsonArray("channels");
                    for (JsonElement jsonElement : channels) {
                        JsonObject asJsonObject = jsonElement.getAsJsonObject();
                        if (asJsonObject.get("stream_url").getAsString().equalsIgnoreCase(audioTrack.getInfo().uri)) {
                            String title = asJsonObject.get("title").getAsString();
                            String artist = asJsonObject.get("artist").getAsString();
                            DiscordMusicBot.jda.getPresence().setActivity(Activity.listening(title + " - " + artist));
                            break;
                        }
                    }
                });
                newThread.start();
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {

            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException e) {

            }
        });
    }
}
