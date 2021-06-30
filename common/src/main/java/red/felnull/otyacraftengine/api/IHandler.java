package red.felnull.otyacraftengine.api;

public interface IHandler {
    Object accept(Object ev);

    boolean canAccept(Object t, Class<?> r);

    Class<?> getHandlerClass();

    Class<?> getReturnClass();
}
