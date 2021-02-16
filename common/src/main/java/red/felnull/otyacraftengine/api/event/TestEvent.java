package red.felnull.otyacraftengine.api.event;

public class TestEvent extends OEEvent {
    private final String name;

    public TestEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
