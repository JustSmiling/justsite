package comm.justsmile.justsite.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(SlackController.REF_PATH)
public class SlackController extends DefaultController{

    static final String REF_PATH = "/slack";

    public SlackController() {
        super.refPath = REF_PATH;
    }
}