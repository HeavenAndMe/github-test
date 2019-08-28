package com.funtl.my.shop.web.ui.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.funtl.my.shop.commons.utils.RegexpUtils;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author Zhan
 * @create 2019/8/25 - 13:22
 */
@Data
public class TbUser implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String verification;
}
