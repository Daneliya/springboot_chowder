package com.xxl.encrypt_hutool.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.core.toolkit.AES;
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
 * @Description: 分词加密测试
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

    /**
     * 单个用户保存测试手机号加密
     *
     * @param person
     * @return
     */
    @RequestMapping(value = "/savePerson", method = RequestMethod.POST)
    public String savePerson(@RequestBody Person person) {
        person.setId(SnowIdUtils.getNextId());
        String encrypt = SymmetricCryptoUtils.encrypt(person.getPhoneNumber());
        person.setPhoneNumber(encrypt);
        // 随机生成18为长度的字符串
        String idCard = RandomUtil.randomNumbers(18);

        // AES
        String key = AES.generateRandomKey();
        String encryptedIdCardNumber = AES.encrypt(idCard, key);
        person.setIdCard(encryptedIdCardNumber);
        person.setIdCardEncrypted(key);
//        String decrypt = AES.decrypt(encryptedIdCardNumber, key);
//        System.out.println(decrypt);

        // RSA
        // 使用公钥加密算法：可以使用RSA（Rivest-Shamir-Adleman）等公钥加密算法对身份证号进行加密。公钥加密算法使用公钥进行加密，私钥进行解密
        String publicKey = "xxl-idCard"; // 公钥用于加密
        String idCardNumber = idCard;
        RSA rsa = SecureUtil.rsa(idCardNumber, publicKey);
        person.setIdCard(rsa.getPublicKeyBase64());
        person.setIdCardEncrypted(rsa.getPrivateKeyBase64());
        registe(person);
        return "保存成功";
    }

    /**
     * 批量初始化
     *
     * @param personList
     * @return
     */
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
            // 随机生成18为长度的字符串
            String idCard = RandomUtil.randomNumbers(18);

            // AES
//            String key = AES.generateRandomKey();
//            String encryptedIdCardNumber = AES.encrypt(idCard, key);
//            e.setIdCard(encryptedIdCardNumber);
//            e.setIdCardEncrypted(key);

            // RSA
            String publicKey = "xxl-idCard"; // 公钥用于加密
            RSA rsa = SecureUtil.rsa(idCard, publicKey);
            e.setIdCard(rsa.getPublicKeyBase64());
            e.setIdCardEncrypted(rsa.getPrivateKeyBase64());
            registe(e);
        });
        long end2 = System.currentTimeMillis();
        System.out.println("耗费时间:" + (end2 - start2));

        return "保存成功";
    }

    /**
     * 保存分词数据
     *
     * @param person
     * @return
     */
    public Person registe(Person person) {
        personMapper.insert(person);

        // 解密手机号
        String phone = SymmetricCryptoUtils.decrypt(person.getPhoneNumber());
        // 分词加密
        String phoneKeywords = SymmetricCryptoUtils.phoneKeywords(phone);

        PersonPhoneEncrypt personPhoneEncrypt = new PersonPhoneEncrypt();
        personPhoneEncrypt.setPersonId(person.getId());
        personPhoneEncrypt.setPhoneKey(phoneKeywords);
        personPhoneEncryptMapper.insert(personPhoneEncrypt);
        return person;
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
        String phoneKeywords = SymmetricCryptoUtils.phoneKeywords(person.getPhoneNumber());

        person.setPhoneNumber(phoneKeywords);


        List<Person> personList = personMapper.queryByPhoneEncrypt(person);
        for (Person peolpe : personList) {
//            String decrypt = AES.decrypt(peolpe.getIdCard(), peolpe.getIdCardEncrypted());
//            peolpe.setIdCard(decrypt);
            String encryptBcd = SecureUtil.rsa(peolpe.getIdCard(), peolpe.getIdCardEncrypted()).encryptBcd("xxl-idCard", KeyType.PrivateKey);
            peolpe.setIdCard(encryptBcd);
        }
        return personList;
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
