package com.ib.ai;

import com.ib.ai.model.implementation.GradientBoostedTreesImpl;
import com.ib.ai.utils.Pair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Predicate;

public class EntryPointGradientBoostedTreesEvaluation {
    public static void main(String... args) {
        try {
            Predicate<Double> filterMinute = val -> val == 20 || val == 40 || val == 0;

            var randomForestImpl = new GradientBoostedTreesImpl(
                    "src/main/resources/AUD_train.csv", "src/main/resources/AUD_test.csv", "",
                    List.of(
                            new Pair<>("Minute", filterMinute)
                    )
            );

            randomForestImpl.evaluateModelPrecision();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
