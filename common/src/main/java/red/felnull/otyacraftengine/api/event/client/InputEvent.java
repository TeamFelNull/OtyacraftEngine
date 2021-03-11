package red.felnull.otyacraftengine.api.event.client;

import red.felnull.otyacraftengine.api.event.OEEvent;

public class InputEvent extends OEEvent {
    public static class RawMouseEvent extends InputEvent {
        private final int button;
        private final int action;
        private final int mods;

        public RawMouseEvent(int button, int action, int mods) {
            this.button = button;
            this.action = action;
            this.mods = mods;
        }

        public int getButton() {
            return this.button;
        }

        public int getAction() {
            return this.action;
        }

        public int getMods() {
            return this.mods;
        }
    }

    public static class MouseInputEvent extends InputEvent {
        private final int button;
        private final int action;
        private final int mods;

        public MouseInputEvent(int button, int action, int mods) {
            this.button = button;
            this.action = action;
            this.mods = mods;
        }

        public int getButton() {
            return this.button;
        }

        public int getAction() {
            return this.action;
        }

        public int getMods() {
            return this.mods;
        }
    }

    public static class MouseScrollEvent extends InputEvent {
        private final double scrollDelta;
        private final double mouseX;
        private final double mouseY;
        private final boolean leftDown;
        private final boolean middleDown;
        private final boolean rightDown;

        public MouseScrollEvent(double scrollDelta, boolean leftDown, boolean middleDown, boolean rightDown, double mouseX, double mouseY) {
            this.scrollDelta = scrollDelta;
            this.leftDown = leftDown;
            this.middleDown = middleDown;
            this.rightDown = rightDown;
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }

        public double getScrollDelta() {
            return this.scrollDelta;
        }

        public boolean isLeftDown() {
            return this.leftDown;
        }

        public boolean isRightDown() {
            return this.rightDown;
        }

        public boolean isMiddleDown() {
            return this.middleDown;
        }

        public double getMouseX() {
            return this.mouseX;
        }

        public double getMouseY() {
            return this.mouseY;
        }
    }

    public static class KeyInputEvent extends InputEvent {
        private final int key;
        private final int scanCode;
        private final int action;
        private final int modifiers;

        public KeyInputEvent(int key, int scanCode, int action, int modifiers) {
            this.key = key;
            this.scanCode = scanCode;
            this.action = action;
            this.modifiers = modifiers;
        }


        public int getKey() {
            return this.key;
        }

        public int getScanCode() {
            return this.scanCode;
        }


        public int getAction() {
            return this.action;
        }


        public int getModifiers() {
            return this.modifiers;
        }
    }
}
