package com.msesoft.fom.controller;

import com.msesoft.fom.business.FriendRelationshipBS;
import com.msesoft.fom.domain.CustomFriendRelationship;
import com.msesoft.fom.domain.FriendRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by oguz on 23.06.2016.
 */
@Controller
@RequestMapping("friendRelationShip")
public class FriendController {

    @Autowired
    FriendRelationshipBS friendRelationshipBS;



    @GetMapping(value = "/findFriendAll")
    @ResponseBody
    public List<FriendRelationship> findFriendAll (@RequestParam("person") String person) {
        return friendRelationshipBS.findFriendAll(person);
    }

    @GetMapping(value = "/findFriendByType")
    @ResponseBody
    public List<FriendRelationship> findFriendByType(@RequestParam("person") String person,@RequestParam("type") String type) {
        return  friendRelationshipBS.findFriendByType(person,type);
    }
    @GetMapping(value = "/friendWay")
    @ResponseBody
    public List<CustomFriendRelationship> friendWay(@RequestParam("startNode") String startNode, @RequestParam("endNode") String endNode){
        return friendRelationshipBS.friendWay(startNode,endNode);
    }

    @PostMapping(value = "saveFriend")
    @ResponseBody
    public void saveFriend(@RequestParam("token") String token,@RequestParam("uniqueId") String uniqueId){
         friendRelationshipBS.saveFriend(token, uniqueId);
    }

    @GetMapping(value = "gcmAddFriendNTF")
    public void gcmAddFriendNTF(@RequestParam("friendAdder") String friendAdder, @RequestParam("friendAdded") String friendAdded, HttpServletRequest req, HttpServletResponse resp) {
        friendRelationshipBS.GCMNotification(friendAdder, friendAdded, req, resp);
    }

    @GetMapping(value = "/deleteFriend")
    public void deleteFriend(@RequestParam("id") Long id) {
        friendRelationshipBS.deleteFriend(id);
    }

    @GetMapping(value = "/deleteNotification")
    @ResponseBody
    public void deleteNotification(@RequestParam("token")String token,@RequestParam("uniqueId") String uniqueId){ friendRelationshipBS.deleteNotification(token, uniqueId);}




}
