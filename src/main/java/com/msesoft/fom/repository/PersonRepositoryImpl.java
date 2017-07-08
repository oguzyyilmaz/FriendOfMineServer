package com.msesoft.fom.repository;

import com.msesoft.fom.domain.CustomPerson;
import com.msesoft.fom.domain.FriendRelationship;
import com.msesoft.fom.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PersonRepositoryImpl implements PersonRepositoryCustom {

    private Neo4jOperations neo4jOperations;

    @Autowired
    public PersonRepositoryImpl(Neo4jOperations neo4jTemplate) {
        this.neo4jOperations = neo4jTemplate;
    }

    @Override
    public List<CustomPerson> findDegreeFriend(String token, String like, int degree, int skip) {

        String query = null;

        Map<String, Object> params = new HashMap<>();
        if (like.matches("")) {
            query = "MATCH p=((n:Person{token: {token} })-[:FRIEND*" + degree + ".." + degree + "]->(w:Person))\n" +
                    "with n as nNode,w as wNode , count(*) as xCount\n" +
                    "where not wNode = nNode and not (n:Person{token: {token}  })-[:FRIEND*1.." + (degree - 1) + "]->(w:Person)\n" +
                    "return wNode skip {skip} limit 12;";

            params.put("token", token);
            params.put("skip", skip);
        } else {
            query = "MATCH p=((n:Person{token: {token} })-[:FRIEND*1..4]->(w:Person))\n" +
                    "with n as nNode,w as wNode , count(*) as xCount\n" +
                    "where not wNode = nNode\n" +
                    "and (wNode.gender=~ {like} or wNode.lastName=~ {like} or wNode.firstName=~{like} or wNode.email=~{like} or wNode.hoby=~{like} or wNode.occupation=~{like} or wNode.school=~{like} " +
                    "or lower(wNode.gender)=~ {like} or lower(wNode.lastName)=~ {like} or lower(wNode.firstName)=~{like} or lower(wNode.email)=~{like} or lower(wNode.hoby)=~{like} or lower(wNode.occupation)=~{like} or lower(wNode.school)=~{like})\n" +
                    "return wNode;";
            params.put("token", token);
            params.put("like", ".*" + like + ".*");
        }


        List<Person> list = new ArrayList<Person>();
        List<CustomPerson> customList = new ArrayList<CustomPerson>();

        for (Person person : neo4jOperations.queryForObjects(Person.class, query, params)) {
            CustomPerson customPerson = new CustomPerson()
                    .setEmail(person.getEmail())
                    .setFirstName(person.getFirstName())
                    .setLastName(person.getLastName())
                    .setGender(person.getGender())
                    .setPhoto(person.getPhoto())
                    .setHoby(person.getHoby())
                    .setPhotoList(person.getPhotoList())
                    .setUniqueId(person.getUniqueId());

            customList.add(customPerson);
        }

        return customList;
    }

    @Override
    public List<Person> findContact(List<String> listNumber) {
        String params="";
        Map<String, Object> param = new HashMap<>();

        for (String number : listNumber) {
            params += " n.phoneNumber='" + number + "' or";
        }

        String query = "MATCH (n:Person) where" + params.substring(0,params.length()-3) + " return n";
        List<Person> list = new ArrayList<>();

        for (Person person : neo4jOperations.queryForObjects(Person.class, query, param)) {

            list.add(person);
        }

        return list;
    }

    @Override
    public List<String> findNotContact(List<String> listNumber) {
        String params="";
        Map<String, Object> param = new HashMap<>();
        List<String> persons = new ArrayList<>();

        for (String number : listNumber) {
            params += " n.phoneNumber='" + number + "' or";
        }

        String query = "MATCH (n:Person) where" + params.substring(0,params.length()-3) + " return n";
        List<String> list = new ArrayList<>();

        for (Person person : neo4jOperations.queryForObjects(Person.class, query, param)) {

            list.add(person.getPhoneNumber());
        }


        for(int i=0; i<listNumber.size(); i++) {

                if (!list.contains(listNumber.get(i))) {
                      persons.add(listNumber.get(i));
                }

        }

        return persons;
    }

    @Override
    public List<Person> findDegreeFriend(String token) {
        String query = "MATCH p=((n:Person{token: {token} })-[:FRIEND*3..3]->(w:Person))\n" +
                "with n as nNode,w as wNode , count(*) as xCount\n" +
                "where not wNode = nNode and not (n:Person{token: {token}  })-[:FRIEND*1..1]->(w:Person)\n" +
                "return wNode";
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        List<Person> persons =new ArrayList<>();
        for (Person person : neo4jOperations.queryForObjects(Person.class, query, params)) {
            persons.add(person);
        }
         return persons;
    }
}