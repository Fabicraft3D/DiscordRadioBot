package net.fabicraft3d.listener;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;
import net.fabicraft3d.DiscordMusicBot;
import net.fabicraft3d.lavaplayer.PlayerManager;
import net.fabicraft3d.utils.ConfigManager;
import net.fabicraft3d.utils.ReadConfig;
import sun.security.util.ArrayUtil;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Listener extends ListenerAdapter {
    @Getter
    public Guild guild;

    public void onMessageReceived(MessageReceivedEvent event) {

    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("help")) {
            JsonObject currentData = DiscordMusicBot.getCurrentData();
            EmbedBuilder helpEmbed = new EmbedBuilder();
            helpEmbed.setColor(Color.BLUE);
            JsonArray channels = currentData.getAsJsonArray("channels");
            helpEmbed.appendDescription("Currently there are " + channels.size() + " Streams available");
            int i = 1;
            for (JsonElement jsonElement : channels) {
                JsonObject asJsonObject = jsonElement.getAsJsonObject();
                helpEmbed.appendDescription("\n" + i + ": " + asJsonObject.get("name").getAsString());
                i++;
            }
            event.replyEmbeds(helpEmbed.build()).setEphemeral(true).queue();
            return;
        }
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
            int sum = operator1.getAsInt();

            JsonObject currentData = DiscordMusicBot.getCurrentData();
            JsonArray channels = currentData.getAsJsonArray("channels");
            try {
                JsonObject jsonElement = channels.get(sum - 1).getAsJsonObject();
                String stream_url = jsonElement.get("stream_url").getAsString();
                String name = jsonElement.get("name").getAsString();
                PlayerManager.getInstance().getMusicManager(guild).scheduler.nextTrack();
                event.reply(name + " wird nun Abgespielt").setEphemeral(true).queue();
                PlayerManager.getInstance().loadAnyPlay(channel, stream_url);
            } catch (IndexOutOfBoundsException e){
                event.reply("Dieser Sender konnte nicht gefunden werden.").setEphemeral(true).queue();
            }
        }

    }


}
