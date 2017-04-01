// var apiHost = "http://127.0.0.1:8080";
var apiHost = "http://www.fireflyenglish.com";
var weixinAppId = "wx2fd08c728191fbfb";


var api = {
    sessionUser: apiHost + "/api/user/sessionUser",
    weixinAuthState: apiHost + "/api/weixin/authState",
    weixinAuth: apiHost + "/api/weixin/auth",
};

var authorizationPage = encodeURI(apiHost + "/weixin/authorization.html");
var weixinApi = {
    authorize: function (state) {
        return "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
            + weixinAppId + "&redirect_uri=" + authorizationPage +
            "&response_type=code&scope=snsapi_userinfo&state=" + state + "#wechat_redirect";
    },
}