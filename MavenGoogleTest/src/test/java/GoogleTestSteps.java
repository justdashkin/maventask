import org.jbehave.core.annotations.Given;
import org.jbehave.core.model.Lifecycle;

import java.util.List;

/**
 * Created by Daria_Ivanova2 on 12/8/2016.
 */
public class GoogleTestSteps extends Lifecycle.Steps {


    public GoogleTestSteps(List<String> steps) {
        super(steps);
    }

    @Given("I open main google page")
    public void iOpenMainPage(){
        int i = 0;
    }
}
