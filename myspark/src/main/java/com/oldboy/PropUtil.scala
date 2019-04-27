package com.oldboy

import java.io.{FileInputStream, InputStreamReader}
import java.util.Properties


object PropUtil {
    def getProp(path: String) = {
        val prop = new Properties()
        prop.load(new InputStreamReader(new FileInputStream(path), "UTF-8"))
        prop
    }

}
