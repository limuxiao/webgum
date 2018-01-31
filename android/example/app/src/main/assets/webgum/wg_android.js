var WG_android = function () {

    this.getOsInfo = function () {
        return JSON.parse(wg_android_native.getOsInfo())
    };
    this.alert = function (test) {
        test('hhhh')
    };

};

window.wg_android = new WG_android()