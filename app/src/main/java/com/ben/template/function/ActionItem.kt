package com.ben.template.function

import android.content.Intent
import android.net.Uri
import com.ben.template.XApplication

/**
 * 动作列表项
 *
 * @author Benhero
 * @date   2021-02-19
 */
enum class ActionItem(val action: String) {
    BROWSER("打开浏览器");
}

fun dispatchAction(actionName: String) {
    when (findActionItem(actionName)) {
        ActionItem.BROWSER -> {
            //从其他浏览器打开
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            val content_url: Uri = Uri.parse("https://www.jianshu.com/u/c2908c614203")
            intent.data = content_url
            val createChooser = Intent.createChooser(intent, "请选择浏览器")
            createChooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            XApplication.app.startActivity(createChooser)
        }
    }
}

fun findActionItem(action: String): ActionItem {
    ActionItem.values().forEach {
        if (it.action == action) {
            return it
        }
    }
    throw IllegalArgumentException("没有匹配的枚举字段")
}