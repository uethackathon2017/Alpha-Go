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

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.matthewtamlin.android_utilities_library.helpers.BitmapEfficiencyHelper;
import com.matthewtamlin.android_utilities_library.helpers.ScreenSizeHelper;
import com.matthewtamlin.sliding_intro_screen_library.background.ColorBlender;
import com.matthewtamlin.sliding_intro_screen_library.buttons.IntroButton;
import com.matthewtamlin.sliding_intro_screen_library.core.IntroActivity;
import com.matthewtamlin.sliding_intro_screen_library.pages.ParallaxPage;
import com.quang.tracnghiemtoan.R;

import java.util.ArrayList;
import java.util.Collection;

public abstract class ThreePageTestBase extends IntroActivity {
	/**
	 * Used during testing to identify this class.
	 */
	private static final String TAG = "[ThreePageTestBase]";

	/**
	 * Colors to use for the desired backgrounds.
	 */
	private static final int[] colors = {0xff4185f4, 0xffed1c24,0xff22b14c ,0xffff7f27};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getLeftButtonAccessor().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "[on click] [left button]");
			}
		});

		getRightButtonAccessor().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "[on click] [right button]");
			}
		});

		getFinalButtonAccessor().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "[on click] [final button]");
			}
		});

		setBackgroundManager(new ColorBlender(colors));

	}

	@Override
	protected Collection<Fragment> generatePages(Bundle savedInstanceState) {
		ArrayList<Fragment> pages = new ArrayList<>();

		final int screenWidth = ScreenSizeHelper.getScreenWidthPx(this);
		final int screenHeight = ScreenSizeHelper.getScreenHeightPx(this);

		final Bitmap lines1 = BitmapEfficiencyHelper.decodeResource(this, R.drawable.one,
				screenWidth, screenHeight);
		final Bitmap lines2 = BitmapEfficiencyHelper.decodeResource(this, R.drawable.two,
				screenWidth, screenHeight);
		final Bitmap lines3 = BitmapEfficiencyHelper.decodeResource(this, R.drawable.three,
				screenWidth, screenHeight);
		final Bitmap lines4 = BitmapEfficiencyHelper.decodeResource(this, R.drawable.four,
				screenWidth, screenHeight);

		for (int i = 0; i < colors.length; i++) {
			final ParallaxPage newPage = ParallaxPage.newInstance();
			switch (i){
				case 0:
					newPage.setFrontImage(lines1);
					newPage.setBackImage(lines1);
					break;
				case 1:
					newPage.setFrontImage(lines2);
					newPage.setBackImage(lines2);
					break;
				case 2:
					newPage.setFrontImage(lines3);
					newPage.setBackImage(lines3);
					break;
				case 3:
					newPage.setFrontImage(lines4);
					newPage.setBackImage(lines4);
					break;
			}
			pages.add(newPage);
		}
		return pages;
	}

	/**
	 * @return the colors used in this activity
	 */
	@Override
	protected IntroButton.Behaviour generateFinalButtonBehaviour() {
		return new IntroButton.DoNothing();
	}
}