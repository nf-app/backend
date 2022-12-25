package cloud.ptl.api.harvest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/harvest")
public class HarvestController {
    @GetMapping("/order")
    public String order() {

        return "Ordered";
    }
}
