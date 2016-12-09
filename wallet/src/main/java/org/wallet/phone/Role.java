package org.wallet.phone;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name="role")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id")
	private int roleId;

	@Column(name="access_level")
	private int accessLevel;

	@Column(name="role_name")
	private String roleName;

	@Column(name="sub_role")
	private String subRole;

	//bi-directional many-to-one association to CommissionParam
	@OneToMany(mappedBy="role")
	private List<CommissionParam> commissionParams;

	//bi-directional one-to-one association to OperationParam
	@OneToMany(mappedBy="role")
	private List<OperationParam> operationParam;

	//bi-directional many-to-one association to Telephone
	@OneToMany(mappedBy="role")
	private List<Telephone> telephones;

	public Role() {
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getAccessLevel() {
		return this.accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getSubRole() {
		return this.subRole;
	}

	public void setSubRole(String subRole) {
		this.subRole = subRole;
	}

	public List<CommissionParam> getCommissionParams() {
		return this.commissionParams;
	}

	public void setCommissionParams(List<CommissionParam> commissionParams) {
		this.commissionParams = commissionParams;
	}

	public CommissionParam addCommissionParam(CommissionParam commissionParam) {
		getCommissionParams().add(commissionParam);
		commissionParam.setRole(this);

		return commissionParam;
	}

	public CommissionParam removeCommissionParam(CommissionParam commissionParam) {
		getCommissionParams().remove(commissionParam);
		commissionParam.setRole(null);

		return commissionParam;
	}

	public List<OperationParam> getOperationParam() {
		return this.operationParam;
	}

	public void setOperationParam(List<OperationParam> operationParam) {
		this.operationParam = operationParam;
	}
	
	public OperationParam addOperationParam(OperationParam operationParam) {
		getOperationParam().add(operationParam);
		operationParam.setRole(this);

		return operationParam;
	}

	public OperationParam removeOperationParam(OperationParam operationParam) {
		getOperationParam().remove(operationParam);
		operationParam.setRole(null);

		return operationParam;
	}

	public List<Telephone> getTelephones() {
		return this.telephones;
	}

	public void setTelephones(List<Telephone> telephones) {
		this.telephones = telephones;
	}

	public Telephone addTelephone(Telephone telephone) {
		getTelephones().add(telephone);
		telephone.setRole(this);

		return telephone;
	}

	public Telephone removeTelephone(Telephone telephone) {
		getTelephones().remove(telephone);
		telephone.setRole(null);

		return telephone;
	}

}