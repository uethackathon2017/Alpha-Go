/*
 * Copyright 2016 Matthew Tamlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.quang.tracnghiemtoan.acivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.matthewtamlin.sliding_intro_screen_library.buttons.IntroButton;
import com.matthewtamlin.sliding_intro_screen_library.buttons.IntroButton.ProgressToNextActivity;

/**
 * Test the IntroActivity and its sub-components when multiple pages are shown. The activity should
 * display as follows: <ul> <li>Window: Status bar shown.</li> <li>Pages: 3.</li> <li>Background:
 * Blue, pink and purple for the pages respectively.</li> <li>Transformation: No parallax
 * effect.</li> <li>Page indicator: Shown with 3 dots. Animations are enabled.</li> <li>Left button:
 * Displayed on first two pages. Displays "SKIP" and no icon. Skips to last page.</li> <li>Right
 * button: Displayed on first two pages. Displays no text and has a right carat. Moves to next
 * page.</li> <li>Final button: Displayed on last page. Displays "FINAL" with no icon. Advances to
 * end activity when pressed.</li> </ul>
 */
public class TestMultiplePageActivity extends ThreePageTestBase {
	/**
	 * Used to identify this class during debugging.
	 */
	@SuppressWarnings("unused")
	private static final String TAG = "[TestMultiplePageBe...]";

	@Override
	protected IntroButton.Behaviour generateFinalButtonBehaviour() {
		final Intent nextActivity = new Intent(this, SplashActivity.class);
		SharedPreferences pre = getSharedPreferences("setting", MODE_PRIVATE);
		SharedPreferences.Editor editor = pre.edit();
		editor.putBoolean("intro", true);
		editor.commit();
		return new ProgressToNextActivity(nextActivity, null);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFinalButtonAccessor().setText("DONE", null);
		getFinalButtonAccessor().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}