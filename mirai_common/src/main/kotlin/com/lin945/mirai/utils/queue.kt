package com.lin945.mirai.utils

import java.util.*

class LimitedQueue<E>(si: Int) : LinkedList<E>() {
    override fun add(element: E): Boolean {
        return super.add(element).apply {
            if (size > size) {
                super.remove()
            }
        }
    }
}