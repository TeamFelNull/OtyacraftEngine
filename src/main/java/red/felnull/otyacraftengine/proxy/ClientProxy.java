package red.felnull.otyacraftengine.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import red.felnull.otyacraftengine.client.handler.RenderHandler;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit() {
		super.preInit();
		MinecraftForge.EVENT_BUS.register(RenderHandler.class);
	}

	public static void clientInit() {

	}

	@Override
	public void init() {
		super.init();

	}

	@Override
	public void posInit() {
		super.posInit();

	}

	@Override
	public Minecraft getMinecraft() {
		return Minecraft.getInstance();
	}
}
