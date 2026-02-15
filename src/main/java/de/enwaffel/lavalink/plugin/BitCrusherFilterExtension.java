package de.enwaffel.lavalink.plugin;

import com.sedmelluq.discord.lavaplayer.filter.FloatPcmAudioFilter;
import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;
import de.enwaffel.lavalink.plugin.filter.BitCrusherFilter;
import dev.arbjerg.lavalink.api.AudioFilterExtension;
import kotlinx.serialization.json.JsonElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class BitCrusherFilterExtension implements AudioFilterExtension {

    @NotNull
    @Override
    public String getName() {
        return "bit_crusher";
    }

    @Nullable
    @Override
    public FloatPcmAudioFilter build(@NotNull JsonElement data, @Nullable AudioDataFormat format, @Nullable FloatPcmAudioFilter output) {
        if (format == null || output == null) return null;

        Integer factor = PrimitiveUtils.parseIntElement(data, "factor");
        if (factor == null) return null;

        return new BitCrusherFilter(output, format, factor);
    }

    @Override
    public boolean isEnabled(@NotNull JsonElement data) {
        return PrimitiveUtils.isGreaterThan(data, "factor", 0);
    }

}
