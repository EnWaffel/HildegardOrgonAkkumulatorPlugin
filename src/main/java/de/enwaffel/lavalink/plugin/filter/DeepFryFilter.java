package de.enwaffel.lavalink.plugin.filter;

import com.sedmelluq.discord.lavaplayer.filter.FloatPcmAudioFilter;
import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;

public class DeepFryFilter extends FilterBase {

    private final float drive;
    private final float mix;

    public DeepFryFilter(FloatPcmAudioFilter downstream, AudioDataFormat format, float drive, float mix) {
        super(downstream, format);

        this.drive = drive;
        this.mix = mix;
    }

    public void process(float[][] input, int offset, int length) throws InterruptedException {
        for (int ch = 0; ch < input.length; ch++) {
            for (int i = offset; i < offset + length; i++) {

                float x = input[ch][i];

                float driven = x * drive;

                float distorted = (float)Math.tanh(driven);

                if (distorted > 0.6f) distorted = 0.6f;
                if (distorted < -0.6f) distorted = -0.6f;

                distorted *= 1.8f;

                if (distorted > 1f) distorted = 1f;
                if (distorted < -1f) distorted = -1f;

                input[ch][i] = x * (1f - mix) + distorted * mix;
            }
        }

        super.process(input, offset, length);
    }

}
