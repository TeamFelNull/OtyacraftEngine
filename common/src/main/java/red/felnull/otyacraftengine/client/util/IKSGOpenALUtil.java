package red.felnull.otyacraftengine.client.util;

import red.felnull.otyacraftengine.throwable.OpenALException;

import static org.lwjgl.openal.AL10.*;

public class IKSGOpenALUtil {
    public static void checkErrorThrower() throws OpenALException {
        switch (alGetError()) {
            case AL_NO_ERROR:
                break;
            case AL_INVALID_NAME:
                throw new OpenALException("Invalid name parameter.");
            case AL_INVALID_ENUM:
                throw new OpenALException("Invalid enumerated parameter value.");
            case AL_INVALID_VALUE:
                throw new OpenALException("Invalid parameter parameter value.");
            case AL_INVALID_OPERATION:
                throw new OpenALException("Invalid operation.");
            case AL_OUT_OF_MEMORY:
                throw new OpenALException("Unable to allocate memory.");
        }
    }

    public static int getOpenALFormat(int channel, int bit) {
        if (channel == 1) {
            if (bit == 8) {
                return AL_FORMAT_MONO8;
            }
            if (bit == 16) {
                return AL_FORMAT_MONO16;
            }
        } else if (channel == 2) {
            if (bit == 8) {
                return AL_FORMAT_STEREO8;
            }

            if (bit == 16) {
                return AL_FORMAT_STEREO16;
            }
        }
        return AL_FORMAT_MONO16;
    }
}
