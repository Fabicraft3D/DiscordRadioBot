package net.fabicraft3d.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReadConfig {
    private String token;
    private String guild_id;
    private String[] modRoles;
    private String standardchannel;


}
