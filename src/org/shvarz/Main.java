package org.shvarz;

public class Main {


    public static void main(String[] args) {
        String arg = args[0];

        System.out.println();
        System.out.println(arg);

        Model m = new Model(arg);
        m.solve(0);

        System.out.println();
        System.out.println();
    }
}
