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
var exec = require('cordova/exec');

class Pix {
	constructor() {}
	open(callbackContext) {
		callbackContext = callbackContext || {};
		cordova.plugins.permissions.requestPermissions([
				cordova.plugins.permissions.CAMERA,
				cordova.plugins.permissions.READ_EXTERNAL_STORAGE,
				cordova.plugins.permissions.WRITE_EXTERNAL_STORAGE,
				cordova.plugins.permissions.READ_MEDIA_IMAGES
			], function (status) {
			exec(callbackContext.success || null, callbackContext.error || null, 'Pix', 'open', [callbackContext.quality || 50]);
		}, function () {
			console.error(arguments);
		});
	}
}

module.exports = new Pix();