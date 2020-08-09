package com.oath.cyclops.types.futurestream;

public class Val<T> {

    Pos pos;
    T val;

    public Val(Pos pos,
               T val) {
        this.pos = pos;
        this.val = val;
    }

    enum Pos {
        left,
        right
    }
}
