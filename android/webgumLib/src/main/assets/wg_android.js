var webgum_native_callbacks = {};

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
        pluginName:plugin_name,
        methodName:method_name,
        params:args
    };
    return JSON.parse(wg_android_native.onJsCallWithResult(JSON.stringify(req)));
};

function __callNativeWithListener(id,plugin_name,method_name, args){

    var req = {
        id:id,
        pluginName:plugin_name,
        methodName:method_name,
        params:args
    };
    wg_android_native.onJsCallWithListener(JSON.stringify(req));
};

function __bridgeWithResult(plugin_name,method_name,args){

    var id = Math.floor(Math.random() * (1 << 10));
    var args_real = [];
    for(var i in args){
        var name = id + "_" + i;
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

function __bridgeWithListener(plugin_name,method_name,args){
    var id = Math.floor(Math.random() * (1 << 10));
    var args_real = [];
    for(var i in args){
        var name = id + "_" + i;
        var type = __getType(args[i]);
        var value = args[i];
        if(type == 4){
            webgum_native_callbacks[id + "_"+ plugin_name + "_" + method_name + "_" + i] = value;
        }

        args_real.push({
            name:name,
            type:type,
            value:value
        });
    }
    __callNativeWithListener(id,plugin_name,method_name,args_real)
};

var WG_android = function () {

    this.onNativeCallback = function(resp){
        var resp_js = JSON.parse(resp);
        var call = webgum_native_callbacks[resp_js.id];
        var type = __getType(call);
        if (type == 4){
            call(resp_js.result);
        }
    };


    this.getOsInfo = function () {
        return __bridgeWithResult('__main__','getOsInfo',arguments)
    };

    this.getPlugins = function(){
        return __bridgeWithResult('__main__','getPlugins',arguments)
    };

    this.getPlugin = function(plugin_name){
        return this[plugin_name];
    };

    this.testCall = function(){
        __bridgeWithListener("__main__","testCall",arguments);
    }

};

window.wg_android = new WG_android();