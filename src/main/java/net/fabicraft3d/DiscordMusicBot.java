package net.fabicraft3d;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.fabicraft3d.lavaplayer.PlayerManager;
import net.fabicraft3d.listener.Listener;
import net.fabicraft3d.utils.ConfigManager;
import net.fabicraft3d.utils.ReadConfig;

import javax.security.auth.login.LoginException;

public class DiscordMusicBot extends ListenerAdapter {
    public static void main(String[] args) throws LoginException, InterruptedException {
        ReadConfig botConfig = new ConfigManager().readConfig();
        JDA jda = JDABuilder.createDefault(botConfig.getToken()).addEventListeners(new Listener()).enableCache(CacheFlag.VOICE_STATE).setAutoReconnect(true).setActivity(Activity.listening("Dein geilo Radio")).build().awaitReady();

        connect(jda.getGuildById(botConfig.getGuild_id()));
        Guild testServer = jda.getGuildById(botConfig.getGuild_id());

        if (testServer != null) {
            testServer.upsertCommand("radio", "Spiele einen Radio Sender ab!").addOptions(new OptionData(OptionType.STRING, "sender", "the first number", true)).queue();
        }
    }

    public static void connect(Guild guild) {
        ReadConfig botConfig = new ConfigManager().readConfig();
        VoiceChannel channel = guild.getVoiceChannelsByName(botConfig.getStandardchannel(), true).get(0);
        AudioManager manager = guild.getAudioManager();
        manager.openAudioConnection(channel);
        PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio14.mp3");
    }
}

