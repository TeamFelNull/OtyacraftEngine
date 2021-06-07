package red.felnull.otyacraftengine.throwable;

import java.util.UUID;

public class AlreadyDownloadException extends Exception {
    private static final long serialVersionUID = 1L;

    public AlreadyDownloadException(UUID id) {
        super("Already Downloaded!" + id.toString());
    }
}
