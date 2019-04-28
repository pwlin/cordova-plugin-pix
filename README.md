# A Cordova Plugin for Pix Image Picker

This plugin is a port of [Pix (WhatsApp Style Image Picker)](https://github.com/akshay2211/PixImagePicker) for using with Cordova.

![cdv-pix-screenshot](https://i.imgur.com/viYvm8S.gif)

```js
cordova.plugins.Pix.open(
    {
        success: function(response){},
        error: function(e){}        
    }
);
```

## Installation

```shell
$ cordova plugin add cordova-plugin-pix
```

## Requirements

The following platforms and versions are supported by the latest release:

- Android 4.4+
- Cordova CLI 7.0 or higher

## Pix.open(callbackContext)

Opens Pix Image Picker and get selected files back as an object.

### Supported Platforms

- Android 4.4+

### Quick Examples

Open Pick Imager Picker and set a callback for success or fail events.

```js
cordova.plugins.Pix.open(
    { 
        success: function(response) {
            console.log(response);
        },
        error: function(e) {
            console.error(e);
        }
    }
);
```

Format of the `response`:

```json
{
    "status": -1,
    "message": "RESULT_OK",
    "data": [
        {
            "file": "/storage/emulated/0/DCIM/Camera/IMG_20190415_182649.jpg",
            "base64": "/9j/4AAQSkZJRgABAQA...",
            "type": "image/jpeg",
            "prefix": "data:image/jpeg;charset=utf-8;base64,"
        }, 
        {
            "file": "/storage/emulated/0/DCIM/Camera/IMG_20190415_182644.jpg",
            "base64": "/9j/4AAQSkZJRgABAQA...",
            "type": "image/jpeg",
            "prefix": "data:image/jpeg;charset=utf-8;base64,"
        }
    ]
}

```

---
