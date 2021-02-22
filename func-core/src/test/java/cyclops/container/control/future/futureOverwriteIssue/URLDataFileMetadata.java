package cyclops.container.control.future.futureOverwriteIssue;

import cyclops.container.control.eager.either.Either;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import lombok.Getter;


@Getter
public class URLDataFileMetadata extends DataFileMetadata {

    private final URL url;

    public URLDataFileMetadata(long customerId,
                               String type,
                               URL url) {
        super(customerId,
              type);
        this.url = url;
    }

    @Override
    public Either<IOException, String> loadContents() {
        try {
            URLConnection conn = url.openConnection();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                                                                              StandardCharsets.UTF_8))) {
                return Either.right(in.lines()
                                      .collect(Collectors.joining("\n")));
            }
        } catch (IOException e) {
            return Either.left(e);
        }
    }

}
