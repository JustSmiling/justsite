package comm.justsmile.justsite.springboot.web.rest_api.slack_api;

import org.springframework.web.bind.annotation.*;

@RestController
public class SlackApiController {

    @PostMapping("/slack/msg")
    public String sendMsg(@RequestParam("msg") final String msg){
        return msg;
    }
}
