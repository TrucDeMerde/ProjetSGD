/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ufrsciencestech.sgd.modele;

import com.mongodb.BasicDBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceCommand.OutputType;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import static com.mongodb.client.model.Filters.eq;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
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
    
    public MongoDatabase getDB(){
        return this.db;
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
    
    public MongoCursor rechercheDocument(String collection, Bson b){
        return db.getCollection(collection).find(b).iterator();
    }

    public MapReduceIterable mapReduce(){

        String funcMap =    "function(){" + 
                            "var s = this.serie;" +
                            "emit(s, 1);}";
                            
        String funcReduce = "function(key, values){" +
                            "var sum = 0;" +
                            "values.forEach( function(doc){sum += 1;});" +
                            "return sum;}";
        
        //BasicDBObject query = new BasicDBObject("serie", new BasicDBObject("$exist",1));
        
        //MapReduceCommand cmd = new MapReduceCommand(db.getCollection("jeuxvideo"), funcMap, funcReduce,"Series", OutputType.INLINE, query);
        
        return db.getCollection("jeuxvideo").mapReduce(funcMap,funcReduce);
    }
    
    public String aggregateAvis(){
        String res = "";
        
        String unwind = "{$unwind:\"$avis\"}";
        String aggr = "{$group:{_id:\"$avis.utilisateur.pseudo\", count:{$sum:1}}}";
        
        Document ud = Document.parse(unwind);
        Document ad = Document.parse(aggr);
        
        AggregateIterable<Document> result = db.getCollection("jeuxvideo").aggregate(Arrays.asList(ud,ad));
        MongoCursor<Document> mc = result.iterator();
        
        while(mc.hasNext()){
            Document next = mc.next();
            res += "Pseudo : " + next.get("_id") + " --- Nombre d'avis : " + next.get("count") + "\n";
        }
        
        return res;
    }
    
    
    public String aggregateNote(){
        String res = "";
        
        String unwind = "{$unwind:\"$avis\"}";
        String group = "{$group:{_id:\"$nom\",avg :{$avg:\"$avis.note\"}}}";
        String sort = "{$sort :{\"avg\" : -1}}";
        
        
        
        Document ud = Document.parse(unwind);
        Document gd = Document.parse(group);
        Document sd = Document.parse(sort);
        
        
        AggregateIterable<Document> result = db.getCollection("jeuxvideo").aggregate(Arrays.asList(ud,gd,sd));
        MongoCursor<Document> mc = result.iterator();
        
        while(mc.hasNext()){
            Document next = mc.next();
            res += "Nom : " + next.get("_id") + " --- Note moyenne : " + next.get("avg") + "\n";
        }
        
        return res;
    }
    
    public void afficherCollection(){
        // Affichage de la liste des collections de la base
        for (String name : db.listCollectionNames()) { 
            System.out.println(name); 
        }
    }

    void miseAJour(String collection, String jeu, float note, String avis, String pseudo, String mail) {
        //Document d = db.getCollection("jeuxvideo").find(eq("nom", jeu)).first();
        Document docUser = new Document("pseudo", pseudo).append("mail", mail);
        //Document docAAjouter = new Document("note", note).append("commentaire", avis).append("utilisateur", docUser);
        Document docAvis = new Document("note", note).append("commentaire", avis).append("utilisateur", docUser);
        Document docAAjouter = new Document("$addToSet", new Document("avis", docAvis));
        db.getCollection("jeuxvideo").updateOne(eq("nom", jeu), docAAjouter);
        //doc.replace("avis", ald);
        //this.insertionBD("jeuxvideo", doc);
    }
    
    public void addJeu(String name, ArrayList<String> cat, int pegi, Date date){
        
        Document jeu = new Document("nom",name).append("categorie", cat).append("pegi",pegi).append("dateSortie", date).append("disponibilité", 1);
        db.getCollection("jeuxvideo").insertOne(jeu);
        
    }
}
