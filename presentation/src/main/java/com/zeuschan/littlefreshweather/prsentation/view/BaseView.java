package com.zeuschan.littlefreshweather.prsentation.view;

import android.content.Context;

/**
 * Created by chenxiong on 2016/6/3.
 */
public interface BaseView {
    /**
     * Show the loading progress bar.
     */
    void showLoading();

    /**
     * Hide the loading progress bar.
     */
    void hideLoading();

    /**
     * Show a dialog that contains specified message.
     * @param msg The message to show.
     */
    void showError(String msg);

    /**
     * Hide the message dialog.
     */
    void hideError();

    /**
     * Show a retry view when some errors occurred.
     */
    void showRetry();

    /**
     * Hide the retry view.
     */
    void hideRetry();

    /**
     * Show the content of the view.
     */
    void showContent();

    /**
     * Hide the content of the view.
     */
    void hideContent();

    /**
     * Get a {@link android.content.Context}.
     */
    Context getContext();
}
