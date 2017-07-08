package com.msesoft.fom.repository;

import com.msesoft.fom.domain.CustomPerson;
import com.msesoft.fom.domain.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by oguz on 18.06.2016.
 */
public interface PersonRepository extends GraphRepository<Person>,PersonRepositoryCustom {


    @Query("MATCH (o:Person{token: {token} })-[:FRIEND]-(p) return p")
    List<Person> findByFirstDegreeFriend(@Param("token") String token);

    @Query("MATCH p=((n:Person{token: {token} })-[:FRIEND*3..3]->(w:Person))\n" +
            "with n as nNode,w as wNode , count(*) as xCount\n" +
            "where not wNode = nNode and not (n:Person{token: {token}  })-[:FRIEND*1..1]->(w:Person)\n" +
            "return wNode ")
    List<Person> findByDegreeFriend(@Param("token") String token);

    @Query(" MATCH p=((o:Person{uniqueId: {uniqueId} })-[:WORK]-(n))\n" +
            "    MATCH r=((n)-[:WORK]-(t))\n" +
            "    WHERE NOT (o)-[:FRIEND]-(t) AND NOT o = t\n" +
            "    RETURN t")
    List<Person> workNotFriend(@Param("uniqueId") String person);

    @Query("MATCH (n:Person{uniqueId : {uniqueId} }) DETACH Delete n")
    void deletePerson(String uniqueId);

    Person findByEmail(String email);

    Person findByFirstName(String name);

    @Query("MATCH p=((n:Person{token: {token} })-[:FRIEND]->(w:Person))\n" +
            "                    with n as nNode,w as wNode , count(*) as xCount\n" +
            "                    where not wNode = nNode\n" +
            "               and (wNode.lastName=~ {like} or wNode.firstName=~ {like} or wNode.email=~ {like})\n" +
            "                    return wNode")
    List<Person> findFriendList (@Param ("token") String token, @Param ("like") String like);

    Person findByUniqueId(String uniqueId);

    Person findByEmailAndPassword(String email, String password);

    Person findByUniqueIdIgnoreCase(String unequeId);

    Person findByToken(String token);


    Person findByPhoneNumber (String phoneNumber);

    @Query("MATCH (n:Person{phoneNumber: {phoneNumber} }) RETURN n.phonenNumber")
    String findPhoneNumber(@Param("phoneNumber") String phoneNumber);


}






