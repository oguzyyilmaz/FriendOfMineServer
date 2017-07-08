package com.msesoft.fom.controller;

import com.msesoft.fom.domain.*;
import com.msesoft.fom.business.PersonBS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("person")
public class PersonController {

    @Autowired
    PersonBS personBS;


    @GetMapping(value = "findByFirstName")
    @ResponseBody
    public CustomPerson findByFirstName (@RequestParam("firstName") String name) {
        return personBS.findByFirstName(name);
    }

    @GetMapping(value = "findByFirstDegreeFriend")
    @ResponseBody
    public List<CustomPerson> findByFirstDegreeFriend(@RequestParam("token") String token) {
        return personBS.findByFirstDegreeFriend(token);
    }

    @GetMapping(value = "deletePerson")
    @ResponseBody
    public void deletePerson(@RequestParam("uniqueId") String uniqueId){
         personBS.deletePerson(uniqueId);
    }

    @GetMapping(value = "workNotFriend")
    @ResponseBody
    public List<CustomPerson> workNotFriend(@RequestParam("uniqueId") String uniqueId) {
        return personBS.workNotFriend(uniqueId);
    }

    @PostMapping(value = "signUp")
    @ResponseBody
    public Person insertPerson(@RequestBody Person person) {
     return personBS.insertPerson(person);
    }

    @GetMapping(value = "signIn")
    @ResponseBody
    public Token singIn(@RequestParam("email") String email, @RequestParam("password") String password){

        return personBS.singIn(email, password);
    }

    @GetMapping(value = "updatePhoto")
    @ResponseBody
    public Person updatephoto(){
        return personBS.updatephoto();
    }



    @GetMapping(value = "friendDegree")
    @ResponseBody
    public List<CustomPerson> findDegreeFriend(@RequestParam("token") String token,@RequestParam("like") String like, @RequestParam("degree") int degree
                                                ,@RequestParam("skip") int skip) {
        return personBS.findDegreeFriend(token,like,degree,skip);
    }

    @GetMapping(value = "regGCM")
    @ResponseBody
    public void registerGCM(@RequestParam("token") String token,@RequestParam("regId") String regId){

        personBS.registerGCM(token, regId);
    }
    @GetMapping(value = "findByToken")
    @ResponseBody
    public CustomPerson findByToken(@RequestParam("token") String token){
        return personBS.findByToken(token);
    }

    @GetMapping(value = "findByUniqueId")
    @ResponseBody
    public CustomPerson findByUniqueId(@RequestParam("uniqueId") String uniqueId){
        return personBS.findByUniqueId(uniqueId);
    }

    @PostMapping(value = "uploadPhoto")
    @ResponseBody
    public void uploadPhoto(@RequestBody Image image){
        personBS.uploadPhoto(image);
    }

    @PostMapping(value = "uploadProfilePhoto")
    @ResponseBody
    public void uploadProfilePhoto(@RequestBody Image image){
        personBS.uploadProfilePhoto(image);
    }


    @GetMapping(value = "media")
    @ResponseBody
    public CustomPerson media (@RequestParam String token) {
        return personBS.media(token);
    }

    @GetMapping(value = "deletePhoto")
    @ResponseBody
    public void deletePhoto (@RequestParam String token,@RequestParam String photoId,@RequestParam int index)
    {
        personBS.deletePhoto(photoId,token,index);
    }

    @PostMapping(value = "registerPerson")
    @ResponseBody
    public void registerPerson (@RequestBody Register register) {
        personBS.registerPerson (register);
    }

    @GetMapping(value = "notifies")
    @ResponseBody
    public List<CustomPerson> notifies(@RequestParam String token){
        return personBS.notifies(token);
    }

    @PostMapping(value = "findContact")
    @ResponseBody
    public List<String> findContact(@RequestBody List<String> numberList){

        return personBS.findContact(numberList);
    }

    @GetMapping(value = "searchFriend")
    @ResponseBody
    public List<CustomPerson> searchFriend (@RequestParam String token,@RequestParam String like) {
        return personBS.searchFriend(token,like);
    }

    @GetMapping(value = "friendSize")
    @ResponseBody
    public int friendSize (@RequestParam String token) {
        return personBS.friendSize(token);
    }

    @PostMapping(value = "updatePerson")
    @ResponseBody
    public CustomPerson updatePerson(@RequestBody CustomPerson customPerson,@RequestParam String token){
        return personBS.updatePerson(customPerson,token);
    }
}