import java.io.BufferedReader;
import java.io.FileReader;
import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
// Valeurs experimentales : 
// 		Pour une taille de 5 bits, il y a une collision tout les 30 de paires de mots environ.
//		Pour une taille de 6 bits, il y a une collision tout les 70 de paires de mots environ.
//		Pour une taille de 7 bits, il y a une collision tou les 130 de paires de mots environ.
//		Pour une taille de 8 bits, il y a une collision tou les 250 de paires de mots environ.
//		Pour une taille de 9 bits, il y a une collision tou les 500 de paires de mots environ.
//		Pour une taille de 10 bits, il y a une collision tou les 10000 de paires de mots environ.
//      Cala correspond au resultat théoriques, en effet deux mots de N bits choisi "au hasard"
//		ont 1/(2**n) d'etre identique 



// La propriété que nous essayons de casser est la résistance à la seconde preimage:
// On fixe f(x) et on chechent y tel que f(y) = f(x), sans autres solution que d'essay pour des y 'aléatoires'

public class Main {
	 static int NBBIT = 9;

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
		//System.out.println(toSha("Poisson", 5)); 
		//trouverCollisionDic();
		//Poisson();
		
		SecretKeySpec mdp = new SecretKeySpec("coucou".getBytes(), "SHA");
		Mac mac = Mac.getInstance("SHA");
		mac.init(mdp);
		// to be continued
		
	}
	private static void Poisson() {
		Random rng = new Random();
		List<Boolean> poisson = toSha("poisson", NBBIT);
		List<Boolean> comp = new ArrayList<>();
		int nbessai = 0;
		while (true) {
			nbessai++;
			for (int i = 0 ; i < NBBIT ; i++) {
				comp.add(rng.nextBoolean());
			}
			if (poisson.equals(comp)) {
				System.out.println("Collision trouvé apres " + nbessai + " essai");
				nbessai = 0;
			}
		comp.clear();
		}

	}
		
	
	private static void trouverCollisionDic() {
		
		List <String> mots = readFile("dic");
		int nbtests = 0;
		
		for (String mot1 : mots) {
			for (String mot2 : mots) {
				if (!mot1.equals(mot2)) {
					List<Boolean> sh1 = toSha(mot1, NBBIT);
					List<Boolean> sh2 = toSha(mot2, NBBIT);
					nbtests++;
					if (sh1.equals(sh2)) {
						System.out.println("Collision entre " + mot1 + " et " + mot2 + "au bout de " + nbtests + " | hash : " + sh1);
						nbtests = 0;
					}
				}
			}
		}
		
	}
	
	private static List<String> readFile(String filename)
	{
	  List<String> records = new ArrayList<String>();
	  try
	  {
	    BufferedReader reader = new BufferedReader(new FileReader(filename));
	    String line;
	    while ((line = reader.readLine()) != null)
	    {
	      records.add(line);
	    }
	    reader.close();
	    return records;
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	    return null;
	  }
	}
	
	public static List<Boolean> toSha(String str, int nbBit) {
		try {
			byte[] buffer = str.getBytes();
			byte[] result = null;
			MessageDigest sha = MessageDigest.getInstance("SHA");

			result = new byte[sha.getDigestLength()];
			sha.reset();
			sha.update(buffer);
			
			result = sha.digest();
			byte b;
			
			List<Boolean> aRet = new ArrayList<>();
			// Pour essayer d'ameliorer l'efficacité on peut travailler bit par bit :  
			for (int i = 0 ; i < nbBit; i++) {
				b = result[i/8];
				Boolean nb = ((b >> i) & 1) == 1;
				aRet.add(nb);
			}
			
			return aRet;
			
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Exception caught: " + e);
			e.printStackTrace();
		}
		return null;
	}

}
