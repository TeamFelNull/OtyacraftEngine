package dev.felnull.otyacraftengine.client.callpoint;

import com.google.common.collect.ImmutableList;
import dev.felnull.otyacraftengine.util.OEUtils;

import java.util.List;
import java.util.function.Consumer;

/**
 * モデル登録等の呼び出しが早すぎてイベントでは不可能な呼び出し用
 */
public class ClientCallPointManager {
    private static final ClientCallPointManager INSTANCE = new ClientCallPointManager();
    private final List<ClientCallPoint> POINTS = ImmutableList.copyOf(OEUtils.getCallPoints("otyacraftengine_client", ClientCallPoint.Sign.class, ClientCallPoint.class));
    private final ClientCallPoint ALL_CALL_POINT = new ClientCallPoint() {
        @Override
        public void onModelRegistry(ModelRegister register) {
            consumer(n -> n.onModelRegistry(register));
        }

        @Override
        public void onLayerRegistry(LayerRegister register) {
            consumer(n -> n.onLayerRegistry(register));
        }
    };

    public static ClientCallPointManager getInstance() {
        return INSTANCE;
    }

    public ClientCallPoint call() {
        return ALL_CALL_POINT;
    }

    private void consumer(Consumer<ClientCallPoint> callPointConsumer) {
        for (ClientCallPoint point : POINTS) {
            callPointConsumer.accept(point);
        }
    }
}
