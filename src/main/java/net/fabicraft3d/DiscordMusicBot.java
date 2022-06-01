package net.fabicraft3d;

import lombok.Getter;
import lombok.Setter;
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
import net.fabicraft3d.listener.RadioCommand;

import javax.security.auth.login.LoginException;

public class DiscordMusicBot extends ListenerAdapter {
    public static JDA jda;
    @Getter
    private static DiscordMusicBot instance;

    public static void main(String[] args) throws LoginException, InterruptedException {
        JDA jda = JDABuilder.createDefault(args[0])
                .addEventListeners(new RadioCommand())
                .enableCache(CacheFlag.VOICE_STATE)
                .setAutoReconnect(true)
                .build().awaitReady();


        connect(jda.getGuildById("954650148754051113"));
        //Guild Command - Added immediately. Make sure you use awaitReady like above.
        Guild testServer = jda.getGuildById("954650148754051113");
        if (testServer != null) {
            testServer.upsertCommand("radio", "Spiele einen Radio Sender ab!")
                    .addOptions(
                            new OptionData(OptionType.STRING, "sender", "the first number", true)
                    )
                    .queue();
        }
    }

    public static void connect(Guild guild) {
        VoiceChannel channel = guild.getVoiceChannelsByName("General", true).get(0);
        AudioManager manager = guild.getAudioManager();
        manager.openAudioConnection(channel);
    }
}

