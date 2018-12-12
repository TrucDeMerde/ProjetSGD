package fr.ufrsciencestech.sgd;

import fr.ufrsciencestech.sgd.modele.*;
import org.bson.Document;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        GestionBD cbd = new GestionBD("tf872254");
        
        cbd.afficherCollection();
        Document d = new Document();
    }
}
