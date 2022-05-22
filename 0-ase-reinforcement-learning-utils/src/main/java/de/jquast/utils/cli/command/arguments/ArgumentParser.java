package de.jquast.utils.cli.command.arguments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArgumentParser {

    public static Arguments parseArguments(String[] args, int startIndex) {
        Map<String, String> options = new HashMap<>();
        List<String> parameters = new ArrayList<>();

        for (int i = startIndex; i < args.length; ++i) {
            String part = args[i];

            if (part.startsWith("--")) {
                if (!parameters.isEmpty())
                    throw new RuntimeException("Options are not empty!");

                if (i + 1 < args.length) {
                    String optionVar = args[i + 1];

                    if (optionVar.startsWith("--")) {
                        options.put(part, null);
                        continue;
                    }
                    //throw new RuntimeException(String.format("Invalid option var!"));

                    options.put(part, optionVar);
                } else {
                    options.put(part, null);
                }

                ++i;
            } else {
                parameters.add(part);
            }
        }

        return new Arguments(options, parameters);
    }

}
