package red.felnull.otyacraftengine.data;

import net.minecraft.nbt.CompoundNBT;

import java.util.Objects;

public abstract class AbstractNBTBased {
    protected String key;
    protected static final String dummy = "dummy";

    public AbstractNBTBased(String key) {
        this.key = key;
    }

    public void init() {
        getParentNBT().put(key, getDefaultNBT());
    }

    public CompoundNBT getNBT() {
        return getParentNBT().getCompound(key);
    }

    public abstract CompoundNBT getParentNBT();

    public abstract CompoundNBT getDefaultNBT();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        if (getParentNBT().contains(key)) {
            return;
        }
        getParentNBT().put(key, getNBT());
        getParentNBT().remove(getKey());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractNBTBased that = (AbstractNBTBased) o;
        return Objects.equals(getKey(), that.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey());
    }
}
