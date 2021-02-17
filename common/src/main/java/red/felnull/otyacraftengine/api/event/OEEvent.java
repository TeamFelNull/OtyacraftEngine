package red.felnull.otyacraftengine.api.event;

/**
 * Forgeイベントもどき
 * @author MORIMORI0317
 * @since 2.0
 */
public class OEEvent {
    private boolean isCanceled = false;

    public boolean isCanceled() {
        return isCanceled;
    }

    public void cancele() {
        isCanceled = true;
    }

}
