package org.jetbrains.skiko

import platform.posix._fullpath
import platform.posix.PATH_MAX
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.toKString
import kotlinx.cinterop.usePinned

private const val RESOURCES_PATH = "src/commonTest/resources"

actual fun resourcePath(resourceId: String) = run {
    val filePath = "$RESOURCES_PATH/$resourceId"
    // Remove all '..' and '.'
    var buffer = ByteArray(PATH_MAX)
    val standardized = buffer.usePinned {
        _fullpath(it.addressOf(0), filePath, buffer.size.convert())?.toKString()
    }
    standardized ?: filePath
}
