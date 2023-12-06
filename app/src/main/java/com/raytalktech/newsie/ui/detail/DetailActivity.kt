package com.raytalktech.newsie.ui.detail

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.raytalktech.newsie.R
import com.raytalktech.newsie.data.source.local.entity.DataEntity
import com.raytalktech.newsie.databinding.ActivityDetailBinding
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader


class DetailActivity : AppCompatActivity() {

    companion object {
        const val DATA_RESULT: String = "data"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var adservers: StringBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.getParcelableExtra<DataEntity>(DATA_RESULT)
        if (bundle != null) {
            readAdServers()
            populateWebView(bundle.url)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun populateWebView(url: String) {
        binding.wvDetail.let {
            with(it) {

                // WebView Control
                scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
                isScrollbarFadingEnabled = true
                isLongClickable = true
                setLayerType(View.LAYER_TYPE_HARDWARE, null)
                webViewClient = MyWebViewClient()
                registerForContextMenu(it)

                // Set WebSettings for a WebView
                val webSettings = it.settings
                webSettings.setSupportZoom(true)
                webSettings.builtInZoomControls = true
                webSettings.displayZoomControls = false
                webSettings.loadWithOverviewMode = true
                webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
                webSettings.domStorageEnabled = true

                // Load google.de
                loadUrl(url)

                val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
                val ani = cm.activeNetworkInfo
                if (ani != null && ani.isConnected)
                    webSettings.cacheMode = WebSettings.LOAD_DEFAULT
                else
                    webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

                webSettings.allowFileAccess = true
                webSettings.javaScriptEnabled =
                    true                            // Enable this only if you need JavaScript support!
                webSettings.javaScriptCanOpenWindowsAutomatically =
                    false       // Enable this only if you want pop-ups!
                webSettings.mediaPlaybackRequiresUserGesture = true

            }
        }
    }

    // if you press Back button this code will work
    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (binding.wvDetail.canGoBack())
            binding.wvDetail.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }

    private fun readAdServers() {
        adservers = StringBuilder()

        var line: String? = ""
        val inputStream = resources.openRawResource(R.raw.adblockserverlist)
        val br = BufferedReader(InputStreamReader(inputStream))

        try {
            while (br.readLine().also { line = it } != null) {
                adservers.append(line)
                adservers.append("\n")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    //Advertise filter with the lists
    inner class MyWebViewClient : WebViewClient() {
        override fun shouldInterceptRequest(
            view: WebView,
            request: WebResourceRequest
        ): WebResourceResponse? {
            val empty = ByteArrayInputStream("".toByteArray())
            val kk5 = adservers.toString()

            if (kk5.contains(":::::" + request.url.host))
                return WebResourceResponse("text/plain", "utf-8", empty)

            return super.shouldInterceptRequest(view, request)
        }

        override fun onPageCommitVisible(view: WebView?, url: String?) {
            super.onPageCommitVisible(view, url)
            binding.progressBar.isGone = true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.progressBar.isGone = true
        }

    }

}