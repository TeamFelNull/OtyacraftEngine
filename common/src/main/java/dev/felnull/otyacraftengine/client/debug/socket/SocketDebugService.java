package dev.felnull.otyacraftengine.client.debug.socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;


public class SocketDebugService {
    private static final Logger LOGGER = LogManager.getLogger(SocketDebugService.class);
    private static final Gson GSON = new Gson();
    private static final boolean enable = true;
    private static final Minecraft mc = Minecraft.getInstance();
    private static final String hostName = "localhost";
    private static final int port = 1919;
    public static DebugConnection connection;
    private static Vector3f rawAngle = new Vector3f();
    private static Vector3f angle = new Vector3f();
    private static Vector3f oldAngle = new Vector3f();
    private static boolean yAngleFlag;
    private static boolean zAngleFlag;
    private static Vector3f rawPosition = new Vector3f();
    private static Vector3f position = new Vector3f();
    private static Vector3f oldPosition = new Vector3f();

    public static void start() {
        if (!enable) return;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignored) {
                }
            }
        }));

        var ct = new ConnectionThread();
        ct.start();
    }

    public static void tick() {
        if (!enable) return;
        oldAngle = angle;
        angle = rawAngle;

        oldPosition = position;
        position = rawPosition;
    }

    public static void onText(String text) {
        try {
            var uuid = UUID.fromString(text);
            connection.sendText(uuid.toString());
            return;
        } catch (Exception ignored) {
        }
        try {
            var jo = GSON.fromJson(text, JsonObject.class);
            parse(jo);
        } catch (Exception ignored) {
        }
    }

    private static void parse(JsonObject jo) {
        if (!jo.has("type")) return;
        var type = jo.get("type").getAsString();
        if ("update".equals(type)) {
            yAngleFlag = jo.get("ayflg").getAsBoolean();
            zAngleFlag = jo.get("azflg").getAsBoolean();
            rawAngle = createAngle(jo.get("ax").getAsFloat(), fixP(jo.get("ay").getAsFloat(), yAngleFlag, zAngleFlag), jo.get("az").getAsFloat());
            boolean yFixAngleFlag = jo.get("ayflgf").getAsBoolean();
            boolean zFixAngleFlag = jo.get("azflgf").getAsBoolean();
            var fAngle = createAngle(jo.get("axf").getAsFloat(), fixP(jo.get("ayf").getAsFloat(), yFixAngleFlag, zFixAngleFlag), jo.get("azf").getAsFloat());
            rawAngle.sub(fAngle);
            rawAngle = new Vector3f(fixRoted(rawAngle.x()), fixRoted(rawAngle.y()), fixRoted(rawAngle.z()));

            rawPosition = new Vector3f(jo.get("px").getAsFloat(), jo.get("py").getAsFloat(), jo.get("pz").getAsFloat());
        }
    }

    private static float fixP(float va, boolean revY, boolean revZ) {
        va = 90 - va;
        if (revY)
            va = 180 - va + 180;
        return va;
    }

    private static Vector3f createAngle(float rx, float ry, float rz) {
        return new Vector3f(360f - fixRoted(rx), (fixRoted(ry) - 180) % 360, fixRoted(rz));
    }

    private static float fixRoted(float val) {
        if (val < 0)
            return 360 + val;
        return val;
    }

    private static float lerpRoted(float delta, float old, float current) {
        float mr = 180f;
        float sa = Mth.abs(current - old);
        if (sa > mr) {
            if (old < current)
                current = -(360 - current);
            else
                current = 360 + current;
        }
        return Mth.lerp(delta, old, current);
    }

    private static class ConnectionThread extends Thread {
        private ConnectionThread() {
            setName("Debug connection");
            setDaemon(true);
        }

        @Override
        public void run() {
            try {
                connect(hostName, port);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Vector3f getAngele(float delta) {
        return new Vector3f(lerpRoted(delta, oldAngle.x(), angle.x()), lerpRoted(delta, oldAngle.y(), angle.y()), lerpRoted(delta, oldAngle.z(), angle.z()));
    }

    public static Vector3f getPosition(float delta) {
        return new Vector3f(Mth.lerp(delta, oldPosition.x(), position.x()), Mth.lerp(delta, oldPosition.y(), position.y()), Mth.lerp(delta, oldPosition.z(), position.z()));
    }

    public static boolean isConnected() {
        if (connection != null)
            return connection.isOpen();
        return false;
    }

    private static void connect(String hostName, int port) throws InterruptedException {
        while (mc.isRunning()) {
            connection = new DebugConnection(hostName, port);
            try {
                connection.run();
            } catch (Exception e) {
                LOGGER.error("Socket connection error", e);
                Thread.sleep(1000);
            } finally {
                connection = null;
            }
        }
    }
}
