package red.felnull.otyacraftengine.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldShareTextureManager {
    public static final Logger LOGGER = LogManager.getLogger(WorldShareTextureManager.class);
    private static final WorldShareTextureManager INSTANCE = new WorldShareTextureManager();

    public static WorldShareTextureManager getInstance() {
        return INSTANCE;
    }
}
