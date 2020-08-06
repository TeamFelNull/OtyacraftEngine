package red.felnull.otyacraftengine.asm.lib;

import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;

public class RefName {

 //   https://github.com/Team-Fruit/Emojicord/blob/master/shared/src/main/java/net/teamfruit/emojicord/asm/lib/RefName.java ← 参考


    private final String mcpName;
    private final String srgName;

    public RefName(@Nonnull String mcpName, @Nonnull String srgName) {
        this.mcpName = mcpName;
        this.srgName = srgName;
    }

    public static RefName of(@Nonnull String mcpName, @Nonnull String srgName) {
        return new RefName(mcpName, srgName);
    }

    public @Nonnull
    String name() {
        return FMLDeobfuscatingRemapper.isMapping() ? Validate.notEmpty(this.mcpName) : Validate.notEmpty(this.srgName);
    }

    public @Nonnull
    String mcpName() {
        return this.mcpName;
    }

    public @Nonnull
    String srgName() {
        return this.srgName;
    }

}
