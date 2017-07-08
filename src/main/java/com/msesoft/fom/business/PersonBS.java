package com.msesoft.fom.business;

import com.msesoft.fom.config.Config;
import com.msesoft.fom.domain.*;
import com.msesoft.fom.repository.FriendRepository;
import com.msesoft.fom.repository.MobileSessionRepository;
import com.msesoft.fom.repository.PersonRepository;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PersonBS {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    FriendRepository friendRepository;


    public CustomPerson findByFirstName(String name) {

        Person person = personRepository.findByFirstName(name);
        CustomPerson customPerson = new CustomPerson()
                .setEmail(person.getEmail())
                .setFirstName(person.getFirstName())
                .setLastName(person.getLastName())
                .setGender(person.getGender())
                .setPhoto(person.getPhoto())
                .setPopular(person.getPopular())
                .setHoby(person.getHoby())
                .setPhotoList(person.getPhotoList())
                .setUniqueId(person.getUniqueId());

        return customPerson;
    }

    public List<CustomPerson> findByFirstDegreeFriend(String token) {

        List<CustomPerson> customPersonList = new ArrayList<CustomPerson>();
        for (Person person : personRepository.findByFirstDegreeFriend(token)) {
            CustomPerson customPerson = new CustomPerson()
                    .setEmail(person.getEmail())
                    .setFirstName(person.getFirstName())
                    .setLastName(person.getLastName())
                    .setGender(person.getGender())
                    .setPhoto(person.getPhoto())
                    .setHoby(person.getHoby())
                    .setPopular(person.getPopular())
                    .setPhotoList(person.getPhotoList())
                    .setPhoneNumber(person.getPhoneNumber())
                    .setUniqueId(person.getUniqueId());

            customPersonList.add(customPerson);
        }

        return customPersonList;
    }

    public List<CustomPerson> workNotFriend(String uniqueId) {
        List<CustomPerson> customPersonList = new ArrayList<CustomPerson>();
        for (Person person : personRepository.workNotFriend(uniqueId)) {
            CustomPerson customPerson = new CustomPerson()
                    .setEmail(person.getEmail())
                    .setFirstName(person.getFirstName())
                    .setLastName(person.getLastName())
                    .setGender(person.getGender())
                    .setPhoto(person.getPhoto())
                    .setHoby(person.getHoby())
                    .setUniqueId(person.getUniqueId())
                    .setPopular(person.getPopular())
                    .setPhotoList(person.getPhotoList());
            customPersonList.add(customPerson);
        }

        return customPersonList;
    }

    public Person insertPerson(Person person)
    {
        return personRepository.save(person);
    }

    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public Token singIn(String email, String password) {
        String token = UUID.randomUUID().toString();
        Person person = personRepository.findByEmailAndPassword(email, password);
        if (person != null) {
            Token tokenModel = new Token();
            if (person.getToken() == null) {

                tokenModel.setNew(true);
                tokenModel.setToken(token);
            } else {
                tokenModel.setToken(token);
                tokenModel.setNew(false);
            }
            person.setToken(token);
            personRepository.save(person);

            return tokenModel;
        } else {
            return null;
        }
    }

    public void deletePerson(String uniqueId) {

        personRepository.deletePerson(uniqueId);

    }

    public Person updatephoto() {
        Person person2 = new Person()
                .setEmail("asdasdasd");

        String[] personsPhoto = {"aaa.png", "A1.png", "A2.jpg", "A3.jpg", "A4.jpg", "A5.jpg", "A6.jpg", "lisa.jpg"};
        for (int i = 1; i <= 200; i++) {
            Person person = new Person();
            person = personRepository.findByFirstName("A" + i);
            person.setPhoto(Config.ROOT_URL + "/" + personsPhoto[i % 7]);
            personRepository.save(person);
        }
        return person2;
    }

    public List<CustomPerson> findDegreeFriend(String token, String like, int degree, int skip) {
        return personRepository.findDegreeFriend(token, like, degree, skip);
    }

    public void registerGCM(String token, String regId) {
        Person person = new Person();
        person = personRepository.findByToken(token);
        person.setDeviceID(regId);
        personRepository.save(person);
    }

    public CustomPerson findByToken(String token) {
        Person person = personRepository.findByToken(token);
        if (person != null) {
            CustomPerson customPerson = new CustomPerson()
                    .setEmail(person.getEmail())
                    .setFirstName(person.getFirstName())
                    .setGender(person.getGender())
                    .setHoby(person.getHoby())
                    .setLastName(person.getLastName())
                    .setPhoto(person.getPhoto())
                    .setUniqueId(person.getUniqueId())
                    .setPopular(person.getPopular())
                    .setPhotoList(person.getPhotoList())
                    .setOccupation(person.getOccupation())
                    .setSchool(person.getSchool());
            return customPerson;
        } else {

            return null;
        }
    }

    public CustomPerson findByUniqueId(String uniqueId) {
        Person person = personRepository.findByUniqueId(uniqueId);
        CustomPerson customPerson = new CustomPerson()
                .setEmail(person.getEmail())
                .setFirstName(person.getFirstName())
                .setGender(person.getGender())
                .setHoby(person.getHoby())
                .setLastName(person.getLastName())
                .setPhoto(person.getPhoto())
                .setUniqueId(person.getUniqueId())
                .setPopular(person.getPopular())
                .setPhotoList(person.getPhotoList())
                .setOccupation(person.getOccupation())
                .setSchool(person.getSchool());
        return customPerson;
    }

    public void uploadPhoto(Image image) {

        byte[] imageByteArray = Base64.decodeBase64(image.getBase64String());
        Person person = new Person();

        person = personRepository.findByToken(image.getToken());
        String photoId = UUID.randomUUID().toString();
        System.out.println(photoId);
        ;
        person.getPhotoList().add(Config.ROOT_URL + "/fomPic/" + person.getUniqueId() + "/media/" + photoId + ".jpg");
        System.out.println(person.getPhotoList());

        personRepository.save(person);

        try {
            File filedir = new File("//var//www//html//fomPic//" + person.getUniqueId());
            filedir.mkdir();
            File filedir2 = new File("//var//www//html//fomPic//" + person.getUniqueId() + "//media//");
            filedir2.mkdir();
            FileOutputStream imageOutFile = new FileOutputStream("//var//www//html//fomPic//" + person.getUniqueId() + "//media//" + photoId + ".jpg");
            imageOutFile.write(imageByteArray);
            imageOutFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CustomPerson media(String token) {
        Person person = new Person();
        CustomPerson customPerson = new CustomPerson();

        person = personRepository.findByToken(token);

        customPerson.setFirstName(person.getFirstName())
                .setLastName(person.getLastName())
                .setEmail(person.getEmail())
                .setPhotoList(person.getPhotoList())
                .setUniqueId(person.getUniqueId())
                .setHoby(person.getHoby())
                .setPopular(person.getPopular())
                .setGender(person.getGender());

        return customPerson;
    }

    public void uploadProfilePhoto(Image image) {
        System.out.println("change profile photo");
        byte[] imageByteArray = Base64.decodeBase64(image.getBase64String());
        Person person = new Person();

        person = personRepository.findByToken(image.getToken());
        person.setPhoto(Config.ROOT_URL + "/fomPic/" + person.getUniqueId() + "/profilePicture/pro.jpg");
        System.out.println(person.getPhotoList());
        personRepository.save(person);
        try {
            File filedir = new File("//var//www//html//fomPic//" + person.getUniqueId());
            filedir.mkdir();
            File filedir2 = new File("//var//www//html//fomPic//" + person.getUniqueId() + "//profilePicture//");
            filedir2.mkdir();
            FileOutputStream imageOutFile = new FileOutputStream("//var//www//html//fomPic//" + person.getUniqueId() + "//profilePicture//pro.jpg");
            imageOutFile.write(imageByteArray);
            imageOutFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePhoto(String photoFile, String token, int index) {
        Person person = new Person();
        person = personRepository.findByToken(token);
        person.getPhotoList().remove(index);
        personRepository.save(person);
        String[] photoId = photoFile.split("/");
        System.out.println(photoId[photoId.length - 1]);
        File deleteFile = new File("//var//www//html//fomPic//" + person.getUniqueId() + "//media//" + photoId[photoId.length - 1]);
        deleteFile.delete();
    }

    public List<CustomPerson> notifies(String token) {
        List<String> list = personRepository.findByToken(token).getNotifyList();
        List<CustomPerson> persons = new ArrayList<CustomPerson>();
        for (int i = 0; i < list.size(); i++) {
            Person person = personRepository.findByUniqueId(list.get(i));

            CustomPerson customPerson = new CustomPerson()
                    .setEmail(person.getEmail())
                    .setLastName(person.getLastName())
                    .setUniqueId(person.getUniqueId())
                    .setPhoto(person.getPhoto())
                    .setFirstName(person.getFirstName())
                    .setHoby(person.getHoby())
                    .setGender(person.getGender())
                    .setPhotoList(person.getPhotoList());


            persons.add(customPerson);
        }
        return persons;

    }

    public void registerPerson(Register register) {
        Person person = register.getPerson();
        Person isPerson = personRepository.findByPhoneNumber(person.getPhoneNumber());
        if (isPerson != null) {
            if (isPerson.isActive()) {
                System.out.println("Kişi zaten var !");
            } else {
                isPerson.setActive(true)
                        .setEmail(person.getEmail())
                        .setFirstName(person.getFirstName())
                        .setLastName(person.getLastName())
                        .setPassword(person.getPassword());
                personRepository.save(isPerson);


                List<String> phoneList = personRepository.findNotContact(register.getContactPhoneList());
                List<Person> personList = new ArrayList<>();
                // rehber de olup da profili olmayanlara profil oluşturur.
                for (String number : phoneList) {
                    String uniqueId = UUID.randomUUID().toString();
                    Person person1 = new Person()
                            .setPhoneNumber(number)
                            .setActive(false)
                            .setUniqueId(uniqueId)
                            .setPhoto(Config.ROOT_URL + "/fomPic/" + uniqueId + "/profilePicture/pro.jpg");
                    try {
                        File imageFile = new File("//var//www//html//pro.jpg");
                        File filedir = new File("//var//www//html//fomPic//" + person.getUniqueId());
                        filedir.mkdir();
                        File filedir2 = new File("//var//www//html//fomPic//" + person.getUniqueId() + "//profilePicture//pro.jpg");
                        filedir2.mkdir();
                        FileUtils.copyFile(imageFile, filedir2);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    personList.add(person1);
                }
                if (personList.size() > 0)
                    personRepository.save(personList);

                List<FriendRelationship> friendRelationships1 = new ArrayList<>();
                List<FriendRelationship> friendRelationships2 = new ArrayList<>();

                for (Person person2 : personRepository.findContact(register.getContactPhoneList())) {
                    FriendRelationship friendRelationship1 = new FriendRelationship().setEndNode(isPerson)
                            .setStartNode(person2);
                    friendRelationships1.add(friendRelationship1);
                    FriendRelationship friendRelationship2 = new FriendRelationship().setEndNode(person2)
                            .setStartNode(isPerson);
                    friendRelationships2.add(friendRelationship2);

                }


                friendRepository.save(friendRelationships1);
                friendRepository.save(friendRelationships2);

            }
        } else {
            person.setActive(true)
                    .setUniqueId(UUID.randomUUID().toString())
                    .setPhoto(Config.ROOT_URL + "/fomPic/" + person.getUniqueId() + "/profilePicture/pro.jpg");
            try {
                File imageFile = new File("//var//www//html//pro.jpg");
                File filedir = new File("//var//www//html//fomPic//" + person.getUniqueId());
                filedir.mkdir();
                File filedir2 = new File("//var//www//html//fomPic//" + person.getUniqueId() + "//profilePicture//pro.jpg");
                filedir2.mkdir();
                //FileOutputStream imageOutFile = new FileOutputStream("//var//www//html//fomPic//" + person.getUniqueId() + "//profilePicture//pro.jpg");
                FileUtils.copyFile(imageFile, filedir2);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<String> phoneList = personRepository.findNotContact(register.getContactPhoneList());
            List<Person> personList = new ArrayList<>();
            for (String number : phoneList) {
                String uniqueId = UUID.randomUUID().toString();
                Person person1 = new Person()
                        .setPhoneNumber(number)
                        .setActive(false)
                        .setUniqueId(uniqueId)
                        .setPhoto(Config.ROOT_URL + "/fomPic/" + uniqueId + "/profilePicture/pro.jpg");
                try {
                    File imageFile = new File("//var//www//html//pro.jpg");
                    File filedir = new File("//var//www//html//fomPic//" + person1.getUniqueId());
                    filedir.mkdir();
                    File filedir2 = new File("//var//www//html//fomPic//" + person1.getUniqueId() + "//profilePicture//pro.jpg");
                    filedir2.mkdir();
                    FileUtils.copyFile(imageFile, filedir2);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                personList.add(person1);
            }
            personRepository.save(person);

/*
            List<String> phoneList = personRepository.findNotContact(register.getContactPhoneList());
            List<Person> personList = new ArrayList<>();
            // rehber de olup da profili olmayanlara profil oluşturur.
            for (String number : phoneList) {
                String uniqueId = UUID.randomUUID().toString();
                Person person1 = new Person()
                        .setPhoneNumber(number)
                        .setActive(false)
                        .setUniqueId(uniqueId)
                        .setPhoto(Config.ROOT_URL + "/fomPic/" + uniqueId + "/profilePicture/pro.jpg");
                try {
                    File imageFile = new File("//var//www//html//pro.jpg");
                    File filedir = new File("//var//www//html//fomPic//" + person.getUniqueId());
                    filedir.mkdir();
                    File filedir2 = new File("//var//www//html//fomPic//" + person.getUniqueId() + "//profilePicture//pro.jpg");
                    filedir2.mkdir();
                    //FileOutputStream imageOutFile = new FileOutputStream("//var//www//html//fomPic//" + person.getUniqueId() + "//profilePicture//pro.jpg");
                    FileUtils.copyFile(imageFile, filedir2);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                personList.add(person1);
            }*/
            if (personList.size() > 0)
                personRepository.save(personList);

            List<FriendRelationship> friendRelationships1 = new ArrayList<>();
            List<FriendRelationship> friendRelationships2 = new ArrayList<>();

            for (Person person2 : personRepository.findContact(register.getContactPhoneList())) {
                FriendRelationship friendRelationship1 = new FriendRelationship().setEndNode(person)
                        .setStartNode(person2);
                friendRelationships1.add(friendRelationship1);
                FriendRelationship friendRelationship2 = new FriendRelationship().setEndNode(person2)
                        .setStartNode(person);
                friendRelationships2.add(friendRelationship2);

            }


            friendRepository.save(friendRelationships1);
            friendRepository.save(friendRelationships2);


        }
 }

    public List<String> findContact(List<String> numberList) {
        List<String> list = new ArrayList<>();

        for (Person person : personRepository.findContact(numberList)) {
            list.add(person.getPhoneNumber());
        }
        return list;
    }

    public int friendSize(String token) {
        return personRepository.findByFirstDegreeFriend(token).size();
    }

    public List<CustomPerson> searchFriend(String token, String like) {
        personRepository.findFriendList(token, like);
        List<CustomPerson> customPersons = new ArrayList<>();
        for (Person person : personRepository.findFriendList(token, like)) {
            CustomPerson customPerson = new CustomPerson()
                    .setEmail(person.getEmail())
                    .setPhoto(person.getPhoto())
                    .setUniqueId(person.getUniqueId())
                    .setLastName(person.getLastName())
                    .setGender(person.getGender())
                    .setPhotoList(person.getPhotoList())
                    .setFirstName(person.getFirstName())
                    .setHoby(person.getHoby())
                    .setPhoneNumber(person.getPhoneNumber())
                    .setPopular(person.getPopular());
            customPersons.add(customPerson);
        }
        return customPersons;
    }

    public CustomPerson updatePerson(CustomPerson customPerson, String token) {
        Person person = personRepository.findByToken(token);
        person.setOccupation(customPerson.getOccupation())
                .setFirstName(customPerson.getFirstName())
                .setLastName(customPerson.getLastName())
                .setHoby(customPerson.getHoby())
                .setGender(customPerson.getGender())
                .setSchool(customPerson.getSchool());
        personRepository.save(person);
        return customPerson;
    }
}
