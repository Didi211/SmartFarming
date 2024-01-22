package com.elfak.smartfarming.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.elfak.smartfarming.domain.enums.NavigationConstants
import com.elfak.smartfarming.ui.screens.deviceDetailsScreen.DeviceDetailsScreen
import com.elfak.smartfarming.ui.screens.deviceDetailsScreen.DeviceDetailsScreenViewModel
import com.elfak.smartfarming.ui.screens.graphScreen.GraphScreen
import com.elfak.smartfarming.ui.screens.graphScreen.GraphScreenViewModel
import com.elfak.smartfarming.ui.screens.homeScreen.HomeScreen
import com.elfak.smartfarming.ui.screens.homeScreen.HomeScreenViewModel
import com.elfak.smartfarming.ui.screens.listScreen.ListScreen
import com.elfak.smartfarming.ui.screens.listScreen.ListScreenViewModel
import com.elfak.smartfarming.ui.screens.notificationScreen.NotificationScreen
import com.elfak.smartfarming.ui.screens.notificationScreen.NotificationScreenViewModel
import com.elfak.smartfarming.ui.screens.ruleDetailsScreen.RuleDetailsScreen
import com.elfak.smartfarming.ui.screens.ruleDetailsScreen.RuleDetailsScreenViewModel
import com.elfak.smartfarming.ui.screens.settingsScreen.SettingsScreen
import com.elfak.smartfarming.ui.screens.settingsScreen.SettingsScreenViewModel

fun NavGraphBuilder.mainGraph(navController: NavController) {

    navigation(startDestination = Screen.HomeScreen.route, route = Screen.Main.route) {
        composable(Screen.HomeScreen.route) {
            val viewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(
                viewModel = viewModel,
                navigateToGraphReadings = { sensorId ->
                    navController.navigate(Screen.GraphScreen.withArgs(sensorId))
                }
            )
        }
        composable(Screen.GraphScreen.route + "/{sensorId}") {
            val viewModel = hiltViewModel<GraphScreenViewModel>()
            GraphScreen(
                viewModel = viewModel,
                navigateBack = { navController.popBackStack() },
                navigateToDeviceDetails = { deviceId, screenState ->
                    var path = Screen.DeviceDetailsScreen
                        .builder()
                        .addArg(screenState.name)
                        .addOptionalArg("deviceId", deviceId)
                        .build()
                    navController.navigate(path)
                },
                navigateToRuleDetails = { ruleId, screenState ->
                    var path = Screen.RuleDetailsScreen
                        .builder()
                        .addArg(screenState.name)
                    if (ruleId != null) {
                        path.addOptionalArg("ruleId", ruleId)
                    }
                    // NOTE - Would be good to add validation for id and screenState
                    navController.navigate(path.build())
                }
            )
        }
        composable(Screen.ListScreen.route) {
            val viewModel = hiltViewModel<ListScreenViewModel>()
            ListScreen(
                viewModel = viewModel,
                 navigateToDeviceDetails = { deviceId, screenState ->
                     var path = Screen.DeviceDetailsScreen
                         .builder()
                         .addArg(screenState.name)
                         .addOptionalArg("deviceId", deviceId)
                         .build()
                     navController.navigate(path)
                 },
                 navigateToRuleDetails = { ruleId, screenState ->
                     var path = Screen.RuleDetailsScreen
                         .builder()
                         .addArg(screenState.name)
                         .addOptionalArg("ruleId", ruleId)
                         .build()
                     navController.navigate(path)
                 }
            )
        }
        composable(Screen.SettingScreen.route,
            deepLinks = listOf(navDeepLink { uriPattern = NavigationConstants.SettingsUri })
        ) {
            val viewModel = hiltViewModel<SettingsScreenViewModel>()
            SettingsScreen(
                viewModel = viewModel,
            )
        }
        composable(Screen.NotificationScreen.route) {
            val viewModel = hiltViewModel<NotificationScreenViewModel>()
            NotificationScreen(viewModel = viewModel)
        }
        composable(Screen.DeviceDetailsScreen.route + "/{screenState}?deviceId={deviceId}") {
            val viewModel = hiltViewModel<DeviceDetailsScreenViewModel>()
            DeviceDetailsScreen(
                viewModel = viewModel,
                navigateBack = { navController.popBackStack() },
                navigateToRuleDetails = { ruleId, screenState ->
                    var path = Screen.RuleDetailsScreen
                        .builder()
                        .addArg(screenState.name)
                    if (ruleId != null) {
                        path.addOptionalArg("ruleId", ruleId)
                    }
                    // NOTE - Would be good to add validation for id and screenState
                    navController.navigate(path.build())
                }
            )
        }
        composable(Screen.RuleDetailsScreen.route + "/{screenState}?ruleId={ruleId}") {
            val viewModel = hiltViewModel<RuleDetailsScreenViewModel>()
            RuleDetailsScreen(
                viewModel = viewModel,
                navigateBack = { navController.popBackStack() },
                navigateToDeviceDetails = { deviceId, screenState ->
                    var path = Screen.DeviceDetailsScreen
                        .builder()
                        .addArg(screenState.name)
                        .addOptionalArg("deviceId", deviceId)
                        .build()
                    navController.navigate(path)
                },
            )
        }
    }
}

