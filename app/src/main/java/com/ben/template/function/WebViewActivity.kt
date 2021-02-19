package com.ben.template.function

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ben.template.R
import java.util.*

/**
 * 网页界面
 *
 * @author Benhero
 */
class WebViewActivity : Activity() {
    private var mWebView: WebView? = null
    private var mLoading: View? = null
    private var mLoadUrl: String? = "https://www.baidu.com/"
    private var isInit = false

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        val url = intent.getStringExtra(INTENT_DATA_URL)
        if (!TextUtils.isEmpty(url)) {
            mLoadUrl = url
        }
        loadDataFromExternal(intent)
        mWebView = findViewById(R.id.web_view)
        mLoading = findViewById(R.id.loading_view)
        initView()
    }

    private fun initView() {
        mWebView!!.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        //支持javascript
        val webSettings = mWebView!!.settings
        webSettings.javaScriptEnabled = true
        // 设置可以支持缩放
        webSettings.setSupportZoom(true)
        // 设置出现缩放工具
        webSettings.builtInZoomControls = true
        //扩大比例的缩放
        webSettings.useWideViewPort = true
        //自适应屏幕
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webSettings.loadWithOverviewMode = true

        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        //断网情况下加载本地缓存
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

        //让WebView支持DOM storage API
        webSettings.domStorageEnabled = true

        //让WebView支持缩放
        webSettings.setSupportZoom(true)

        //启用WebView内置缩放功能
        webSettings.builtInZoomControls = true

        //让WebView支持可任意比例缩放
        webSettings.useWideViewPort = true

        //让WebView支持播放插件
        webSettings.pluginState = WebSettings.PluginState.ON

        //设置WebView使用内置缩放机制时，是否展现在屏幕缩放控件上
        webSettings.displayZoomControls = false

        //设置在WebView内部是否允许访问文件
        webSettings.allowFileAccess = true

        //设置脚本是否允许自动打开弹窗
        webSettings.javaScriptCanOpenWindowsAutomatically = true

        // 加快HTML网页加载完成速度
        webSettings.loadsImagesAutomatically = true
        // 开启Application H5 Caches 功能
        webSettings.setAppCacheEnabled(true)
        // 设置编码格式
        webSettings.defaultTextEncodingName = "utf-8"

        //如果不设置WebViewClient，请求会跳转系统浏览器
        mWebView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    try {
                        val intent = Intent.parseUri(url, 0)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    } catch (e: Throwable) {
                    }
                    return true
                }
                return false
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                if (!isInit && mLoadUrl == url) {
                    isInit = true
                    // 初始化完毕，加载完网址首页
                    mLoading!!.visibility = View.GONE
                }
            }
        }
        mWebView!!.loadUrl(mLoadUrl!!)
        mWebView!!.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            if ("application/vnd.android.package-archive" == mimetype && !TextUtils.isEmpty(url)) {
                var pkgName: String? = null
                if (url.endsWith(".apk")) {
                    pkgName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf('.'))
                }
                if (TextUtils.isEmpty(pkgName)) {
                    pkgName = UUID.randomUUID().toString()
                }
            }
        }
    }

    private fun loadDataFromExternal(intent: Intent) {
        val data: Uri? = intent.data
        if (data != null) {
            try {
                val scheme: String? = data.getScheme()
                val host: String? = data.getHost()
                val path: String? = data.getPath()
                val text = "Scheme: $scheme\nhost: $host\npath: $path"
                Log.i("JKL", "URL : $text")
                mLoadUrl = "$scheme://$host$path"
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            loadDataFromExternal(it)
            mLoadUrl?.let { url -> mWebView?.loadUrl(url) }
        }
    }

    override fun onBackPressed() {
        if (mWebView!!.canGoBack()) {
            //点击返回按钮的时候判断有没有上一页
            mWebView!!.goBack()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        /**
         * intent数据传输的url key
         */
        private const val INTENT_DATA_URL = "intent_data_url"
        fun start(context: Activity, requestCode: Int, url: String?) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(INTENT_DATA_URL, url)
            context.startActivityForResult(intent, requestCode)
        }
    }
}