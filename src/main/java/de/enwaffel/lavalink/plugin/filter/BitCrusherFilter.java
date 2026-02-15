package de.enwaffel.lavalink.plugin.filter;

import com.sedmelluq.discord.lavaplayer.filter.FloatPcmAudioFilter;
import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;

public class BitCrusherFilter extends FilterBase {

    private final int factor;

    public BitCrusherFilter(FloatPcmAudioFilter downstream, AudioDataFormat format, int factor) {
        super(downstream, format);

        this.factor = factor;
    }

    @Override
    public void process(float[][] input, int offset, int length) throws InterruptedException {
        for (int j = 0; j < input.length; j++) {
            float held = 0f;

            for (int i = offset; i < offset + length; i++) {
                if (i % factor == 0) {
                    held = Math.round(input[j][i] * 128f) / 128f;
                }

                input[j][i] = held;
            }
        }

        super.process(input, offset, length);
    }

}
