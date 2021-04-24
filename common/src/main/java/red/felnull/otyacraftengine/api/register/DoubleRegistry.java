package red.felnull.otyacraftengine.api.register;

import java.util.HashMap;
import java.util.Map;

public class DoubleRegistry<T, V> implements IDoubleRegister<T, V> {
    private final Map<T, V> REGSTS = new HashMap<>();

    @Override
    public Map<T, V> getMap() {
        return REGSTS;
    }



}
