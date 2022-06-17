package model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("F")
public class Freund extends Person {
	private static final long serialVersionUID = -5480224740645459254L;
	
	private String freundeskreis;

	public String getFreundeskreis() {
		return freundeskreis;
	}

	public void setFreundeskreis(String freundeskreis) {
		this.freundeskreis = freundeskreis;
	}
	

}
