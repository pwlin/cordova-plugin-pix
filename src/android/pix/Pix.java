/*
The MIT License (MIT)

Copyright (c) 2019 pwlin - pwlin05@gmail.com - https://gtihub.com/pwlin/cordova-plugin-pix

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package io.github.pwlin.cordova.plugins.pix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

public class Pix extends CordovaPlugin {

    private CallbackContext cntx;
    private static ProgressDialog pDialogObj = null;

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.cntx = callbackContext;
        if (this.cordova.getActivity().isFinishing()) {
            return true;
        } else {
            if (action.equals("open")) {
                _open();
            } else {
                JSONObject errorObj = new JSONObject();
                errorObj.put("status", PluginResult.Status.INVALID_ACTION.ordinal());
                errorObj.put("message", "Invalid action");
                this.cntx.error(errorObj);
            }
            return true;
        }
    }

    private void _open() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                _showLoader();
                Intent intent = new Intent("io.github.pwlin.cordova.plugins.pix.DialogShowPicker");
                // intent.putExtra("key1", "val1");
                cordova.startActivityForResult(Pix.this, intent, 100);
            }
        };
        this.cordova.getActivity().runOnUiThread(runnable);
    }

    private void _showLoader() {
        if (pDialogObj != null && pDialogObj.isShowing()) {
            _hideLoader();
        }
        pDialogObj = new ProgressDialog(cordova.getActivity(), ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
        pDialogObj.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialogObj.setCancelable(false);
        pDialogObj.setCanceledOnTouchOutside(false);
        // pDialogObj.setTitle("...");
        pDialogObj.setMessage("Loading...");
        pDialogObj.setIndeterminate(true);
        pDialogObj.show();
    }

    private void _hideLoader() {
        pDialogObj.dismiss();
        pDialogObj = null;
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                _sendResponse(requestCode, resultCode, data);
                _hideLoader();
            };
        };
        this.cordova.getActivity().runOnUiThread(runnable);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void _sendResponse(final int requestCode, final int resultCode, final Intent data) {
        Bundle extras = data.getExtras();
        ArrayList<String> imagesList = extras.getStringArrayList("data");
        if (resultCode == this.cordova.getActivity().RESULT_OK) {
            try {
                JSONObject retObj = new JSONObject();
                retObj.put("status", this.cordova.getActivity().RESULT_OK);
                retObj.put("message", "RESULT_OK");
                JSONArray arObj = new JSONArray();
                for (String s : imagesList) {
                    JSONObject dataObj = new JSONObject();
                    dataObj.put("file", s);
                    dataObj.put("base64", this._toBase64(s));
                    dataObj.put("type", "image/jpeg");
                    dataObj.put("prefix", "data:image/jpeg;charset=utf-8;base64,");
                    arObj.put(dataObj);
                }
                retObj.putOpt("data", arObj);
                this.cntx.success(retObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == this.cordova.getActivity().RESULT_CANCELED) {
            try {
                JSONObject retObj = new JSONObject();
                retObj.put("status", this.cordova.getActivity().RESULT_CANCELED);
                retObj.put("message", "RESULT_CANCELED");
                JSONArray arObj = new JSONArray();
                retObj.putOpt("data", arObj);
                this.cntx.error(retObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String _toBase64(String filePath) {
        File imagefile = new File(filePath);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imagefile);
        } catch (FileNotFoundException e) {
            // logger.error(Log.getStackTraceString(e));
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] img = baos.toByteArray();
        String s = Base64.encodeToString(img, Base64.NO_WRAP);
        return s;
    }

}
