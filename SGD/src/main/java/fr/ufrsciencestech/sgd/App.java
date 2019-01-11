package fr.ufrsciencestech.sgd;

import fr.ufrsciencestech.sgd.modele.*;
import fr.ufrsciencestech.sgd.vueControleur.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        GestionBD gbd = new GestionBD("tf872254");
        
        Modele m = new Modele(gbd);
    
        RechercheUtilisateur ru = new RechercheUtilisateur(m);
        
        ru.setVisible(true);
                
    }
}
