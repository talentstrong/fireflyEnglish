var common = {
    getSessionUser: function () {
        return $.ajax({
            url: api.sessionUser,
            type: 'GET', //GET
            async: false,
            dataType: 'json'
        });
    },
    getWeixinAuthState: function () {
        return $.ajax({
            url: api.weixinAuthState,
            type: 'GET', //GET
            async: false,
            dataType: 'json'
        });
    },
    submitWeixinAuth: function (state, code) {
        return $.ajax({
            url: api.weixinAuth,
            type: 'POST', //GET
            async: false,
            data : {
                state : state,
                code : code
            },
            dataType: 'json'
        });
    }

};