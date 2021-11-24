
// This file has been auto generated.

#include <iostream>
#include "SkShaper.h"
#include "src/utils/SkUTF.h"
#include "unicode/ubidi.h"
#include "common.h"
#include "FontRunIterator.hh"
#include "src/utils/SkUTF.h"
#include "TextLineRunHandler.hh"

static void deleteShaper(SkShaper* instance) {
    // std::cout << "Deleting [SkShaper " << instance << "]" << std::endl;
    delete instance;
}

SKIKO_EXPORT KNativePointer org_jetbrains_skia_shaper_Shaper__1nGetFinalizer() {
    return reinterpret_cast<KNativePointer>((&deleteShaper));
}

SKIKO_EXPORT KNativePointer org_jetbrains_skia_shaper_Shaper__1nMakePrimitive
  () {
    return reinterpret_cast<KNativePointer>(SkShaper::MakePrimitive().release());
}

SKIKO_EXPORT KNativePointer org_jetbrains_skia_shaper_Shaper__1nMakeShaperDrivenWrapper
  (KNativePointer fontMgrPtr) {
    SkFontMgr* fontMgr = reinterpret_cast<SkFontMgr*>((fontMgrPtr));
    return reinterpret_cast<KNativePointer>(SkShaper::MakeShaperDrivenWrapper(sk_ref_sp(fontMgr)).release());
}

SKIKO_EXPORT KNativePointer org_jetbrains_skia_shaper_Shaper__1nMakeShapeThenWrap
  (KNativePointer fontMgrPtr) {
    SkFontMgr* fontMgr = reinterpret_cast<SkFontMgr*>((fontMgrPtr));
    return reinterpret_cast<KNativePointer>(SkShaper::MakeShapeThenWrap(sk_ref_sp(fontMgr)).release());
}

SKIKO_EXPORT KNativePointer org_jetbrains_skia_shaper_Shaper__1nMakeShapeDontWrapOrReorder
  (KNativePointer fontMgrPtr) {
    SkFontMgr* fontMgr = reinterpret_cast<SkFontMgr*>((fontMgrPtr));
    return reinterpret_cast<KNativePointer>(SkShaper::MakeShapeDontWrapOrReorder(sk_ref_sp(fontMgr)).release());
}

SKIKO_EXPORT KNativePointer org_jetbrains_skia_shaper_Shaper__1nMakeCoreText() {
    #ifdef SK_SHAPER_CORETEXT_AVAILABLE
        return reinterpret_cast<KNativePointer>(SkShaper::MakeCoreText().release());
    #else
        return 0;
    #endif
}

SKIKO_EXPORT KNativePointer org_jetbrains_skia_shaper_Shaper__1nMake
  (KNativePointer fontMgrPtr) {
    SkFontMgr* fontMgr = reinterpret_cast<SkFontMgr*>((fontMgrPtr));
    return reinterpret_cast<KNativePointer>(SkShaper::Make(sk_ref_sp(fontMgr)).release());
}


SKIKO_EXPORT KNativePointer org_jetbrains_skia_shaper_Shaper__1nShapeBlob
  (KNativePointer ptr, KNativePointer textPtr, KNativePointer fontPtr, KInt optsFeaturesLen, KInt* optsFeatures, KInt optsBooleanProps, KFloat width, KFloat offsetX, KFloat offsetY) {
    SkShaper* instance = reinterpret_cast<SkShaper*>(ptr);
    SkString& text = *(reinterpret_cast<SkString*>(textPtr));

    std::shared_ptr<UBreakIterator> graphemeIter = skija::shaper::graphemeBreakIterator(text);
    if (!graphemeIter) return 0;
    SkFont* font = reinterpret_cast<SkFont*>(fontPtr);

    std::vector<SkShaper::Feature> features = skija::shaper::ShapingOptions::getFeaturesFromIntsArray(optsFeatures, optsFeaturesLen);

    bool aproximatePunctuation = (optsBooleanProps & 0x01) != 0;
    bool aproximateSpaces = (optsBooleanProps & 0x02) != 0;
    bool isLeftToRight = (optsBooleanProps & 0x04) != 0;

    uint8_t defaultBiDiLevel = isLeftToRight ? UBIDI_DEFAULT_LTR : UBIDI_DEFAULT_RTL;
    std::unique_ptr<SkShaper::BiDiRunIterator> bidiRunIter(SkShaper::MakeBiDiRunIterator(text.c_str(), text.size(), defaultBiDiLevel));
    if (!bidiRunIter) return 0;

    std::unique_ptr<SkShaper::ScriptRunIterator> scriptRunIter(SkShaper::MakeHbIcuScriptRunIterator(text.c_str(), text.size()));
    if (!scriptRunIter) return 0;

    std::unique_ptr<SkShaper::LanguageRunIterator> languageRunIter(SkShaper::MakeStdLanguageRunIterator(text.c_str(), text.size()));
    if (!languageRunIter) return 0;

     FontRunIterator fontRunIter(
        text.c_str(),
        text.size(),
        *font,
        SkFontMgr::RefDefault(),
        graphemeIter,
        aproximateSpaces,
        aproximatePunctuation
    );

    SkTextBlobBuilderRunHandler rh(text.c_str(), {offsetX, offsetY});
    instance->shape(text.c_str(), text.size(), fontRunIter, *bidiRunIter, *scriptRunIter, *languageRunIter, features.data(), features.size(), width, &rh);
    SkTextBlob* blob = rh.makeBlob().release();

    return reinterpret_cast<KNativePointer>(blob);
}

SKIKO_EXPORT KNativePointer org_jetbrains_skia_shaper_Shaper__1nShapeLine
  (KNativePointer ptr, KNativePointer textManagedStringPtr, KNativePointer fontPtr, KInt optsFeaturesLen, KInt* optsFeatures, KInt optsBooleanProps) {
    SkShaper* instance = reinterpret_cast<SkShaper*>(ptr);

    SkString& text = *(reinterpret_cast<SkString*>(textManagedStringPtr));
    SkFont* font = reinterpret_cast<SkFont*>(fontPtr);

    if (text.size() == 0) {
        return reinterpret_cast<KNativePointer>(new TextLine(*font));
    }

    std::shared_ptr<UBreakIterator> graphemeIter = skija::shaper::graphemeBreakIterator(text);
    if (!graphemeIter) return 0;

    std::vector<SkShaper::Feature> features = skija::shaper::ShapingOptions::getFeaturesFromIntsArray(optsFeatures, optsFeaturesLen);

    bool aproximatePunctuation = (optsBooleanProps & 0x01) != 0;
    bool aproximateSpaces = (optsBooleanProps & 0x02) != 0;
    bool isLeftToRight = (optsBooleanProps & 0x04) != 0;

    uint8_t defaultBiDiLevel = isLeftToRight ? UBIDI_DEFAULT_LTR : UBIDI_DEFAULT_RTL;
    std::unique_ptr<SkShaper::BiDiRunIterator> bidiRunIter(SkShaper::MakeBiDiRunIterator(text.c_str(), text.size(), defaultBiDiLevel));
    if (!bidiRunIter) return 0;

    std::unique_ptr<SkShaper::ScriptRunIterator> scriptRunIter(SkShaper::MakeHbIcuScriptRunIterator(text.c_str(), text.size()));
    if (!scriptRunIter) return 0;

    std::unique_ptr<SkShaper::LanguageRunIterator> languageRunIter(SkShaper::MakeStdLanguageRunIterator(text.c_str(), text.size()));
    if (!languageRunIter) return 0;

    FontRunIterator fontRunIter(
        text.c_str(),
        text.size(),
        *font,
        SkFontMgr::RefDefault(),
        graphemeIter,
        aproximateSpaces,
        aproximatePunctuation);

    TextLineRunHandler rh(text, graphemeIter);
    instance->shape(text.c_str(), text.size(), fontRunIter, *bidiRunIter, *scriptRunIter, *languageRunIter, features.data(), features.size(), std::numeric_limits<float>::infinity(), &rh);
    return reinterpret_cast<KNativePointer>(rh.makeLine().release());
}

SKIKO_EXPORT void org_jetbrains_skia_shaper_Shaper__1nShape
  (KNativePointer ptr, KNativePointer textPtr, KInteropPointer fontRunIterObj, KInteropPointer bidiRunIterObj, KInteropPointer scriptRunIterObj, KInteropPointer languageRunIterObj, KInt optsFeaturesLen, KInt* optsFeatures, KInt optsBooleanProps, KFloat width, KInteropPointer runHandlerObj)
{
    auto* instance = reinterpret_cast<SkShaper*>(ptr);


    SkString& text = *(reinterpret_cast<SkString*>(textPtr));
    printf("shape called: %d\n", text.size());

    if (text.size() == 0) {
        return;
    }

    std::vector<SkShaper::Feature> features = skija::shaper::ShapingOptions::getFeaturesFromIntsArray(optsFeatures, optsFeaturesLen);

    auto* fontRunIter = reinterpret_cast<SkShaper::FontRunIterator*>(fontRunIterObj);
    auto* languageRunIter = reinterpret_cast<SkShaper::LanguageRunIterator*>(languageRunIterObj);
    auto* scriptRunIter = reinterpret_cast<SkShaper::ScriptRunIterator*>(scriptRunIterObj);
    auto* bidiRunIter = reinterpret_cast<SkShaper::BiDiRunIterator*>(bidiRunIterObj);
    auto* runHandler = reinterpret_cast<SkShaper::RunHandler*>(runHandlerObj);

    printf("text is %s\n features %d\n", text.c_str(), features.size());
    for (auto& f: features) {
        printf("feature %d %d %d", f.value, f.start, f.end);
    }

    instance->shape(text.c_str(), text.size(), *fontRunIter, *bidiRunIter, *scriptRunIter, *languageRunIter, features.data(), features.size(), std::numeric_limits<float>::infinity(), runHandler);
}

class SkikoRunIteratorBase {
public:
    virtual ~SkikoRunIteratorBase() {}
    virtual void init(KInteropPointer onConsume, KInteropPointer onEndOfCurrentRun, KInteropPointer onAtEnd, KInteropPointer onCurrent) = 0;
};

template<typename T, typename CurrentCallback>
class SkikoRunIterator: public T, public SkikoRunIteratorBase {
    static_assert(std::is_base_of<SkShaper::RunIterator, T>::value, "");
public:
    SkikoRunIterator(const SkString& text) :
        _onCurrent(nullptr),
        _onAtEnd(nullptr),
        _onEndOfCurrentRun(nullptr),
        _onConsume(nullptr),
        _indicesConverter(text) {}

    void consume() override {
        printf("SkikoRunIterator::consume\n");
        _onConsume();
        _endOfCurrentRun = _indicesConverter.from16To8(_onEndOfCurrentRun());
    }
    
    size_t endOfCurrentRun() const override {
        printf("SkikoRunIterator::endOfCurrentRun: %d\n", _endOfCurrentRun);
        return _endOfCurrentRun;
    }
    
    bool atEnd() const override {
        return static_cast<bool>(_onAtEnd());
    }
    
    virtual void init(KInteropPointer onConsume, KInteropPointer onEndOfCurrentRun, KInteropPointer onAtEnd, KInteropPointer onCurrent) {
        _onConsume = KVoidCallback(onConsume);
        _onEndOfCurrentRun = KIntCallback(onEndOfCurrentRun);
        _onAtEnd = KBooleanCallback(onAtEnd);
        _onCurrent = CurrentCallback(onCurrent);
    }
protected:
    CurrentCallback _onCurrent;
private:
    KVoidCallback _onConsume;
    KIntCallback  _onEndOfCurrentRun;
    KBooleanCallback _onAtEnd;
    skija::UtfIndicesConverter _indicesConverter;
    size_t _endOfCurrentRun = 0;
};

class SkikoFontRunIterator : public SkikoRunIterator<SkShaper::FontRunIterator, KNativePointerCallback> {
public:
    SkikoFontRunIterator(const SkString& text) : SkikoRunIterator<SkShaper::FontRunIterator, KNativePointerCallback>(text) {}

    const SkFont& currentFont() const override {
        return *reinterpret_cast<SkFont*>(_onCurrent());
    }
};

class SkikoBiDiRunIterator : public SkikoRunIterator<SkShaper::BiDiRunIterator, KIntCallback> {
public:
    SkikoBiDiRunIterator(const SkString& text) : SkikoRunIterator<SkShaper::BiDiRunIterator, KIntCallback>(text) {}

    uint8_t currentLevel() const override {
        return static_cast<uint8_t>(_onCurrent());
    }
};

class SkikoScriptRunIterator : public SkikoRunIterator<SkShaper::ScriptRunIterator, KIntCallback>  {
public:
    SkikoScriptRunIterator(const SkString& text) : SkikoRunIterator<SkShaper::ScriptRunIterator, KIntCallback>(text) {}

    SkFourByteTag currentScript() const override {
        return static_cast<SkFourByteTag>(_onCurrent());
    }
};

class SkikoLanguageRunIterator : public SkikoRunIterator<SkShaper::LanguageRunIterator, KInteropPointerCallback>  {
public:
    SkikoLanguageRunIterator(const SkString& text) : SkikoRunIterator<SkShaper::LanguageRunIterator, KInteropPointerCallback>(text) {}

    const char* currentLanguage() const override {
        return reinterpret_cast<char *>(_onCurrent());
    }
};

static void deleteRunIterator(SkShaper::RunIterator* runIterator) {
    delete runIterator;
}

SKIKO_EXPORT KNativePointer org_jetbrains_skia_shaper_Shaper_RunIterator_1nGetFinalizer() {
    return reinterpret_cast<KNativePointer>(deleteRunIterator);
}

SKIKO_EXPORT KNativePointer org_jetbrains_skia_shaper_Shaper_RunIterator_1nCreateRunIterator(KInt type, KNativePointer textPtr) {
    const auto& text = *(reinterpret_cast<SkString*>(textPtr));
    switch (type) {
        case 1: // FontRunIterator
            return reinterpret_cast<KNativePointer>(new SkikoFontRunIterator(text));
        case 2: // BiDiRunIterator
            return reinterpret_cast<KNativePointer>(new SkikoBiDiRunIterator(text));
        case 3: // ScriptRunIterator
            return reinterpret_cast<KNativePointer>(new SkikoScriptRunIterator(text));
        case 4: // LanguageRunIterator
            return reinterpret_cast<KNativePointer>(new SkikoLanguageRunIterator(text));
        default:
            TODO("unsupported run iterator type");
    }
}

SKIKO_EXPORT void org_jetbrains_skia_shaper_Shaper_RunIterator_1nInitRunIterator
  (KNativePointer ptr, KInteropPointer onConsume, KInteropPointer onEndOfCurrentRun, KInteropPointer onAtEnd, KInteropPointer onCurrent) {
    auto* iter = static_cast<SkikoRunIteratorBase*>(reinterpret_cast<SkikoFontRunIterator*>(ptr));
    iter->init(onConsume, onEndOfCurrentRun, onAtEnd, onCurrent);
}

// Run Handler
class SkikoRunHandler : public SkShaper::RunHandler {
public:
    SkikoRunHandler() :
        _onBeginLine(nullptr),
        _onRunInfo(nullptr),
        _onCommitRunInfo(nullptr),
        _onRunOffset(nullptr),
        _onCommitRun(nullptr),
        _onCommitLine(nullptr),
        _runInfo(nullptr) {}

    void init(
        KInteropPointer onBeginLine,
        KInteropPointer onRunInfo,
        KInteropPointer onCommitRunInfo,
        KInteropPointer onRunOffset,
        KInteropPointer onCommitRun,
        KInteropPointer onCommitLine
    ) {
        _onBeginLine = KVoidCallback(onBeginLine);
        _onRunInfo = KVoidCallback(onRunInfo);
        _onCommitRunInfo = KVoidCallback(onCommitRunInfo);
        _onRunOffset = KVoidCallback(onRunOffset);
        _onCommitRun = KVoidCallback(onCommitRun);
        _onCommitLine = KVoidCallback(onCommitLine);
    }

    void beginLine() override {
        _onBeginLine();
    }

    void runInfo(const RunInfo& info) override {
        _runInfo = &info;
        _onRunInfo();
        _runInfo = nullptr;
    }

    void commitRunInfo() override {
        _onCommitRunInfo();
    }

    Buffer runBuffer(const RunInfo& info) override {
        printf("native runBuffer: %d\n", info.glyphCount);
        _glyphs    = std::vector<SkGlyphID>(info.glyphCount);
        _positions = std::vector<SkPoint>(info.glyphCount);
        _clusters  = std::vector<uint32_t>(info.glyphCount);

        _runInfo = &info;
        _onRunOffset();
        _runInfo = nullptr;

        printf("native: x=%f y=%f\n", _point.x(), _point.y());

        return {
            _glyphs.data(),
            _positions.data(),
            nullptr,
            _clusters.data(),
            _point
        };
    }

    void commitRunBuffer(const RunInfo& info) override {
        _runInfo = &info;
        _onCommitRun();
        _runInfo = nullptr;
    }

    virtual void commitLine() override {
        _onCommitLine();
    }

    void setOffset(KFloat x, KFloat y) {
        _point = { x, y };
    }

    const std::vector<SkGlyphID>& glyphs() const { return _glyphs; }
    const std::vector<SkPoint>& positions() const { return _positions; }
    const std::vector<uint32_t>& clusters() const { return _clusters; }

    const RunInfo& runInfo() const { return *_runInfo; }
private:
    KVoidCallback _onBeginLine;
    KVoidCallback _onRunInfo;
    KVoidCallback _onCommitRunInfo;
    KVoidCallback _onRunOffset;
    KVoidCallback _onCommitRun;
    KVoidCallback _onCommitLine;

    SkPoint _point;
    std::vector<SkGlyphID> _glyphs;
    std::vector<SkPoint> _positions;
    std::vector<uint32_t> _clusters;

    const RunInfo* _runInfo;
};

static void deleteRunHandler(SkikoRunHandler* runIterator) {
    delete runIterator;
}

SKIKO_EXPORT KNativePointer org_jetbrains_skia_shaper_Shaper_RunHandler_1nGetFinalizer() {
    return reinterpret_cast<KNativePointer>(deleteRunHandler);
}

SKIKO_EXPORT KNativePointer org_jetbrains_skia_shaper_Shaper_RunHandler_1nGetRunInfo
  (KNativePointer ptr, KInteropPointer fields) {
    auto* instance = reinterpret_cast<SkikoRunHandler*>(ptr);
    KInt* repr = reinterpret_cast<KInt*>(fields);
    repr[0] = instance->runInfo().fBidiLevel;
    repr[1] = rawBits(instance->runInfo().fAdvance.x());
    repr[2] = rawBits(instance->runInfo().fAdvance.y());
    repr[3] = instance->runInfo().glyphCount;
    repr[4] = instance->runInfo().utf8Range.begin();
    repr[5] = instance->runInfo().utf8Range.size();

    auto* font = const_cast<SkFont*>(&(instance->runInfo().fFont));
    return reinterpret_cast<KNativePointer>(font);
}

SKIKO_EXPORT void org_jetbrains_skia_shaper_Shaper_RunHandler_1nGetGlyphs
  (KNativePointer ptr, KInteropPointer result) {
    auto* instance = reinterpret_cast<SkikoRunHandler*>(ptr);
    auto& src = instance->glyphs();
    std::copy(src.cbegin(), src.cend(), reinterpret_cast<SkGlyphID*>(result));
}

SKIKO_EXPORT void org_jetbrains_skia_shaper_Shaper_RunHandler_1nGetPositions
  (KNativePointer ptr, KInteropPointer result) {
    auto* instance = reinterpret_cast<SkikoRunHandler*>(ptr);
    auto& src = instance->positions();
    std::copy(src.cbegin(), src.cend(), reinterpret_cast<SkPoint*>(result));
}

SKIKO_EXPORT void org_jetbrains_skia_shaper_Shaper_RunHandler_1nGetClusters
  (KNativePointer ptr, KInteropPointer result) {
    auto* instance = reinterpret_cast<SkikoRunHandler*>(ptr);
    auto& src = instance->clusters();
    std::copy(src.cbegin(), src.cend(), reinterpret_cast<uint32_t*>(result));
}

SKIKO_EXPORT void org_jetbrains_skia_shaper_Shaper_RunHandler_1nSetOffset
  (KNativePointer ptr, KFloat x, KFloat y) {
    auto* instance = reinterpret_cast<SkikoRunHandler*>(ptr);
    instance->setOffset(x, y);
}

SKIKO_EXPORT KNativePointer org_jetbrains_skia_shaper_Shaper_RunHandler_1nCreate() {
    return reinterpret_cast<KNativePointer>(new SkikoRunHandler());
}

SKIKO_EXPORT void org_jetbrains_skia_shaper_Shaper_RunHandler_1nInit
  (KNativePointer ptr, KInteropPointer onBeginLine, KInteropPointer onRunInfo, KInteropPointer onCommitRunInfo, KInteropPointer onRunOffset, KInteropPointer onCommitRun, KInteropPointer onCommitLine)
{
    auto* instance = reinterpret_cast<SkikoRunHandler*>(ptr);
    instance->init(onBeginLine, onRunInfo, onCommitRunInfo, onRunOffset, onCommitRun, onCommitLine);
}