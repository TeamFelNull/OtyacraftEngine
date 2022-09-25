package dev.felnull.otyacraftengine.data.provider;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import dev.felnull.fnjl.util.FNDataUtil;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import net.minecraft.Util;
import net.minecraft.data.CachedOutput;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class InputCopyProviderWrapper extends DevToolProviderWrapper {
    public InputCopyProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(crossDataGeneratorAccess);
    }

    public abstract boolean isCopy(Path inputFolder, Path path);

    @Override
    public void run(CachedOutput cachedOutput) throws IOException {
        List<CompletableFuture<TaskResult>> tasks = new ArrayList<>();

        var outPath = getGenerator().getOutputFolder();
        var inPaths = getCrossGeneratorAccess().getResourceInputFolders();

        for (Path path : inPaths) {
            try (var walk = Files.walk(path)) {
                walk.forEach(tp -> {
                    if (!Files.isDirectory(tp) && isCopy(path, tp))
                        tasks.add(CompletableFuture.supplyAsync(() -> task(outPath, path, tp), Util.backgroundExecutor()));
                });
            }
        }

        for (CompletableFuture<TaskResult> task : tasks) {
            try {
                var tr = task.get();
                cachedOutput.writeIfNeeded(tr.path, tr.data, tr.hash);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private TaskResult task(Path outPath, Path inPath, Path path) {
        var rp = inPath.relativize(path);
        var op = outPath.resolve(rp);

        byte[] bs;
        HashCode hashCode;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); HashingOutputStream hashingOutputStream = new HashingOutputStream(Hashing.sha1(), byteArrayOutputStream); InputStream stream = new BufferedInputStream(new FileInputStream(path.toFile()))) {
            FNDataUtil.i2o(stream, hashingOutputStream);
            bs = byteArrayOutputStream.toByteArray();
            hashCode = hashingOutputStream.hash();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new TaskResult(op, bs, hashCode);
    }

    @Override
    public String getName() {
        return "Input copy";
    }

    private static record TaskResult(Path path, byte[] data, HashCode hash) {
    }
}
