package red.felnull.otyacraftengine.api.register;

import red.felnull.otyacraftengine.api.IHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Consumer;
import java.util.function.Function;

public class OEHandlerRegister extends SingleRegistry<IHandler> {

    public <T> void register(Class<T> clas, Consumer<T> entry) {
        register(new IHandler() {
            @Override
            public Object accept(Object ev) {
                entry.accept((T) ev);
                return null;
            }

            @Override
            public boolean canAccept(Object t, Class<?> r) {
                return t.getClass() != null && t.getClass() == getHandlerClass() && r == null;
            }

            @Override
            public Class<?> getHandlerClass() {
                return clas;
            }

            @Override
            public Class<?> getReturnClass() {
                return null;
            }
        });
    }

    public <T, R> void register(Class<T> clasT, Class<R> clasR, Function<T, R> entry) {
        register(new IHandler() {
            @Override
            public Object accept(Object ev) {
                return entry.apply((T) ev);
            }

            @Override
            public boolean canAccept(Object t, Class<?> r) {
                return t.getClass() != null && t.getClass() == getHandlerClass() && r != null && r == getReturnClass();
            }

            @Override
            public Class<?> getHandlerClass() {
                return clasT;
            }

            @Override
            public Class<?> getReturnClass() {
                return clasR;
            }
        });
    }

    public <T, R> void register(Class<?> clas) {
        for (Method method : clas.getMethods()) {
            try {
                if (Modifier.isStatic(method.getModifiers())) {
                    Class<?>[] signaruters = method.getParameterTypes();
                    if (signaruters.length == 1) {
                        Class<T> evclas = (Class<T>) signaruters[0];
                        Class<?> r = method.getReturnType();
                        if (r == boolean.class)
                            r = Boolean.class;
                        Class<R> ret = (Class<R>) r;
                        if (ret == void.class) {
                            register(evclas, n -> {
                                try {
                                    method.invoke(null, n);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            });
                        } else {
                            register(evclas, ret, n -> {
                                try {
                                    return ret.cast(method.invoke(null, n));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                return null;
                            });
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


}
