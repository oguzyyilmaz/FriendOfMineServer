package data;

import com.msesoft.fom.business.PersonBS;
import com.msesoft.fom.controller.PersonController;
import com.msesoft.fom.domain.Person;
import com.msesoft.fom.domain.Places;
import com.msesoft.fom.domain.FriendRelationship;
import com.msesoft.fom.domain.WorkRelationship;
import com.msesoft.fom.repository.FriendRepository;
import com.msesoft.fom.repository.PersonRepository;
import com.msesoft.fom.repository.PlacesRepository;
import com.msesoft.fom.repository.WorkRepository;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * Created by oguz on 23.06.2016.
 */


public class InsertTestData {
    @Autowired
    PlacesRepository placesRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    WorkRepository workRepository;

    @Autowired
    FriendRepository friendRepository;

    Person friendPerson1 = new Person();
    Person friendPerson2 = new Person();

    @Test
    public void placesInsert() {
        String placesType [] = {"HOSPITAL","ENGINEER OFFICE","SCHOOL","SPORT CLUB"};
        String placesNameHospital [] ={"ANKARA HASTANESİ","GAZİ HASTANESİ","HACETTEPE HASTANESİ"};
        String placesNameEngineerOffice [] ={"BEAM TEKNOLOJİ","ABC TEKNOLOJİ","ABC","TEKNOLOJİ","COMPUTER"};
        String placesNameSchool [] = {"GAZİ ÜNİVERSİTESİ","ANKARA ÜNİVERSİTESİ","ORTA DOĞU TEKNİK ÜNİVERSİTESİ","BİLKENT ÜNİVERSİTESİ","SELÇUK ÜNİVERSİTESİ"};
        String placesNameClub [] = {"GALATASARAY","ANKARAGÜCÜ","FENERBAHÇE","BEŞİKTAŞ","SİVASSPOR","İSTANBUL BÜYÜKŞEHİR BELEDİYE SPOR","TRABZON SPOR","KONYA SPROR"};
        String placesName [] = {"ANKARA HASTANESİ","GAZİ HASTANESİ","HACETTEPE HASTANESİ","BEAM TEKNOLOJİ","ABC TEKNOLOJİ","ABC","TEKNOLOJİ","COMPUTER","GAZİ ÜNİVERSİTESİ","ANKARA ÜNİVERSİTESİ"
                ,"ORTA DOĞU TEKNİK ÜNİVERSİTESİ","BİLKENT ÜNİVERSİTESİ","SELÇUK ÜNİVERSİTES","GALATASARAY","ANKARAGÜCÜ","FENERBAHÇE","BEŞİKTAŞ","SİVASSPOR","İSTANBUL BÜYÜKŞEHİR BELEDİYE SPOR","TRABZON SPOR","KONYA SPROR"};
        String hospital [] = {"NURSE","DOCTOR"};
        String engineer [] = {"ENGINEER","DEVOLOPER"};
        String school [] = {"TEACHER","STUDENT"};
        String sport [] = {"FOOTBOLLER","DIRECTOR"};

        for (int i = 0; i<3; i++) {
            Places places = new Places();
            places.setType(placesType[0])
                    .setName(placesNameHospital[i]);
            placesRepository.save(places);
        }
        for (int i = 0; i<5; i++) {
            Places places = new Places();
            places.setType(placesType[1])
                    .setName(placesNameEngineerOffice[i]);
            placesRepository.save(places);
        }
        for (int i = 0; i<5; i++) {
            Places places = new Places();
            places.setType(placesType[2])
                    .setName(placesNameSchool[i]);
            placesRepository.save(places);
        }
        for (int i = 0; i<8; i++) {
            Places places = new Places();
            places.setType(placesType[3])
                    .setName(placesNameClub[i]);
            placesRepository.save(places);
        }
    }
    @Test
    public void placesAndWorkRelationInsert() {
        String placesType [] = {"HOSPITAL","ENGINEER OFFICE","SCHOOL","SPORT CLUB"};
        String placesName [] = {"ANKARA HASTANESİ","GAZİ HASTANESİ","HACETTEPE HASTANESİ","BEAM TEKNOLOJİ","ABC TEKNOLOJİ","ABC","TEKNOLOJİ","COMPUTER","GAZİ ÜNİVERSİTESİ","ANKARA ÜNİVERSİTESİ"
                ,"ORTA DOĞU TEKNİK ÜNİVERSİTESİ","BİLKENT ÜNİVERSİTESİ","SELÇUK ÜNİVERSİTESİ","GALATASARAY","ANKARAGÜCÜ","FENERBAHÇE","BEŞİKTAŞ","SİVASSPOR","İSTANBUL BÜYÜKŞEHİR BELEDİYE SPOR","TRABZON SPOR","KONYA SPROR"};
        String hospital [] = {"NURSE","DOCTOR"};
        String engineer [] = {"ENGINEER","DEVOLOPER"};
        String school [] = {"TEACHER","STUDENT"};
        String sport [] = {"FOOTBOLLER","DIRECTOR"};
        Random r = new Random();
        int rn2;
        for (int i = 1; i<=200; i=i+2) {
            rn2=r.nextInt(placesName.length-1);
            WorkRelationship wR = new WorkRelationship();
            Person person = new Person();
            person = personRepository.findByFirstName("A"+i);
            Places places= new Places();

            places = placesRepository.findByName(placesName[rn2]);
            if (places.getType().equals(placesType[0]))  {
                wR.setStartNode(person)
                        .setEndNode(places)
                        .setWorkType(hospital[i%2]);
                workRepository.save(wR);
            }
            else if (places.getType().equals(placesType[1])) {
                wR.setStartNode(person)
                        .setEndNode(places)
                        .setWorkType(engineer[i%2]);
                workRepository.save(wR);
            }
            else if (places.getType().equals(placesType[2])) {
                wR.setStartNode(person)
                        .setEndNode(places)
                        .setWorkType(school[i%2]);
                workRepository.save(wR);

            }
            else if (places.getType().equals(placesType[3])) {
                wR.setStartNode(person)
                        .setEndNode(places)
                        .setWorkType(sport[i%2]);
                workRepository.save(wR);
            }
        }
    }

    @Test
    public void testPersonRepositoryInsert () {

        personRepository.deleteAll();
        char genders [] = {'M','F'};
        String Hoby [] = {"Music","Art","Play Game"};

        for(int i= 1; i<=200; i++) {

            Person person = new Person()
                    .setGender("")
                    .setHoby(Hoby[i%3])
                    .setFirstName("A"+i)
                    .setPhoto("/home/photo/img.jpeg")
                    .setLastName("B"+i)
                    .setEmail("ab"+i+"@fom.com")
                    .setUniqueId(UUID.randomUUID().toString());
            person =personRepository.save(person);
            System.out.println(person.getEmail());
        }
        Random random = new Random();
        String[] friend={"Facebook","Instagram","Work"};
        for (int i = 1; i<=200; i++) {

            friendPerson1 = personRepository.findByFirstName("A"+i);
            for (int j = 1; j<=10; j++) {
                int [] rndm = new int[10];
                int rondomFriend=random.nextInt(199)+1;

                boolean deger=true;
                for(int k=0;k<rndm.length;k++){
                    if(rndm[k]==rondomFriend)
                        deger=false;

                }
                rndm[j-1]=rondomFriend;
                if (i != rondomFriend && deger ){
                    friendPerson2 = personRepository.findByFirstName("A"+rondomFriend);
                    FriendRelationship fr = new FriendRelationship();
                    fr.setStartNode(friendPerson1);
                    fr.setEndNode(friendPerson2);
                    fr.setFriendType(friend[i%3]);
                    FriendRelationship fr2 = new FriendRelationship();
                    fr2.setStartNode(friendPerson2);
                    fr2.setEndNode(friendPerson1);
                    fr2.setFriendType(friend[i%3]);
                    friendRepository.save(fr);
                    friendRepository.save(fr2);
                }
                deger=true;
            }
        }
    }
    @Test
    public void relaitonTest(){
        friendPerson2 = personRepository.findByFirstName("A187");
        friendPerson1 = personRepository.findByFirstName("A194");
        FriendRelationship fr = new FriendRelationship();
        fr.setStartNode(friendPerson2);
        fr.setEndNode(friendPerson1);
        fr.setFriendType("Work");
        friendRepository.save(fr);
    }

    @Test
    public void test2(){


    }
    @Test
    public void setupDB () {
        Person person = new Person()
                .setFirstName("oguz")
                .setLastName("yılmaz")
                .setEmail("yilmazoguz94@gmail.com")
                .setGender("Male")
                .setPassword("1234");
        System.out.println(person.getFirstName());
        personRepository.save(person);

    }
    @Test
    public void testFile () {
        File imageFile = new File("//var//www//html//pro.jpg");
        File filedir = new File("//var//www//html//fomPic//" + "1233");
        filedir.mkdir();
        File filedir2 = new File("//var//www//html//fomPic//" + "1233" + "//profilePicture//pro.jpg");
        filedir2.mkdir();
        try {
            FileUtils.copyFile(imageFile, filedir2);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
