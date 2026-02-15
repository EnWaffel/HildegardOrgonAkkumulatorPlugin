package de.enwaffel.lavalink.plugin;

import com.sedmelluq.discord.lavaplayer.filter.FloatPcmAudioFilter;
import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;
import de.enwaffel.lavalink.plugin.filter.DeepFryFilter;
import dev.arbjerg.lavalink.api.AudioFilterExtension;
import kotlinx.serialization.json.JsonElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class DeepFryFilterExtension implements AudioFilterExtension {

    @NotNull
    @Override
    public String getName() {
        return "deepfry";
    }

    @Nullable
    @Override
    public FloatPcmAudioFilter build(@NotNull JsonElement data, @Nullable AudioDataFormat format, @Nullable FloatPcmAudioFilter output) {
        if (format == null || output == null) return null;

        Float drive = PrimitiveUtils.parseFloatElement(data, "drive");
        if (drive == null) return null;

        Float mix = PrimitiveUtils.parseFloatElement(data, "mix");
         if (mix == null) return null;

        return new DeepFryFilter(output, format, drive, mix);
    }

    @Override
    public boolean isEnabled(@NotNull JsonElement data) {
        return PrimitiveUtils.isGreaterThan(data, "drive", 0)
                && PrimitiveUtils.isGreaterThan(data, "mix", 0);
    }

}
