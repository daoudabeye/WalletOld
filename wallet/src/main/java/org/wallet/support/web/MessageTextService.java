package org.wallet.support.web;

import java.math.BigInteger;

public class MessageTextService {

	public MessageTextService() {
		// TODO Auto-generated constructor stub
	}

	public static String SMSSuccesDepot(Double montantDepot, BigInteger numSrc, Double nouveauSold, String id){
		return "Depot de "+montantDepot+" FCFA effectue par le "+numSrc
				+". Votre nouveau solde est de "+nouveauSold+" FCFA. ID: "+id;
	}
	
	public static String SMSSuccesDepotAgent(Double montantDepot, BigInteger numClient, Double nouveauSold, String id){
		return "Vous venez d'effectuer un Depot de "+montantDepot+" FCFA sur le  "+numClient
				+". Votre nouveau solde est de "+nouveauSold+" FCFA. ID: "+id;
	}
	
	public static String succesOperation(Double montant, String numClient, String id, String type){
		return "Vous venez d'effectuer un "+type+" de "+montant+" FCFA sur le  "+numClient+". ID Operation: "+id;
	}
	
	public static String cash(Double montant, String numClient, String id, String code, String type){
		return "Vous venez d'effectuer un "+type+" de "+montant+" FCFA sur le  "+numClient
				+". ID Operation: "+id+" Code :"+code;
	}
	
	public static String SMSInitRetrait(Double montantRetrait, Double frais){
		return "Vous allez faire un retrait de "+montantRetrait+", Frais : "+frais
				+". Veuillez confirmer en envoyant votre code secret par SMS au 36014.";
	}
	
	public static String SMSSuccesRetraitClient(Double montantRetrait, BigInteger numDst, Double nouveauSold,Double frais,String id){
		return "Vous venez d'effectuer retrait de "+montantRetrait+" FCFA effectue par "+numDst
				+". Frais:"+frais+" ID:"+id+". Votre nouveau solde est de "+nouveauSold+" FCFA";
	}
	
	public static String SMSSuccesRetraitAgent(Double montantRetrait, BigInteger numDst, Double nouveauSold,Double frais,String id){
		return "Retrait de "+montantRetrait+" FCFA effectue du compte "+numDst
				+". Frais:"+frais+" ID:"+id+". Votre nouveau solde est de "+nouveauSold+" FCFA";
	}
	
	public static String SMSInitTransfert(Double montantTrans, BigInteger numDst,Double frais,String code){
		return "Vous allez faire un transfert de "+montantTrans+" vers le "+numDst+". Frais:"+frais
				+" FCFA. code de confirmation:"+code;
	}
	
	public static String SMSSuccesTransfert(Double montantTrans, BigInteger numDst, Double nouveauSold,Double frais,String id){
		return "Votre transfert de "+montantTrans+" vers le "+numDst+" a reussi. Frais:"+frais
				+" FCFA. Votre nouveau solde est de "+nouveauSold+" FCFA. ID:"+id;
	}
	
	public static String SMSSuccesTransfertRecepteur(Double montantDepot, BigInteger numSrc, Double nouveauSold, String id){
		return "Vous avez re�u un transfert de "+montantDepot+" FCFA effectue par le "+numSrc
				+". Votre nouveau solde est de "+nouveauSold+" FCFA. ID: "+id;
	}
	
	public static String SMSSold(Double sold){
		return "Le solde de votre compte est de "+sold+" FCFA";
	}
	
	public static String SMSNouveauCompteAgr(String profile,String code,String motDePasse, Double depot){
		return "Votre compte "+profile+" Lemonway a ete cree, Votre sold initial est de :"+depot+", "
				+ "votre code est :"+code+" votre mot de passe est :"+motDePasse+"";
	}
	
	public static String SMSNouveauCompteAge(String numeroAgent){
		return "Bienvenu a Lemonway, vous compte a ete cree avec succes"
				+ ", votre ID: "+numeroAgent;
	}
	
	public static String SMSResetPin(String pin){
		return "Votre code Pin a ete reinitialise, le nouveau pin est : "+pin;
	}
}
