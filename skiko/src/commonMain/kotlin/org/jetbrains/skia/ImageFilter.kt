package org.jetbrains.skia

import org.jetbrains.skia.impl.Library.Companion.staticLoad
import org.jetbrains.skia.impl.RefCnt
import org.jetbrains.skia.impl.Native
import org.jetbrains.skia.impl.Stats
import org.jetbrains.skia.impl.reachabilityBarrier
import kotlin.jvm.JvmStatic

class ImageFilter internal constructor(ptr: Long) : RefCnt(ptr) {
    companion object {
        fun makeAlphaThreshold(
            r: Region?,
            innerMin: Float,
            outerMax: Float,
            input: ImageFilter?,
            crop: IRect?
        ): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeAlphaThreshold(
                        getPtr(
                            r
                        ), innerMin, outerMax, getPtr(input), crop
                    )
                )
            } finally {
                reachabilityBarrier(r)
                reachabilityBarrier(input)
            }
        }

        fun makeArithmetic(
            k1: Float,
            k2: Float,
            k3: Float,
            k4: Float,
            enforcePMColor: Boolean,
            bg: ImageFilter?,
            fg: ImageFilter?,
            crop: IRect?
        ): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeArithmetic(
                        k1,
                        k2,
                        k3,
                        k4,
                        enforcePMColor,
                        getPtr(bg),
                        getPtr(fg),
                        crop
                    )
                )
            } finally {
                reachabilityBarrier(bg)
                reachabilityBarrier(fg)
            }
        }

        fun makeBlend(blendMode: BlendMode, bg: ImageFilter?, fg: ImageFilter?, crop: IRect?): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeBlend(
                        blendMode.ordinal,
                        getPtr(bg),
                        getPtr(fg),
                        crop
                    )
                )
            } finally {
                reachabilityBarrier(bg)
                reachabilityBarrier(fg)
            }
        }

        fun makeBlur(
            sigmaX: Float,
            sigmaY: Float,
            mode: FilterTileMode,
            input: ImageFilter? = null,
            crop: IRect? = null
        ): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeBlur(
                        sigmaX,
                        sigmaY,
                        mode.ordinal,
                        getPtr(input),
                        crop
                    )
                )
            } finally {
                reachabilityBarrier(input)
            }
        }

        fun makeColorFilter(f: ColorFilter?, input: ImageFilter?, crop: IRect?): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeColorFilter(
                        getPtr(
                            f
                        ), getPtr(input), crop
                    )
                )
            } finally {
                reachabilityBarrier(f)
                reachabilityBarrier(input)
            }
        }

        fun makeCompose(outer: ImageFilter?, inner: ImageFilter?): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeCompose(
                        getPtr(
                            outer
                        ), getPtr(inner)
                    )
                )
            } finally {
                reachabilityBarrier(outer)
                reachabilityBarrier(inner)
            }
        }

        fun makeDisplacementMap(
            x: ColorChannel,
            y: ColorChannel,
            scale: Float,
            displacement: ImageFilter?,
            color: ImageFilter?,
            crop: IRect?
        ): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeDisplacementMap(
                        x.ordinal,
                        y.ordinal,
                        scale,
                        getPtr(displacement),
                        getPtr(color),
                        crop
                    )
                )
            } finally {
                reachabilityBarrier(displacement)
                reachabilityBarrier(color)
            }
        }

        fun makeDropShadow(
            dx: Float,
            dy: Float,
            sigmaX: Float,
            sigmaY: Float,
            color: Int,
            input: ImageFilter? = null,
            crop: IRect? = null
        ): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeDropShadow(
                        dx,
                        dy,
                        sigmaX,
                        sigmaY,
                        color,
                        getPtr(input),
                        crop
                    )
                )
            } finally {
                reachabilityBarrier(input)
            }
        }

        fun makeDropShadowOnly(
            dx: Float,
            dy: Float,
            sigmaX: Float,
            sigmaY: Float,
            color: Int,
            input: ImageFilter? = null,
            crop: IRect? = null
        ): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeDropShadowOnly(
                        dx,
                        dy,
                        sigmaX,
                        sigmaY,
                        color,
                        getPtr(input),
                        crop
                    )
                )
            } finally {
                reachabilityBarrier(input)
            }
        }

        fun makeImage(image: Image): ImageFilter {
            val r: Rect = Rect.makeWH(image.width.toFloat(), image.height.toFloat())
            return makeImage(image, r, r, SamplingMode.DEFAULT)
        }

        fun makeImage(image: Image?, src: Rect, dst: Rect, mode: SamplingMode): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeImage(
                        getPtr(
                            image
                        ),
                        src.left,
                        src.top,
                        src.right,
                        src.bottom,
                        dst.left,
                        dst.top,
                        dst.right,
                        dst.bottom,
                        mode._pack()
                    )
                )
            } finally {
                reachabilityBarrier(image)
            }
        }

        fun makeMagnifier(r: Rect, inset: Float, input: ImageFilter?, crop: IRect?): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeMagnifier(
                        r.left,
                        r.top,
                        r.right,
                        r.bottom,
                        inset,
                        getPtr(input),
                        crop
                    )
                )
            } finally {
                reachabilityBarrier(input)
            }
        }

        fun makeMatrixConvolution(
            kernelW: Int,
            kernelH: Int,
            kernel: FloatArray?,
            gain: Float,
            bias: Float,
            offsetX: Int,
            offsetY: Int,
            tileMode: FilterTileMode,
            convolveAlpha: Boolean,
            input: ImageFilter?,
            crop: IRect?
        ): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeMatrixConvolution(
                        kernelW,
                        kernelH,
                        kernel,
                        gain,
                        bias,
                        offsetX,
                        offsetY,
                        tileMode.ordinal,
                        convolveAlpha,
                        getPtr(input),
                        crop
                    )
                )
            } finally {
                reachabilityBarrier(input)
            }
        }

        fun makeMatrixTransform(matrix: Matrix33, mode: SamplingMode, input: ImageFilter?): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeMatrixTransform(
                        matrix.mat,
                        mode._pack(),
                        getPtr(input)
                    )
                )
            } finally {
                reachabilityBarrier(input)
            }
        }

        fun makeMerge(filters: Array<ImageFilter?>, crop: IRect?): ImageFilter {
            return try {
                Stats.onNativeCall()
                val filterPtrs = LongArray(filters.size)
                filterPtrs.forEachIndexed { i: Int, _: Long -> getPtr(filters[i]) }
                ImageFilter(_nMakeMerge(filterPtrs, crop))
            } finally {
                reachabilityBarrier(filters)
            }
        }

        fun makeOffset(dx: Float, dy: Float, input: ImageFilter?, crop: IRect?): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeOffset(
                        dx,
                        dy,
                        getPtr(input),
                        crop
                    )
                )
            } finally {
                reachabilityBarrier(input)
            }
        }

        fun makePaint(paint: Paint?, crop: IRect?): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakePaint(
                        getPtr(
                            paint
                        ), crop
                    )
                )
            } finally {
                reachabilityBarrier(paint)
            }
        }

        fun makeTile(src: Rect, dst: Rect, input: ImageFilter?): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeTile(
                        src.left,
                        src.top,
                        src.right,
                        src.bottom,
                        dst.left,
                        dst.top,
                        dst.right,
                        dst.bottom,
                        getPtr(input)
                    )
                )
            } finally {
                reachabilityBarrier(input)
            }
        }

        fun makeDilate(rx: Float, ry: Float, input: ImageFilter?, crop: IRect?): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeDilate(
                        rx,
                        ry,
                        getPtr(input),
                        crop
                    )
                )
            } finally {
                reachabilityBarrier(input)
            }
        }

        fun makeErode(rx: Float, ry: Float, input: ImageFilter?, crop: IRect?): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeErode(
                        rx,
                        ry,
                        getPtr(input),
                        crop
                    )
                )
            } finally {
                reachabilityBarrier(input)
            }
        }

        fun makeDistantLitDiffuse(
            x: Float,
            y: Float,
            z: Float,
            lightColor: Int,
            surfaceScale: Float,
            kd: Float,
            input: ImageFilter?,
            crop: IRect?
        ): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeDistantLitDiffuse(
                        x,
                        y,
                        z,
                        lightColor,
                        surfaceScale,
                        kd,
                        getPtr(input),
                        crop
                    )
                )
            } finally {
                reachabilityBarrier(input)
            }
        }

        fun makePointLitDiffuse(
            x: Float,
            y: Float,
            z: Float,
            lightColor: Int,
            surfaceScale: Float,
            kd: Float,
            input: ImageFilter?,
            crop: IRect?
        ): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakePointLitDiffuse(
                        x,
                        y,
                        z,
                        lightColor,
                        surfaceScale,
                        kd,
                        getPtr(input),
                        crop
                    )
                )
            } finally {
                reachabilityBarrier(input)
            }
        }

        fun makeSpotLitDiffuse(
            x0: Float,
            y0: Float,
            z0: Float,
            x1: Float,
            y1: Float,
            z1: Float,
            falloffExponent: Float,
            cutoffAngle: Float,
            lightColor: Int,
            surfaceScale: Float,
            kd: Float,
            input: ImageFilter?,
            crop: IRect?
        ): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeSpotLitDiffuse(
                        x0,
                        y0,
                        z0,
                        x1,
                        y1,
                        z1,
                        falloffExponent,
                        cutoffAngle,
                        lightColor,
                        surfaceScale,
                        kd,
                        getPtr(input),
                        crop
                    )
                )
            } finally {
                reachabilityBarrier(input)
            }
        }

        fun makeDistantLitSpecular(
            x: Float,
            y: Float,
            z: Float,
            lightColor: Int,
            surfaceScale: Float,
            ks: Float,
            shininess: Float,
            input: ImageFilter?,
            crop: IRect?
        ): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeDistantLitSpecular(
                        x,
                        y,
                        z,
                        lightColor,
                        surfaceScale,
                        ks,
                        shininess,
                        getPtr(input),
                        crop
                    )
                )
            } finally {
                reachabilityBarrier(input)
            }
        }

        fun makePointLitSpecular(
            x: Float,
            y: Float,
            z: Float,
            lightColor: Int,
            surfaceScale: Float,
            ks: Float,
            shininess: Float,
            input: ImageFilter?,
            crop: IRect?
        ): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakePointLitSpecular(
                        x,
                        y,
                        z,
                        lightColor,
                        surfaceScale,
                        ks,
                        shininess,
                        getPtr(input),
                        crop
                    )
                )
            } finally {
                reachabilityBarrier(input)
            }
        }

        fun makeSpotLitSpecular(
            x0: Float,
            y0: Float,
            z0: Float,
            x1: Float,
            y1: Float,
            z1: Float,
            falloffExponent: Float,
            cutoffAngle: Float,
            lightColor: Int,
            surfaceScale: Float,
            ks: Float,
            shininess: Float,
            input: ImageFilter?,
            crop: IRect?
        ): ImageFilter {
            return try {
                Stats.onNativeCall()
                ImageFilter(
                    _nMakeSpotLitSpecular(
                        x0,
                        y0,
                        z0,
                        x1,
                        y1,
                        z1,
                        falloffExponent,
                        cutoffAngle,
                        lightColor,
                        surfaceScale,
                        ks,
                        shininess,
                        getPtr(input),
                        crop
                    )
                )
            } finally {
                reachabilityBarrier(input)
            }
        }

        @JvmStatic
        external fun _nMakeAlphaThreshold(
            regionPtr: Long,
            innerMin: Float,
            outerMax: Float,
            input: Long,
            crop: IRect?
        ): Long

        @JvmStatic external fun _nMakeArithmetic(
            k1: Float,
            k2: Float,
            k3: Float,
            k4: Float,
            enforcePMColor: Boolean,
            bg: Long,
            fg: Long,
            crop: IRect?
        ): Long

        @JvmStatic external fun _nMakeBlend(blendMode: Int, bg: Long, fg: Long, crop: IRect?): Long
        @JvmStatic external fun _nMakeBlur(sigmaX: Float, sigmaY: Float, tileMode: Int, input: Long, crop: IRect?): Long
        @JvmStatic external fun _nMakeColorFilter(colorFilterPtr: Long, input: Long, crop: IRect?): Long
        @JvmStatic external fun _nMakeCompose(outer: Long, inner: Long): Long
        @JvmStatic external fun _nMakeDisplacementMap(
            xChan: Int,
            yChan: Int,
            scale: Float,
            displacement: Long,
            color: Long,
            crop: IRect?
        ): Long

        @JvmStatic external fun _nMakeDropShadow(
            dx: Float,
            dy: Float,
            sigmaX: Float,
            sigmaY: Float,
            color: Int,
            input: Long,
            crop: IRect?
        ): Long

        @JvmStatic external fun _nMakeDropShadowOnly(
            dx: Float,
            dy: Float,
            sigmaX: Float,
            sigmaY: Float,
            color: Int,
            input: Long,
            crop: IRect?
        ): Long

        @JvmStatic external fun _nMakeImage(
            image: Long,
            l0: Float,
            t0: Float,
            r0: Float,
            b0: Float,
            l1: Float,
            t1: Float,
            r1: Float,
            b1: Float,
            samplingMode: Long
        ): Long

        @JvmStatic external fun _nMakeMagnifier(
            l: Float,
            t: Float,
            r: Float,
            b: Float,
            inset: Float,
            input: Long,
            crop: IRect?
        ): Long

        @JvmStatic external fun _nMakeMatrixConvolution(
            kernelW: Int,
            kernelH: Int,
            kernel: FloatArray?,
            gain: Float,
            bias: Float,
            offsetX: Int,
            offsetY: Int,
            tileMode: Int,
            convolveAlpha: Boolean,
            input: Long,
            crop: IRect?
        ): Long

        @JvmStatic external fun _nMakeMatrixTransform(matrix: FloatArray?, samplingMode: Long, input: Long): Long
        @JvmStatic external fun _nMakeMerge(filters: LongArray?, crop: IRect?): Long
        @JvmStatic external fun _nMakeOffset(dx: Float, dy: Float, input: Long, crop: IRect?): Long
        @JvmStatic external fun _nMakePaint(paint: Long, crop: IRect?): Long
        @JvmStatic external fun _nMakePicture(picture: Long, l: Float, t: Float, r: Float, b: Float): Long
        @JvmStatic external fun _nMakeTile(
            l0: Float,
            t0: Float,
            r0: Float,
            b0: Float,
            l1: Float,
            t1: Float,
            r1: Float,
            b1: Float,
            input: Long
        ): Long

        @JvmStatic external fun _nMakeDilate(rx: Float, ry: Float, input: Long, crop: IRect?): Long
        @JvmStatic external fun _nMakeErode(rx: Float, ry: Float, input: Long, crop: IRect?): Long
        @JvmStatic external fun _nMakeDistantLitDiffuse(
            x: Float,
            y: Float,
            z: Float,
            lightColor: Int,
            surfaceScale: Float,
            kd: Float,
            input: Long,
            crop: IRect?
        ): Long

        @JvmStatic external fun _nMakePointLitDiffuse(
            x: Float,
            y: Float,
            z: Float,
            lightColor: Int,
            surfaceScale: Float,
            kd: Float,
            input: Long,
            crop: IRect?
        ): Long

        @JvmStatic external fun _nMakeSpotLitDiffuse(
            x0: Float,
            y0: Float,
            z0: Float,
            x1: Float,
            y1: Float,
            z1: Float,
            falloffExponent: Float,
            cutoffAngle: Float,
            lightColor: Int,
            surfaceScale: Float,
            kd: Float,
            input: Long,
            crop: IRect?
        ): Long

        @JvmStatic external fun _nMakeDistantLitSpecular(
            x: Float,
            y: Float,
            z: Float,
            lightColor: Int,
            surfaceScale: Float,
            ks: Float,
            shininess: Float,
            input: Long,
            crop: IRect?
        ): Long

        @JvmStatic external fun _nMakePointLitSpecular(
            x: Float,
            y: Float,
            z: Float,
            lightColor: Int,
            surfaceScale: Float,
            ks: Float,
            shininess: Float,
            input: Long,
            crop: IRect?
        ): Long

        @JvmStatic external fun _nMakeSpotLitSpecular(
            x0: Float,
            y0: Float,
            z0: Float,
            x1: Float,
            y1: Float,
            z1: Float,
            falloffExponent: Float,
            cutoffAngle: Float,
            lightColor: Int,
            surfaceScale: Float,
            ks: Float,
            shininess: Float,
            input: Long,
            crop: IRect?
        ): Long

        init {
            staticLoad()
        }
    }
}