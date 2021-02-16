package red.felnull.otyacraftengine.api.register;

import red.felnull.otyacraftengine.api.IHandler;
import red.felnull.otyacraftengine.api.event.OEEvent;

import java.util.function.Consumer;

public class OEHandlerRegister extends SingleRegistry<IHandler> {
    public <T extends OEEvent> void register(Class<T> clas, Consumer<T> entry) {
        register(new IHandler() {
            @Override
            public void accept(OEEvent ev) {
                entry.accept((T) ev);
            }

            @Override
            public boolean canAccept(OEEvent ev) {
                return ev.getClass() == clas;
            }
        });
    }
}
