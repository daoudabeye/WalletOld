package org.wallet.phone;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the role_param database table.
 * 
 */
@Entity
@Table(name="role_param")
@NamedQuery(name="RoleParam.findAll", query="SELECT r FROM RoleParam r")
public class RoleParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	public RoleParam() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

}