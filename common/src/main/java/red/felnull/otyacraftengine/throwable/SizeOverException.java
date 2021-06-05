package red.felnull.otyacraftengine.throwable;

public class SizeOverException extends Exception {
    private static final long serialVersionUID = 1L;

    public SizeOverException(long maxLength, long length) {
        super("Size Over: " + maxLength + "byte" + " current: " + length);
    }
}
