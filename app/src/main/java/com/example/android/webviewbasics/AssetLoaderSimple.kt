

package com.example.android.webviewbasics


import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewAssetLoader.AssetsPathHandler
import com.example.android.webviewbasics.databinding.ActivityAssetLoaderBinding


//Copyright
//
//Copyright (C) 2020 The Android Open Source Project
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

/**
 * An [Activity] to show case a very simple use case of using
 * [androidx.webkit.WebViewAssetLoader].
 */

class AssetLoaderSimpleActivity : AppCompatActivity() {
    private inner class MyWebViewClient : WebViewClient() {
        // use the old one for compatibility with all API levels.
        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            return false
        }

        @RequiresApi(21)
        override fun shouldInterceptRequest(
            view: WebView,
            request: WebResourceRequest
        ): WebResourceResponse? {
            return mAssetLoader!!.shouldInterceptRequest(request.url)
        }

        // use the old one for compatibility with all API levels.
        override fun shouldInterceptRequest(
            view: WebView,
            request: String
        ): WebResourceResponse? {
            return mAssetLoader?.shouldInterceptRequest(Uri.parse(request))
        }
    }

    private var mAssetLoader: WebViewAssetLoader? = null
    private var mWebView: WebView? = null
    private lateinit var binding: ActivityAssetLoaderBinding
    private lateinit var webView: WebView


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.example.android.webviewbasics.R.layout.activity_asset_loader)
        mWebView = binding.webViewAssetLoaderWebView
        mWebView!!.setWebViewClient(MyWebViewClient())

        // Host application assets under http://appassets.androidplatform.net/assets/...
        mAssetLoader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", AssetsPathHandler(this))
            .build()
        val path = Uri.Builder()
            .scheme("https")
            .authority(WebViewAssetLoader.DEFAULT_DOMAIN)
            .appendPath("assets")
            .appendPath("www")
            .appendPath("some_text.html")
            .build()

        mWebView!!.loadUrl(path.toString())
    }
}