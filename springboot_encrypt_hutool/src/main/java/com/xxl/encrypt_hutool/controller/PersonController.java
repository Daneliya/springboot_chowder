package com.xxl.encrypt_hutool.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xxl.encrypt_hutool.anno.NeedDecrypt;
import com.xxl.encrypt_hutool.anno.NeedEncrypt;
import com.xxl.encrypt_hutool.entity.Person;
import com.xxl.encrypt_hutool.entity.PersonPhoneEncrypt;
import com.xxl.encrypt_hutool.mapper.PersonMapper;
import com.xxl.encrypt_hutool.mapper.PersonPhoneEncryptMapper;
import com.xxl.encrypt_hutool.util.SnowIdUtils;
import com.xxl.encrypt_hutool.util.SymmetricCryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/05/31 13:54
 * @Version: 1.0
 */
@RestController
public class PersonController {

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonPhoneEncryptMapper personPhoneEncryptMapper;

    @RequestMapping(value = "/savePerson", method = RequestMethod.POST)
    public String savePerson(@RequestBody Person person) {
        person.setId(SnowIdUtils.getNextId());
        String encrypt = SymmetricCryptoUtils.encrypt(person.getPhoneNumber());
        person.setPhoneNumber(encrypt);
        registe(person);
        return "保存成功";
    }

    @RequestMapping(value = "/savePersonList", method = RequestMethod.POST)
    public String savePersonList(@RequestBody List<Person> personList) {
        // FIXME-1 循环导入
//        long start1 = System.currentTimeMillis();
//        for (Person person : personList) {
//            person.setId(SnowIdUtils.getNextId());
//            String encrypt = encrypt(person.getPhoneNumber());
//            person.setPhoneNumber(encrypt);
//            registe(person);
//        }
//        long end1 = System.currentTimeMillis();
//        System.out.println("耗费时间:" + (end1 - start1));
        // FIXME-2 并行流导入
        long start2 = System.currentTimeMillis();
        personList.parallelStream().forEach(e -> {
            e.setId(SnowIdUtils.getNextId());
            String encrypt = SymmetricCryptoUtils.encrypt(e.getPhoneNumber());
            e.setPhoneNumber(encrypt);
            registe(e);
        });
        long end2 = System.currentTimeMillis();
        System.out.println("耗费时间:" + (end2 - start2));

        return "保存成功";
    }

    public Person registe(Person person) {
        personMapper.insert(person);
        String phone = SymmetricCryptoUtils.decrypt(person.getPhoneNumber());
        String phoneKeywords = this.phoneKeywords(phone);

        PersonPhoneEncrypt personPhoneEncrypt = new PersonPhoneEncrypt();
        personPhoneEncrypt.setPersonId(person.getId());
        personPhoneEncrypt.setPhoneKey(phoneKeywords);
        personPhoneEncryptMapper.insert(personPhoneEncrypt);
        return person;
    }

    private String phoneKeywords(String phone) {
        String keywords = this.keywords(phone, 1);
        //System.out.println("加密后长度 " + keywords.length());
        return keywords;
    }

    //分词组合加密
    private String keywords(String word, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            int start = i;
            int end = i + len;
            String sub1 = word.substring(start, end);
            //System.out.println("每次截取数据 " + sub1);
            sb.append(SymmetricCryptoUtils.encrypt(sub1));
            if (end == word.length()) {
                break;
            }
        }
        return sb.toString();
    }



    public static void main(String[] args) {
        SecretKey secretKey = SecureUtil.generateKey("fanfu-csdn");
        System.out.println(secretKey);
    }

    /**
     * 查询加密信息
     *
     * @param person
     * @return
     */
    @NeedDecrypt
    @RequestMapping(value = "/findPerson", method = RequestMethod.POST)
    public List<Person> findPerson(@RequestBody Person person) {
        if (person.getPhoneNumber() != null && person.getPhoneNumber().length() < 4) {
            return new ArrayList<>();
        }
        String phoneKeywords = this.phoneKeywords(person.getPhoneNumber());

        person.setPhoneNumber(phoneKeywords);
        return personMapper.queryByPhoneEncrypt(person);
    }

    /**
     * 添加人员信息
     *
     * @param person
     * @return
     */
    @PostMapping("/add")
    @NeedEncrypt
    public Person add(@RequestBody Person person) {
        personMapper.insert(person);
        return person;
    }

    /**
     * 人员信息列表查询
     *
     * @return
     */
    @GetMapping("/list")
    @NeedDecrypt
    public List<Person> getPerson() {
        List<Person> persons = personMapper.selectList(Wrappers.<Person>lambdaQuery());
        return persons;
    }

    /**
     * 人员信息详情查询
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @NeedDecrypt
    public Person get(@PathVariable Long id) {
        Person person = personMapper.selectById(id);
        return person;
    }

}
