package com.example.stocksapp.screens.ProductScreen

//@Composable
//fun LineChartScreen() {
//    val steps = 5
//    val pointsData: List<Point> =
//        listOf(
//            Point(0f, 40f),
//            Point(1f, 90f),
//            Point(2f, 0f),
//            Point(3f, 60f),
//            Point(4f, 10f)
//        )
//
//    val xAxisData = AxisData.Builder()
//        .axisStepSize(70.dp)
//        .backgroundColor(Color.Transparent)
//        .steps(pointsData.size - 1)
//        .labelData { i -> i.toString() }
//        .labelAndAxisLinePadding(15.dp)
//        .axisLineColor(MaterialTheme.colorScheme.tertiary)
//        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
//        .build()
//
//    val yAxisData = AxisData.Builder()
//        .steps(steps)
//        .backgroundColor(Color.Transparent)
//        .labelAndAxisLinePadding(20.dp)
//        .labelData { i ->
//            val yScale = 100 / steps
//            (i * yScale).toString()
//        }
//        .axisLineColor(MaterialTheme.colorScheme.tertiary)
//        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
//        .build()
//
//    val lineCharData = LineChartData(
//        linePlotData = LinePlotData(
//            lines = listOf(
//                Line(
//                    dataPoints = pointsData,
//                    lineStyle = LineStyle(
//                        color = MaterialTheme.colorScheme.tertiary,
//                        lineType = LineType.SmoothCurve(isDotted = false)
//                    ),
//                    intersectionPoint = IntersectionPoint(
//                        color = MaterialTheme.colorScheme.tertiary
//                    ),
//                    selectionHighlightPoint = SelectionHighlightPoint(
//                        color = MaterialTheme.colorScheme.primary
//                    ),
//                    shadowUnderLine = ShadowUnderLine(
//                        alpha = 0.5f,
//                        brush = Brush.verticalGradient(
//                            colors = listOf(
//                                MaterialTheme.colorScheme.inversePrimary,
//                                Color.Transparent
//                            )
//                        )
//                    ),
//                    selectionHighlightPopUp = SelectionHighlightPopUp()
//                )
//            )
//        ),
//        xAxisData = xAxisData,
//        yAxisData = yAxisData,
//        gridLines = GridLines(
//            color = MaterialTheme.colorScheme.outlineVariant
//        ),
//        backgroundColor = MaterialTheme.colorScheme.surface
//    )
//
//    LineChart(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(300.dp),
//        lineChartData = lineCharData
//    )
//}
