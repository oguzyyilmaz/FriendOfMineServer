package queryTest;


import com.msesoft.fom.domain.Person;
import com.msesoft.fom.domain.Places;
import com.msesoft.fom.repository.WorkRepository;
import com.msesoft.fom.domain.FriendRelationship;
import com.msesoft.fom.domain.WorkRelationship;
import com.msesoft.fom.repository.FriendRepository;
import com.msesoft.fom.repository.PersonRepository;
import com.msesoft.fom.repository.PlacesRepository;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by oguz on 21.06.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Query {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    FriendRepository friendRepository;


    @Autowired
    WorkRepository workRepository;

    @Autowired
    PlacesRepository placesRepository;

    @Test
    public void findFriendByTypeTest () {


        for (FriendRelationship friendRelationship: this.friendRepository.findFriendByType("A5","Facebook")) {
            System.out.println(friendRelationship);
        }
        System.out.println();
    }

    @Test
    public void findFriendAllTest () {


        for (FriendRelationship friendRelationship: this.friendRepository.findFriendAll("A9")) {
            System.out.println(friendRelationship);
        }
        System.out.println();
    }

  /*  @Test
    public void workNotFriendTest () {

        List<Person> list=new ArrayList<Person>();
        for (Person person: this.personRepository.workNotFriend("A73")) {
            list.add(person);
        }
        Random r=new Random();
        System.out.println(list.get(r.nextInt(list.size()-1)).getName()+" İsimli kişide sizinle aynı yerde çalışıyor" +
                " Merhaba Demek İster misin ?");
    }
    @Test
    public void nameSearchTest() {
        Person person = new Person();
        WorkRelationship workRelationship = new WorkRelationship();

        String user = "A1";
        String searchName = "A3";
        System.out.println("Hoş geldin "+ user +" Aramak istediğiniz kişinin ismini giriniz");
        person = personRepository.findByName(searchName);
        Places places = new Places();
        places= placesRepository.workSearch(person.getId());


        System.out.println("Aranan kişi'nin adı : " + person.getName() + "\n" +
                "Aranan kişinin soyadı : " + person.getSurname() + "\n" +
                "Aranan kişi : " + places.getName() + " 'de çalışıyor..." + "\n" +
                "Arkadaş ilişkiniz \n");

        for (FriendRelationship friendRelationship: this.friendRepository.friendWay(5, user,searchName, 3)) {
            System.out.println(friendRelationship);
        }
    }/*
    @Test
    public void workSearchTest () {
        Long user = 1012;
        System.out.println(workRelationshipRepository.findByWorkType("STUDENT"));
        WorkRelationship workRelationship = workRelationshipRepository.findNodeWorkType(user);
        if (workRelationshipRepository.findByWorkType(user)==null) {
            System.out.println("iş bulunamadı");
        } else {
            Places places = placesRepository.workSearch(user);
            System.out.println("\n isim'e göre iş bulma : " + workRelationship.getWorkType() + "\n" +
                    "Nerede Çalışıyor : " + places.getName());

        }
    }*/
    @Test
    public void firstDegreeFriend () {
        String user = "A2";
        String limit = "10";
        List<Person> personList = personRepository.findByFirstDegreeFriend(user);
        for (int i = 0 ; i<personList.size(); i++)
            System.out.println(personList.get(i));
    }



}