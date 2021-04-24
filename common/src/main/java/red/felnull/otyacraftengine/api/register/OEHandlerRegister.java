package red.felnull.otyacraftengine.api.register;

import red.felnull.otyacraftengine.api.IHandler;
import red.felnull.otyacraftengine.api.event.OEEvent;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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

    public void register(Class<?> clas) {
        for (Method method : clas.getMethods()) {
            try {
                if (Modifier.isStatic(method.getModifiers())) {
                    Class<?>[] signaruters = method.getParameterTypes();
                    if (signaruters.length == 1) {
                        if (isInstanceofClass(signaruters[0], OEEvent.class)) {
                            Class<? extends OEEvent> evclas = (Class<? extends OEEvent>) signaruters[0];
                            register(evclas, n -> {
                                try {
                                    method.invoke(null, n);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            });
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static boolean isInstanceofClass(Class<?> cl1, Class<?> cl2) {
        if (cl1.getSuperclass() == null)
            return false;

        if (cl1.getSuperclass() == cl2 || cl1 == cl2)
            return true;

        return isInstanceofClass(cl1.getSuperclass(), cl2);
    }
}
