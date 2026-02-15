package de.enwaffel.lavalink.plugin.filter;

import com.sedmelluq.discord.lavaplayer.filter.FloatPcmAudioFilter;
import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuaFilter extends FilterBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(LuaFilter.class);

    public static final String FUNC_PROCESS_SAMPLE = "ProcessSample";

    private final Globals globals;

    public LuaFilter(FloatPcmAudioFilter downstream, AudioDataFormat format, Globals globals) {
        super(downstream, format);

        this.globals = globals;

        globals.set("CHANNELS", format.channelCount);
        globals.set("SAMPLE_RATE", format.sampleRate);
        globals.set("SAMPLES_PER_CHUNK", format.chunkSampleCount);
    }

    @Override
    public void process(float[][] input, int offset, int length) throws InterruptedException {
        try {
            boolean hasSampleProcessing = globals.get(FUNC_PROCESS_SAMPLE) != LuaValue.NIL;
            if (!hasSampleProcessing) {
                super.process(input, offset, length);
                return;
            }

            for (int j = 0; j < input.length; j++) {
                for (int i = offset; i < offset + length; i++) {
                    LuaValue func = globals.get(FUNC_PROCESS_SAMPLE);
                    LuaValue result = func.call(
                            LuaValue.valueOf(input[j][i]),
                            LuaValue.valueOf(j),
                            LuaValue.valueOf(i)
                    );

                    if (!result.isnumber()) return;

                    input[j][i] = result.tofloat();
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }

        super.process(input, offset, length);
    }

}
