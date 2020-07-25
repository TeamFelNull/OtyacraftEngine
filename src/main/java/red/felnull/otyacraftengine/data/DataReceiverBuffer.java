package red.felnull.otyacraftengine.data;

import net.minecraft.util.ResourceLocation;

public class DataReceiverBuffer {

    private int cont;
    public int allcont;
    private byte[] bytes;
    public boolean stop;
    public final String dataName;
    public final String dataUuid;
    public final ResourceLocation dataLocation;

    public DataReceiverBuffer(int bytecont, String uuid, ResourceLocation location, String name) {
        this.bytes = new byte[bytecont];
        this.allcont = bytecont;
        this.cont = 0;
        this.stop = false;
        this.dataUuid = uuid;
        this.dataLocation = location;
        this.dataName = name;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public int getCont() {
        return this.cont;
    }

    public void addBytes(byte[] addedbytes) {
        for (int i = 0; i < addedbytes.length; i++) {
            this.bytes[cont + i] = addedbytes[i];
        }
        this.cont += addedbytes.length;
    }

    public boolean isPerfectByte() {

        return cont == bytes.length;
    }

    public String getPrograsePar() {

        return Math.round(((float) cont / (float) allcont) * 100) + " %";
    }
}