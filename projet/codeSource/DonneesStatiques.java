package parcInfo.data;

import java.util.Vector;

public class DonneesStatiques{
	String nomPc;
	String processeur;
	String totalMasStorage;
	String availableMasStorage;
	String installedRam;
	String os;
			
	//_ _ _ CONSTRUCTORS
	public DonneesStatiques(String os, String nomPc, String proc, String totalMasStorage, String availableMasStorage, String installedRam){
		this.setOs(os);
		this.setNomPc(nomPc);
		this.setTotalMasStorage(totalMasStorage);
		this.setAvailableMasStorage(availableMasStorage);
		this.setInstalledRam(installedRam);
		this.setProcesseur(proc);
	}

	//_ _ _ Methods
	public Vector<String> toVector(){
		//stockage des attributs dans un vector
		Vector<String> res = new Vector<String>();
		res.add("sta");
		res.add(this.getOs());
		res.add(this.getProcesseur());
		res.add(this.getNomPc());
		res.add(this.getTotalMasStorage());
		res.add(this.getAvailableMasStorage());
		res.add(this.getInstalledRam());
		return res;
	}

	// _ _ _ GET SET

	public void setOs(String os) {
		this.os = os;
	}

	public String getOs() {
		return os;
	}
	public String getNomPc() {
		return nomPc;
	}

	public void setNomPc(String nomPc) {
		this.nomPc = nomPc;
	}

	public String getTotalMasStorage() {
		return totalMasStorage;
	}

	public void setTotalMasStorage(String totalMasStorage) {
		this.totalMasStorage = totalMasStorage;
	}

	public String getAvailableMasStorage() {
		return availableMasStorage;
	}

	public void setAvailableMasStorage(String availableMasStorage) {
		this.availableMasStorage = availableMasStorage;
	}

	public String getInstalledRam() {
		return installedRam;
	}

	public void setInstalledRam(String installedRam) {
		this.installedRam = installedRam;
	}

	public String getProcesseur() {
		return processeur;
	}

	public void setProcesseur(String processeur) {
		this.processeur = processeur;
	}}
