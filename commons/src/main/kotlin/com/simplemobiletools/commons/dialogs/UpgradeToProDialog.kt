package com.simplemobiletools.commons.dialogs

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.simplemobiletools.commons.R
import com.simplemobiletools.commons.extensions.baseConfig
import com.simplemobiletools.commons.extensions.launchUpgradeToProIntent
import com.simplemobiletools.commons.extensions.launchViewIntent
import com.simplemobiletools.commons.extensions.setupDialogStuff
import kotlinx.android.synthetic.main.dialog_upgrade_to_pro.view.*

class UpgradeToProDialog(val activity: Activity) {
    private val packageName = activity.baseConfig.appId.removeSuffix(".debug")
    private val FIRST_APPS_FREE_TILL = 1541980800000L     // November 12
    private val NOV_12 = "Nov 12 2018"

    private val CONTACTS_FREE_TILL = 1542067200000L     // November 13
    private val NOV_13 = "Nov 13 2018"

    init {
        var text = activity.getString(R.string.upgrade_to_pro_long)
        if ((packageName.endsWith("draw") || packageName.endsWith("gallery") || packageName.endsWith("filemanager")) && System.currentTimeMillis() < FIRST_APPS_FREE_TILL) {
            val freeTill = String.format(activity.getString(R.string.it_is_free), NOV_12)
            text += "\n$freeTill"
        } else if (packageName.endsWith("contacts") && System.currentTimeMillis() < CONTACTS_FREE_TILL) {
            val freeTill = String.format(activity.getString(R.string.it_is_free), NOV_13)
            text += "\n$freeTill"
        }

        val view = activity.layoutInflater.inflate(R.layout.dialog_upgrade_to_pro, null).apply {
            upgrade_to_pro.text = text
        }

        AlertDialog.Builder(activity)
                .setPositiveButton(R.string.upgrade) { dialog, which -> upgradeApp() }
                .setNeutralButton(R.string.more_info, null)
                .setNegativeButton(R.string.cancel, null)
                .create().apply {
                    activity.setupDialogStuff(view, this)
                    getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener {
                        moreInfo()
                    }
                }
    }

    private fun upgradeApp() {
        activity.launchUpgradeToProIntent()
    }

    private fun moreInfo() {
        activity.launchViewIntent("https://medium.com/@tibbi/some-simple-mobile-tools-apps-are-becoming-paid-d053268f0fb2")
    }
}
