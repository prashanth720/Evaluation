package com.example.evaluation.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class FragmentHelper {


	public static void replace(FragmentManager mgr, int id, Fragment frgmt) {
		mgr.popBackStack();
		mgr.beginTransaction().replace(id, frgmt).addToBackStack(null).commit();
	}

	public static void addToBackStackAndAddWithAnimation(
			FragmentManager mgr, int id, Fragment frgmt, int enterAnimation, int exitAnimation) {
		mgr.beginTransaction().setCustomAnimations(enterAnimation, exitAnimation, enterAnimation, exitAnimation)
				.addToBackStack(null)
				.add(id, frgmt).commit();
	}

	}