package cyclops.container.talk;

import cyclops.container.control.Option;

public class OptionExample {

    public static void main(String[] args) {
        Option.none()
              .orElse(-1);
    }
}
