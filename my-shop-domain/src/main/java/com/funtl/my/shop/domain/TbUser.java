package com.funtl.my.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.funtl.my.shop.commons.persisten.BaseEntity;
import com.funtl.my.shop.commons.utils.RegexpUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户表
 * @author Zhan
 * @create 2019/8/21 - 8:23
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class TbUser extends BaseEntity implements Serializable {
    @Length(min = 6, max = 20, message = "姓名长度必须介于 6 - 20 位之间")
    private String username;

    // 禁止密码在JSON中传输，避免暴露
    @JsonIgnore
    @Length(min = 6, max = 20, message = "密码长度必须介于 6 - 20 位之间")
    private String password;

    @Pattern(regexp = RegexpUtils.PHONE, message = "手机号格式不正确")
    private String phone;

    @Pattern(regexp = RegexpUtils.EMAIL, message = "邮箱格式不正确")
    private String email;
}
