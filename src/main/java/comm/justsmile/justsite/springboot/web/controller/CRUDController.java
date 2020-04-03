package comm.justsmile.justsite.springboot.web.controller;

import comm.justsmile.justsite.springboot.web.controller.default_ctrl.DefaultController;
import comm.justsmile.justsite.springboot.web.global.config.auth.dto.SessionUser;
import comm.justsmile.justsite.springboot.web.rest_api.crud_api.dto.PostsResponseDto;
import comm.justsmile.justsite.springboot.web.rest_api.crud_api.service.PostsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(CRUDController.REF_PATH)
public class CRUDController extends DefaultController {

    static final String REF_PATH = "/crud";
    private final PostsService postsService;
    private final HttpSession httpSession;

    public CRUDController(PostsService postsService, HttpSession httpSession) {
        super.refPath = REF_PATH;
        this.postsService = postsService;
        this.httpSession = httpSession;
    }

    @GetMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            System.out.println(user.getName());
            model.addAttribute("loginUserName", user.getName());
        }
        return resultPath("/index");
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return resultPath("/posts-save");
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        System.out.println(resultPath("posts-update"));
        return resultPath("/posts-update");
    }
}
