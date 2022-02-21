package dev.felnull.otyacraftengine.client.loader;

import java.util.concurrent.ExecutorService;

//WIP
public class URLTextureManager {
    private static final URLTextureManager INSTANCE = new URLTextureManager();
    private ExecutorService executorService;

    public static URLTextureManager getInstance() {
        return INSTANCE;
    }

    public void reload() {
        if (executorService != null) {
            executorService.shutdown();
            executorService.shutdownNow();
        }
        check();
    }

    private void check() {
        // if (executorService == null)
        //      executorService = Executors.newFixedThreadPool(OtyacraftEngine.CONFIG.maxURLTextureLoadThreadCount);
    }
}
