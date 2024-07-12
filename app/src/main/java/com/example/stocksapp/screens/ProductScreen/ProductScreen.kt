package com.example.stocksapp.screens.ProductScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.stocksapp.model.CompanyOverview.TimeSeriesMonthy.X19991231
import com.example.stocksapp.navigation.AppScreens
import com.example.stocksapp.ui.theme.LightGreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(navController: NavController, productName: String, productPrice: String,
                  viewModel: ProductScreenViewModel = hiltViewModel()
){
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row {
                      Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Arrow Back",
                                tint = Color.Gray.copy(alpha=0.7f),
                          modifier = Modifier.clickable { navController.navigate(AppScreens.ExploreScreen.name) })

                        Text(text = "Details Screen", modifier = Modifier.padding(start=20.dp),
                            style= TextStyle( fontWeight = FontWeight.Bold, fontSize = 25.sp)
                        )
                        Spacer(modifier=Modifier.width(90.dp))
                        Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search",
                            tint = Color.Gray.copy(alpha=0.7f),
                            modifier = Modifier.padding(start=20.dp).clickable { navController.navigate(AppScreens.SearchScreen.name) })

                    }
                }

            )
        },
        ) { innerPadding ->
        Column(
            Modifier.background(color=MaterialTheme.colorScheme.primaryContainer)
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())) {
            ProductScreenContent( viewModel,navController,productName, productPrice)
        }


    }
}

@Composable
fun ProductScreenContent(viewModel: ProductScreenViewModel,
                         navController: NavController,
                         productName: String, productPrice: String) {
    viewModel.loadCompanyOverview(productName)
    var overview=viewModel.companyOverview
    var loading=viewModel.isloading
    if(loading){
        Row(horizontalArrangement = Arrangement.SpaceBetween ) {
            Text(text = "Loading..")
            LinearProgressIndicator()
        }
    }else{
        Column(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(10.dp)) {
                Column(                    Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color(0xFFD8D8D8),
                        shape = RoundedCornerShape(size = 8.dp)
                    )){
                    Row ()
                    { Row(Modifier.padding(top=10.dp, end=30.dp,start=10.dp), horizontalArrangement = Arrangement.End) {

                        Text(
                            text = productName,
                            modifier = Modifier
                                .padding(start = 20.dp, top = 10.dp, bottom = 16.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp

                        )
                    }
                        Row(Modifier.padding(top=10.dp, end=30.dp,start=10.dp).fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            Text(
                                text = overview!!.Currency+" "+productPrice,
                                modifier = Modifier
                                    .padding(start=20.dp, top=10.dp, bottom=16.dp),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize=25.sp,
                                color = LightGreen

                            )
                        }
                    }
                    Row(Modifier.padding(top=10.dp, end=30.dp,start=10.dp), horizontalArrangement = Arrangement.End) {

                        Text(
                            text = overview!!.Name,
                            modifier = Modifier
                                .padding(start = 20.dp, top = 10.dp, bottom = 16.dp),
                            fontSize = 15.sp

                        )
                    }
                }

                Column(Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color(0xFFD8D8D8),
                        shape = RoundedCornerShape(size = 8.dp)
                    )){
                    viewModel.loadMonthlyTimeSeries(productName)
                    var monthlyTimeSeriesList=viewModel.timeSeriesOverview
                    if(viewModel.isloading2){
                        Row(horizontalArrangement = Arrangement.SpaceBetween ) {
                            Text(text = "Loading..")
                            LinearProgressIndicator()
                        }
                    }else {
                        LineChartScreen(monthlyTimeSeriesList)
                        Row(Modifier.fillMaxWidth().padding(end=30.dp,start=10.dp), horizontalArrangement = Arrangement.Center) {

                            Text(
                                text = "Past 12 Month Stock Prices",
                                modifier = Modifier
                                    .padding(start = 20.dp, bottom = 16.dp),
                                fontSize = 10.sp

                            )
                        }
                    }

                }
                Spacer(Modifier.padding(15.dp))
                Row(
                    Modifier
                        .fillMaxWidth()

                ){Text(text = "About ${overview?.Symbol}",
                    Modifier.padding(start=20.dp, bottom=5.dp), fontSize=20.sp )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color(0xFFD8D8D8),
                            shape = RoundedCornerShape(size = 8.dp)
                        )) {
                    Text(text=overview!!.Description,
                        style=TextStyle(fontSize=15.sp,),
                        modifier = Modifier.padding(20.dp,)
                        )
                    Column(Modifier.padding(20.dp)) {
                        ElevatedButton(onClick = { }) {
                            Text("Industry : ${overview.Industry}",
                                style=TextStyle(fontSize=10.sp,),)
                        }
                        ElevatedButton(onClick = { }) {
                            Text("Sector : ${overview.Sector}",
                                style=TextStyle(fontSize=10.sp,),)
                        }
                    }
                }
                Spacer(Modifier.padding(15.dp))
                Row(
                    Modifier
                        .fillMaxWidth()

                ){Text(text = "Performance", Modifier.padding(start=20.dp, top=20.dp, bottom=5.dp), fontSize=20.sp )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color(0xFFD8D8D8),
                            shape = RoundedCornerShape(size = 8.dp)
                        ))
                {

                    var fundamentals = mutableMapOf(
                        "52 Week Low" to overview?.Currency+" " + overview?.`52WeekLow`,
                        "52 Week High" to overview?.Currency+" " + overview?.`52WeekHigh`,
                        "Profit Margin" to overview?.ProfitMargin,
                    )

                    Column(Modifier.padding(top=5.dp)) {
                        fundamentals.forEach() {
                            Row(modifier = Modifier.padding(start=20.dp, end=10.dp, bottom = 10.dp, top=10.dp)) {
                                Row(Modifier.width(150.dp),) {
                                    Text(
                                        text = it.key,
                                        Modifier.padding(start = 5.dp, bottom = 5.dp),
                                        fontSize = 12.sp
                                    )
                                }

                                Row(Modifier.width(150.dp), horizontalArrangement = Arrangement.End) {
                                    Text(
                                        text = it.value!!,
                                        Modifier.padding(start = 5.dp, bottom = 5.dp),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                            }
                        }
                    }


                }

                Spacer(Modifier.padding(15.dp))
                Row(
                    Modifier
                        .fillMaxWidth()

                ){Text(text = "Fundamentals", Modifier.padding(start=20.dp, top=20.dp, bottom=5.dp), fontSize=20.sp )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color(0xFFD8D8D8),
                            shape = RoundedCornerShape(size = 8.dp)
                        ))
                {

                    var fundamentals = mutableMapOf(
                        "MKt Cap" to overview?.Currency+" " + overview?.MarketCapitalization,
                        "ROE" to overview?.ReturnOnEquityTTM,
                        "P/E Ratio" to overview?.PERatio,
                        "EPS" to overview?.EPS,
                        "P/B Ratio" to overview?.PriceToBookRatio,
                        "Div Yield" to overview?.DividendYield,
                    )

                    Column(Modifier.padding(top=5.dp)) {
                        fundamentals.forEach() {
                            Row(modifier = Modifier.padding(start=20.dp, end=10.dp, bottom = 10.dp, top=10.dp)) {
                                Row(Modifier.width(150.dp),) {
                                    Text(
                                        text = it.key,
                                        Modifier.padding(start = 5.dp, bottom = 5.dp),
                                        fontSize = 12.sp
                                    )
                                }

                                Row(Modifier.width(150.dp), horizontalArrangement = Arrangement.End) {
                                    Text(
                                        text = it.value!!,
                                        Modifier.padding(start = 5.dp, bottom = 5.dp),
                                        fontSize = 12.sp,fontWeight = FontWeight.Bold

                                    )
                                }

                            }
                        }
                    }


                }
            }
        }
    }

}


@Composable
fun LineChartScreen(monthlyTimeSeriesList: Map<String, X19991231>?) {
    val steps = 5
//    val pointsData: List<Point> =
//        listOf(
//            Point(0f, 40f),
//            Point(1f, 90f),
//            Point(2f, 0f),
//            Point(3f, 60f),
//            Point(4f, 10f)
//        )
    val pointsData = mutableListOf<Point>()
//    monthlyTimeSeriesList. {  // Loop from 0 to 4
//        pointsData.add(Point(i.toFloat(), (i * 20).toFloat()))  // Add Point to the list
//    }
    var num=11
    monthlyTimeSeriesList?.forEach() loop@{
        if (num==-1) {
            return@loop // Exit the loop completely
        }
        pointsData.add(Point(num.toFloat(), (it.value.close).toFloat()))
        num--
    }
    var maxValue = pointsData.maxByOrNull { it.y }
    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .backgroundColor(Color.Transparent)
        .steps(pointsData.size - 1)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(15.dp)
        .axisLineColor(MaterialTheme.colorScheme.tertiary)
        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(maxValue?.y!!.toInt()/pointsData!!.size)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            (i ).toString()
        }
        .axisLineColor(MaterialTheme.colorScheme.tertiary)
        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
        .build()

    val lineCharData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    lineStyle = LineStyle(
                        color = MaterialTheme.colorScheme.tertiary,
                        lineType = LineType.SmoothCurve(isDotted = false)
                    ),
                    intersectionPoint = IntersectionPoint(
                        color = MaterialTheme.colorScheme.tertiary
                    ),
                    selectionHighlightPoint = SelectionHighlightPoint(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    shadowUnderLine = ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.inversePrimary,
                                Color.Transparent
                            )
                        )
                    ),
                    selectionHighlightPopUp = SelectionHighlightPopUp()
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(
            color = MaterialTheme.colorScheme.outlineVariant
        ),
        backgroundColor = MaterialTheme.colorScheme.surface
    )

    LineChart(
        modifier = Modifier.padding(top=8.dp)
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineCharData
    )
}