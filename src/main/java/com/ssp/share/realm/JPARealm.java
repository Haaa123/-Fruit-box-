package com.ssp.share.realm;

import com.ssp.share.pojo.User;
import com.ssp.share.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class JPARealm extends AuthorizingRealm {

    @Autowired
    UserService userService;
    /*
    授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
        return s;
    }

    /*
    验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = token.getPrincipal().toString();
        User user = userService.getByName(userName);
        String passwordInDB = user.getPassword();
        String salt = user.getSalt();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName, passwordInDB, ByteSource.Util.bytes(salt),
                getName());
        return authenticationInfo;
    }
}
