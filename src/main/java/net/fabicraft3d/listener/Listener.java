package net.fabicraft3d.listener;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;
import net.fabicraft3d.DiscordMusicBot;
import net.fabicraft3d.lavaplayer.PlayerManager;

public class SlashCommandListener extends ListenerAdapter {
    @Getter
    public Guild guild;

    public void onMessageReceived(MessageReceivedEvent event) {
        Guild guild = event.getGuild();
        VoiceChannel channel = guild.getVoiceChannelsByName("General", true).get(0);
        AudioManager manager = guild.getAudioManager();
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();
        if (event.getMessage().getContentRaw().equalsIgnoreCase("!radio")) {
            System.out.println("HURENSOHN");
            manager.openAudioConnection(channel);
            PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio14.mp3");
        }
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("radio")) {

            OptionMapping operator1 = event.getOption("sender");

            guild = event.getGuild();
            VoiceChannel channel = guild.getVoiceChannelsByName("General", true).get(0);
            AudioManager manager = guild.getAudioManager();

            if (operator1 == null) {
                return;
            }
            int sum = operator1.getAsInt() + 1;

            if (sum == 1) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Wird nun Abgespielt").queue();
                DiscordMusicBot.connect(event.getGuild());
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio14.mp3");
            } else if (sum == 2) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Wird nun Abgespielt").queue();
                DiscordMusicBot.connect(event.getGuild());
                PlayerManager.getInstance().loadAnyPlay(channel, "https://baseradiode.stream.laut.fm/baseradiode");
            } else if (sum == 3) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Wird nun Abgespielt").queue();
                DiscordMusicBot.connect(event.getGuild());
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio17.mp3");
            } else if (sum == 4) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Wird nun Abgespielt").queue();
                DiscordMusicBot.connect(event.getGuild());
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio2.mp3");
            } else if (sum == 5) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Wird nun Abgespielt").queue();
                DiscordMusicBot.connect(event.getGuild());
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio6.mp3");
            } else if (sum == 6) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Wird nun Abgespielt").queue();
                DiscordMusicBot.connect(event.getGuild());
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio16.mp3");
            } else if (sum == 7) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Wird nun Abgespielt").queue();
                DiscordMusicBot.connect(event.getGuild());
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio3.mp3");
            } else if (sum == 8) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Wird nun Abgespielt").queue();
                DiscordMusicBot.connect(event.getGuild());
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio14.mp3");
            } else if (sum == 9) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Wird nun Abgespielt").queue();
                DiscordMusicBot.connect(event.getGuild());
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio13.mp3");
            } else if (sum == 10) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Wird nun Abgespielt").queue();
                DiscordMusicBot.connect(event.getGuild());
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio8.mp3");
            }
        }
    }
}
