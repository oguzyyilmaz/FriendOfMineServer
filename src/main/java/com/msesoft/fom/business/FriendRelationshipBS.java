package com.msesoft.fom.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msesoft.fom.domain.CustomFriendRelationship;
import com.msesoft.fom.domain.CustomPerson;
import com.msesoft.fom.domain.Person;
import com.msesoft.fom.repository.FriendRepository;
import com.msesoft.fom.domain.FriendRelationship;
import com.msesoft.fom.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by oguz on 23.06.2016.
 */
@Service
public class FriendRelationshipBS {

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    PersonRepository personRepository;

    public List<FriendRelationship> findFriendAll(String person) {
        return friendRepository.findFriendAll(person);
    }

    public List<FriendRelationship> findFriendByType(String person, String type) {
        return friendRepository.findFriendByType(person, type);
    }

    public List<CustomFriendRelationship> friendWay(String startNode, String endNode) {
        List<CustomFriendRelationship> cFriendRelationships = new ArrayList<>();
       for (FriendRelationship friendRelationship:friendRepository.friendWay( startNode, endNode)) {
            CustomPerson customPerson = new CustomPerson()
                    .setEmail(friendRelationship.getStartNode().getEmail())
                    .setPhoto(friendRelationship.getStartNode().getPhoto())
                    .setPhotoList(friendRelationship.getStartNode().getPhotoList())
                    .setHoby(friendRelationship.getStartNode().getHoby())
                    .setFirstName(friendRelationship.getStartNode().getFirstName())
                    .setLastName(friendRelationship.getStartNode().getLastName())
                    .setGender(friendRelationship.getStartNode().getGender())
                    .setUniqueId(friendRelationship.getStartNode().getUniqueId());
           CustomPerson customPerson1 = new CustomPerson()
                   .setEmail(friendRelationship.getEndNode().getEmail())
                   .setPhoto(friendRelationship.getEndNode().getPhoto())
                   .setPhotoList(friendRelationship.getEndNode().getPhotoList())
                   .setHoby(friendRelationship.getEndNode().getHoby())
                   .setFirstName(friendRelationship.getEndNode().getFirstName())
                   .setLastName(friendRelationship.getEndNode().getLastName())
                   .setGender(friendRelationship.getEndNode().getGender())
                   .setUniqueId(friendRelationship.getEndNode().getUniqueId());
            cFriendRelationships.add(new CustomFriendRelationship().setStartNode(customPerson)
            .setEndNode(customPerson1));

        }

        return cFriendRelationships;
    }

    public void saveFriend(String token,String uniqueId) {
        Person person1 = personRepository.findByToken(token);
        Person person2 = personRepository.findByUniqueId(uniqueId);
        FriendRelationship friendRelationship1 = new FriendRelationship()
                .setEndNode(person2)
                .setStartNode(person1);
        friendRepository.save(friendRelationship1);
        FriendRelationship friendRelationship2 = new FriendRelationship()
                .setEndNode(person1)
                .setStartNode(person2);
        friendRepository.save(friendRelationship2);
        person1.getNotifyList().remove(person2.getUniqueId());
        personRepository.save(person1);

        if (0 == person1.getPopular()) {
            person1.setPopular(10);
        }
        if(0 == person2.getPopular()) {
            person2.setPopular(10);
        }
        float per1,per2;
        per1 = person1.getPopular();
        per2=person2.getPopular();
        person1.setPopular(per1+per2/per1);
        person2.setPopular(per2+per1/per2);
        personRepository.save(person1);
        personRepository.save(person2);
    }


    public void deleteFriend(Long id) {
        friendRepository.delete(id);
    }

    public void GCMNotification(String friendAdder, String friendAdded, HttpServletRequest req, HttpServletResponse resp) {

        String GOOGLE_SERVER_KEY = "AIzaSyB88PAc35WT1E0_tGsFv-XHwtOOdF0QCsk";
        String MESSAGE_KEY = "message";

        Person person = personRepository.findByToken(friendAdder);
        CustomPerson customPerson = new CustomPerson()
                .setUniqueId(person.getUniqueId())
                .setHoby(person.getHoby())
                .setPhoto(person.getPhoto())
                .setLastName(person.getLastName())
                .setFirstName(person.getFirstName())
                .setGender(person.getGender())
                .setEmail(person.getEmail());


        ObjectMapper mapper = new ObjectMapper();
        String customPersonJSON = null;
        try {

            customPersonJSON = mapper.writeValueAsString(customPerson);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Person addedPerson = personRepository.findByUniqueId(friendAdded);
        String regId = addedPerson.getDeviceID();

        if(!addedPerson.getNotifyList().contains(person.getUniqueId()))
        addedPerson.getNotifyList().add(person.getUniqueId());
        personRepository.save(addedPerson);

        InetAddress ip;

        Result result = null;
        String share = req.getParameter("shareRegId");
        String sendMessage ="N";
        sendMessage+=customPersonJSON;
        try {
            Sender sender = new Sender(GOOGLE_SERVER_KEY);
            Message message = new Message.Builder().
                    timeToLive(30).delayWhileIdle(true).addData(MESSAGE_KEY, sendMessage)
                    .build();
            System.out.println("regId: " + regId);
            result = sender.send(message, regId, 1);
            req.setAttribute("pushStatus", result.toString());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            req.setAttribute("pushStatus", "RegId required: " + ioe.toString());
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("pushStatus", e.toString());
        }
    }


    public void deleteNotification(String token, String uniqueId) {

        System.out.println(uniqueId);
        Person person = personRepository.findByToken(token);
        person.getNotifyList().remove(uniqueId);
        personRepository.save(person);
    }

}
