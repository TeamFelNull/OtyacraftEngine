package red.felnull.otyacraftengine.fluid;

public class FluidProperties {
    private int color = 0;
    private boolean coloring = true;

    public FluidProperties color(int color) {
        this.color = color;
        return this;
    }

    public FluidProperties noColoring() {
        this.coloring = false;
        return this;
    }

    public int getColor() {
        return color;
    }

    public boolean isColoring() {
        return coloring;
    }
}
