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

import android.app.Activity;
import android.content.Intent;
import java.util.ArrayList;

public class DialogShowPicker extends Activity {
	private boolean firstTime = true;

	@ Override
	public void onStart() {
		super.onStart();
		if (firstTime == true) {
			// Bundle extras = getIntent().getExtras();
			// if (extras != null) { ... }
			// String key1 = extras.getString("key1");
			com.fxn.pix.Pix.start(this, 100, 10);
		}
	}

	@ Override
	public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		firstTime = false;
		Intent intent = new Intent();
		switch (resultCode) {
		case 0:
		default:
			// cancelled
			ArrayList < String > dataEmpty = new ArrayList < String > () { {}
			};
			intent.putStringArrayListExtra("data", dataEmpty);
			setResult(Activity.RESULT_CANCELED, intent);
			break;

		case -1:
			// ok
			ArrayList < String > returnValue = data.getStringArrayListExtra(com.fxn.pix.Pix.IMAGE_RESULTS);
			intent.putStringArrayListExtra("data", returnValue);
			this.setResult(Activity.RESULT_OK, intent);
			break;
		}
		finish();
	}
}
