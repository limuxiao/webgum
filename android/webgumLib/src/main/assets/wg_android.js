function __getType(args){
    var t = Object.prototype.toString.apply(args);
    var type = 0;
    if(t.indexOf('String') > 0){
        type = 1;
    } else if(t.indexOf('Number') > 0){
        type = 2;
    } else if(t.indexOf('Boolean') > 0){
        type = 3;
    } else if(t.indexOf('Function') > 0){
        type = 4;
    } else if(t.indexOf('Object') > 0){
        type = 5;
    }else if(t.indexOf('Array') > 0){
        type = 6;
    }
    return type;
};

function __callNativeWithResult(id,plugin_name,method_name, args){
    var req = {
        id:id,
        plugin:plugin_name,
        method:method_name,
        params:args
    };
    var req_str = JSON.stringify(req);
    var rep_str = wg_android_native.onJsCallWithResult(req_str);
    return JSON.parse(rep_str);
};

function __callNativeWithListener(){
};

function __bridgeWithResult(plugin_name,method_name,args){

    var id = Math.floor(Math.random() * (1 << 10));
    var args_real = [];
    for(var i in args){
        var name = id + "_a" + i;
        var type = __getType(args[i]);
        var value = args[i];
        args_real.push({
            name:name,
            type:type,
            value:value
        });
    }
    return __callNativeWithResult(id,plugin_name,method_name,args_real)

};

function __bridgeWithListener(args){

};

var WG_android = function () {

    var callbacks = [];

    this.getOsInfo = function () {
        return __bridgeWithResult('__main__','getOsInfo',arguments)
    };

    this.getPlugins = function(){
        return wg_android_native.getPlugins()
    };

    this.getPlugin = function(plugin_name){
        return this[plugin_name];
    };

    this.getName = function(){
        return __bridgeWithResult(arguments)
    };

};

window.wg_android = new WG_android();


WG_android.prototype.battery = {

    getPower:function(){
        return "30%";
    }

};