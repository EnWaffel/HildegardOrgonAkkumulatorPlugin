package de.enwaffel.lavalink.plugin.filter;

import com.sedmelluq.discord.lavaplayer.filter.FloatPcmAudioFilter;
import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;

public class BitrateFilter extends FilterBase {

    private final float bitrateKnob;
    private final float[] held;
    private int holdCounter = 0;

    public BitrateFilter(FloatPcmAudioFilter downstream, AudioDataFormat format, float quality) {
        super(downstream, format);

        held = new float[format.channelCount];
        bitrateKnob = 1f - quality;
    }

    public void process(float[][] input, int offset, int length) throws InterruptedException {
        float bits = 16f - 14f * bitrateKnob;
        if (bits < 2f) bits = 2f;

        float quantLevels = (float)(1 << (int)bits);
        int holdSamples = 1 + (int)(bitrateKnob * 40f);

        for (int i = offset; i < offset + length; i++) {
            boolean update = (holdCounter++ % holdSamples) == 0;

            for (int ch = 0; ch < input.length; ch++) {
                float x = input[ch][i];

                if (x > 1f) x = 1f;
                else if (x < -1f) x = -1f;

                if (update) {
                    held[ch] = Math.round(x * quantLevels) / quantLevels;
                }

                input[ch][i] = held[ch];
            }
        }

        super.process(input, offset, length);
    }

}
