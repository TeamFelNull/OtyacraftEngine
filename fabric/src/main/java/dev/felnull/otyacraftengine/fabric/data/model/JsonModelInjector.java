package dev.felnull.otyacraftengine.fabric.data.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.felnull.otyacraftengine.data.model.FileModel;
import dev.felnull.otyacraftengine.data.model.OverridePredicate;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class JsonModelInjector {
    private final BiConsumer<ResourceLocation, Supplier<JsonElement>> originalModelOutput;
    private final List<Pair<FileModel, List<OverridePredicate>>> overrides = new ArrayList<>();

    public JsonModelInjector(BiConsumer<ResourceLocation, Supplier<JsonElement>> originalModelOutput) {
        this.originalModelOutput = originalModelOutput;
    }

    public BiConsumer<ResourceLocation, Supplier<JsonElement>> injectedModelOutput() {
        BiConsumer<ResourceLocation, Supplier<JsonElement>> ret = (location, jsonElementSupplier) -> {
            originalModelOutput.accept(location, () -> injectJsonModel(jsonElementSupplier.get()));
        };
        return ret;
    }

    private JsonElement injectJsonModel(JsonElement jsonElement) {
        if (jsonElement.isJsonObject()) {
            var jo = jsonElement.getAsJsonObject();
            var ja = new JsonArray();

            this.overrides.forEach(it -> {
                var fileModel = it.getLeft();
                var predicates = it.getRight();

                var apjso = new JsonObject();
                apjso.addProperty("model", fileModel.getLocation().toString());

                var pjso = new JsonObject();

                for (OverridePredicate predicate : predicates) {
                    pjso.addProperty(predicate.key().toString(), predicate.value());
                }

                apjso.add("predicate", pjso);

                ja.add(apjso);
            });

            jo.add("overrides", ja);
        }
        return jsonElement;
    }

    public void addOverride(FileModel model, List<OverridePredicate> predicates) {
        this.overrides.add(Pair.of(model, predicates));
    }

}
