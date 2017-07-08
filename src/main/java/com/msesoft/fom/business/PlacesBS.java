package com.msesoft.fom.business;

import com.msesoft.fom.domain.Person;
import com.msesoft.fom.domain.Places;
import com.msesoft.fom.repository.PlacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlacesBS {

    @Autowired
    PlacesRepository placesRepository;

    public List<Places> listAll(){

       return (List<Places>) placesRepository.findAll();

    }

    public List<Person> listWorkAllNode(String uniqueId){

        return placesRepository.findWorkAllNode(uniqueId);
    }

    public Places findOne(String uniqueId){

        return placesRepository.findByUniqueId(uniqueId);

    }

    public List<Places> findByType(String type){

        return placesRepository.findByType(type);

    }

    public Places workSearch(String id){

        return placesRepository.workSearch(id);

    }


    public Places findByName(String name) {

        return placesRepository.findByName(name);
    }



    public Places update(Places places,String name,String type){

      places.setName(name)
            .setType(type);


        placesRepository.save(places);

        return places;
    }

    public Places dbInsert(Places place) {

        return placesRepository.save(place);

    }

    public Places deletePlaces(String uniqueId){
        Places places=new Places();

        places=placesRepository.findByUniqueId(uniqueId);
        placesRepository.deletePlaces(uniqueId);

        return places;
    }


}
