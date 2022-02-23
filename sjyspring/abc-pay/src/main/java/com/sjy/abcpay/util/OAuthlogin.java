package com.sjy.abcpay.util;


import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 第三方商户授权登陆对接快 E 通
 * 方法  三个
 */
@UtilityClass
public class OAuthlogin {

    /**
     * 掌银扫码调起交易获取临时令牌
     *
     * @param appId       分配给应用的 appid
     * @param redirectUri 成功授权后的回调地址，必须是注册 appid 时填写的主域 名下的地址，建议设置为网站首页或者用户中心。
     * @param state       Client 端的状态值。用于第三方应用防止 CSRF 攻击，成功 授权后回调时会原样带回。请务必严格按照流程检查用户 与 state 参数状态的绑定。
     * @param scope       请求用户授权时向用户显示的可进行授权的列表
     */

    public void oauthLogin(String appId, String redirectUri, String state, String scope) {

        String oauthLoginUrl = "https://www.abchina.com/luascript/oauthLogin/";
        //参数
        String template = "{\"client_id\":\"{}\",\"redirect_uri\":\"{}\",\"state\":\"{}\",\"scope\":\"{}\",\"response_type\":\"code\"}";

        String str = StrUtil.format(template, appId, redirectUri, state, scope);

        System.out.println(str);
        String url = oauthLoginUrl + str;

        String result = HttpRequest.get(url).execute().body();
        if (StrUtil.isNotBlank(result)) {
            UrlUtil.UrlEntity parse = UrlUtil.parse(result);
            String rCode = parse.params.get("code");
            String rState = parse.params.get("state");
            Console.log(rCode);
            Console.log(rState);
        }
    }

    /**
     * 第三方通过临时令牌获取访问令牌
     *
     * @param appid        分配给应用的 appid
     * @param appkey
     * @param code
     * @param redirect_uri 与上一步中传入的 redirect_uri 保持一致。
     */
    @SneakyThrows
    public void oauth2(String appid, String appkey, String code, String redirect_uri) {
        //第三方通过临时令牌获取访问令牌
        String tokenUrl = "https://openbank.abchina.com/OAuthServer/OAuth/Token";

        HttpResponse execute = HttpRequest
                .post(tokenUrl)
                .body(
                        JSONUtil.createObj()
                                .put("client_id", appid)
                                .put("client_secret", appkey)
                                .put("code", code)
                                .put("grant_type", "authorization_code")
                                .put("redirect_uri", redirect_uri)
                                .toString()
                ).execute();

        String resStr = execute.body();
        if (StrUtil.isNotBlank(resStr)) {
            JSONObject jObj = JSONUtil.parseObj(resStr);
            String accessToken = jObj.getStr("access_token");
            String tokenTypeype = jObj.getStr("token_type");
            String expiresIn = jObj.getStr("expires_in");
            String refreshToken = jObj.getStr("refresh_token");
            String scope = jObj.getStr("scope");

        }
    }

    /**
     * 3 第三方用访问令牌获取用户信息
     *
     * @param accessToken
     */

    @SneakyThrows
    public void getUserInfo(String accessToken) {

        String userInfoUrl = "https://openbank.abchina.com/OAuthResource/api/userinfo";
        String resStr = cn.hutool.http.HttpRequest.get(userInfoUrl)
                .header("Authorization", "Bearer" + accessToken).execute().body();

        if (StrUtil.isNotBlank(resStr)) {
            JSONObject jObj = JSONUtil.parseObj(resStr);
            /**
             * ,其中 RetCode 0000 为成功，其他为失败，
             * 失败时只有 RetCode 和 RetMsg 信息；
             * RetMsg 为信息内容；
             *
             * 对于同一个第三方的不同应用，
             * 每个应用会拥有独立的 APPID 和 APPSecret，
             * 并且每个 应用获取到同一个用户的 OpenID 也是不同的，。
             */
            String retCode = jObj.getStr("RetCode");
            if ("0000".equals(retCode)) {
                //成功
            }
            //失败
            String RetMsg = jObj.getStr("RetMsg");
            String openId = jObj.getStr("OpenID");

        }
    }

}
