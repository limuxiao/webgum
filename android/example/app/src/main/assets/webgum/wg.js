/**
 * @Title:
 * @Desc:
 * @CreateTime: 2018-01-08 09:09
 * @Creator: Liy
 * @Version: 1.0.1
 *
 */
(function (){


    try {


        var wg = {

        }

        var ua = window.navigator.userAgent.toLowerCase();
        if(ua.indexOf("webgum") > -1){

            var ua_webgum = ua.match('webgum/[0-9_.]+\\s\\([a-z;-\\s]+\\)','g')
            var os_name = ua_webgum[0].match('\\([a-z;-\\s]+\\)','g')[0]

            if(os_name.indexOf('rn-android') > -1){
                wg = window.wg_rn_android
            }else if(os_name.indexOf('android') > -1){
                wg = window.wg_android
            }else if(os_name.indexOf('rn-ios') > -1){
                wg = window.wg_ios
            }else if(os_name.indexOf('ios') > -1){
                wg = window.wg_ios
            }else{
                //
            }

        }else{
            // 不支持wg
        }

        window.wg = wg
        alert(wg)

    }catch (e){
        alert(e.message)
    }



})()