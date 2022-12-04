package dev.felnull.otyacraftengine.data.provider;

import com.google.common.collect.ImmutableMap;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.*;
import dev.felnull.fnjl.util.FNDataUtil;
import dev.felnull.fnjl.util.FNStringUtil;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import net.minecraft.Util;
import net.minecraft.data.CachedOutput;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public abstract class ModelDivisionProviderWrapper extends DevToolProviderWrapper {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public ModelDivisionProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(crossDataGeneratorAccess);
    }

    protected abstract boolean isTarget(Path inputFolder, Path path);

    @Override
    public void run(CachedOutput cachedOutput) throws IOException {
        List<CompletableFuture<List<EntryResult>>> results = new ArrayList<>();

        var outPath = getGenerator().getOutputFolder();
        var inPaths = getCrossGeneratorAccess().getResourceInputFolders();

        for (Path path : inPaths) {
            try (var walk = Files.walk(path)) {
                walk.forEach(tp -> {
                    if (!Files.isDirectory(tp) && isTarget(path, tp))
                        results.add(CompletableFuture.supplyAsync(() -> task(outPath, path, tp), Util.backgroundExecutor()));
                });
            }
        }

        for (CompletableFuture<List<EntryResult>> result : results) {
            try {
                var ret = result.get();
                for (EntryResult entryResult : ret) {
                    cachedOutput.writeIfNeeded(entryResult.path, entryResult.data, entryResult.hash);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<EntryResult> task(Path outPath, Path inPath, Path path) {
        var rp = inPath.relativize(path);
        var op = outPath.resolve(rp);

        JsonObject model;
        try (Reader reader = new FileReader(path.toFile()); Reader bufReader = new BufferedReader(reader)) {
            model = GSON.fromJson(bufReader, JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return divModel(FNStringUtil.removeExtension(op.toFile().getName()), model).entrySet().stream().map(ret -> {
            var name = ret.getKey();
            var divOutPath = op.getParent().resolve(FNStringUtil.escapeFileName(name, "_") + ".json");
            var divModel = processModel(path, divOutPath, name, ret.getValue());

            byte[] bs;
            HashCode hashCode;
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); HashingOutputStream hashingOutputStream = new HashingOutputStream(Hashing.sha1(), byteArrayOutputStream); InputStream stream = new ByteArrayInputStream(GSON.toJson(divModel).getBytes(StandardCharsets.UTF_8))) {
                FNDataUtil.i2o(stream, hashingOutputStream);
                bs = byteArrayOutputStream.toByteArray();
                hashCode = hashingOutputStream.hash();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return new EntryResult(divOutPath, bs, hashCode);
        }).toList();
    }

    protected JsonObject processModel(Path orizinalPath, Path divOutPath, String groupName, JsonObject model) {
        return model;
    }

    protected Map<String, JsonObject> divModel(String name, JsonObject original) {
        var groups = original.getAsJsonArray("groups");

        if (groups == null) return ImmutableMap.of(name, original);

        Map<String, List<JsonElement>> divGroups = new HashMap<>();

        for (JsonElement group : groups) {
            if (group instanceof JsonObject jsonObject && jsonObject.has("name") && isDivGroup(jsonObject.get("name").getAsString())) {
                var gname = convertDivGroupName(jsonObject.get("name").getAsString());
                divGroups.putIfAbsent(gname, new ArrayList<>());
                divGroups.get(gname).add(group);
            } else {
                divGroups.putIfAbsent(name, new ArrayList<>());
                divGroups.get(name).add(group);
            }
        }

        var orelement = original.getAsJsonArray("elements");

        return divGroups.entrySet().stream().map(ent -> Pair.of(ent.getKey(), ent.getValue().stream().flatMap(r -> getAllChildren(r).stream()).toList())).map(ent -> {
            JsonObject oriCopy = original.deepCopy();
            oriCopy.remove("elements");
            oriCopy.remove("groups");
            JsonArray nelements = new JsonArray();
            for (Integer integer : ent.getRight()) {
                nelements.add(orelement.get(integer));
            }
            oriCopy.add("elements", nelements);
            return Pair.of(ent.getLeft(), oriCopy);
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    protected boolean isChildDir(Path inputFolder, Path path, Path targetPath) {
        var rp = inputFolder.relativize(path);

        var rps = rp.toString().replace("\\", "/");
        var tps = targetPath.toString().replace("\\", "/");

        return rps.startsWith(tps);
    }

    protected boolean isDivGroup(String groupName) {
        return groupName.startsWith("_");
    }

    protected String convertDivGroupName(String groupName) {
        return groupName.substring(1);
    }

    private List<Integer> getAllChildren(JsonElement group) {
        List<Integer> ret = new ArrayList<>();
        if (group instanceof JsonObject groupJson) {
            JsonArray children = groupJson.getAsJsonArray("children");
            for (JsonElement child : children) {
                ret.addAll(getAllChildren(child));
            }
        } else if (group instanceof JsonPrimitive jsonPrimitive && jsonPrimitive.isNumber()) {
            ret.add(jsonPrimitive.getAsInt());
        }
        return ret;
    }

    @Override
    public String getName() {
        return "Model Division";
    }

    private static record EntryResult(Path path, byte[] data, HashCode hash) {
    }
}
