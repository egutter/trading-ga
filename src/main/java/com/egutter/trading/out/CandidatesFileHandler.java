package com.egutter.trading.out;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.out.adapters.BitStringGsonAdapter;
import com.egutter.trading.out.adapters.ClassTypeAdapterFactory;
import com.egutter.trading.out.adapters.JodaLocalDateGsonAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.joda.time.LocalDate;
import org.uncommons.maths.binary.BitString;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CandidatesFileHandler {

    public static final String ALL_CANDIDATES_JSON_FILE_NAME = "all_candidates.json";
//    public static final String ALL_CANDIDATES_JSON_FILE_NAME = "2020-2021-04_all_candidates_last_good.json";
    private final Gson gson;

    public CandidatesFileHandler() {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapterFactory(new ClassTypeAdapterFactory());
        gsonBuilder.registerTypeAdapter(BitString.class, new BitStringGsonAdapter());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new JodaLocalDateGsonAdapter());
        gson = gsonBuilder.create();
    }

    public void toJson(Collection<Candidate> candidates) {
        writeFile(gson.toJson(candidates));
    }

    public List<BitString> chromosomesfromJson() {
        return fromJson().stream().map(candidate -> candidate.getChromosome()).distinct().collect(Collectors.toList());
    }

    public List<Candidate> fromJson() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(ALL_CANDIDATES_JSON_FILE_NAME).toURI()));
            Type listType = new TypeToken<ArrayList<Candidate>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void writeFile(String json) {
        try {
            String fileName = LocalDate.now().toString() + "_" + ALL_CANDIDATES_JSON_FILE_NAME;
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
