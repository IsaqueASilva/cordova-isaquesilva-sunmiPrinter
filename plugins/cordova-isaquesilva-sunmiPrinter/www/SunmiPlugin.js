var exec = cordova.require('cordova/exec');

var SunmiPlugin = function() {
    console.log('AndroidToast instanced');
};

SunmiPlugin.prototype.show = function(msg, onSuccess, onError) {
    var errorCallback = function(obj) {
        onError(obj);
    };

    var successCallback = function(obj) {
        onSuccess(obj);
    };

    exec(successCallback, errorCallback, 'SunmiPlugin', 'startService', [msg]);
    exec(successCallback, errorCallback, 'SunmiPlugin', 'checkStatus', [msg]);
    exec(successCallback, errorCallback, 'SunmiPlugin', 'sendPrint', [msg]);
    exec(successCallback, errorCallback, 'SunmiPlugin', 'finalizeService', [msg]);
};

    module.exports = SunmiPlugin;
