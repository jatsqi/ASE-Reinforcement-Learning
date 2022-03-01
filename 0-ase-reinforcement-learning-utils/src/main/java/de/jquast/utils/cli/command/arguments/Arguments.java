package de.jquast.utils.cli.command.arguments;

import java.util.List;
import java.util.Map;

public record Arguments(Map<String, String> options, List<String> parameters) {
}
