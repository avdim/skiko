package org.jetbrains.skia.impl

actual abstract class Native actual constructor(ptr: NativePointer) {
    actual var _ptr: NativePointer

    actual open fun _nativeEquals(other: Native?): Boolean = TODO()

    actual companion object {
        actual val NullPointer: NativePointer
            get() = 0
    }

    actual override fun toString(): String {
        return this::class.simpleName + "(_ptr=0x" + _ptr.toString(16) + ")"
    }

    init {
        if (ptr == NullPointer) throw RuntimeException("Can't wrap nullptr")
        _ptr = ptr
    }
}

actual fun reachabilityBarrier(obj: Any?) {
    TODO()
}

actual typealias NativePointer = Int
actual typealias InteropPointer = Int

actual inline fun <T> interopScope(block: InteropScope.() -> T): T {
    val scope = InteropScope()
    try {
        return scope.block()
    } finally {
        scope.release()
    }
}

actual class InteropScope actual constructor() {
    private val elements = mutableListOf<NativePointer>()

    actual fun toInterop(string: String?): InteropPointer {
        return if (string != null) {
            val data = _malloc(string.length * 4)
            stringToUTF8(string, data, string.length * 4)
            elements.add(data)
            data
        } else {
            0
        }
    }

    actual fun InteropPointer.fromInterop(result: CharArray) {
        fromWasm(this@fromInterop, result)
    }

    actual fun toInterop(array: ByteArray?): InteropPointer {
        return if (array != null) {
            val data = _malloc(array.size)
            elements.add(data)
            toWasm(data, array)
            data
        } else {
            0
        }
    }
    actual fun InteropPointer.fromInterop(result: ShortArray) {
        fromWasm(this@fromInterop, result)
    }

    actual fun toInterop(array: ShortArray?): InteropPointer {
        return if (array != null) {
            val data = _malloc(array.size * 2)
            elements.add(data)
            toWasm(data, array)
            data
        } else {
            0
        }
    }

    actual fun InteropPointer.fromInterop(result: IntArray) {
        fromWasm(this@fromInterop, result)
    }

    actual fun toInterop(array: IntArray?): InteropPointer {
        return if (array != null) {
            val data = _malloc(array.size * 4)
            elements.add(data)
            toWasm(data, array)
            data
        } else {
            0
        }
    }

    actual fun InteropPointer.fromInterop(result: LongArray) {
        TODO("implement wasm fromInterop(LongArray)")
    }

    actual fun toInterop(array: LongArray?): InteropPointer {
        TODO("implement wasm toInterop(LongArray)")
    }

    actual fun InteropPointer.fromInterop(result: FloatArray) {
        fromWasm(this@fromInterop, result)
    }

    actual fun toInterop(array: FloatArray?): InteropPointer {
        return if (array != null) {
            val data = _malloc(array.size * 4)
            elements.add(data)
            toWasm(data, array)
            data
        } else {
            0
        }
    }

    actual fun InteropPointer.fromInterop(result: DoubleArray) {
        TODO("implement wasm fromInterop(DoubleArray)")
    }

    actual fun toInterop(array: DoubleArray?): InteropPointer {
        TODO("implement wasm toInterop(DoubleArray)")
    }

    actual fun InteropPointer.fromInterop(result: ByteArray) {
        fromWasm(this@fromInterop, result)
    }

    actual fun toInterop(array: NativePointerArray?): InteropPointer {
        return if (array != null) {
            val data = _malloc(array.size * 4)
            elements.add(data)
            toWasm(data, array.backing)
            data
        } else {
            0
        }
    }

    actual fun InteropPointer.fromInterop(result: NativePointerArray) {
        TODO("implement wasm fromInterop(NativePointerArray)")
    }

    actual fun toInterop(stringArray: Array<String>?): InteropPointer =
        TODO("implement wasm toInterop(Array<String>)")

    actual fun InteropPointer.fromInteropNativePointerArray(): NativePointerArray {
        TODO("implement wasm fromInteropNativePointerArray")
    }

    actual inline fun <reified T> InteropPointer.fromInterop(decoder: ArrayInteropDecoder<T>): Array<T> =
        TODO("implement wasm fromInteropArray()")

    actual fun release()  {
        elements.forEach {
            _free(it)
        }
    }
}

// Those functions are defined by Emscripten.
private external fun _malloc(size: Int): NativePointer

private external fun _free(ptr: NativePointer)

private external fun stringToUTF8(str: String, outPtr: NativePointer, maxBytesToWrite: Int)

private external val HEAPU8: ByteArray


// Data copying routines.
private fun toWasm(dest: NativePointer, src: ByteArray) {
    js("HEAPU8.set(src, dest)")
}

private fun toWasm(dest: NativePointer, src: CharArray) {
    js("HEAPU16.set(src, dest)")
}

private fun toWasm(dest: NativePointer, src: ShortArray) {
    js("HEAPU16.set(src, dest)")
}

private fun toWasm(dest: NativePointer, src: FloatArray) {
    js("HEAPU32.set(src, dest)")
}

private fun toWasm(dest: NativePointer, src: IntArray) {
    js("HEAPU32.set(src, dest)")
}

private fun fromWasm(src: NativePointer, result: ByteArray) {
    js("result.set(HEAPU8.subarray(src, result.size))")
}

private fun fromWasm(src: NativePointer, result: CharArray) {
    js("result.set(HEAPU16.subarray(src, result.size))")
}

private fun fromWasm(src: NativePointer, result: ShortArray) {
    js("result.set(HEAPU16.subarray(src, result.size))")
}

private fun fromWasm(src: NativePointer, result: IntArray) {
    js("result.set(HEAPU32.subarray(src, result.size))")
}

private fun fromWasm(src: NativePointer, result: FloatArray) {
    js("result.set(HEAPU32.subarray(src, result.size))")
}

actual class NativePointerArray actual constructor(size: Int) {
    internal val backing = IntArray(size)
    actual operator fun get(index: Int): NativePointer {
        return backing[index]
    }

    actual operator fun set(index: Int, value: NativePointer) {
        backing[index] = value
    }

    actual val size: Int
        get() = backing.size
}