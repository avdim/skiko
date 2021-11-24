package org.jetbrains.skia

import org.jetbrains.skia.shaper.RunHandler
import org.jetbrains.skia.shaper.RunInfo
import org.jetbrains.skia.shaper.Shaper
import org.jetbrains.skia.shaper.ShapingOptions
import org.jetbrains.skia.tests.makeFromResource
import org.jetbrains.skiko.tests.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ShaperTest {

    private suspend fun fontInter36() =
        Font(Typeface.makeFromResource("./fonts/Inter-Hinted-Regular.ttf"), 36f).also {
            println("font is $it")
        }

    @Test
    fun canShapeLine() = runTest {
        val textLine = Shaper.make().shapeLine(
            text = "Abc123", font = fontInter36()
        )

        assertEquals(6, textLine.glyphsLength)

        assertContentEquals(
            expected = shortArrayOf(2, 574, 581, 1292, 1293, 1295),
            actual = textLine.glyphs
        )
    }

    @Test
    fun canShapeTextBlob() = runTest {
        val textBlob = Shaper.make().shape(
            text = "text",
            font = fontInter36(),
            width = 100f
        )

        assertNotEquals(null, textBlob)
        assertEquals(4, textBlob!!.glyphsLength)
        assertContentEquals(
            expected = shortArrayOf(882, 611, 943, 882),
            actual = textBlob.glyphs
        )
    }

    @Test
    fun canShapeWithRunHandler() = runTest {
        val callCount = object {
            var beginLine = 0
            var runInfo = 0
            var commitRunInfo = 0
            var runOffset = 0
            var commitRun = 0
            var commitLine = 0
        }

        Shaper.make().shape(
            text = "text\ntext text\r\ntext",
            font = fontInter36(),
            opts = ShapingOptions.DEFAULT,
            width = 100f,
            runHandler = object : RunHandler {
                override fun beginLine() {
                    println("beginLine")
                    callCount.beginLine += 1
                }

                override fun runInfo(info: RunInfo?) {
                    println("runInfo: $info")
                    callCount.runInfo += 1
                }

                override fun commitRunInfo() {
                    println("commitRunInfo")
                    callCount.commitRunInfo += 1
                }

                override fun runOffset(info: RunInfo?): Point {
                    println("runOffset: $info")

                    callCount.runOffset += 1
                    return Point.ZERO
                }

                override fun commitRun(
                    info: RunInfo?,
                    glyphs: ShortArray?,
                    positions: Array<Point?>?,
                    clusters: IntArray?
                ) {
                    println("commitRun: $info ${glyphs.contentToString()} ${positions.contentDeepToString()} ${clusters.contentToString()}")
                    callCount.commitRun += 1
                }

                override fun commitLine() {
                    println("commitLine")
                    callCount.commitLine += 1
                }

            }
        )
        /*
        runInfo: RunInfo(_fontPtr=0x23c67f0, _bidiLevel=0, _advanceX=379.1363, _advanceY=0.0, _glyphCount=20, _rangeBegin=0, _rangeSize=20)
    commitRunInfo
    runOffset: RunInfo(_fontPtr=0x23c67f0, _bidiLevel=0, _advanceX=379.1363, _advanceY=0.0, _glyphCount=20, _rangeBegin=0, _rangeSize=20)
    info is RunInfo(_fontPtr=0x23c67f0, _bidiLevel=0, _advanceX=379.1363, _advanceY=0.0, _glyphCount=20, _rangeBegin=0, _rangeSize=20)
    commitRun: RunInfo(_fontPtr=0x23c67f0, _bidiLevel=0, _advanceX=379.1363, _advanceY=0.0, _glyphCount=20, _rangeBegin=0, _rangeSize=20) [882, 611, 943, 882, 0, 882, 611, 943, 882, 1673, 882, 611, 943, 882, 0, 0, 882, 611, 943, 882] [Point(_x=0.0, _y=0.0), Point(_x=12.795441, _y=0.0), Point(_x=33.284073, _y=0.0), Point(_x=52.284073, _y=0.0), Point(_x=65.28407, _y=0.0), Point(_x=101.28407, _y=0.0), Point(_x=114.07951, _y=0.0), Point(_x=134.56815, _y=0.0), Point(_x=153.56815, _y=0.0), Point(_x=166.56815, _y=0.0), Point(_x=176.56815, _y=0.0), Point(_x=189.36359, _y=0.0), Point(_x=209.85222, _y=0.0), Point(_x=228.85222, _y=0.0), Point(_x=241.85222, _y=0.0), Point(_x=277.85223, _y=0.0), Point(_x=313.85223, _y=0.0), Point(_x=326.64767, _y=0.0), Point(_x=347.1363, _y=0.0), Point(_x=366.1363, _y=0.0)] [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]
    commitLine


        runInfo: RunInfo(_fontPtr=140537746558496, _bidiLevel=0, _advanceX=101.28407, _advanceY=0.0, _glyphCount=5, _rangeBegin=0, _rangeSize=5)
    commitRunInfo
    runOffset: RunInfo(_fontPtr=140537746673392, _bidiLevel=0, _advanceX=101.28407, _advanceY=0.0, _glyphCount=5, _rangeBegin=0, _rangeSize=5)
    commitRun: RunInfo(_fontPtr=140537746677568, _bidiLevel=0, _advanceX=101.28407, _advanceY=0.0, _glyphCount=5, _rangeBegin=0, _rangeSize=5) [882, 611, 943, 882, 0] [Point(_x=0.0, _y=0.0), Point(_x=12.795441, _y=0.0), Point(_x=33.284073, _y=0.0), Point(_x=52.284073, _y=0.0), Point(_x=65.28407, _y=0.0)] [0, 1, 2, 3, 4]
    commitLine
    beginLine
    runInfo: RunInfo(_fontPtr=140537746682736, _bidiLevel=0, _advanceX=75.28407, _advanceY=0.0, _glyphCount=5, _rangeBegin=5, _rangeSize=5)
    commitRunInfo
    runOffset: RunInfo(_fontPtr=140537746673360, _bidiLevel=0, _advanceX=75.28407, _advanceY=0.0, _glyphCount=5, _rangeBegin=5, _rangeSize=5)
    commitRun: RunInfo(_fontPtr=140537746683488, _bidiLevel=0, _advanceX=75.28407, _advanceY=0.0, _glyphCount=5, _rangeBegin=5, _rangeSize=5) [882, 611, 943, 882, 1673] [Point(_x=0.0, _y=0.0), Point(_x=12.795441, _y=0.0), Point(_x=33.284073, _y=0.0), Point(_x=52.284073, _y=0.0), Point(_x=65.28407, _y=0.0)] [5, 6, 7, 8, 9]
    commitLine
    beginLine
    runInfo: RunInfo(_fontPtr=140537746683520, _bidiLevel=0, _advanceX=137.28409, _advanceY=0.0, _glyphCount=6, _rangeBegin=10, _rangeSize=6)
    commitRunInfo
    runOffset: RunInfo(_fontPtr=140537746673280, _bidiLevel=0, _advanceX=137.28409, _advanceY=0.0, _glyphCount=6, _rangeBegin=10, _rangeSize=6)
    commitRun: RunInfo(_fontPtr=140537746683856, _bidiLevel=0, _advanceX=137.28409, _advanceY=0.0, _glyphCount=6, _rangeBegin=10, _rangeSize=6) [882, 611, 943, 882, 0, 0] [Point(_x=0.0, _y=0.0), Point(_x=12.795441, _y=0.0), Point(_x=33.284073, _y=0.0), Point(_x=52.284073, _y=0.0), Point(_x=65.28407, _y=0.0), Point(_x=101.28407, _y=0.0)] [10, 11, 12, 13, 14, 15]
    commitLine
    beginLine
    runInfo: RunInfo(_fontPtr=140537746683888, _bidiLevel=0, _advanceX=65.28406, _advanceY=0.0, _glyphCount=4, _rangeBegin=16, _rangeSize=4)
    commitRunInfo
    runOffset: RunInfo(_fontPtr=140537746683408, _bidiLevel=0, _advanceX=65.28406, _advanceY=0.0, _glyphCount=4, _rangeBegin=16, _rangeSize=4)
    commitRun: RunInfo(_fontPtr=140537746683952, _bidiLevel=0, _advanceX=65.28406, _advanceY=0.0, _glyphCount=4, _rangeBegin=16, _rangeSize=4) [882, 611, 943, 882] [Point(_x=0.0, _y=0.0), Point(_x=12.795441, _y=0.0), Point(_x=33.284073, _y=0.0), Point(_x=52.284073, _y=0.0)] [16, 17, 18, 19]
    commitLine

         */

        assertEquals(4, callCount.beginLine)
        assertEquals(4, callCount.runInfo)
        assertEquals(4, callCount.commitRunInfo)
        assertEquals(4, callCount.runOffset)
        assertEquals(4, callCount.commitRun)
        assertEquals(4, callCount.commitLine)
    }
}
