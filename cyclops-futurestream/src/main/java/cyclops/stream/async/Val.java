package cyclops.stream.async;

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
