package fr.ufrsciencestech.sgd;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import fr.ufrsciencestech.sgd.modele.*;
import fr.ufrsciencestech.sgd.vueControleur.*;
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
        GestionBD gbd = new GestionBD("tf872254");
        
        RechercheUtilisateur ru = new RechercheUtilisateur(gbd);
        
        ru.setVisible(true);
        
        //Document d = new Document("nom", "Coucou").append("prenom", "je").append("ville", "m'emmerde");
        
        //gbd.insertionBD("utilisateur", d);
        //gbd.suppressionDocument("utilisateur", eq("nom", "Coucou"));
        
        //Bson a = eq("nom", "Coucou");
        
        //Bson b = gte("year", 2011);
        
        //Bson c = or(a,b);
        
        /*mc = gbd.rechercheDocument("utilisateur", a);
        
        while(mc.hasNext()){
            System.out.println(mc.next());
        }*/
    }
}
