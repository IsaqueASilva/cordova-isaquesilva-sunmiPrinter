package br.eti.isaquesilva.sunmi.cordovaPlugin;


import android.content.Context;
import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.eti.isaquesilva.ionic.plugins.PluginProcessor;
import br.eti.isaquesilva.printerUtils.sunmi.PrinterService;

public class SunmiPlugin extends CordovaPlugin {

    PrinterService impressora;
    PluginProcessor processor;


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        switch (action){
            case "startService": startService(callbackContext); break;
            case "checkStatus": checkStatus(callbackContext); break;
            case "sendPrint": sendPrint(args.getJSONObject(0),callbackContext); break;
            case "finalizeService": finalizeService(callbackContext); break;
        }
        return true;
    }

        public void startService(CallbackContext callbackContext){
            //Pega o contexto da aplicação
            Context context = this.cordova.getContext();

            impressora = PrinterService.getInstance();

            //Inicializa serviço de Impressão para o APP
            impressora.initPrinterService(context);

            if(impressora.sunmiPrinter == PrinterService.NoSunmiPrinter){
                callbackContext.error("Impressora Nao Encontrada");
                callbackContext.success();
            }

            if(impressora.sunmiPrinter == PrinterService.FoundSunmiPrinter){
                callbackContext.success();
            }else if(impressora.sunmiPrinter == PrinterService.CheckSunmiPrinter){
                callbackContext.success();
            }else if(impressora.sunmiPrinter == PrinterService.LostSunmiPrinter){
                callbackContext.success();
            }
        }

        public void checkStatus(CallbackContext callbackContext){
            //Pega o contexto da aplicação
            Context context = this.cordova.getContext();
            try {
                JSONObject result = new JSONObject();
                result.put("STATUS_DESCRIPTION",impressora.showPrinterStatus().toString());
                result.put("STATUS_NAME",impressora.showPrinterStatus());
                callbackContext.success(result);
            }catch (JSONException e){
                callbackContext.error("Error on check status");
            }
        }

        public void  sendPrint(JSONObject requestData,CallbackContext callbackContext){

            try{
                processor = new PluginProcessor(impressora,requestData);
                processor.printContent();
            }catch (Exception e){
                callbackContext.error("Erro na execução do plugin");
            }
            processor = null;

            callbackContext.success();
            callbackContext.success();

        }

        public void finalizeService(CallbackContext callbackContext){
            //Pega o contexto da aplicação
            Context context = this.cordova.getContext();
            impressora.deInitPrinterService(context);
        }
}
