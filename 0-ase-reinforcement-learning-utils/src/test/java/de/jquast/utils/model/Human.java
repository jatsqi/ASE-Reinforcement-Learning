package de.jquast.utils.model;

import de.jquast.utils.di.annotations.Inject;

public class Human {

    public String name;
    private int age;

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Inject
    public Human(String name) {
        this(name, 50);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public static Human createDummyHuman() {
        return new Human("Johannes Quast", 22);
    }
}
