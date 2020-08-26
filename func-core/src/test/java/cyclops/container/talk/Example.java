package cyclops.container.talk;


import cyclops.container.immutable.impl.Seq;

public class Example {

    public static void main(String[] args) {

        Seq<Integer> seq = Seq.empty();
        int first = seq.fold(c -> c.head(),
                             n -> -1);


    }
}
