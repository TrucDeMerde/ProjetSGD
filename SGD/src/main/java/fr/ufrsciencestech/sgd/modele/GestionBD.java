/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ufrsciencestech.sgd.modele;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import java.util.Arrays;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.conversions.Bson;
/**
 *
 * @author tf872254
 */
public class GestionBD {
    final private String id;
    private MongoDatabase db;
    
    public GestionBD(String idC){
        id = idC;
        
        initConnexion();
    }
    
    private void initConnexion(){
        char [ ] pass= new char[10];
        pass = id.toCharArray();
        MongoCredential credential = MongoCredential.createCredential(id, id, pass);
        MongoClient client = new MongoClient(new ServerAddress("mongo", 27017), Arrays.asList(credential));
        db= client.getDatabase(id);        
    }
    
    public void insertionBD(String collection, Document d){
        db.getCollection(collection).insertOne(d);
    }
    
    public void insertionMultipleBD(String collection, ArrayList<Document> ld){
        db.getCollection(collection).insertMany(ld);
    }
    
    public void suppressionDocument(String collection, Bson bson){
        db.getCollection(collection).deleteOne(bson);
    }
    
    public void rechercheDocument(String collection, Bson b){
        db.getCollection(collection).find(b);
    }
    
    private void afficherCollection(){
        // Affichage de la liste des collections de la base
        for (String name : db.listCollectionNames()) { 
            System.out.println(name); 
        }
    }
}
