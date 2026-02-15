package de.enwaffel.lavalink.plugin;

import com.sedmelluq.discord.lavaplayer.filter.FloatPcmAudioFilter;
import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;
import de.enwaffel.lavalink.plugin.filter.BitrateFilter;
import dev.arbjerg.lavalink.api.AudioFilterExtension;
import kotlinx.serialization.json.JsonElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class BitrateFilterExtension implements AudioFilterExtension {

    @NotNull
    @Override
    public String getName() {
        return "bitrate";
    }

    @Nullable
    @Override
    public FloatPcmAudioFilter build(@NotNull JsonElement data, @Nullable AudioDataFormat format, @Nullable FloatPcmAudioFilter output) {
        if (format == null || output == null) return null;

        Float quality = PrimitiveUtils.parseFloatElement(data, "quality");
        if (quality == null) return null;

        return new BitrateFilter(output, format, quality);
    }

    @Override
    public boolean isEnabled(@NotNull JsonElement data) {
        return PrimitiveUtils.isGreaterOrEqualsThan(data, "quality", 0);
    }

}
