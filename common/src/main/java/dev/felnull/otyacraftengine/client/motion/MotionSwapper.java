package dev.felnull.otyacraftengine.client.motion;

import org.jetbrains.annotations.Nullable;

public interface MotionSwapper {
    MotionSwapper EMPTY = new MotionSwapper() {
        @Override
        public @Nullable
        MotionPoint swapPrePoint(Motion motion, float par, int num, MotionPoint pre, MotionPoint next) {
            return null;
        }

        @Override
        public @Nullable
        MotionPoint swapNextPoint(Motion motion, float par, int num, MotionPoint pre, MotionPoint next) {
            return null;
        }
    };

    static MotionSwapper swapStartAndEnd(MotionPoint start, MotionPoint end) {
        return new MotionSwapper() {
            @Override
            public @Nullable
            MotionPoint swapPrePoint(Motion motion, float par, int num, MotionPoint pre, MotionPoint next) {
                if (num == 0)
                    return start;
                return null;
            }

            @Override
            public @Nullable
            MotionPoint swapNextPoint(Motion motion, float par, int num, MotionPoint pre, MotionPoint next) {
                if (num + 1 == motion.getPoints().size() - 1)
                    return end;
                return null;
            }
        };
    }

    static MotionSwapper swapStartAndEnd(MotionPose start, MotionPose end) {
        return new MotionSwapper() {
            @Override
            public @Nullable
            MotionPoint swapPrePoint(Motion motion, float par, int num, MotionPoint pre, MotionPoint next) {
                if (num == 0)
                    return new MotionPoint(start, 1f);
                return null;
            }

            @Override
            public @Nullable
            MotionPoint swapNextPoint(Motion motion, float par, int num, MotionPoint pre, MotionPoint next) {
                if (num + 1 == motion.getPoints().size() - 1)
                    return new MotionPoint(end, 1f);
                return null;
            }
        };
    }

    @Nullable
    MotionPoint swapPrePoint(Motion motion, float par, int num, MotionPoint pre, MotionPoint next);

    @Nullable
    MotionPoint swapNextPoint(Motion motion, float par, int num, MotionPoint pre, MotionPoint next);
}
