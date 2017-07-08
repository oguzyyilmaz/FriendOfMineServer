package com.msesoft.fom.controller;

import com.msesoft.fom.business.ActivityBS;
import com.msesoft.fom.domain.ActivityModelCustom;
import com.msesoft.fom.domain.CustomPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by oguz on 9/5/16.
 */
@Controller
@RequestMapping("activity")
public class ActivityController {

     @Autowired
     ActivityBS activityBS;

    @PostMapping(value = "share")
    @ResponseBody
    public void shareActivity (@RequestBody ActivityModelCustom activityModelCustom, @RequestParam("token") String token, HttpServletRequest req, HttpServletResponse resp) {
        activityBS.share(activityModelCustom,token, req, resp);
    }

    @GetMapping(value = "allActivity")
    @ResponseBody
    public List<ActivityModelCustom> allActivity (@RequestParam String token) {
        return activityBS.allActivity(token);
    }

    @GetMapping(value = "join")
    @ResponseBody
    public void join (@RequestParam String token,@RequestParam String uniqueId){activityBS.join(token,uniqueId);}

    @GetMapping(value="notJoin")
    @ResponseBody
    public void notJoin (@RequestParam String token, @RequestParam String uniqueId) {
        activityBS.notJoin(token,uniqueId);
    }

    @PostMapping(value = "joinList")
    @ResponseBody
    public List<CustomPerson> joinList (@RequestBody List<String> customPerson) {
        return activityBS.joinList(customPerson);
    }
}
