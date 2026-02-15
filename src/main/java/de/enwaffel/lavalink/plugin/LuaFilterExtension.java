package de.enwaffel.lavalink.plugin;

import com.sedmelluq.discord.lavaplayer.filter.FloatPcmAudioFilter;
import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;
import de.enwaffel.lavalink.plugin.filter.LuaFilter;
import dev.arbjerg.lavalink.api.AudioFilterExtension;
import kotlinx.serialization.json.JsonElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.*;
import org.luaj.vm2.lib.jse.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class LuaFilterExtension implements AudioFilterExtension {

    private static final Logger LOGGER = LoggerFactory.getLogger(LuaFilterExtension.class);

    @NotNull
    @Override
    public String getName() {
        return "lua";
    }

    /*
    function ProcessSample(sample, channel, sampleIndex)
        return sample * 0.5
    end
     */

    @Nullable
    @Override
    public FloatPcmAudioFilter build(@NotNull JsonElement data, @Nullable AudioDataFormat format, @Nullable FloatPcmAudioFilter output) {
        if (format == null || output == null) return null;

        String encodedCode = PrimitiveUtils.parseStringElement(data, "code");
        if (encodedCode == null) return null;

        String code = new String(Base64.getDecoder().decode(encodedCode), StandardCharsets.UTF_8);

        Globals globals = new Globals();
        LuaTable packageTable = LuaTable.tableOf();
        packageTable.set("loaded", LuaTable.tableOf());
        globals.set("package", packageTable);

        globals.load(new JseBaseLib());
        globals.load(new Bit32Lib());
        globals.load(new TableLib());
        globals.load(new StringLib());
        globals.load(new JseMathLib());

        globals.set("io", LuaValue.NIL);
        globals.set("os", LuaValue.NIL);
        globals.set("debug", LuaValue.NIL);

        LoadState.install(globals);
        LuaC.install(globals);

        try {
            globals.load(code).call();
        } catch (Exception e) {
            LOGGER.error("", e);
            return null;
        }

        return new LuaFilter(output, format, globals);
    }

    @Override
    public boolean isEnabled(@NotNull JsonElement data) {
        return true;
    }

}
