package frc.lib.util.auto;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

public class Auto {
    @Expose
    public Map<String, Object> command;

    public Auto(String path) {
        this(Path.of(path));
    }

    public Auto(Path path) {
        String json = "";
        try {
            json = Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create();

        Type type = new TypeToken<Auto>() {}.getType();
        Auto auto = gson.fromJson(json, type);

        System.out.println("AUTO JSON" + gson.toJson(auto));

        this.command = auto.command;
    }
}
