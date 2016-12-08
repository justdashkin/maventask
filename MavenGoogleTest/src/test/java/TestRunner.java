/**
 * Created by Daria_Ivanova2 on 12/8/2016.
 */

import org.jbehave.core.embedder.Embedder;

import java.util.Arrays;
import java.util.List;

public class TestRunner {
    private static Embedder embedder = new Embedder();
    private static List<String> storyPaths = Arrays
            .asList("*.story");

    public static void main(String[] args) {
       // embedder.candidateSteps().add(new GoogleTestSteps());
        embedder.runStoriesAsPaths(storyPaths);
    }
}
