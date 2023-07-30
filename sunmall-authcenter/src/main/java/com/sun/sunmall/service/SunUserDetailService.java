package com.sun.sunmall.service;

import com.sun.sunmall.domain.MemberDetails;
import com.sun.sunmall.mapper.UmsMemberMapper;
import com.sun.sunmall.model.UmsMember;
import com.sun.sunmall.model.UmsMemberExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @vlog: 高于生活，源于生活
* @desc: 类的描述:认证服务器加载用户的类
* @author: smlz
* @createDate: 2020/1/21 21:29
* @version: 1.0
*/
@Slf4j
@Component
public class SunUserDetailService implements UserDetailsService {

    /**
     * 方法实现说明:用户登陆
     * @author:smlz
     * @param userName 用户名密码
     * @return: UserDetails
     * @exception:
     * @date:2020/1/21 21:30
     */

    @Autowired
    private UmsMemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        if(StringUtils.isEmpty(userName)) {
            log.warn("用户登陆用户名为空:{}",userName);
            throw new UsernameNotFoundException("用户名不能为空");
        }

        UmsMember umsMember = getByUsername(userName);

        if(null == umsMember) {
            log.warn("根据用户名没有查询到对应的用户信息:{}",userName);
        }

        log.info("根据用户名:{}获取用户登陆信息:{}",userName,umsMember);

        MemberDetails memberDetails = new MemberDetails(umsMember);

        return memberDetails;
    }

    /**
     * 方法实现说明:根据用户名获取用户信息
     * @author:smlz
     * @param username:用户名
     * @return: UmsMember 会员对象
     * @exception:
     * @date:2020/1/21 21:34
     */
    public UmsMember getByUsername(String username) {
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsMember> memberList = memberMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(memberList)) {
            return memberList.get(0);
        }
        return null;
    }
}
