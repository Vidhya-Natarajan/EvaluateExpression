package springevaluateexp.Spring.Boot.Evaluate.Exp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;



import java.util.List;
import java.util.Map;

@RestController
public class ExpController {


    // Save
    //return 201 instead of 200
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/expression")

    public String customerInformation(@RequestBody Expression exp) {
    	EvaluateBoolean eb = new EvaluateBoolean();
        return eb.mainCalculator(exp);
    }

    

}