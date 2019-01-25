package com.thorebenoit.enamel.android.examples

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import com.thorebenoit.enamel.android.dsl.constraints.buildChain
import com.thorebenoit.enamel.android.dsl.constraints.constraints
import com.thorebenoit.enamel.android.dsl.enamelContext
import com.thorebenoit.enamel.android.dsl.enamelDelegate
import com.thorebenoit.enamel.android.dsl.views.backgroundColor
import com.thorebenoit.enamel.android.dsl.views.constraintLayout
import com.thorebenoit.enamel.android.dsl.views.frameLayout
import com.thorebenoit.enamel.android.dsl.views.textView
import com.thorebenoit.enamel.android.matchParent

object _Example_ConstraintLayout_Basic {
    fun example1(ctx: Context) = ctx.enamelContext {
        ctx.constraintLayout {

            val view1 = textView("ABCDEF")
            val view2 = textView("012345") {
                backgroundColor = Color.RED
            }

            constraints {
                view1.connect(
                    TOP to TOP of parentId with 8.dp,
                    START to START of parentId,
                    END to END of parentId
                )

                view2.connect(START to START of view1)
                view2.connect(TOP to BOTTOM of view1 with 8.dp)
            }
        }
    }
}

object _Example_ConstraintLayout_ChainBuilder {
    fun build_chain_basic(ctx: Context) = ctx.enamelContext {
        ctx.constraintLayout {
            val view1 = textView("ABCDEF")
            val view2 = textView("012345") {
                backgroundColor = Color.RED
                textSize = 20f
            }
            val view3 = textView("!@£$%^")

            val spacing = 8.dp

            constraints {
                buildChain {
                    +view1
                    space(spacing)
                    +view2
                    space(spacing)
                    +view3

                    vertical()

                    packed()
                }
            }
        }
    }


    fun build_chain_from_list(ctx: Context) = ctx.enamelContext {
        ctx.constraintLayout {
            val view1 = textView("ABCDEF")
            val view2 = textView("012345") {
                backgroundColor = Color.RED
                textSize = 20f
            }
            val view3 = textView("!@£$%^")

            constraints {
                buildChain {
                    listOf(view1, view2, view3)
                        .buildChain {
                            defaultMargin = 8.dp
                            defaultGoneMargin = 8.dp
                            vertical()
                            packed()
                        }.forEach {
                            it.alignParentStart(50.dp)
                        }
                }
            }
        }
    }
}