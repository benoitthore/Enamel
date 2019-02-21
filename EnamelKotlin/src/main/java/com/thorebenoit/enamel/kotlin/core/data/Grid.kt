package com.thorebenoit.enamel.kotlin.core.data

import com.thorebenoit.enamel.kotlin.core.math.i


fun <T> List<T>.toGrid(rows: Int, cols: Int) = Grid<T>(rows, cols) { i -> this@toGrid.get(i) }

data class Cell<T>(val x: Int, val y: Int, val content: T)

class Grid<T>(val rows: Int, val cols: Int, private val default: Grid<T>.(Int) -> T) {

    companion object {
        fun <T> fromList(list: List<T>, rows: Int, cols: Int) = list.toGrid(rows, cols)
    }

    constructor(cols: Number, rows: Number, default: Grid<T>.(Int) -> T) : this(
        cols.toInt(),
        rows.toInt(),
        default
    )

    val size = cols * rows

    private val listeners = mutableListOf<(Cell<T>) -> Unit>()

    fun addListener(listener: (Cell<T>) -> Unit) {
        listeners.add(listener)
    }

    fun removeListener(listener: (Cell<T>) -> Unit) {
        listeners.remove(listener)
    }

    private fun notifyListeners(cell: Cell<T>) {
        listeners.forEach { it(cell) }
    }

    private val cellValues: MutableList<T> = (0 until size)
        .mapIndexed { index, _ ->
            default(index)
        }
        .toMutableList()

    private var cells: List<Cell<T>>? = null

    fun getAllElements() = cellValues.toList()

    fun getAllElementsAsCells(): List<Cell<T>> {
        if (cells == null) {
            cells = cellValues
                .mapIndexed { index, value ->
                    Cell(index.xFromIndex(), index.yFromIndex(), value)
                }
                .toList()
        }

        return cells!!
    }

    fun getIndex(obj: T) = cellValues.findIndex { obj == it }

    operator fun set(x: Int, y: Int, value: T) {
        cellValues[indexFromXY(x, y)!!] = value
        cells = null
        notifyListeners(getAsCell(x, y))
    }

    operator fun set(index: Int, value: T) {
        cellValues[index] = value
        cells = null
        notifyListeners(getAsCell(index))
    }

    operator fun get(i: Int) = cellValues[i]
    operator fun get(x: Number, y: Number) = cellValues[indexFromXY(x.i, y.i)!!]

    fun getOrNull(i: Int) = cellValues.getOrNull(i)
    fun getOrNull(x: Int, y: Int) = cellValues.getOrNull(indexFromXY(x, y) ?: -1)

    fun getAsCell(x: Int, y: Int): Cell<T> = Cell(x, y, get(x, y))
    fun getAsCell(i: Int): Cell<T> = Cell(i.xFromIndex(), i.yFromIndex(), get(i))

    fun getAsCellOrNull(x: Int, y: Int): Cell<T>? = getOrNull(x, y)?.let { Cell(x, y, it) }
    fun getAsCellOrNull(i: Int): Cell<T>? = getOrNull(i)?.let { Cell(i.xFromIndex(), i.yFromIndex(), it) }

    fun getNeighbors(index: Int) = getNeighbors(getAsCell(index))
    fun getNeighbors(x: Int, y: Int) = getNeighbors(getAsCell(x, y))

    fun getNeighbors(cell: Cell<T>): List<Cell<T>> {
        val x = cell.x
        val y = cell.y

        val left = getAsCellOrNull(x - 1, y)
        val right = getAsCellOrNull(x + 1, y)
        val top = getAsCellOrNull(x, y + 1)
        val bottom = getAsCellOrNull(x, y - 1)

        val topleft = getAsCellOrNull(x - 1, y + 1)
        val topright = getAsCellOrNull(x + 1, y + 1)
        val bottomleft = getAsCellOrNull(x - 1, y - 1)
        val bottomright: Cell<T>? = getAsCellOrNull(x + 1, y - 1)

        return listOfNotNull(left, right, top, bottom, bottomleft, bottomright, topleft, topright)
    }

    fun copy(): Grid<T> {
        return Grid(cols, rows) { this.cellValues[it] }
    }

    override fun toString(): String {
        val sb = StringBuilder()

        val largetString = cellValues.map { it.toString().length }.max() ?: 0

        for (y in 0 until rows) {
            sb.append("[ ")
            for (x in 0 until cols) {
                sb.append("'")
                val contentString = cellValues[x + y * cols].toString()
                sb.append(contentString)
                sb.append("' ")
                for (i in 0..(largetString - contentString.length)) {
                    sb.append(" ")
                }
            }
            sb.append(" ]")
            sb.appendln()
        }

        return sb.toString()
    }

    private fun Int.xFromIndex() = this % cols
    private fun Int.yFromIndex() = (this - xFromIndex()) / cols
    private fun indexFromXY(x: Int, y: Int) = if (x.isXInBound() && y.isXInBound()) x + y * cols else null

    inline fun forEach(function: (Cell<T>) -> Unit) {
        getAllElementsAsCells().forEach(function)
    }

    fun Int.isXInBound(): Boolean = this in 0 until cols
    fun Int.isYInBound(): Boolean = this in 0 until rows

}