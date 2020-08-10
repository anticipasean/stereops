package cyclops.stream.async;

import java.util.concurrent.Executor;

public interface HasExec {

    Executor getExec();
}
