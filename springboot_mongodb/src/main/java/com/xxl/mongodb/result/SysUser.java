package com.xxl.mongodb.result;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author xxl
 * @date 2023/12/21 0:47
 */
@Data
@Document(collection = "sys_user")
public class SysUser {

    @Id
    private String id;

    private String userName;

    private String phoneNumber;

    private String address;

    private String idNumber;

    private Date birthday;

    public SysUser() {
    }

    public SysUser(String userName, String phoneNumber, String address, String idNumber, Date birthday) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.idNumber = idNumber;
        this.birthday = birthday;
    }
}
