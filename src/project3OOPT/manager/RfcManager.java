package project3OOPT.manager;

import java.util.List;

import project3OOPT.persistance.dao.RfcDAO;
import project3OOPT.persistance.model.Rfc;

/*
 * RfcManager
 * Class manager responsible of manage the Rfc data.
 * */
public class RfcManager {
	private static RfcManager Singleton;
		
	public static RfcManager getInstance(){
		if(Singleton == null) return new RfcManager();
		else return Singleton;
	}
	
	private RfcDAO rfcD;
	
	private RfcManager() {
		this.rfcD = RfcDAO.getInstance();
	}
	
	public Rfc getRfcByNumber(int number) {
		return rfcD.read(number);		
	}
	
	public List<Rfc> getRfcsByName(String name) {
		return rfcD.readByName(name);		
	}
}
