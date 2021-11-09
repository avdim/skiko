#ifndef SKIKO_COMMON_H
#define SKIKO_COMMON_H

// Note that this file is only common between Wasm and Native targets, JVM uses different headers.

#include <stdexcept>

#include "SkCodec.h"
#include "SkFontMetrics.h"
#include "SkFontStyle.h"
#include "SkImageInfo.h"
#include "SkMatrix.h"
#include "SkM44.h"
#include "SkPaint.h"
#include "SkRefCnt.h"
#include "SkRect.h"
#include "SkRRect.h"
#include "SkScalar.h"
#include "SkShaper.h"
#include "SkString.h"
#include "SkSurfaceProps.h"
#include "SkSVGTypes.h"
#include "TextStyle.h"
#include "SkFont.h"
#include "SkShaper.h"
#include "SkLoadICU.h"
#include "unicode/ubrk.h"
#include "mppinterop.h"

#include "types.h"

KLong packTwoInts(KInt a, KInt b);

KLong packIPoint(SkIPoint p);

KLong packISize(SkISize p);

namespace skija {
    namespace SamplingMode {
        SkSamplingOptions unpack(KLong val);
        SkSamplingOptions unpackFrom2Ints(KInt val1, KInt val2);
    }

    namespace Rect {
        void copyToInterop(const SkRect& rect, KInteropPointer pointer);
    }

    namespace RRect {
        void copyToInterop(const SkRRect& rect, KInteropPointer pointer);
    }

    namespace Point {
        void copyToInterop(const SkPoint& point, KInteropPointer pointer);
    }

    namespace RRect {
        SkRRect toSkRRect(KFloat left, KFloat top, KFloat right, KFloat bottom, KFloat* jradii, KInt size);
    }

    namespace FontStyle {
        SkFontStyle fromKotlin(KInt style);
        KInt toKotlin(const SkFontStyle& fs);
    }

    namespace ImageInfo {
       void writeImageInfoForInterop(SkImageInfo imageInfo, KInt* imageInfoResult, KNativePointer* colorSpacePtrsArray);
    }

    namespace SurfaceProps {
        std::unique_ptr<SkSurfaceProps> toSkSurfaceProps(KInt* surfacePropsInts);
    }

    namespace IRect {
        std::unique_ptr<SkIRect> toSkIRect(KInt* rectInts);
    }

    namespace FontFeature {
        // every feature is encoded as 4 ints
        std::vector<SkShaper::Feature> fromIntArray(KInt* array, KInt featuresLen);

        // caller needs to ensure the resultArr size is sufficient (every feature is encoded as 2 ints)
        void writeToIntArray(std::vector<skia::textlayout::FontFeature> features, int* resultArr);

        namespace FourByteTag {
            int fromString(SkString str);
        }
    }

    namespace shaper {
        namespace ShapingOptions {
            std::vector<SkShaper::Feature> getFeaturesFromIntsArray(KInt* featuresArray, KInt featuresLen);
        }

        using ICUUText = std::unique_ptr<UText, SkFunctionWrapper<decltype(utext_close), utext_close>>;
        std::shared_ptr<UBreakIterator> graphemeBreakIterator(SkString& text);
    }

    namespace AnimationFrameInfo {
        void copyToInterop(const SkCodec::FrameInfo& info, KInteropPointer dst);
        void copyToInterop(const std::vector<SkCodec::FrameInfo>& infos, KInteropPointer dst);
    }

    namespace svg {
        namespace SVGLength {
            void copyToInterop(const SkSVGLength& length, KInteropPointer dst);
        }

        namespace SVGPreserveAspectRatio {
            void copyToInterop(const SkSVGPreserveAspectRatio& aspectRatio, KInteropPointer dst);
        }
    }
}

std::unique_ptr<SkMatrix> skMatrix(KFloat* matrixArray);
std::unique_ptr<SkM44> skM44(KFloat* matrixArray);
SkString skString(KNativePointer str);
std::vector<SkString> skStringVector(KInteropPointerArray arr, KInt size);

template <typename T>
inline T interopToPtr(KNativePointer ptr) {
    return reinterpret_cast<T>(ptr);
}

template <typename T>
KNativePointer ptrToInterop(T* ptr) {
    return ptr;
}

#ifdef __clang__
__attribute__((noreturn))
#elif defined(_MSC_VER)
__declspec(noreturn)
#else
#endif
void TODO(const char*);

#ifdef SKIKO_WASM
#include <emscripten.h>
#define SKIKO_EXPORT EMSCRIPTEN_KEEPALIVE extern "C"
#else
#define SKIKO_EXPORT extern "C"
#endif

static inline KInt rawBits(KFloat f) {
    union {
        KFloat f;
        KInt i;
    } u;
    u.f = f;
    return u.i;
}
#endif /* SKIKO_COMMON_H */

