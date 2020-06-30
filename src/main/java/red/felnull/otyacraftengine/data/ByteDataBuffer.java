package red.felnull.otyacraftengine.data;

public class ByteDataBuffer {
    private byte[] data;
    private boolean isTimeDelete;
    private long remainingTime;
    private long lastTime = 0;

    public ByteDataBuffer(byte[] data, boolean timedelete, long deletetime) {
        this.data = data;
        this.isTimeDelete = timedelete;
        this.remainingTime = deletetime;
    }

    public byte[] getData() {
        return data;
    }

    public boolean isTimeDelete() {
        return isTimeDelete;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public void updateTime() {
        long nowTime = System.currentTimeMillis();
        if (lastTime != 0) {
            long keika = nowTime - lastTime;
            remainingTime -= keika;
        }
        lastTime = nowTime;
    }

    public boolean canDelete() {
        return remainingTime <= 0;
    }
}
