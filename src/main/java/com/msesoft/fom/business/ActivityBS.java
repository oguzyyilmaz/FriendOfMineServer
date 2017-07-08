package com.msesoft.fom.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.msesoft.fom.config.Config;
import com.msesoft.fom.domain.*;
import com.msesoft.fom.repository.ActivityRepository;
import com.msesoft.fom.repository.PersonRepository;
import com.msesoft.fom.repository.RelationRepository;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.list.CursorableLinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by oguz on 9/5/16.
 */
@Service
public class ActivityBS {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RelationRepository relationRepository;

    public void share(ActivityModelCustom activityModelCustom, String token, HttpServletRequest req, HttpServletResponse resp) {
        CustomPerson customPerson = activityModelCustom.getPerson();
        Person person;
        person = personRepository.findByUniqueId(activityModelCustom.getPerson().getUniqueId());
        List<String> customPersonUniqueId = new ArrayList<>();
        String unique = UUID.randomUUID().toString();
        String uniquePhoto=UUID.randomUUID().toString();
        ActivityModel activityModel = new ActivityModel()
                .setActivityDate(activityModelCustom.getActivityDate())
                .setActivityDescription(activityModelCustom.getActivityDescription())
                .setActivityName(activityModelCustom.getActivityName())
                .setActivityPhoto(activityModelCustom.getActivityPhoto())
                .setActivityPlaces(activityModelCustom.getActivityPlaces())
                .setPersonUniqueId(customPerson.getUniqueId())
                .setActivityArea(activityModelCustom.getActivityArea())
                .setPhotoId(Config.ROOT_URL+"/activityPhoto/"
                        + unique
                        + "/" + uniquePhoto +".jpg")
                .setUniqueId(unique);
        activityModel = activityRepository.save(activityModel);
        byte[] imageByteArray = Base64.decodeBase64(activityModelCustom.getImage().getBase64String());
        try {
            File filedir = new File("//var//www//html//activityPhoto//");
            filedir.mkdir();
            File filedir2 = new File("//var//www//html//activityPhoto//" + activityModel.getUniqueId());
            filedir2.mkdir();
            FileOutputStream imageOutFile = null;
            imageOutFile = new FileOutputStream("//var//www//html//activityPhoto//" + activityModel.getUniqueId() + "//" +uniquePhoto +".jpg");
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RelationshipModel relationshipModel = new RelationshipModel();
        relationshipModel.setStartNode(person)
                .setEndNode(activityModel);
        relationRepository.save(relationshipModel);

        ///////////////////////////////////////////////////////////////////////////////////////
        String GOOGLE_SERVER_KEY = "AIzaSyB88PAc35WT1E0_tGsFv-XHwtOOdF0QCsk";
        String MESSAGE_KEY = "message";

        Person personByToken = personRepository.findByToken(token);
        CustomPerson sharedPerson = new CustomPerson()
                .setUniqueId(personByToken.getUniqueId())
                .setHoby(personByToken.getHoby())
                .setPhoto(personByToken.getPhoto())
                .setLastName(personByToken.getLastName())
                .setFirstName(personByToken.getFirstName())
                .setGender(personByToken.getGender())
                .setEmail(personByToken.getEmail());

        List<Person> persons = new ArrayList<>();
        if (activityModelCustom.getActivityArea().equals("all")){
            persons.addAll(personRepository.findByDegreeFriend(token));
            persons.addAll(personRepository.findByFirstDegreeFriend(token));
        }else if(activityModelCustom.getActivityArea().equals("friend")){
            persons.addAll(personRepository.findByFirstDegreeFriend(token));
        }


        ObjectMapper mapper = new ObjectMapper();
        String customPersonJSON = null;
        try {

            customPersonJSON = mapper.writeValueAsString(customPerson);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<String> deviceIds = new ArrayList<>();
        for(Person personDevice:persons){
            deviceIds.add(personDevice.getDeviceID());
        }



        InetAddress ip;

        MulticastResult result = null;
        String share = req.getParameter("shareRegId");
        String sendMessage ="A";
        sendMessage+=customPersonJSON;
        try {
            Sender sender = new Sender(GOOGLE_SERVER_KEY);
            Message message = new Message.Builder().timeToLive(30).delayWhileIdle(true).addData(MESSAGE_KEY, sendMessage)
                    .build();
            result = sender.send(message, deviceIds, 1);
            req.setAttribute("pushStatus", result.toString());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            req.setAttribute("pushStatus", "RegId required: " + ioe.toString());
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("pushStatus", e.toString());
        }

    }

    public List<ActivityModelCustom> allActivity(String token) {
        List<ActivityModel> activityModels = new ArrayList<>();
        activityModels.addAll(activityRepository.degreeActivity(token));
        activityModels.addAll(activityRepository.friendActivity(token));
        Person personUniqueId = personRepository.findByToken(token);
        activityModels.addAll(activityRepository.findByPersonUniqueId(personUniqueId.getUniqueId()));
        List<ActivityModelCustom> activityModelCustomList = new ArrayList<>();
        for (int i = 0; i < activityModels.size(); i++) {
            Person person = personRepository.findByUniqueId(activityModels.get(i).getPersonUniqueId());
            CustomPerson customPerson = new CustomPerson()
                    .setGender(person.getGender())
                    .setPopular(person.getPopular())
                    .setEmail(person.getEmail())
                    .setPhoneNumber(person.getPhoneNumber())
                    .setLastName(person.getLastName())
                    .setFirstName(person.getFirstName())
                    .setHoby(person.getHoby())
                    .setPhoto(person.getPhoto())
                    .setPhotoList(person.getPhotoList())
                    .setUniqueId(person.getUniqueId());
            ActivityModelCustom activityModelCustom = new ActivityModelCustom()
                    .setPerson(customPerson)
                    .setActivityArea(activityModels.get(i).getActivityArea())
                    .setActivityDate(activityModels.get(i).getActivityDate())
                    .setActivityDescription(activityModels.get(i).getActivityDescription())
                    //activityModelCustomList.get(i).setActivityJoinPerson(activityModels.get(i).getActivityJoinPerson());
                    .setActivityName(activityModels.get(i).getActivityName())
                    .setActivityPhoto(activityModels.get(i).getActivityPhoto())
                    .setPhotoId(activityModels.get(i).getPhotoId())
                    .setUniqueId(activityModels.get(i).getUniqueId())
                    .setJoinList(activityModels.get(i).getJoinList())
                    .setActivityPlaces(activityModels.get(i).getActivityPlaces());
            activityModelCustomList.add(activityModelCustom);

        }

        return activityModelCustomList;
    }

    public void join(String token,String uniqueId) {
        Person person = personRepository.findByToken(token);
        ActivityModel activityModel = activityRepository.findByUniqueId(uniqueId);
        RelationshipModel relationshipModel = new RelationshipModel();
        relationshipModel.setStartNode(person)
                .setEndNode(activityModel);
        relationRepository.save(relationshipModel);
        activityModel.getJoinList().add(person.getUniqueId());
        activityRepository.save(activityModel);
    }

    public void notJoin(String token, String uniqueId) {
        ActivityModel activityModel = activityRepository.findByUniqueId(uniqueId);
        Person person = personRepository.findByToken(token);
        activityModel.getJoinList().remove(person.getUniqueId());
        activityRepository.save(activityModel);
        relationRepository.notJoinActivity(token,uniqueId);

    }

    public List<CustomPerson> joinList(List<String> customPerson) {
        Person person = new Person();
        List<CustomPerson> customPersons = new ArrayList<>();
        for (String uniqueId:customPerson) {
            person = personRepository.findByUniqueId(uniqueId);
            CustomPerson customPerson1 = new CustomPerson()
                    .setEmail(person.getEmail())
                    .setFirstName(person.getFirstName())
                    .setGender(person.getGender())
                    .setHoby(person.getHoby())
                    .setLastName(person.getLastName())
                    .setOccupation(person.getOccupation())
                    .setPhoneNumber(person.getPhoneNumber())
                    .setPopular(person.getPopular())
                    .setPhoto(person.getPhoto())
                    .setPhotoList(person.getPhotoList())
                    .setSchool(person.getSchool())
                    .setUniqueId(person.getUniqueId());
            customPersons.add(customPerson1);
        }
    return customPersons;
    }
}


