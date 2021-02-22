package cyclops.container.talk;

import cyclops.container.control.eager.option.Option;

public class OptionExample {

    public static void main(String[] args) {
        Option.none()
              .orElse(-1);
    }
}
