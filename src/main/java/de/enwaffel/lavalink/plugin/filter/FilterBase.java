package de.enwaffel.lavalink.plugin.filter;

import com.sedmelluq.discord.lavaplayer.filter.FloatPcmAudioFilter;
import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;

public abstract class FilterBase implements FloatPcmAudioFilter {

    protected final FloatPcmAudioFilter downstream;
    protected final AudioDataFormat format;

    public FilterBase(FloatPcmAudioFilter downstream, AudioDataFormat format) {
        this.downstream = downstream;
        this.format = format;
    }

    @Override
    public void seekPerformed(long requestedTime, long providedTime) {
    }

    @Override
    public void close() {
    }

    @Override
    public void flush() throws InterruptedException {
    }

    @Override
    public void process(float[][] input, int offset, int length) throws InterruptedException {
        downstream.process(input, offset, length);
    }

}
