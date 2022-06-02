package net.fabicraft3d;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import org.apache.commons.io.IOUtils;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;


public class DiscordMusicBot extends ListenerAdapter {

    public static JDA jda;

    public static void main(String[] args) throws LoginException, InterruptedException {
        ReadConfig botConfig = new ConfigManager().readConfig();
        jda = JDABuilder.createDefault(botConfig.getToken()).addEventListeners(new Listener()).enableCache(CacheFlag.VOICE_STATE).setAutoReconnect(true).setActivity(Activity.listening("Radio <3")).build().awaitReady();


        connect(jda.getGuildById(botConfig.getGuild_id()));
        Guild testServer = jda.getGuildById(botConfig.getGuild_id());

        if (testServer != null) {
            testServer.upsertCommand("radio", "Spiele einen Radio Sender ab!").addOptions(new OptionData(OptionType.INTEGER, "sender", "Gebe die Sender Id ein", true)).queue();
        }

    }

    public static void connect(Guild guild) {
        ReadConfig botConfig = new ConfigManager().readConfig();
        VoiceChannel channel = guild.getVoiceChannelById(botConfig.getStandardchannel());
        AudioManager manager = guild.getAudioManager();
        manager.openAudioConnection(channel);
        PlayerManager.getInstance().loadAnyPlay(channel, "https://streams.ilovemusic.de/iloveradio14.mp3");
    }

    public static JsonObject getCurrentData() {
        try {
            JsonElement json = new JsonParser().parse(IOUtils.toString(new URL("https://api.ilovemusic.team/traffic/"), Charset.forName("UTF-8")));
            return json.getAsJsonObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

