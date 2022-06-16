package model.listener;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import model.Person;

public class PersonListener {

    @PostPersist
    public void protokolliereEinfuegen (Person p){
        System.out.println("Protokoll: Person mit ID " + p.getId() + " wurde eingefügt.");
    }

    @PostUpdate
    public void protokolliereAktualisieren (Person p){
        System.out.println("Protokoll: Person mit ID " + p.getId() + " wurde aktualisiert.");
    }

    @PostRemove
    public void protokolliereLoeschen (Person p){
        System.out.println("Protokoll: Person mit ID " + p.getId() + " wurde gelöscht.");
    }

}
