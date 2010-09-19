package com.exdriven.shopping.struts;

public class LogoutAction extends BaseShoppingAction {

	public String execute() {
		getShoppingSession().logout();
		return SUCCESS;
	}
}
