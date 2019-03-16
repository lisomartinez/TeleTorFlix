package com.teletorflix.app.dtos;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonTestFiles {
    private static Path path = Paths.get("src", "test", "resources", "tvMaze");

    private JsonTestFiles() {
    }

    public static File getExternalsExpected() {
        return path.resolve("ExternalsJson_Expected.json").toFile();
    }

    public static File getShow() {
        return path.resolve("ShowJson_Deserialize.json").toFile();
    }

    public static File getShowWithSummaryBlank() {
        return path.resolve("ShowJson_Deserialize_with_summary_blank.json").toFile();
    }

    public static File getShowSerielizeExpected() {
        return path.resolve("ShowJson_Serialize_Expected.json").toFile();
    }

    public static File getExpectedImage() {
        return path.resolve("ImageJson_Expected.json").toFile();
    }

    public static File getImageDeserialize() {
        return path.resolve("ImageJson_Expected.json").toFile();
    }

    public static File getShowPage0() {
        return path.resolve("TvMaze_Shows_Page0.json").toFile();
    }

    public static File getSeasonOne() {
        return path.resolve("TvMaze_Season_One.json").toFile();
    }

    public static File getShowSeasons() {
        return path.resolve("TvMaze_ShowOne_AllSeasons.json").toFile();
    }

    public static File getSeason6233Episodes() {
        return path.resolve("Episodes_Season6233.json").toFile();
    }
}
