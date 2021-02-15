package red.felnull.otyacraftengine.api.register;

import java.util.HashSet;
import java.util.Set;

public class SingleRegistry<T> implements ISingleRegister<T> {
    private final Set<T> REGSTS = new HashSet<>();

    @Override
    public Set<T> getSet() {
        return REGSTS;
    }

}
