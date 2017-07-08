package controllerTest;

import com.msesoft.fom.domain.*;
import com.msesoft.fom.repository.PersonRepository;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import org.apache.catalina.util.ParameterMap;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by oguz on 23.06.2016.
 */
public class controllerRandomTest {

    @Autowired
    PersonRepository personRepository;

    @Test
    public void personTest() {
        String name = "A1";
        String uri = new String("http://localhost:8081/person/findByFirstName?firstName=" +
                name);
        RestTemplate restTemplate = new RestTemplate();
        Person person = restTemplate.getForObject(uri,Person.class);
        System.out.println(person.getEmail());


        uri = new String ("http://localhost:8081/person/findByFirstDegreeFriend?name=A1");
        Person [] personlist = restTemplate.getForObject(uri,Person[].class);
        System.out.println(personlist.length);

        uri = new String("http://localhost:8081/person/workNotFriend?name=A1");
        personlist = restTemplate.getForObject(uri,Person[].class);
        System.out.println(personlist.length);
    }
    @Test
    public void friendRelationShip() {
        String uri = new String("http://localhost:8081/friendRelationShip/findFriendAll?person=A1");
        RestTemplate restTemplate = new RestTemplate();
        FriendRelationship[] friendList = restTemplate.getForObject(uri,FriendRelationship[].class);
        System.out.println(friendList.length);


        uri = new String("http://localhost:8081/friendRelationShip/findFriendByType?person=A2&type=Instagram");
        friendList = restTemplate.getForObject(uri,FriendRelationship[].class);
        System.out.println(friendList.length);


        uri = new String("http://localhost:8081/friendRelationShip/friendWay?limit=1&startNode=A1&endNode=A10&length=3");
        friendList = restTemplate.getForObject(uri,FriendRelationship[].class);
        System.out.println(friendList.length);
    }
    @Test
    public void testPersonInsert ()  throws IOException, BiffException {
        String uri = new String("http://localhost:8081/person/alldelete");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(uri,Person.class);

        uri = "http://localhost:8081/person/signUp";
        Person person = new Person();


        String filepath = "//home//oguz//Masaüstü//Adsız Klasör//test.xls";

        File fileExcel = new File(filepath);
        WorkbookSettings ws = new WorkbookSettings();
        ws.setEncoding("Cp1252");

        Workbook workbook = Workbook.getWorkbook(fileExcel,ws);

        Sheet excelPage = workbook.getSheet(0);

        for(int j=0; j<excelPage.getRows(); j++)
        {

            for(int i=0; i<excelPage.getColumns(); i++)
            {

                Cell cell=excelPage.getCell(i, j);
                switch (i) {
                    case 0:
                        person.setFirstName(cell.getContents());
                        System.out.println(i+" "+cell.getContents());
                        break;
                    case 1:
                        person.setLastName(cell.getContents());
                        System.out.println(i+"  "+cell.getContents());
                        break;
                    case 2:
                        person.setPassword(cell.getContents());
                        System.out.println(i+"  "+cell.getContents());
                        break;
                    case 3:
                        if (cell.getContents() ==  "Erkek")
                        person.setGender("E");
                        else
                        person.setGender("K");
                        System.out.println(i+"  "+cell.getContents());
                        break;
                    case 4:
                        person.setEmail(cell.getContents());
                        System.out.println(i+"  "+cell.getContents());
                        break;
                    case 5:
                        person.setHoby(cell.getContents());
                        System.out.println(i+"  "+cell.getContents());
                        break;

                }




            }
            person.setUniqueId(UUID.randomUUID().toString());
            Person mPerson = restTemplate.postForObject(uri,person,Person.class);
            System.out.println(mPerson.getEmail());

        }


    }
    @Test
    public void contactTest(){
        List<String> list=new ArrayList<>();
        list.add("05079972723");
        list.add("02523223232");
        list.add("05334641568");
        RestTemplate restTemplate = new RestTemplate();

        String[] response;
        response = restTemplate.postForObject("http://192.168.0.15:9091/person/findContact",list,String[].class);
        for (int i=0;i<response.length;i++)

        System.out.println(response[i]);

    }
    @Test//1268 1366
    public void personFolder () {
        int sayac =1;
        for (int i=1268; i<1366; i++) {
            Long aa =new Long(i);
            Person person1 = personRepository.findOne(aa);
            File file = new File("//var//www//html//fomPic//AnimePictures//"+ sayac%40+".jpg");
            FileInputStream imageInFile = null;
            try {
                imageInFile = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            byte imageData[] = new byte[(int) file.length()];
            try {
                imageInFile.read(imageData);
                String imageDataString = Base64.encodeBase64URLSafeString(imageData);
                // Converting a Base64 String into Image byte array
                byte[] imageByteArray = Base64.decodeBase64(imageDataString);

                // Write a image byte array into file system
                FileOutputStream imageOutFile = new FileOutputStream("//var//www//html//fomPic//AnimePictures//"+ person1.getUniqueId()+"//profilePicture//pro.jpg");
                person1.setPhoto("http://192.168.2.130/fomPic/"+ person1.getUniqueId()+"/profilePicture/pro.jpg");
                imageOutFile.write(imageByteArray);
                imageInFile.close();
                imageOutFile.close();
                sayac++;
            } catch (IOException e) {
                e.printStackTrace();
            }



        }




    }
    @Test
    public void activityTest () {
        String uri = new String("http://localhost:9091/activity/allActivity?token=4b5930cb-e6da-4012-a9b7-cc2b6b4cab42");
        RestTemplate restTemplate = new RestTemplate();
        ActivityModelCustom[] activityModel = restTemplate.getForObject(uri, ActivityModelCustom[].class);
        System.out.println(activityModel.length);
    }

    @Test
    public void uploadPhoto () {

    }
    /* @Test
    public void testPersonRepositoryInsert () {
                                                                                                                                                 
        personRepository.deleteAll();
        char genders [] = {'M','F'};
        String Hoby [] = {"Music","Art","Play Game"};

        for(int i= 1; i<=200; i++) {

            Person person = new Person()
                    .setGender(genders[i%2])
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
    }*/
 /*   @Test
    public void addPerson () {
        Person person = new Person()
                .setSurname("YILMAZ")
                .setHoby("Write Code")
                .setGender("E")
                .setOccupation("Gazi")
                .setPhoto("/Home/Oguz/Sample")
                .setName("OGUZ");
        String name = "A1";
        String uri = new String("http://localhost:8081/person/insertPerson");
        RestTemplate restTemplate = new RestTemplate();
        person = restTemplate.postForObject(uri,person,Person.class);
        System.out.println(person.getName());

    }
    @Test
    public void addMPerson () {
        MPerson mPerson = new MPerson()
                .setEmail("aaaa")
                .setId(UUID.randomUUID().toString())
                .setPassword("aaaa");

        String uri = new String("http://localhost:8081/person/insertMPerson");
        RestTemplate restTemplate = new RestTemplate();
        mPerson = restTemplate.postForObject(uri,mPerson,MPerson.class);
        System.out.println(mPerson.getEmail());

    }



    @Test
    public void placesTest(){
        String uri = new String("http://localhost:8081/places/getName?name=ABC");
        RestTemplate restTemplate = new RestTemplate();
        Places place = restTemplate.getForObject(uri,Places.class);
        System.out.println(place);

    }

    @Test
    public void placesIdTest(){
        String uri = new String("http://localhost:8081/places/getId?id=206");
        RestTemplate restTemplate = new RestTemplate();
        Places place = restTemplate.getForObject(uri,Places.class);
        System.out.println(place);

    }

    @Test
    public void placesUpdateTest(){
        Places places = new Places();

        String uri = new String("http://localhost:8081/places/update?name=LG TECH&type=ENGINEER OFFICE");
        RestTemplate restTemplate = new RestTemplate();
        Places place = restTemplate.postForObject(uri,places,Places.class);
        System.out.println(place);

    }

    @Test
    public void placesInsertTest(){

        Places places = new Places()
                .setType("SPORT CLUB")
                .setName("GAZİANTEPSPOR");


        String uri = new String("http://localhost:8081/places/insert");
        RestTemplate restTemplate = new RestTemplate();
        Places place = restTemplate.postForObject(uri,places,Places.class);
        System.out.println(place);

    }

    @Test
    public void placesDeleteTest(){
        String uri = new String("http://localhost:8081/places/deleteplace?id={id}");

        long id = 223;
        Map<String,Long> params = new ParameterMap<>();
        params.put("id", id);

        RestTemplate restTemplate = new RestTemplate();
        Places place = restTemplate.getForObject(uri,Places.class,params);


    }*/

    @Test
    public void ankaraObs () {
    String getUrl = "https://obs.ankara.edu.tr/reregistration/listprevioussyllabus?uniqueId=d898ed14-821c-4a07-9941-ce88755d79c0&syllabusNo=218016&previousStudentLessonNo=&courseNo=258152&kontenjanNo=267850&_=1486371169293";
    RestTemplate restTemplate = new RestTemplate();
//    restTemplate.getForObject(uri);

}


}
