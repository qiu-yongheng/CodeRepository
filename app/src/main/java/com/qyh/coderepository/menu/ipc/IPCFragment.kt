package com.qyh.coderepository.menu.ipc

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.baidu.tts.f.n
import com.qyh.coderepository.R
import com.qyh.coderepository.menu.ipc.aidl.AidlClient
import com.qyh.coderepository.menu.ipc.bundle.BundleService
import com.qyh.coderepository.menu.ipc.file.Book
import com.qyh.coderepository.menu.ipc.file.FileService
import com.qyh.coderepository.menu.ipc.messenger.MessengerClient
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream

/**
 * @author 邱永恒
 *
 * @time 2018/3/17  11:08
 *
 * @desc 跨进程通讯的几种方式
 *
 */

class IPCFragment : Fragment(), View.OnClickListener {
    companion object {
        val TAG = "IPCFragment"
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_ipc, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener(view!!)
    }

    private fun initListener(view: View) {
        view.findViewById<Button>(R.id.btn_bundle).setOnClickListener(this)
        view.findViewById<Button>(R.id.btn_file).setOnClickListener(this)
        view.findViewById<Button>(R.id.btn_messenger).setOnClickListener(this)
        view.findViewById<Button>(R.id.btn_aidl).setOnClickListener(this)
        view.findViewById<Button>(R.id.btn_aidl).setOnLongClickListener {
            AidlClient.unregister()
            true
        }
        view.findViewById<Button>(R.id.btn_content_provider).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_bundle -> {
                val intent = Intent(context, BundleService::class.java)
                val bundle = Bundle()
                bundle.putString(BundleService.DATA_KEY, "我是使用Bundle与你进行进程间通信")
                intent.putExtras(bundle)
                activity.startService(intent)
            }
            R.id.btn_file -> {
                persistToFile()
                activity.startService(Intent(context, FileService::class.java))
            }
            R.id.btn_messenger -> {
                MessengerClient.bindService(context)
            }
            R.id.btn_aidl -> {
                AidlClient.bindService(context)
            }
            R.id.btn_content_provider -> {
                val uri = Uri.parse("content://com.qyh.coderepository.provider.book")
                context.contentResolver.query(uri, null, null, null, null)
                context.contentResolver.query(uri, null, null, null, null)
                context.contentResolver.query(uri, null, null, null, null)
            }
        }
    }

    /**
     * 序列化对象
     */
    private fun persistToFile() = Thread(Runnable {
        val book = Book("Android权威编程指南", 666)
        val file = File(context.cacheDir, "ipc")
        if (!file.exists()) {
            file.mkdirs()
        }
        val bookFile = File(file, "Book")
        var outputStream: ObjectOutputStream? = null
        try {
            outputStream = ObjectOutputStream(FileOutputStream(bookFile))
            outputStream.writeObject(book)
            Log.d(TAG, "序列化book")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            outputStream?.close()
        }

    }).start()
}
