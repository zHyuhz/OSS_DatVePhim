package vn.edu.stu.oss_appdatvexemphim.Models;

import java.util.Calendar;

public class Accounts {
    private int ac_id;
    private String ac_userName;
    private String ac_password;
    private String ac_email;
    private Calendar ac_createAt;
    private int ac_status;
    private String ac_role;

    public int getAc_id() {
        return ac_id;
    }

    public void setAc_id(int ac_id) {
        this.ac_id = ac_id;
    }

    public String getAc_userName() {
        return ac_userName;
    }

    public void setAc_userName(String ac_userName) {
        this.ac_userName = ac_userName;
    }

    public String getAc_password() {
        return ac_password;
    }

    public void setAc_password(String ac_password) {
        this.ac_password = ac_password;
    }

    public String getAc_email() {
        return ac_email;
    }

    public void setAc_email(String ac_email) {
        this.ac_email = ac_email;
    }

    public Calendar getAc_createAt() {
        return ac_createAt;
    }

    public void setAc_createAt(Calendar ac_createAt) {
        this.ac_createAt = ac_createAt;
    }

    public int getAc_status() {
        return ac_status;
    }

    public void setAc_status(int ac_status) {
        this.ac_status = ac_status;
    }

    public String getAc_role() {
        return ac_role;
    }

    public void setAc_role(String ac_role) {
        this.ac_role = ac_role;
    }

    public Accounts(int ac_id, String ac_userName, String ac_password, String ac_email, Calendar ac_createAt, int ac_status, String ac_role) {
        this.ac_id = ac_id;
        this.ac_userName = ac_userName;
        this.ac_password = ac_password;
        this.ac_email = ac_email;
        this.ac_createAt = ac_createAt;
        this.ac_status = ac_status;
        this.ac_role = ac_role;
    }

    public Accounts(int ac_id, String ac_userName, String ac_password, String ac_role) {
        this.ac_id = ac_id;
        this.ac_userName = ac_userName;
        this.ac_password = ac_password;
        this.ac_role = ac_role;
    }

    public Accounts() {

    }
}
