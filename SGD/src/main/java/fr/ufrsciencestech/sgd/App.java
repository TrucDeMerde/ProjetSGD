package fr.ufrsciencestech.sgd;

import static com.mongodb.client.model.Filters.*;
import fr.ufrsciencestech.sgd.modele.*;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        GestionBD cbd = new GestionBD("tf872254");
        
        Document d = new Document("nom", "Coucou").append("prenom", "je").append("ville", "m'emmerde");
        
        //cbd.insertionBD("utilisateur", d);
        cbd.suppressionDocument("utilisateur", eq("nom", "Coucou"));
        
        Bson a = eq("nom", "test");
        
        Bson b = gte("year", 2014);
        
        Bson c = and(a,b);
        
        cbd.rechercheDocument("utilisateur", c);
    }
}
