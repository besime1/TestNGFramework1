package com.exelenter.testcases;
import com.exelenter.base.BaseClass;
import com.exelenter.utils.ConfigsReader;
import org.testng.annotations.Test;

public class AddEmployeeTest extends BaseClass {
    @Test(groups = {"smoke" , "regression"})
    public void addEmployeeTest() {
        loginPage.loginToWebsite(ConfigsReader.getProperties("username"), ConfigsReader.getProperties("password"));
        pimPage.navigateToAddEmployee();
        System.out.println("New Employee ID: " + addEmployeePage.employeeId.getAttribute("value"));
        addEmployeePage.addEmployee("empFirstname", "empLastname", "filePath"); // This method will add a new employee
    }

}
