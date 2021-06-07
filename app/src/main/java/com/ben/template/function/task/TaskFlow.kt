package com.ben.template.function.task

import android.widget.Toast
import com.ben.template.XApplication
import com.elvishew.xlog.XLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 任务流
 *
 * @author Benhero
 * @date   2021/6/3
 */
class TaskFlow {

    private val list = arrayListOf<ITaskNode>()

    init {
        list.add(Task1())
        list.add(Task2())

        bindTaskFlow()
    }

    /**
     * 绑定任务流程
     */
    private fun bindTaskFlow() {
        var temp: ITaskNode? = null
        for (i in 0 until list.size) {
            val reverseIndex = list.size - 1 - i
            if (temp != null) {
                list[reverseIndex].setNext(temp)
            }
            temp = list[reverseIndex]
        }
    }

    fun start() {
        if (list.isEmpty()) {
            return
        }

        list[0].enter()
    }
}

/**
 * 任务节点接口
 */
interface ITaskNode {
    /**
     * 获取下一个任务
     */
    fun getNext(): ITaskNode?

    /**
     * 设置下一个任务
     */
    fun setNext(next: ITaskNode)

    /**
     * 进入任务流程
     */
    fun enter()
}

/**
 * 任务节点
 */
abstract class BaseTaskNode : ITaskNode {
    var nextNode: ITaskNode? = null

    override fun getNext(): ITaskNode? {
        return nextNode
    }

    override fun setNext(next: ITaskNode) {
        nextNode = next
    }

    /**
     * 进入流程判断
     */
    abstract fun onEnter(): Boolean

    /**
     * 执行操作
     */
    abstract fun execute()

    override fun enter() {
        if (onEnter()) {
            execute()
        } else {
            exit()
        }
    }

    /**
     * 退出当前任务
     */
    fun exit() {
        nextNode?.enter()
    }
}

class Task1 : BaseTaskNode() {

    override fun onEnter(): Boolean {
        XLog.i("TaskFlow - enter: 1")
        return true
    }

    override fun execute() {
        XLog.i("TaskFlow - run: 1")
        CoroutineScope(Dispatchers.IO).launch {
            Thread.sleep(1000)
            withContext(Dispatchers.Main) {
                XLog.i("TaskFlow - onSuccess: 1")
                Toast.makeText(XApplication.app, "Task1.Suc", Toast.LENGTH_SHORT).show()
                exit()
            }
        }
    }
}

class Task2 : BaseTaskNode() {

    override fun onEnter(): Boolean {
        XLog.i("TaskFlow - enter: 2")
        return true
    }

    override fun execute() {
        XLog.i("TaskFlow - run: 2")
        CoroutineScope(Dispatchers.IO).launch {
            Thread.sleep(5000)
            withContext(Dispatchers.Main) {
                XLog.i("TaskFlow - onSuccess: 2")
                Toast.makeText(XApplication.app, "Task2.Suc", Toast.LENGTH_SHORT).show()
                exit()
            }
        }
    }
}