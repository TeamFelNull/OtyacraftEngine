package red.felnull.otyacraftengine.api.register;

import java.util.Map;

public interface IDoubleRegister<T, V> extends IRegister {
    default void register(T key, V value) {
        if (key != null && value != null)
            getMap().put(key, value);
    }

    Map<T, V> getMap();

    default boolean contains(T key) {
        return getMap().containsKey(key);
    }
}
