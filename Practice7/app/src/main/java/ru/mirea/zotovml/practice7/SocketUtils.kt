package ru.mirea.zotovml.practice7

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class SocketUtils {
    companion object {
        public fun getReader(s: Socket): BufferedReader {
            return BufferedReader(InputStreamReader(s.getInputStream()))
        }

        public fun getWriter(s: Socket): PrintWriter {
            return PrintWriter(s.getOutputStream(), true)
        }
    }
}