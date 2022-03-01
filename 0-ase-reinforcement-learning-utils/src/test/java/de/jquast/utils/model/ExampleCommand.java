package de.jquast.utils.model;

import de.jquast.utils.cli.command.annotations.Command;
import de.jquast.utils.cli.command.annotations.Option;
import de.jquast.utils.cli.command.annotations.Parameter;

@Command(name = "helloFriend", description = "fcjjffej")
public class ExampleCommand implements Runnable {

    @Option(names = { "--test", "--test2" }, required = true)
    public int test;

    @Parameter(index = 0)
    public String param1;

    public ExampleCommand() {

    }

    @Override
    public void run() {
        System.out.println(test);
        System.out.println(String.format("Test '%s' war %s", test, param1));
    }
}
