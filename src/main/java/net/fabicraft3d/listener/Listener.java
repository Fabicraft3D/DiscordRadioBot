package net.fabicraft3d.listener;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;
import net.fabicraft3d.lavaplayer.PlayerManager;
import net.fabicraft3d.utils.ConfigManager;
import net.fabicraft3d.utils.ReadConfig;
import sun.security.util.ArrayUtil;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Listener extends ListenerAdapter {
    @Getter
    public Guild guild;

    public void onMessageReceived(MessageReceivedEvent event) {

    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        ReadConfig botConfig = new ConfigManager().readConfig();
        OptionMapping operator1 = event.getOption("sender");
        guild = event.getGuild();
        VoiceChannel channel = guild.getVoiceChannelById(botConfig.getStandardchannel());

        if (event.getName().equals("radio")) {
            if (event.getMember().getRoles().stream().noneMatch(role -> Arrays.stream(botConfig.getModRoles()).anyMatch(s -> s.equalsIgnoreCase(role.getId())))) {
                event.reply("KEINE RECHTE").setEphemeral(true).queue();
                return;
            }

            if (operator1 == null) {
                return;
            }
            int sum = operator1.getAsInt() + 0;


            if (sum == 1) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Standard-Radio wird nun Abgespielt").queue();
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio14.mp3");
            } else if (sum == 2) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Base-Radio.de wird nun Abgespielt").queue();
                PlayerManager.getInstance().loadAnyPlay(channel, "https://baseradiode.stream.laut.fm/baseradiode");
            } else if (sum == 3) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Chill-Radio wird nun Abgespielt").queue();
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio17.mp3");
            } else if (sum == 4) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Dance-Radio wird nun Abgespielt").queue();
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio2.mp3");
            } else if (sum == 5) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Deutsch-Rap-Radio wird nun Abgespielt").queue();
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio6.mp3");
            } else if (sum == 6) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Greatest-hits-Radio wird nun Abgespielt").queue();
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio16.mp3");
            } else if (sum == 7) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Hip-hop-Radio wird nun Abgespielt").queue();
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio3.mp3");
            } else if (sum == 8) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Party-Radio wird nun Abgespielt").queue();
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio14.mp3");
            } else if (sum == 9) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("Us-Rap-Radio wird nun Abgespielt").queue();
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio13.mp3");
            } else if (sum == 10) {
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply("X-Mas-Radio wird nun Abgespielt").queue();
                PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio8.mp3");
            } else
                event.reply("Dieser Radio Sender existiert nicht!").queue();
        }

    }


}
